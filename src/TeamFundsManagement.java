import java.util.ArrayList;
import java.util.IntSummaryStatistics;

/**
 * Created by ken12_000 on 10/10/2016.
 */
public class TeamFundsManagement {
    ArrayList<Transaction> transactions;
    Double amount;
    FundAccess fundAccess;

    /**
     * Constructor that creates the FundAccess object and creates transaction list and fund amount
     */
    public TeamFundsManagement(){
        fundAccess = new FundAccess();

        if(fundAccess.getTransactions() != null)
            transactions = fundAccess.getTransactions();
        else
            transactions = new ArrayList<>();

        if(fundAccess.getFunds() != null)
            amount = fundAccess.getFunds();
        else
            amount = 0.0;
    }

    /**
     * Return the list transactions, sorting them in reverse-chronological order.
     */
    public ArrayList<Transaction> getTransactions(){
        // sortEventList();
        return transactions;
    }


    /**
     * Sort the transactions in reverse-chronological order.
     */
    private void sortTransactions(){
        //eventList.sort();
    }

    /**
     * Add a new transaction to the funds log.
     */
    public void addTransaction(Transaction transaction){
        transactions.add(transaction);

        //Rewrites the event list array to the file
        fundAccess.saveTransactions(transactions);
    }

    /**
     * Update the transactions list.
     */
    public void updateTransactions(ArrayList<Transaction> transactions){
        fundAccess.saveTransactions(transactions);
        this.transactions = transactions;
        sortTransactions();
    }

    /**
     * Get the current amount of available funds.
     */
    public Double getFunds(){
        return fundAccess.getFunds();
    }

    /**
     * Update the amount of available funds
     */
    public void updateFunds(Double amount){
        fundAccess.saveFunds(amount);
    }

}
