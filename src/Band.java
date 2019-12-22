import java.util.Random;

public class Band implements Bands{

    public int getTemperature(){
        Random rd = new Random();
        return rd.nextInt(5) + 35;
    };
    public int getPulse(){
        Random rd = new Random();
        return rd.nextInt(300) + 20;
    };
}
