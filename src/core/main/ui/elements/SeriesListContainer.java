
package core.main.ui.elements;

public abstract class SeriesListContainer extends BasicListContainer{
    
    public class Builder extends BasicListContainer.Builder{
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("sep")){ seperation = Double.parseDouble(value); }
        }
    }
    
    protected double seperation;
    
    public final void setSeperation(double s){ seperation = s; }
}
