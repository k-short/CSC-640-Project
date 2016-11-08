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
    int month;
    int day;

    public TeamEvent(String tl, String s, String l, String dt, String tm, String ds){
        title = tl;
        speedway = s;
        location = l;
        date = dt;
        time = tm;
        details = ds;
    }

    public TeamEvent(){
      //default constructor
    }

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

    public void setDate(int month, int day) {
        this.month = month;
        this.day = day;

        int year;
        if(month == 11 || month == 12)
            year = 2016;
        else
            year = 2017;

        date = month + "\\" + day + "\\" + year;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
