package velvet.automation;

import velvet.main.handlers.EventHandler;

import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TaskScheduler implements Runnable{

    private final LinkedList<Supplier<Task>> tasks;
    private boolean running;

    private final EventHandler<Task> onTaskAdded, onTaskReady;

    public TaskScheduler() {
        tasks = new LinkedList<>();
        onTaskAdded = new EventHandler<>();
        onTaskReady = new EventHandler<>();
    }

    public void addTaskReadyConsumer(Consumer<Task> handler) { onTaskAdded.add(handler); }
    public void addTaskAddedListener(Consumer<Task> handler) { onTaskReady.add(handler); }

    public void start(){
        if(running){
            System.err.println("Cannot start TaskScheduler twice");
            return;
        }

        running = true;
        new Thread(this).start();
    }

    public void run(){
        System.err.println("Task Scheduler is currently out of production");
        /*while(running){
            if(tasks.isEmpty()){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
                continue;
            }

            Supplier<Task> current = tasks.pop();
            Task currentTask = current.get();
            if(currentTask.readyToRun()){
                for(Consumer<Task> consumer : taskReadyConsumers){
                    consumer.accept(current);
                }
            }
            else{
                tasks.add(current);
            }
        }*/
    }
/*
    public synchronized void addTask(Task task){
        for(Consumer<Task> listener : taskAddedListeners){
            listener.accept(task);
        }
    }*/

    public synchronized void kill(){ running = false; }
}
