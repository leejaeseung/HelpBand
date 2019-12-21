
import java.util.Random;

public class Band2 implements Bands{

    public int getTemperature(){
        Random rd = new Random();
        return rd.nextInt(100) + 300;
    };
    public int getPulse(){
        Random rd = new Random();
        return rd.nextInt(100) + 100;
    };
}