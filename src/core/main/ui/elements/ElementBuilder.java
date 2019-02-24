
package core.main.ui.elements;

public interface ElementBuilder {
    
    public abstract void handleString(String field, String value);
    public abstract IElement get();
}
