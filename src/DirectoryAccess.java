import java.io.*;
import java.util.ArrayList;

/**
 * Created by ken12_000 on 11/3/2016.
 */
public class DirectoryAccess {
    /**
     * Insert a member into the directory collection.
     */
    public void saveDirectory(ArrayList<DirectoryMember> directory){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("directory.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(directory);
            out.close();
            fileOut.close();
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Get a list of all members in the directory collection.
     * @return Members in an ArrayList (not sorted)
     */
    public ArrayList<DirectoryMember> getDirectory(){
        ArrayList<DirectoryMember> dir = null;
        File file = new File("directory.ser");
        try {
            if(file.exists()) {
                FileInputStream fileIn = new FileInputStream("directory.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                dir = (ArrayList<DirectoryMember>) in.readObject();
                in.close();
                fileIn.close();
            }
        }catch(IOException i) {
            i.printStackTrace();
        }catch(ClassNotFoundException c) {
            System.out.println("Directory class not found");
            c.printStackTrace();
        }

        return dir;
    }
}
