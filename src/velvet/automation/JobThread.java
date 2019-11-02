package velvet.automation;

import java.util.function.Supplier;

public class JobThread implements Runnable{

    private Supplier<Boolean> killSwitch;
    private Supplier<Runnable> jobQueue;
    private Runnable onThreadStop;

    public JobThread(Supplier<Runnable> jobQueue, Supplier<Boolean> killSwitch) {
        this.killSwitch = killSwitch;
        this.jobQueue = jobQueue;
    }

    public synchronized void setOnThreadStop(Runnable r){
        onThreadStop = r;
    }

    private synchronized void onThreadStop(){
        onThreadStop.run();
    }

    public void run(){
        try {
            while (!killSwitch.get()) {
                Runnable job = jobQueue.get();
                if (job == null) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                try {
                    job.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        onThreadStop();
    }
}
