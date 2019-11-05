package velvet.web.scrapers;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import java.util.Optional;

public abstract class ScraperImpl<T> implements Scraper<T>{

    protected final WebDriver driver;

    public ScraperImpl(WebDriver driver) {
        this.driver = driver;
    }

    public abstract void focus();

    public final Optional<T> scrape(){
        try{
            focus();
            return Optional.of(tryScrape());
        }
        catch (NoSuchElementException | StaleElementReferenceException e){
            onScrapeFail();
        }

        return Optional.empty();
    }

    //custom error message ?
    protected void onScrapeFail(){
        System.err.println(getClass().getSimpleName()+" failed to scrape an item");
    }

    protected abstract T tryScrape() throws NoSuchElementException, StaleElementReferenceException;
}
