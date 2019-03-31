
package core.main.ui.elements;

import core.main.Mouse;

public interface IElementBuilderFactory {
    
    public ElementBuilder fromString(String name, Mouse mouse);
}
