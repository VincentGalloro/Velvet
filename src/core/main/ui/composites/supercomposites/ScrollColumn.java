
package core.main.ui.composites.supercomposites;

import core.main.Mouse;
import core.main.smooth.motion.Motion;
import core.main.structs.Vector;
import core.main.ui.active.impl.OffsetTransition;
import core.main.ui.composites.VScrollBar;
import core.main.ui.elements.IElementBuilder;
import core.main.ui.elements.impl.ColumnElement;
import core.main.ui.elements.impl.RowElement;

public class ScrollColumn extends BasicScrollListContainer{
    
    public ScrollColumn(Mouse mouse){
        super(new RowElement(), new ColumnElement(), new VScrollBar(mouse));
        
        OffsetTransition ot = new OffsetTransition(Motion.swish(60));
        //ot.apply(seriesListContainer);
        addUpdateHandler(ot::update);
        seriesListContainer.addTransformHandler(i -> ot.getTransform());
        
        scroll.addDeltaHandler(d -> ot.setPos(new Vector(0,-d*(seriesListContainer.getSize().y-listSize.getSize().y))));
        addUpdateHandler(at -> scroll.setScrollablePercentage(1 - listSize.getSize().y/seriesListContainer.getSize().y));
    }
    
    public IElementBuilder getBuilder(){ return new Builder(); }
    
    public void setSize(Vector s){ listSize.setSize(s); scroll.setLength(s.y-10); }
}