import java.io.Serializable;

public class IssueRecord implements Serializable {
    int order;
    String description;
    String solution;
    String actionsTaken;
    String timeline;
    String priority;
    String status;

    public IssueRecord(int order, String description, String solution, String actionsTaken, String timeline, String priority, String status) {
        this.order = order;
        this.description = description;
        this.solution = solution;
        this.actionsTaken = actionsTaken;
        this.timeline = timeline;
        this.priority = priority;
        this.status = status;

    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getActionsTaken() {
        return actionsTaken;
    }

    public void setActionsTaken(String actionsTaken) {
        this.actionsTaken = actionsTaken;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
