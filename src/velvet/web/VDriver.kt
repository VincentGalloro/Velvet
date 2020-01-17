package velvet.web

import org.openqa.selenium.UnexpectedAlertBehaviour
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.CapabilityType
import java.io.File

class VDriver {

    val driver: ChromeDriver

    init{
        val options = ChromeOptions()
        options.addExtensions(File("ublock.crx"))
        options.addArguments("--disable-notifications")
        options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
                UnexpectedAlertBehaviour.ACCEPT)
        driver = ChromeDriver(options)
    }

    fun shutdown(){
        System.err.println("Closing Driver")
        driver.quit()
        System.err.println("Killing Bot")
    }
}