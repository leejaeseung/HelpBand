import java.util.ArrayList;

public class UserInfo {

    private String name;
    private int age;
    private int personalNum;
    private String gender;
    private ArrayList<Integer> ProtectorList;

    public UserInfo(String name, int personalNum, String gender,int age){
        this.name = name;
        this.age = age;
        this.personalNum = personalNum;
        this.gender = gender;
    }
    public void addProtector(int ProtectorID){
        this.ProtectorList.add(ProtectorID);
    }
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getPersonalNum() {
        return personalNum;
    }
    public String getGender() {
    	return gender;
    }

    public ArrayList<Integer> getProtectorList() {
        return ProtectorList;
    }
}
