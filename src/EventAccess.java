import java.io.*;
import java.util.ArrayList;

/**
 * Created by ken12_000 on 11/3/2016.
 */
public class EventAccess {

    /**
     * Insert an event into the event collection.
     * @param eventList Event given.
     */
    public void saveEvents(ArrayList<TeamEvent> eventList){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("events.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(eventList);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in events.ser");
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Get a list of all events in the event collection.
     * @return Events in an ArrayList (not sorted)
     */
    public ArrayList<TeamEvent> getEvents(){
        ArrayList<TeamEvent> events = null;
        File file = new File("events.ser");
        try {
            if(file.exists()) {
                FileInputStream fileIn = new FileInputStream("events.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                events = (ArrayList<TeamEvent>) in.readObject();
                in.close();
                fileIn.close();
            }
        }catch(IOException i) {
            i.printStackTrace();
        }catch(ClassNotFoundException c) {
            System.out.println("Events class not found");
            c.printStackTrace();
        }

        return events;
    }
}
