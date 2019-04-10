package core.main.ui.elements;

import core.main.Mouse;
import core.main.ui.composites.Button;
import core.main.ui.composites.Toggle;
import core.main.ui.elements.impl.BoxElement;
import core.main.ui.elements.impl.CenteredElement;
import core.main.ui.elements.impl.ColumnElement;
import core.main.ui.composites.HScrollBar;
import core.main.ui.composites.supercomposites.ScrollColumn;
import core.main.ui.elements.impl.ImageElement;
import core.main.ui.elements.impl.PaddingElement;
import core.main.ui.elements.impl.RowElement;
import core.main.ui.elements.impl.ScaleElement;
import core.main.ui.elements.impl.LabelElement;
import core.main.ui.elements.impl.TextAreaElement;
import core.main.ui.composites.VScrollBar;

public class ElementFactory implements IElementFactory{

    public IElement fromString(String s, Mouse mouse){
        //base elements
        if(s.equals("box")){ return new BoxElement(); }
        if(s.equals("column")){ return new ColumnElement(); }
        if(s.equals("row")){ return new RowElement(); }
        if(s.equals("image")){ return new ImageElement(); }
        if(s.equals("padding")){ return new PaddingElement(); }
        if(s.equals("size")){ return new BasicSizeable(); }
        if(s.equals("scale")){ return new ScaleElement(); }
        if(s.equals("centered")){ return new CenteredElement(); }
        if(s.equals("label")){ return new LabelElement(); }
        if(s.equals("text area")){ return new TextAreaElement(); }
        if(s.equals("hscrollbar")){ return new HScrollBar(mouse); }
        if(s.equals("vscrollbar")){ return new VScrollBar(mouse); }
        
        //composites
        if(s.equals("button")){ return new Button(); }
        if(s.equals("toggle")){ return new Toggle(); }
        if(s.equals("scroll column")){ return new ScrollColumn(mouse); }
        
        return null;
    }
}