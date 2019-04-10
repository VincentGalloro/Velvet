
import core.main.Mouse;
import core.main.ui.UIController;
import core.main.ui.UIHandler;
import core.main.ui.composites.BasicScrollBar;
import core.main.ui.elements.ElementFactory;
import core.main.ui.elements.IElement;
import core.main.ui.elements.IElementFactory;
import core.main.ui.elements.impl.BoxElement;
import java.awt.Color;

public class CustomElements implements IElementFactory{
    
    public static class ColorBoxFactory{
        
        private static String[] BAR_NAMES = {"red bar", "green bar", "blue bar", "rounding bar"};
        
        public static IElement create(UIHandler uiHandler){
            UIController controller = uiHandler.loadController("ui/custom_elems/color_box.txt");
            
            BoxElement box = (BoxElement)controller.getElement("box");
            
            BasicScrollBar[] bars = new BasicScrollBar[BAR_NAMES.length];
            for(int i = 0; i < BAR_NAMES.length; i++){ bars[i] = (BasicScrollBar)controller.getElement(BAR_NAMES[i]); }
            
            float[] c = new float[3];
            for(int i = 0; i < 3; i++){
                final int index = i;
                bars[i].addDeltaHandler(d -> {
                    c[index] = (float)d;
                    box.setFillColor(new Color(c[0],c[1],c[2]));
                });
            }
            bars[3].addDeltaHandler(d -> box.setRounding(d*60));
                             
            return controller.getRoot();
        }
    }
    
    private final UIHandler uiHandler;
    private ElementFactory ebf;
    
    public CustomElements(UIHandler uiHandler){
        this.uiHandler = uiHandler;
        ebf = new ElementFactory();
    }
    
    public IElement fromString(String name, Mouse mouse){
        if(name.equals("pulse line")){ return null; }
        if(name.equals("color box")){ return ColorBoxFactory.create(uiHandler); }
        
        return ebf.fromString(name, mouse);
    }
}
