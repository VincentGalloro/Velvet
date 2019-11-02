package velvet.web.scrapers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class SlowLoadListScraper<T> implements ListScraper<T> {

    protected final WebDriver driver;
    private final String selector;
    private final Function<WebElement, T> itemMap;

    public SlowLoadListScraper(WebDriver driver,
                                  String selector,
                                  Function<WebElement, T> itemMap) {
        this.driver = driver;
        this.selector = selector;
        this.itemMap = itemMap;
    }

    public final ArrayList<T> scrape() {
        List<WebElement> elements = new ArrayList<>();
        int lastElemCount = elements.size();
        long lastCountUpdate = System.currentTimeMillis();

        while(withinItemLimit(lastElemCount) &&
                System.currentTimeMillis() < lastCountUpdate+getLoadTimeout(lastElemCount)){
            preUpdate();
            elements = driver.findElements(By.cssSelector(selector));
            if(elements.size() > lastElemCount){
                lastElemCount = elements.size();
                lastCountUpdate = System.currentTimeMillis();
            }
        }

        postLoad();

        return elements.stream()
                .map(itemMap)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    protected abstract void preUpdate();
    protected abstract void postLoad();

    protected abstract boolean withinItemLimit(int elemCount);
    protected abstract int getLoadTimeout(int currentElemCount);
}