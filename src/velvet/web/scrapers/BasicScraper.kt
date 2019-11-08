package velvet.web.scrapers

import org.openqa.selenium.*
import java.util.NoSuchElementException

abstract class BasicScraper<T>(driver: WebDriver,
                               private val root: SearchContext = driver,
                               private val selector: String,
                               private val elementMapper: (WebElement)->T) : ScraperImpl<T>(driver) {

    @Throws(NoSuchElementException::class, StaleElementReferenceException::class)
    override fun tryScrape(): T {
        val element = root.findElement(By.cssSelector(selector))
        return elementMapper.invoke(element)
    }
}
