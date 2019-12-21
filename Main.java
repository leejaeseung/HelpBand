public class Main {

    public static void main(String[] argv) throws InterruptedException{
        Band mB = new Band();
        CareApp CA = new CareApp("이재승", 24, 1231231, mB);
        CA.StartBioAnalyze();
    }
}
