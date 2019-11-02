package velvet.structs;

//a time stamp represents a moment in time
public class TimeStamp {

    private static final int MINUTES_PER_DAY = 60 * 24;

    public static final TimeStamp
            START_OF_DAY = new TimeStamp(0),
            END_OF_DAY = new TimeStamp(MINUTES_PER_DAY);

    //time is stored in minutes since midnight (hours are stored as groups of 60 minutes)
    private final int totalMinutes;

    private TimeStamp(int totalMinutes){
        this.totalMinutes = totalMinutes;
    }

    public TimeStamp(int hours, int minutes){
        this(hours*60 + minutes);
    }

    public TimeStamp(int hours, int minutes, boolean isPM){
        this(hours + (isPM ? 12 : 0), minutes);
    }



    public TimeStamp add(TimeDuration duration){
        return new TimeStamp(totalMinutes + duration.getTotalMinutes());
    }
    public TimeStamp subtract(TimeDuration duration){
        return new TimeStamp(totalMinutes - duration.getTotalMinutes());
    }

    public TimeDuration getTimeBetween(TimeStamp other){
        return new TimeDuration(totalMinutes - other.totalMinutes);
    }

    //returns the hours/minutes represented by this timestamp
    public int getMinutes(){ return totalMinutes%60; }
    public int getHours(){ return totalMinutes/60; }

    //exclusive before/after checks
    public boolean isBefore(TimeStamp timeStamp){
        return totalMinutes < timeStamp.totalMinutes;
    }
    public boolean isAfter(TimeStamp timeStamp){
        return totalMinutes > timeStamp.totalMinutes;
    }

    //inclusive before/after checks
    public boolean isAtOrBefore(TimeStamp timeStamp){
        return totalMinutes <= timeStamp.totalMinutes;
    }
    public boolean isAtOrAfter(TimeStamp timeStamp){
        return totalMinutes >= timeStamp.totalMinutes;
    }

    //get the string as a time of day (ex: 2:00 AM)
    public String toString(){
        int hours = getHours();
        int minutes = getMinutes();
        return (hours%12==0 ? 12 : hours%12) + ":" +
                (minutes<10 ? "0" : "") +
                minutes + " " + (hours < 12 ? "AM" : "PM");
    }

    public int hashCode(){
        return totalMinutes;
    }

    public boolean equals(Object o){
        return o.getClass()==TimeStamp.class && ((TimeStamp)o).totalMinutes==totalMinutes;
    }
}
