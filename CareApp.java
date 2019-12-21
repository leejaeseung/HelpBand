public class CareApp {

    private String usercode;
    private UserInfo UI;
    private String BandName;
    private Device myDevice;
    private Bands myBand;

    public CareApp(String name, int age, int personalNum, Bands myBand){
        UI = new UserInfo(name, age, personalNum);
        this.myBand = myBand;
        this.myDevice = new Adapter();
    }
    public void StartBioAnalyze() throws InterruptedException{
        while(true) {
            UserStatus temp = myDevice.measure(myBand);
            System.out.println("체온 : " + temp.getTemperature());
            System.out.println("심박 : " + temp.getPulse());
            Thread.sleep(1000);
        }
    }
    public void enrolUser(){

    }
}
