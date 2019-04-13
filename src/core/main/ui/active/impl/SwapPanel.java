package core.main.ui.active.impl;

import core.main.smooth.motion.Motion;
import core.main.structs.Vector;
import core.main.ui.elements.IContainer;
import core.main.ui.elements.IElement;
import core.main.ui.elements.IListContainer;
import java.util.ArrayList;

public class SwapPanel {
    
    private static class IntroTransition{
        private AlphaTransition alphaTransition;
        private OffsetTransition offsetTransition;
        
        public IntroTransition(IElement e){
            alphaTransition = new AlphaTransition();
            alphaTransition.apply(e);
            alphaTransition.overrideAlpha(0);
            
            offsetTransition = new OffsetTransition(Motion.swish(40));
            offsetTransition.apply(e);
            offsetTransition.overridePos(new Vector(-100, 0));
        }
        
        public void activate(){
            alphaTransition.setAlpha(1);
            offsetTransition.setPos(new Vector());
        }
        
        public void detach(){
            alphaTransition.detach();
            offsetTransition.detach();
        }
    }
    
    private IContainer parent;
    private IListContainer panel;
    private ArrayList<IntroTransition> transitions;
    private int currentTransition, nextUpdate;
    
    public SwapPanel(IContainer parent){
        this.parent = parent;
        this.transitions = new ArrayList<>();
    }
    
    public void load(IListContainer panel){
        if(this.panel != null){ unload(); }
        
        this.panel = panel;
        parent.setElement(panel);
        if(this.panel != null){
            this.panel.getElements().forEach(e -> {
                transitions.add(new IntroTransition(e));
            });
        }
    }
    
    public void unload(){
        parent.setElement(null);
        panel = null;
        for(IntroTransition t : transitions){ t.detach(); }
        transitions.clear();
        currentTransition = 0;
        nextUpdate = 0;
    }
    
    public void update(){
        if(currentTransition < transitions.size()){
            nextUpdate--;
            if(nextUpdate <= 0){
                nextUpdate = 8;
                
                transitions.get(currentTransition).activate();
                currentTransition++;
            }
        }
    }
}
