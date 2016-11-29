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
        sortDirectory();
        return directory;
    }


    /**
     * Sort the members in the event list by name.
     */
    private void sortDirectory(){
        //Insertion sort
        for(int i = 1; i < directory.size(); i++){
            DirectoryMember key = directory.get(i);
            String name = key.getName();
            int k = i - 1;
            while(k >= 0 && directory.get(k).getName().compareToIgnoreCase(name) > 0){
                directory.set(k + 1, directory.get(k));
                k--;
            }
            directory.set(k + 1, key);
        }
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
