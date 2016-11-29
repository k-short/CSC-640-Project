import java.io.Serializable;

public class ExpenseRequest implements Serializable {

    String description;
    Double cost;
    String timeline;
    String priority;


    public ExpenseRequest(String description, Double cost, String timeline, String priority) {
        this.description = description;
        this.cost = cost;
        this.timeline = timeline;
        this.priority = priority;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
