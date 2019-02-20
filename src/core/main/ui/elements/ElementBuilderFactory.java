package core.main.ui.elements;

import core.main.ui.elements.impl.BoxElement;
import core.main.ui.elements.impl.ColumnElement;
import core.main.ui.elements.impl.ImageElement;
import core.main.ui.elements.impl.PaddingElement;
import core.main.ui.elements.impl.RowElement;
import core.main.ui.elements.impl.ScaleElement;
import core.main.ui.elements.impl.TextElement;

public class ElementBuilderFactory {

    public static ElementBuilder fromString(String s){
        if(s.equals("box")){ return new BoxElement.Builder(); }
        if(s.equals("column")){ return new ColumnElement.Builder(); }
        if(s.equals("image")){ return new ImageElement.Builder(); }
        if(s.equals("padding")){ return new PaddingElement.Builder(); }
        if(s.equals("row")){ return new RowElement.Builder(); }
        if(s.equals("scale")){ return new ScaleElement.Builder(); }
        if(s.equals("text")){ return new TextElement.Builder(); }
        return null;
    }
}