import java.io.*;
import java.util.ArrayList;

/**
 * Created by ken12_000 on 11/3/2016.
 */
public class FundAccess {
    /**
     * Insert a transaction record into the funds log
     */
    public void saveTransactions(ArrayList<Transaction> transaction){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("funds_log.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(transaction);
            out.close();
            fileOut.close();
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Update the available funds record
     */
    public void saveFunds(Double amount){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("total_funds.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(amount);
            out.close();
            fileOut.close();
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Get a list of all log records in the transaction collection.
     */
    public ArrayList<Transaction> getTransactions(){
        ArrayList<Transaction> trans = null;
        File file = new File("funds_log.ser");
        try {
            if(file.exists()) {
                FileInputStream fileIn = new FileInputStream("funds_log.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                trans = (ArrayList<Transaction>) in.readObject();
                in.close();
                fileIn.close();
            }
        }catch(IOException i) {
            i.printStackTrace();
        }catch(ClassNotFoundException c) {
            System.out.println("Transaction class not found");
            c.printStackTrace();
        }

        return trans;
    }

    /**
     * Get the amount of total funds available.
     */
    public Double getFunds(){
        Double amount = 0.0;
        File file = new File("total_funds.ser");
        try {
            if(file.exists()) {
                FileInputStream fileIn = new FileInputStream("total_funds.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                amount = (Double) in.readObject();
                in.close();
                fileIn.close();
            }
        }catch(IOException i) {
            i.printStackTrace();
        }catch(ClassNotFoundException c) {
            System.out.println("Double class not found");
            c.printStackTrace();
        }

        return amount;
    }
}
