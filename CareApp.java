public class CareApp {

    private String usercode;
    private UserInfo UI;
    private Device myDevice;
    private Bands myBand;

    public void StartBioAnalyze() throws InterruptedException{
        JudgeAI_IF JA = new JudgeAI();
        UI.addProtector(new WatchingApp(1));
        UI.addProtector(new WatchingApp(2));
        UI.addProtector(new WatchingApp(3));
        JA.match(UI.getProtectorList());
        while(true) {
            JA.diagnose(myDevice.measure(myBand));

            Thread.sleep(1000);
        }
    }
    public void enrolUser(String name, int age, int personalNum, Bands myBand){
        UI = new UserInfo(name, age, personalNum);
        this.myBand = myBand;
        this.myDevice = new Adapter();
    }
}
