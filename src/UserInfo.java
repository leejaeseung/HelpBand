import java.util.ArrayList;

public class UserInfo {

    private String name;
    private int age;
    private int personalNum;
    private ArrayList<WatchingApp> ProtectorList;
    private String gender;

    public UserInfo(String name, int personalNum, String gender,int age){
        this.name = name;
        this.age = age;
        this.personalNum = personalNum;
        this.gender = gender;
        ProtectorList = new ArrayList<>();
    }
    public void addProtector(WatchingApp Protector) {
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
    public String getGender() {
    	return gender;
    }

    public ArrayList<WatchingApp> getProtectorList() {
            return ProtectorList;
    }
}
