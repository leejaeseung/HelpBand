public class Adapter implements Device{

    public UserStatus measure(Bands myBand){
        return new UserStatus(myBand.getTemperature(), myBand.getPulse());
    }
}