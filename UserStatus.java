public class UserStatus {

    private int temperature;
    private int pulse;

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
}
