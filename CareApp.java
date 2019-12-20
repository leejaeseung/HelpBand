public class CareApp {

    private String usercode;
    private UserInfo UI;
    private Device myDevice;
    private Band myBand;
    private String BandName;

    public CareApp(String name, int age, int personalNum, String BandName){
        UI = new UserInfo(name, age, personalNum);
        this.BandName = BandName;
    }
    public void StartBioAnalyze(){
        myBand = new Band();
        myDevice = new Adapter(myBand, BandName);


    }
    public void enrolUser(){

    }
}
