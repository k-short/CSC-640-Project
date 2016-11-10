import java.io.Serializable;

/**
 * Created by ken12_000 on 11/3/2016.
 */
public class TeamEvent implements Serializable{
    private String title;
    private String speedway;
    private String location;
    private String date;
    private String time;
    private String details;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String AMPM;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpeedway() {
        return speedway;
    }

    public void setSpeedway(String speedway) {
        this.speedway = speedway;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public int getMonth(){
        return month;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getAMPM() {
        return AMPM;
    }

    public int getDay(){
        return day;

    }

    public void setDate(int month, int day) {
        this.month = month;
        this.day = day;

        int year;
        if(month == 11 || month == 12)
            year = 2016;
        else
            year = 2017;

        date = month + "/" + day + "/" + year;
    }

    public String getTime() {
        return time;
    }

    public void setTime(int h, int m, String ampm) {
        hour = h;
        minute = m;
        AMPM = ampm;

        String minuteStr = "";
        if(minute == 0)
            minuteStr = "00";
        else if(minute == 5)
            minuteStr = "05";
        else
            minuteStr = minute + "";

        time = hour + ":" + minuteStr + " " + ampm;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
