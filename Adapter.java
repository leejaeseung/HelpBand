public class Adapter implements Device{

    private Band myBand;
    private String BN;
    public Adapter(Band myBand, String BN){
        this.myBand = myBand;
        this.BN = BN;
    }
    public int measure(){
        if(BN.equals("meBand")){
            return myBand.meBand() - 30;
        }
        else if(BN.equals("smartWatch")){
            return myBand.smartBand() - 10;
        }
        System.out.println("밴드 이름이 잘못 되었습니다.");
        return -1;
    }
}
