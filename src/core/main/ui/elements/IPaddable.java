package core.main.ui.elements;

import core.main.structs.GridDirection;

public interface IPaddable extends IElement{

    public void setPadding(double p);
    public void setPadding(double p, GridDirection dir);
}