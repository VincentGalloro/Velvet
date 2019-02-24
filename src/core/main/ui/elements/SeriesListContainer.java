
package core.main.ui.elements;

public abstract class SeriesListContainer extends BasicListContainer{
    
    public static abstract class Builder extends BasicListContainer.Builder{
        
        private final SeriesListContainer slc;
        
        public Builder(SeriesListContainer slc) {
            super(slc);
            this.slc = slc;
        }
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("sep")){ slc.seperation = Double.parseDouble(value); }
        }
    }
    
    protected double seperation;
    
    public final void setSeperation(double s){ seperation = s; }
}
