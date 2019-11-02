package velvet.structs;

//a range from one point in time to another
public class TimeRange {

    public static final TimeRange SINGLE_DAY = new TimeRange(TimeStamp.START_OF_DAY, TimeStamp.END_OF_DAY);

    private final TimeStamp startTime, endTime;

    public TimeRange(TimeStamp startTime, TimeStamp endTime) {
        //check if times were given in a bad order
        if(startTime.isAfter(endTime)){
            //flip vars
            this.startTime = endTime;
            this.endTime = startTime;
        }
        else {
            //times were given in right order
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }
    public TimeRange(TimeStamp startTime, TimeDuration duration){
        this(startTime, startTime.add(duration));
    }

    public TimeStamp getStartTime() {
        return startTime;
    }
    public TimeStamp getEndTime(){
        return endTime;
    }

    public TimeDuration getDuration(){
        return endTime.getTimeBetween(startTime);
    }

    //checks if the timestamp is anywhere in the range
    public boolean contains(TimeStamp timeStamp){
        return timeStamp.isAtOrAfter(startTime) && timeStamp.isBefore(endTime);
    }

    //checks if the two time-ranges have any overlap
    public boolean overlaps(TimeRange timeRange){
        return startTime.isBefore(timeRange.endTime) && endTime.isAfter(timeRange.startTime);
    }

    //checks if this time-range contains the entire argument time-range
    public boolean contains(TimeRange timeRange){
        return timeRange.startTime.isAtOrAfter(startTime) && timeRange.endTime.isAtOrBefore(endTime);
    }
    //checks if the other time-range contains this time-range
    public boolean withinRange(TimeRange timeRange){
        return startTime.isAtOrAfter(timeRange.startTime) && endTime.isAtOrBefore(timeRange.endTime);
    }

    public String toString(){
        return startTime + " to " + endTime;
    }
}
