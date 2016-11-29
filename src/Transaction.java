import java.io.Serializable;
import java.util.Date;

/**
 * Created by ken12_000 on 11/11/2016.
 */
public class Transaction implements Serializable {
    private Double amount;
    private String type;
    private Double remainingFunds;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRemainingFunds() {
        return remainingFunds;
    }

    public void setRemainingFunds(Double remainingFunds) {
        this.remainingFunds = remainingFunds;
    }
}
