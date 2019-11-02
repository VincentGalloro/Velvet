package velvet.automation;

import java.util.ArrayList;
import java.util.function.BooleanSupplier;

public abstract class Task implements Runnable{

    private final ArrayList<Runnable> startedListeners, successListeners, failureListeners, completeListeners;
    private final ArrayList<BooleanSupplier> readyPrerequisites, runPrerequisites;

    public Task(){
        startedListeners = new ArrayList<>();
        successListeners = new ArrayList<>();
        failureListeners = new ArrayList<>();
        completeListeners = new ArrayList<>();
        readyPrerequisites = new ArrayList<>();
        runPrerequisites = new ArrayList<>();
    }

    public final synchronized void addStartedListener(Runnable r){ startedListeners.add(r); }
    public final synchronized void addSuccessListener(Runnable r){ successListeners.add(r); }
    public final synchronized void addFailureListener(Runnable r){ failureListeners.add(r); }
    public final synchronized void addCompleteListener(Runnable r){ completeListeners.add(r); }

    public final synchronized void addReadyPrerequisite(BooleanSupplier s){ readyPrerequisites.add(s); }
    public final synchronized void addRunPrerequisite(BooleanSupplier s){ runPrerequisites.add(s); }

    public final synchronized boolean readyToRun(){
        return readyPrerequisites.stream().allMatch(BooleanSupplier::getAsBoolean);
    }
    public final synchronized boolean shouldRun(){
        return runPrerequisites.stream().allMatch(BooleanSupplier::getAsBoolean);
    }

    private synchronized void onEvent(ArrayList<Runnable> runnables){
        for(Runnable r : runnables){ r.run(); }
    }

    protected abstract boolean performTask();

    public final synchronized void run(){
        onEvent(startedListeners);
        if(!shouldRun()){
            onEvent(completeListeners);
            return;
        }

        boolean success = performTask();

        if (success) { onEvent(successListeners); }
        else { onEvent(failureListeners); }

        onEvent(completeListeners);
    }

    public String getName(){ return "Default Task"; }
}
