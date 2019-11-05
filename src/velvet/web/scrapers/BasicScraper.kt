package velvet.web.scrapers;

import org.openqa.selenium.*;

import java.util.NoSuchElementException;
import java.util.function.Function;

public abstract class BasicScraper<T> extends ScraperImpl<T> {

    private final SearchContext root;
    private final String selector;
    private final Function<WebElement, T> elementMapper;

    public BasicScraper(WebDriver driver,
                        SearchContext root,
                        String selector,
                        Function<WebElement, T> elementMapper) {
        super(driver);
        this.root = root;
        this.selector = selector;
        this.elementMapper = elementMapper;
    }

    public void focus(){}

    protected final T tryScrape() throws NoSuchElementException, StaleElementReferenceException {
        WebElement element = root.findElement(By.cssSelector(selector));
        return elementMapper.apply(element);
    }
}
