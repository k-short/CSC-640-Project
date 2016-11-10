import java.util.ArrayList;

/**
 * Created by ken12_000 on 10/10/2016.
 */
public class TeamDirectoryManagement {
    ArrayList<DirectoryMember> directory;
    DirectoryAccess directoryAccess;

    /**
     * Constructor that creates the EventAccess object and retrieves event list from it.
     */
    public TeamDirectoryManagement(){
        directoryAccess = new DirectoryAccess();
        directory = directoryAccess.getDirectory();
    }

    /**
     * Return the list of events after sorting them in reverse-chronological order.
     * @return ArrayList of all events.
     */
    public ArrayList<DirectoryMember> getDirectory(){
        // sortEventList();
        return directory;
    }


    /**
     * Sort the events in the event list by reverse-chronological order.
     */
    private void sortDirectory(){
        //sort
    }

    /**
     * Add a new event to the event array.
     */
    public void addMember(DirectoryMember member){
        directory.add(member);

        //Rewrites the event list array to the file
        directoryAccess.saveDirectory(directory);
    }

    /**
     * Update the event list
     */
    public void updateEventList(ArrayList<DirectoryMember> dir){
        directoryAccess.saveDirectory(dir);
        directory = dir;
        sortDirectory();
    }

}
