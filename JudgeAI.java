public class JudgeAI implements JudgeAI_IF, Provider{

    private UserStatus US;

    public JudgeAI(int temperature, int pulse){
        US = new UserStatus(temperature, pulse);
    }

    public void diagnose(){

    }
    public void checkEmergency(){

    }
    public void addOb(Observer Ob){

    }
    public void subOb(Observer Ob){

    }
    public void Notify(){

    }
}
