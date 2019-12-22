
import java.util.Random;

public class Band2 implements Bands{

    public int getTemperature(){
        Random rd = new Random();
        return rd.nextInt(3) + 36;
    };
    public int getPulse(){
        Random rd = new Random();
        return rd.nextInt(100) + 40;
    };
}