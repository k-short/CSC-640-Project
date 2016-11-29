import java.io.*;
import java.util.ArrayList;

public class TimeAccess {

    /**
     * write entire array of TimeRecords to file
     */
    public static void saveTimeRecords(ArrayList<TimeRecord> timeRecords){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("time_records.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(timeRecords);
            out.close();
            fileOut.close();
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * get entire array of TimeRecords from file
     * @return records in an ArrayList
     */
    public static ArrayList<TimeRecord> getTimeRecords(){
        ArrayList<TimeRecord> timeRecords = null;
        File file = new File("time_records.ser");
        try {
            if(file.exists()) {
                FileInputStream fileIn = new FileInputStream("time_records.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                timeRecords = (ArrayList<TimeRecord>) in.readObject();
                in.close();
                fileIn.close();
            }
        }catch(IOException i) {
            i.printStackTrace();
        }catch(ClassNotFoundException c) {
            System.out.println("TimeRecord class not found");
            c.printStackTrace();
        }

        return timeRecords;
    }
}
