import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main_Watcher {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Scanner sc = new Scanner(System.in);
		WatchingApp W = new WatchingApp();
		W.write("watchApp");
		W.checkProtectorCode();//여기서 protectorCode가없으면 생성하는작업 ㄱ
		System.out.println("1. User추가  2. 사용자 상태 확인 3. 이전 기록보기");
		String str = sc.nextLine();
		if(str.equals("1")) { //User 추가
			W.write("addUser");
			String userCode;
			System.out.print("사용자의 UserCode를 입력하시오 : ");
			userCode = sc.nextLine();
			W.enrolProtector(userCode);
		}
		else if(str.equals("2")) { //사용자 상태 확인
			W.write("watch");
		}
		else if(str.equals("3")) { //이전 기록 보기
			W.write("getLog");
			
			W.show_userCodes();
			if(W.userCodes.size()!=0) {
				System.out.println("추가한 UserCoder가 없습니다.");		
			}
			else { //UserCode 선택
				String Ucode;
				Ucode = sc.nextLine();
				
				W.write(W.protectorCode);
				W.write(Ucode);
			}
			
		}
		else {
			System.out.println("잘못된 입력입니다.");
		}
		
	}
}
