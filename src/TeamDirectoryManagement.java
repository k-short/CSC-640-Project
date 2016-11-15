import java.util.ArrayList;

/**
 * Created by ken12_000 on 10/10/2016.
 */
public class TeamDirectoryManagement {
    ArrayList<DirectoryMember> directory;
    DirectoryAccess directoryAccess;

    /**
     * Constructor that creates the DirectoryAcceess object and retrieves event list from it.
     */
    public TeamDirectoryManagement(){
        directoryAccess = new DirectoryAccess();

        if(directoryAccess.getDirectory() != null)
            directory = directoryAccess.getDirectory();
        else
            directory = new ArrayList<>();
    }

    /**
     * Return the list of members after sorting them in by name.
     */
    public ArrayList<DirectoryMember> getDirectory(){
        // sortEventList();
        return directory;
    }


    /**
     * Sort the members in the event list by name.
     */
    private void sortDirectory(){
        //sort
    }

    /**
     * Add a new member to the directory array.
     */
    public void addMember(DirectoryMember member){
        directory.add(member);

        //Rewrites the event list array to the file
        directoryAccess.saveDirectory(directory);
    }

    /**
     * Update the directory list
     */
    public void updateDirectory(ArrayList<DirectoryMember> dir){
        directoryAccess.saveDirectory(dir);
        directory = dir;
        sortDirectory();
    }

}
