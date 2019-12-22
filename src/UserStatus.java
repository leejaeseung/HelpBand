
public class UserStatus {

    private int temperature;
    private int pulse;
    private String symptom;

    public UserStatus(int temperature, int pulse){
        this.temperature = temperature;
        this.pulse = pulse;
    }
    public int getTemperature() {
        return temperature;
    }

    public int getPulse() {
        return pulse;
    }

    public void setSymptom(String symptom) { this.symptom = symptom;}

    public String getSymptom() { return symptom;}
}
