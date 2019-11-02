package velvet.automation;

public class JobPool {

    private JobQueue queue;
    private JobThread thread;
    private int activeThreadCount;
    private boolean running;

    public JobPool(){
        queue = new JobQueue();
        thread = new JobThread(queue::popJob, () -> !isRunning());
        thread.setOnThreadStop(this::onThreadClose);
    }

    public synchronized void createThreads(int threadCount){
        running = true;
        activeThreadCount += threadCount;
        for(int i = 0; i < threadCount; i++){ new Thread(thread).start(); }
    }

    public void addJob(Runnable job){ queue.addJob(job); }
    public Runnable popJob(){ return queue.popJob(); }

    private synchronized void onThreadClose(){ activeThreadCount--; }
    public synchronized int getActiveThreadCount(){ return activeThreadCount; }

    public synchronized boolean isRunning(){ return running; }
    public synchronized void kill(){ running = false; }
}
