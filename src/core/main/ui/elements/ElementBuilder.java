
package core.main.ui.elements;

import java.awt.Color;

public interface ElementBuilder {
    
    public abstract void handleString(String field, String value);
    public abstract IElement create();
}
