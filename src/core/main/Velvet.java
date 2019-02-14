
package core.main;

import java.awt.Graphics2D;
import java.awt.Point;

public abstract class Velvet {
    
    protected Mouse mouse;
    protected Keyboard keyboard;
    protected Point size;
    
    public Velvet(Point size){
        this.size = size;
    }
    
    protected final void setMouse(Mouse mouse){ this.mouse = mouse; }
    protected final void setKeyboard(Keyboard keyboard){ this.keyboard = keyboard; }
    
    public abstract void update();
    public abstract void render(Graphics2D g);
    
    public final Point getSize(){ return size; }
    
    public static void start(Velvet level, String name){
        Main main = new Main(level, name);
    }
}
