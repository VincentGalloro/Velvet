
package core.main.ui.elements;

public abstract class BasicContainer extends BasicElement implements IContainer{

    protected IElement element;
    
    public final void setElement(IElement e) { element = e; }
    public final IElement getElement() { return element; }
    
    public final void containerUpdate(){
        if(element != null){
            element.update(getTransform());
        }
    }
}
