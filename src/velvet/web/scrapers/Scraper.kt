package velvet.web.scrapers

interface Scraper<T> {

    fun scrape(): T?
}
