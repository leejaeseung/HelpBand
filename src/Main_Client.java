import java.io.IOException;
import java.util.Scanner;

public class Main_Client {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		Bands MyBand =null;
		
		System.out.println("어떤 밴드를 사용하시겠습니까? 1: bandA 2: bandB" );
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		if(str.equals("1")) {
			MyBand = new Band();
		}
		else if(str.equals("2")) {
			MyBand = new Band2();
		}
		else {
			System.out.println("잘못된 입력입니다.");
		}

		CareApp CA = new CareApp(MyBand);
		if(CA.searchLog()){
			try {
				CA.StartBioAnalyze();
			}catch (ClassNotFoundException e){
				System.out.print(e);
			}
		}
		else{
			String name, gender;
			int age, pers_num;
			System.out.print("이름을 입력하시오 : ");
			name = sc.nextLine();
			System.out.print("주민번호를 입력하시오 : ");
			pers_num = sc.nextInt();
			sc.nextLine();
			System.out.print("성별을 입력하시오 : ");
			gender = sc.nextLine();
			System.out.print("나이를 입력하시오 : ");
			age = sc.nextInt();
			CA.enrolUser(name, age, pers_num, gender);
		}
	}
}
