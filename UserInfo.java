import java.util.ArrayList;

public class UserInfo {

    private String name;
    private int age;
    private int personalNum;
    private ArrayList<WatchingApp> ProtectorList;

    public UserInfo(String name, int age, int personalNum){
        this.name = name;
        this.age = age;
        this.personalNum = personalNum;
        ProtectorList = new ArrayList<>();
    }
    public void addProtector(WatchingApp Protector){
        this.ProtectorList.add(Protector);
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

    public ArrayList<WatchingApp> getProtectorList() {
        return ProtectorList;
    }
}
