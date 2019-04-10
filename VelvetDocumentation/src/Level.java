
import core.main.ui.active.impl.SwapPanel;
import core.main.VGraphics;
import core.main.Velvet;
import core.main.ui.UIController;
import core.main.ui.UIHandler;
import core.main.ui.composites.Button;
import core.main.ui.elements.IContainer;
import core.main.ui.elements.IListContainer;
import java.awt.Point;

public class Level extends Velvet{

    private CustomElements customElements;
    private UIHandler uiHandler;
    private SwapPanel primaryPanel, secondaryPanel;
    
    public Level() { super(new Point(1300, 700)); }

    public void init() {
        uiHandler = new UIHandler(mouse, keyboard);
        customElements = new CustomElements(uiHandler);
        uiHandler.setElementBuilderFactory(customElements);
        
        UIController main = uiHandler.loadController("ui/main.txt");
        uiHandler.setRoot(main);
        primaryPanel = new SwapPanel((IContainer)main.getElement("primary panel"));
        secondaryPanel = new SwapPanel((IContainer)main.getElement("secondary panel"));
        
        main.getElement("logo").addMousePressHandler(() -> { init(); });
        
        main.getNamedElements().filter(e -> e instanceof Button).forEach(b -> {
            UIController panel = uiHandler.loadController("ui/"+b.getName()+"/panel.txt");
            if(panel != null){
                b.addMousePressHandler(() -> {
                    primaryPanel.load((IListContainer)panel.getRoot());
                });
                panel.getNamedElements().forEach(innerB -> {
                    UIController layout = uiHandler.loadController("ui/"+b.getName()+"/"+innerB.getName()+"/layout.txt");
                    if(layout != null){
                        innerB.addMousePressHandler(() -> {
                            secondaryPanel.load((IListContainer)layout.getRoot());
                        });
                    }
                    
                    //temp debug code
                    //automatically open a certain panel by simulating button clicks
                    if(innerB.getName().equals("ui_package")){
                        innerB.onMousePress();
                        innerB.onMouseRelease();
                        b.onMousePress();
                        b.onMouseRelease();
                    }
                });
            }
        });
    }

    public void update() {
        uiHandler.update();
        primaryPanel.update();
        secondaryPanel.update();
    }

    public void render(VGraphics g) {
        uiHandler.render(g);
    }
    
    public static void main(String[] args){
        Velvet.start(new Level(), "Velvet Documentation");
    }
}
