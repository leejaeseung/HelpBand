import java.util.Random;

public class Band implements Bands{

    public int getTemperature(){
        Random rd = new Random();
        return rd.nextInt(10) + 30;
    };
    public int getPulse(){
        Random rd = new Random();
        return rd.nextInt(10) + 10;
    };
}