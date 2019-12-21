import java.util.ArrayList;

public class JudgeAI implements JudgeAI_IF, Provider{

    private ArrayList<Observer> observers = new ArrayList<>();
    private UserStatus myUS;

    public UserStatus diagnose(UserStatus US){
        myUS = US;
        int temper = US.getTemperature();
        int pulse = US.getPulse();
        String symptom = "";

        checkEmergency();

        if(temper >= 37 && temper <= 38 && pulse >= 60 && pulse <= 150){
            US.setSymptom("정상");
            return US;
        }
        if(temper < 37){
            symptom += "저체온 ";
        }
        if(temper > 38){
            symptom += "발열 ";
        }
        if(pulse < 60){
            symptom += "저심박 ";
        }
        if(pulse > 150){
            symptom += "고심박 ";
        }
        US.setSymptom(symptom);
        Notify();
        return US;
    }
    public void checkEmergency(){
        if(myUS.getTemperature() <= 35 || myUS.getPulse() > 300 || myUS.getTemperature() >= 40 || myUS.getPulse() < 30){
            for(Observer o : observers){
                o.alert();
            }
        }
    }
    public void addOb(Observer Ob) { observers.add(Ob); }
    public void subOb(Observer Ob) { observers.remove(Ob); }
    public void Notify(){
        for(Observer o : observers){
            o.updateUserStatus();
        }
    }
}
