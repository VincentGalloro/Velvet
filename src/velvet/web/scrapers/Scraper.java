package velvet.web.scrapers;

import java.util.Optional;

public interface Scraper<T> {
    Optional<T> scrape();
}
