import java.util.ArrayList;

public class JudgeAI implements JudgeAI_IF, Provider{

    private ArrayList<Observer> observers = new ArrayList<>();
    private UserStatus myUS;

    public String diagnose(UserStatus US){
        myUS = US;
        int temper = US.getTemperature();
        int pulse = US.getPulse();
        String symptom = "";
        String ret = "";

        checkEmergency();

        if(temper >= 37 && temper <= 38 && pulse >= 60 && pulse <= 150){
            myUS.setSymptom("정상");
            ret = myUS.getTemperature() + "\n" + myUS.getPulse() + "\n" + myUS.getSymptom();
            return ret;
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
        myUS.setSymptom(symptom);
        Notify();
        ret = myUS.getTemperature() + "\n" + myUS.getPulse() + "\n" + myUS.getSymptom();
        return ret;
    }
    public void checkEmergency(){
        if(myUS.getTemperature() <= 35 || myUS.getPulse() > 300 || myUS.getTemperature() >= 40 || myUS.getPulse() < 30){
            for(Observer o : observers){
                o.alert();
            }
        }
    }
    public void match(ArrayList<WatchingApp> Watchers){
        observers.clear();
        for (WatchingApp w: Watchers) {
            addOb(w);
        }
    }
    public void addOb(Observer Ob) { observers.add(Ob); }
    //public void subOb(Observer Ob) { observers.remove(Ob); }
    public void Notify(){
        for(Observer o : observers){
            o.updateUserStatus(myUS);
        }
    }
}
