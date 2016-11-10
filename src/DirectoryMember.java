/**
 * Created by ken12_000 on 11/10/2016.
 */
public class DirectoryMember {
    String name;
    String jobTitle;
    String address;
    String phoneNumber;

    public DirectoryMember(String name, String jobTitle, String address, String phoneNumber){
        this.name = name;
        this.jobTitle = jobTitle;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
