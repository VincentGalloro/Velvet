package core.main.ui.elements;

import core.main.ui.elements.impl.BoxElement;
import core.main.ui.elements.impl.ColumnElement;
import core.main.ui.elements.impl.ImageElement;
import core.main.ui.elements.impl.PaddingElement;
import core.main.ui.elements.impl.RowElement;
import core.main.ui.elements.impl.ScaleElement;
import core.main.ui.elements.impl.TextElement;

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