package velvet.web;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import velvet.automation.JobPool;

public abstract class Bot {

    private final JobPool jobPool;
    protected ChromeDriver driver;

    public Bot() {
        jobPool = new JobPool();
        jobPool.createThreads(1); //no parallelism

        jobPool.addJob(() -> {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
                    UnexpectedAlertBehaviour.ACCEPT);
            driver = new ChromeDriver(options);
        });
    }

    //add a job to the queue
    public void addJob(Runnable job){ jobPool.addJob(job); }
    public Runnable shutdown(){
        return () -> {
            System.err.println("Closing Driver");
            driver.quit();
            System.err.println("Killing job pool");
            jobPool.kill();
            onShutdown();
        };
    }

    protected abstract void onShutdown();
}
