package velvet.automation;

import java.util.LinkedList;

public class JobQueue {

    private LinkedList<Runnable> jobs;

    public JobQueue(){
        jobs = new LinkedList<>();
    }

    public synchronized Runnable popJob(){
        if(jobs.isEmpty()){ return null; }
        return jobs.pop();
    }

    public synchronized void addJob(Runnable job){
        jobs.add(job);
    }
}
