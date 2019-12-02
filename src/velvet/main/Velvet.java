
package velvet.main;

import velvet.structs.Position;
import velvet.velements.interact.UIEventHandler;

public abstract class Velvet {

    protected Mouse mouse;
    protected Keyboard keyboard;
    protected UIEventHandler uiEventHandler;
    protected FileDrop fileDrop;
    protected Position size;
    
    public Velvet(Position size){
        this.size = size;
    }
    
    public abstract void init();
    
    protected final void setMouse(Mouse mouse){ this.mouse = mouse; }
    protected final void setKeyboard(Keyboard keyboard){ this.keyboard = keyboard; }
    protected final void setUiEventHandler(UIEventHandler uiEventHandler) { this.uiEventHandler = uiEventHandler; }
    protected final void setFileDrop(FileDrop fileDrop){ this.fileDrop = fileDrop; }
    
    public abstract void update();
    public abstract void render(VGraphics g);

    public abstract void onClose();
    public final Position getSize(){ return size; }
    
    public static void start(Velvet level, String name){
        new Main(level, name);
    }
}
