import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class CareApp {

	static final int PORT = 2000;
	private Socket socket;
	private OutputStream os;
	private InputStream is;
	private BufferedWriter w;
	private BufferedReader r;
	private ObjectInputStream ob_reader;
	private ArrayList<WatchingApp> ProtectorList;
	private String usercode;
	private Device myDevice;
	private Bands myBand;

	public CareApp(Bands MyBand) throws IOException {
		this.myDevice = new Adapter();
		myBand = MyBand;

		socket = new Socket("localhost", PORT);
		System.out.println("PORT(" + PORT + ")로 접속을 시도합니다.");

		os = socket.getOutputStream();
		is = socket.getInputStream();
		w = new BufferedWriter(new OutputStreamWriter(os));
		r = new BufferedReader(new InputStreamReader(is));
		ob_reader = new ObjectInputStream(is);
	}

	public void StartBioAnalyze() throws InterruptedException, IOException, ClassNotFoundException {
		JudgeAI_IF JA = new JudgeAI();
		String str;
		//유저코드를 서버에 전송한다.
		w.write(usercode + "\n");
		w.flush();
		//ProtectorList를 대기한다.
		ProtectorList = (ArrayList<WatchingApp>)ob_reader.readObject();
		JA.match(ProtectorList);
		while (true) {
			str = JA.diagnose(myDevice.measure(myBand));
			w.write(str + "\n");
			w.flush();
			Thread.sleep(1000);
		}
	}

	public void enrolUser(String name, int age, int personalNum, String gender) {
		try {
			w.write(name + "\n");
			w.flush();
			w.write(age + "\n");
			w.flush();
			w.write(personalNum + "\n");
			w.flush();
			w.write(gender + "\n");
			w.flush();

			usercode = r.readLine();
			try {
				StartBioAnalyze();
			} catch (ClassNotFoundException e){
				System.out.print(e);
			} catch (InterruptedException e2){
				System.out.print(e2);
			}
		} catch(IOException e){
			System.out.print(e);
		}
	}
	public boolean searchLog(){
		try{
			File file = new File("./Log_user.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			usercode = br.readLine();
			w.write("careApp" + "\n");
			w.flush();
			w.write("2\n"); //careApp작동
			w.flush();
			return true;
		} catch (FileNotFoundException e){
			try {
				w.write("careApp" + "\n");
				w.flush();
				w.write("1\n"); //사용자 등록
				w.flush();
			}
			catch (IOException e2){
				System.out.print(e2);
			}
			return false;
		}catch(IOException e){
			return false;
		}
	}
}