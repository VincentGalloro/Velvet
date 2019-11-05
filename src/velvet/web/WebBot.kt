package velvet.web

import org.openqa.selenium.UnexpectedAlertBehaviour
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.CapabilityType
import velvet.automation.Bot
import velvet.automation.SynchronizedQueue

abstract class WebBot<T>(externalQueue: SynchronizedQueue<(T)->Unit>? = null) : Bot<T>(externalQueue) {

    lateinit var driver: ChromeDriver

    init {
        addJob {
            val options = ChromeOptions()
            options.addArguments("--disable-notifications")
            options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
                    UnexpectedAlertBehaviour.ACCEPT)
            driver = ChromeDriver(options)
        }
    }

    override val shutdown = {
        System.err.println("Closing Driver")
        driver.quit()
        System.err.println("Killing Bot")
        super.shutdown.invoke()
    }
}
