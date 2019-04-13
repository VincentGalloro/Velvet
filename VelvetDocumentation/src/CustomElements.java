
import core.main.Mouse;
import core.main.smooth.SmoothColor;
import core.main.smooth.motion.Motion;
import core.main.smooth.motion.MotionFactory;
import core.main.structs.Vector;
import core.main.ui.UIController;
import core.main.ui.UIHandler;
import core.main.ui.active.IUpdateable;
import core.main.ui.active.impl.OffsetTransition;
import core.main.ui.composites.BasicScrollBar;
import core.main.ui.composites.HScrollBar;
import core.main.ui.elements.ElementFactory;
import core.main.ui.elements.IElement;
import core.main.ui.elements.IElementFactory;
import core.main.ui.elements.impl.BoxElement;
import core.main.ui.elements.impl.LabelElement;
import java.awt.Color;
import java.awt.geom.AffineTransform;

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
    
    public static class SmoothVectorDemoFactory{
        
        private static String[] MOTION_NAMES = {"Linear", "Swish", "Overshoot", "S-Curve"};
        private static MotionFactory[] MOTIONS = {Motion.linear(60), Motion.swish(90), Motion.overshoot(120), Motion.scruve(90)};
        
        public static IElement create(UIHandler uiHandler){
            UIController controller = uiHandler.loadController("ui/custom_elems/smooth_vector_demo.txt");
            
            BoxElement box = (BoxElement)controller.getElement("box");
            LabelElement label = (LabelElement)controller.getElement("label");
            
            OffsetTransition ot = new OffsetTransition();
            ot.apply(box);
            ot.overridePos(new Vector(100,0));
            
            box.addUpdateHandler(new IUpdateable(){
                private boolean left = true;
                private int currentMotion = 0, nextMotion = 0;
                public void update(AffineTransform at){
                    if(ot.atTarget()){
                        nextMotion--;
                        if(nextMotion <= 0){
                            left = !left;
                            ot.setMotionFactory(MOTIONS[currentMotion]);
                            ot.setPos(new Vector(left?100:400,0));
                            if(left){
                                nextMotion = 60;
                                currentMotion = (currentMotion+1)%MOTIONS.length;
                            }
                            else{
                                label.setText(MOTION_NAMES[currentMotion]+" Motion");
                            }
                        }
                    }
                }
            });
            
            return controller.getRoot();
        }
    }
    
    public static class SmoothColorDemoFactory{
            
        public static IElement create(UIHandler uiHandler){
            UIController controller = uiHandler.loadController("ui/custom_elems/smooth_color_demo.txt");
            
            BoxElement box = (BoxElement)controller.getElement("box");
            SmoothColor color = new SmoothColor(Motion.linear(30));
            box.addUpdateHandler(at -> {
                color.update();
                box.setFillColor(color.getSmooth());
            });
            
            controller.getElement("red").addMousePressHandler(() -> color.setColor(new Color(255, 150, 150)));
            controller.getElement("yellow").addMousePressHandler(() -> color.setColor(new Color(255, 255, 150)));
            controller.getElement("green").addMousePressHandler(() -> color.setColor(new Color(150, 255, 150)));
            controller.getElement("blue").addMousePressHandler(() -> color.setColor(new Color(150, 180, 255)));
            
            HScrollBar speedBar = (HScrollBar)controller.getElement("speed");
            speedBar.addDeltaHandler(d -> color.setMotionFactory(Motion.linear((int)((1-d)*100) + 15)));
            
            return controller.getRoot();
        }
    }
    
    private final UIHandler uiHandler;
    private final ElementFactory ebf;
    
    public CustomElements(UIHandler uiHandler){
        this.uiHandler = uiHandler;
        ebf = new ElementFactory();
    }
    
    public IElement fromString(String name, Mouse mouse){
        if(name.equals("pulse line")){ return null; }
        if(name.equals("color box")){ return ColorBoxFactory.create(uiHandler); }
        if(name.equals("smooth vector demo")){ return SmoothVectorDemoFactory.create(uiHandler); }
        if(name.equals("smooth color demo")){ return SmoothColorDemoFactory.create(uiHandler); }
        
        return ebf.fromString(name, mouse);
    }
}
