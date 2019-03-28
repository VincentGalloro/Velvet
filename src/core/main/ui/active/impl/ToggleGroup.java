package core.main.ui.active.impl;

import core.main.ui.elements.IToggleable;
import java.util.ArrayList;

public class ToggleGroup {

    private ArrayList<IToggleable> toggles;
    private Integer activeIndex;
    
    public ToggleGroup(){
        toggles = new ArrayList<>();
    }
    
    public Integer getActiveIndex(){ return activeIndex; }
    public IToggleable getActive(){ return activeIndex==null ? null : toggles.get(activeIndex); }
    
    public void addToggleable(IToggleable toggle){
        int index = toggles.size();
        toggle.addToggleOnHandler(() -> setActive(index));
        toggle.addToggleOffHandler(() -> { if(activeIndex == index){ activeIndex = null; } });
        toggles.add(toggle);
    }
    
    private void setActive(int index){
        activeIndex = index;
        for(int i = 0; i < toggles.size(); i++){ 
            if(i == index){ continue; }
            toggles.get(i).setToggle(false); 
        }
    }
}