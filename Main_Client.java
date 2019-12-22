import java.io.IOException;
import java.util.Scanner;

public class Main_Client {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		Bands MyBand;
		
		System.out.println("어떤 밴드를 사용하시겠습니까? 1: bandA 2: bandB" );
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		if(str.equals("1")) {
			MyBand = new Band();
			new CareApp(MyBand);
		}
		else if(str.equals("2")) {
			MyBand = new Band2();
			new CareApp(MyBand);
			
		}
		else {
			System.out.println("잘못된 입력입니다.");
		}
		
	}

}
