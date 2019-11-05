package velvet.web.scrapers;

import java.util.ArrayList;

public interface ListScraper<T> {
    ArrayList<T> scrape();
}
