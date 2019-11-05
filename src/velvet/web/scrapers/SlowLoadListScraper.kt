package velvet.web.scrapers

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import java.util.*
import java.util.function.Function

abstract class SlowLoadListScraper<T>(private val driver: WebDriver,
                                      private val selector: String,
                                      private val itemMap: Function<WebElement, T>) : ListScraper<T> {

    override fun scrape(): List<T> {
        var elements: List<WebElement> = ArrayList()
        var lastElemCount = elements.size
        var lastCountUpdate = System.currentTimeMillis()

        while (withinItemLimit(lastElemCount) && System.currentTimeMillis() < lastCountUpdate + getLoadTimeout(lastElemCount)) {
            preUpdate()
            elements = driver.findElements(By.cssSelector(selector))
            if (elements.size > lastElemCount) {
                lastElemCount = elements.size
                lastCountUpdate = System.currentTimeMillis()
            }
        }

        postLoad()

        return elements.map { itemMap.apply(it) }
    }

    protected abstract fun preUpdate()
    protected abstract fun postLoad()

    protected abstract fun withinItemLimit(elemCount: Int): Boolean
    protected abstract fun getLoadTimeout(currentElemCount: Int): Int
}