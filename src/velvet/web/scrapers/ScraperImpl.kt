package velvet.web.scrapers

import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver

abstract class ScraperImpl<T>(protected val driver: WebDriver) : Scraper<T> {

    open fun focus(){}

    override fun scrape(): T? {
        try {
            focus()
            return tryScrape()
        } catch (e: NoSuchElementException) {
            onScrapeFail()
        } catch (e: StaleElementReferenceException) {
            onScrapeFail()
        }

        return null
    }

    //custom error message ?
    protected fun onScrapeFail() {
        System.err.println(javaClass.simpleName + " failed to scrape an item")
    }

    @Throws(NoSuchElementException::class, StaleElementReferenceException::class)
    protected abstract fun tryScrape(): T
}
