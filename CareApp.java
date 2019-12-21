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
        JudgeAI_IF JA = new JudgeAI();
        while(true) {
            //System.out.println(JA.diagnose(myDevice.measure(myBand)));

            Thread.sleep(1000);
        }
    }
    public void enrolUser(){

    }
}
