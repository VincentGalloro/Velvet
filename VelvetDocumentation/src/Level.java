
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
        customElements = new CustomElements();
        uiHandler = new UIHandler(mouse, keyboard);
        uiHandler.setElementBuilderFactory(customElements);
        
        UIController main = uiHandler.loadController("ui/main.txt");
        uiHandler.setRoot(main);
        primaryPanel = new SwapPanel((IContainer)main.getElement("primary panel"));
        secondaryPanel = new SwapPanel((IContainer)main.getElement("secondary panel"));
        
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
