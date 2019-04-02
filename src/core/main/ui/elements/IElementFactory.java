
package core.main.ui.elements;

import core.main.Mouse;

public interface IElementFactory {
    
    public IElement fromString(String name, Mouse mouse);
}
