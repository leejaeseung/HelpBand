import java.util.ArrayList;

public class UserInfo {

    private String name;
    private int age;
    private int personalNum;
    private ArrayList<Integer> ProtectorList;

    public UserInfo(String name, int age, int personalNum){
        this.name = name;
        this.age = age;
        this.personalNum = personalNum;
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

    public ArrayList<Integer> getProtectorList() {
        return ProtectorList;
    }
}
