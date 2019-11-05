package velvet.web.scrapers

interface ListScraper<T> {

    fun scrape(): List<T>
}
