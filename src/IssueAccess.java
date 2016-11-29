import java.io.*;
import java.util.ArrayList;

public class IssueAccess {

    /**
     * write entire array of IssueRecords to file
     */
    public static void saveIssueRecords(ArrayList<IssueRecord> issueRecords){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("issue_records.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(issueRecords);
            out.close();
            fileOut.close();
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * get entire array of IssueRecords from file
     * @return records in an ArrayList
     */
    public static ArrayList<IssueRecord> getIssueRecords(){
        ArrayList<IssueRecord> issueRecords = null;
        File file = new File("issue_records.ser");
        try {
            if(file.exists()) {
                FileInputStream fileIn = new FileInputStream("issue_records.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                issueRecords = (ArrayList<IssueRecord>) in.readObject();
                in.close();
                fileIn.close();
            }
        }catch(IOException i) {
            i.printStackTrace();
        }catch(ClassNotFoundException c) {
            System.out.println("IssueRecord class not found");
            c.printStackTrace();
        }

        return issueRecords;
    }
}
