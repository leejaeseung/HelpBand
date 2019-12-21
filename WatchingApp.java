public class WatchingApp implements Observer{

    private int usercode;
    private UserStatus myUS;
    private int mynum;

    public WatchingApp(int num){
        mynum = num;
    }

    public void enrolProtector(){

    }
    public void display(){
        System.out.println("보호자 " + mynum);
        System.out.println("체온 : " + myUS.getTemperature());
        System.out.println("맥박 : " + myUS.getPulse());
        System.out.println("증상 : " + myUS.getSymptom());
    }
    public void updateUserStatus(UserStatus US){
        myUS = US;
        display();
    }
    public void alert(){
        System.out.println("비상!비상!비상!!!!!!!!");
    }
}
