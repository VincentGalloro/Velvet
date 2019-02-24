package core.main.ui.elements;

import java.awt.image.BufferedImage;

public interface IImageable extends IElement{

    public void setImage(BufferedImage i);
    public BufferedImage getImage();
}