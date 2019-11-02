package velvet.structs;

public class TimeDuration {

    public static final TimeDuration NONE = new TimeDuration(0),
            ONE_HOUR = new TimeDuration(60);

    private final int totalMinutes;

    public TimeDuration(int totalMinutes) {
        //correct for negative time durations
        this.totalMinutes = Math.abs(totalMinutes);
    }

    public TimeDuration(int hours, int minutes){
        this(hours*60 + minutes);
    }

    public int getTotalMinutes(){ return totalMinutes; }

    //returns the hours/minutes represented by this timestamp
    public int getMinutes(){ return totalMinutes%60; }
    public int getHours(){ return totalMinutes/60; }

    //get the string as a duration of time (ex: 4h 25m)
    public String toString(){
        int hours = getHours();
        int minutes = getMinutes();
        return (hours>0 ? hours + "h " : "") + (minutes>0 || hours==0 ? minutes + "m" : "");
    }
}
