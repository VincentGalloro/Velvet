
package core.main;

import java.awt.Graphics2D;
import java.awt.Point;

public interface ILevel {
    
    public void setMouse(Mouse mouse);
    public void setKeyboard(Keyboard keyboard);
    
    public void update();
    public void render(Graphics2D g);
    
    public Point getSize();
}
