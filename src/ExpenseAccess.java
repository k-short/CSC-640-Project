import java.io.*;
import java.util.ArrayList;

public class ExpenseAccess {

    /**
     * write entire array of ExpenseRequests to file
     */
    public static void saveExpenseRequests(ArrayList<ExpenseRequest> ExpenseRequests){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("expense_requests.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(ExpenseRequests);
            out.close();
            fileOut.close();
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * get entire array of ExpenseRequests from file
     * @return records in an ArrayList
     */
    public static ArrayList<ExpenseRequest> getExpenseRequests(){
        ArrayList<ExpenseRequest> expenseRequests = null;
        File file = new File("expense_requests.ser");
        try {
            if(file.exists()) {
                FileInputStream fileIn = new FileInputStream("expense_requests.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                expenseRequests = (ArrayList<ExpenseRequest>) in.readObject();
                in.close();
                fileIn.close();
            }
        }catch(IOException i) {
            i.printStackTrace();
        }catch(ClassNotFoundException c) {
            System.out.println("ExpenseRequest class not found");
            c.printStackTrace();
        }

        return expenseRequests;
    }
}
