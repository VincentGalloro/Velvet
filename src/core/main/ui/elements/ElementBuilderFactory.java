package core.main.ui.elements;

import core.main.Mouse;
import core.main.ui.composites.Button;
import core.main.ui.composites.Toggle;
import core.main.ui.elements.impl.BoxElement;
import core.main.ui.elements.impl.CenteredElement;
import core.main.ui.elements.impl.ColumnElement;
import core.main.ui.elements.impl.HScrollElement;
import core.main.ui.elements.impl.ImageElement;
import core.main.ui.elements.impl.PaddingElement;
import core.main.ui.elements.impl.RowElement;
import core.main.ui.elements.impl.ScaleElement;
import core.main.ui.elements.impl.LabelElement;
import core.main.ui.elements.impl.TextAreaElement;
import core.main.ui.elements.impl.VScrollElement;

public class ElementBuilderFactory {

    public static ElementBuilder fromString(String s, Mouse mouse){
        //base elements
        if(s.equals("box")){ return new BoxElement.Builder(); }
        if(s.equals("column")){ return new ColumnElement.Builder(); }
        if(s.equals("row")){ return new RowElement.Builder(); }
        if(s.equals("image")){ return new ImageElement.Builder(); }
        if(s.equals("padding")){ return new PaddingElement.Builder(); }
        if(s.equals("scale")){ return new ScaleElement.Builder(); }
        if(s.equals("centered")){ return new CenteredElement.Builder(); }
        if(s.equals("label")){ return new LabelElement.Builder(); }
        if(s.equals("textarea")){ return new TextAreaElement.Builder(); }
        if(s.equals("hscrollbar")){ return new HScrollElement.Builder(mouse); }
        if(s.equals("vscrollbar")){ return new VScrollElement.Builder(mouse); }
        
        //composites
        if(s.equals("button")){ return new Button.Builder(); }
        if(s.equals("toggle")){ return new Toggle.Builder(); }
        
        return null;
    }
}