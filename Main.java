public class Main {

    public static void main(String[] argv) throws InterruptedException{
        Band mB = new Band();
        //Band2 mB = new Band2();
        CareApp CA = new CareApp();
        CA.enrolUser("이재승", 24, 1231231, mB);
        CA.StartBioAnalyze();
        WatchingApp W1 = new WatchingApp(1);
    }
}
