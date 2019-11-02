package velvet.web.elements;

import org.openqa.selenium.WebElement;

public abstract class BasicElement {

    protected final WebElement element;

    public BasicElement(WebElement element) {
        this.element = element;
    }
}
