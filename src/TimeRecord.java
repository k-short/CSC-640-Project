import java.io.Serializable;

public class TimeRecord implements Serializable {
    String eventName;
    String eventDate;
    int lapNum;
    Double lapTime;

    public TimeRecord(String eventName, String eventDate, int lapNum, Double lapTime) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.lapNum = lapNum;
        this.lapTime = lapTime;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public int getLapNum() {
        return lapNum;
    }

    public void setLapNum(int lapNum) {
        this.lapNum = lapNum;
    }

    public Double getLapTime() {
        return lapTime;
    }

    public void setLapTime(Double lapTime) {
        this.lapTime = lapTime;
    }
}
