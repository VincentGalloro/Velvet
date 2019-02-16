package core.main.ui.elements.impl;

import core.main.ui.elements.IElement;

public class ElementFactory {

    public static IElement fromString(String s){
        if(s.equals("box")){ return new BoxElement(); }
        if(s.equals("column")){ return new ColumnElement(); }
        if(s.equals("image")){ return new ImageElement(); }
        if(s.equals("padding")){ return new PaddingElement(); }
        if(s.equals("row")){ return new RowElement(); }
        if(s.equals("scale")){ return new ScaleElement(); }
        if(s.equals("text")){ return new TextElement(); }
        return null;
    }
}