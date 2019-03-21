package core.main.ui.active;

import core.main.VGraphics;

public interface IRenderable {

    public void preRender(VGraphics g);
    public void postRender(VGraphics g);
}