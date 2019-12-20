import java.util.Random;

public class Band {

    public int meBand(){
        Random rd = new Random();
        return rd.nextInt(10) + 30;          //1번 Band는 30~40 의 값을 보냄
    }
    public int smartBand(){
        Random rd = new Random();
        return rd.nextInt(10) + 10;     //2번 Band는 10~20의 값을 보냄
    }
}
