import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class ServerProxy extends Thread implements ServerIF {

    private Server sv;

    InputStream is;
    OutputStream os;
    BufferedReader r;
    BufferedWriter w;
    FileOutputStream fos;
    FileInputStream fis;
    DataInputStream dis;
    DataOutputStream dos;
    ObjectOutputStream ob_sender;

    public ServerProxy(Socket socket,ConcurrentHashMap<String,Socket> Net) throws IOException{
        sv = new Server(socket, Net);
        is = sv.is;
        os = sv.os;
        r = sv.r;
        w = sv.w;
        fos = sv.fos;
        dis = sv.dis;
        dos = sv.dos;
        ob_sender = sv.ob_sender;
    }

    public void run() {
        String typ;
        while(true) {
            try {
                typ = r.readLine();
                if (typ.equals("careApp")) {
                    String choice;
                    choice = r.readLine();
                    if (choice.equals("1")) { //careApp등록
                        try {
                            startEnroll();
                        } catch(SQLException e){
                            System.out.print(e);
                        }
                    } else if (choice.equals("2")) { //careApp진단 작동
                        try {
                            updateStatus();
                        } catch (SQLException e){
                            System.out.print(e);
                        }
                    }
                } else if (typ.equals("watchApp")) {
                    String choice;
                    choice = r.readLine();
                    if (choice.equals("Match")) {
                        try {
                            String protectorCode = r.readLine();
                            String[] userinfo = QH.getUserInfo(protectorCode);
                            if (this.authorCheck(userinfo)) {
                                try {
                                    matchUser(userinfo[0], protectorCode);
                                }catch (SQLException e){
                                    System.out.print(e);
                                }
                            } else {
                                System.out.println("권한이 없습니다.\n");
                                break;
                            }
                        } catch (SQLException e) {
                            System.out.println(e);
                        }
                        //이 유저코드가 데이터베이스에 있는지 확인
                        //case1: 데이터베이스에 없을때
                        //없다고 알림(예외처리)

                        //case2: 데이터베이스 있을 때
                        //보호자 등록시작
                    } else if (choice.equals("getLog")) {
                        try {
                            String[] usercodes = QH.getMatchingByProtectorcode(r.readLine());
                            if (this.authorCheck(usercodes)) {
                                getLog(r.readLine());
                            } else {
                                System.out.println("권한이 없습니다.\n");
                                break;
                            }
                        } catch (SQLException e) {
                            System.out.println(e);
                        }
                        //데이터베이스에서 상태정보를 가져와 보여줌
                    }
                    else if(choice.equals("NewProtectorCode")) {

                        try{
                            startEnrollProtector();
                        } catch (SQLException e){
                            System.out.print(e);
                        } catch (ClassNotFoundException e){
                            System.out.print(e);
                        }
                    }
                } else { // watchApp도아니고 careApp도 아니면 뭐냐
                    System.out.println("@");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void startEnroll() throws IOException, SQLException{
        sv.startEnroll();
    }
    public void updateStatus() throws IOException, SQLException{
        sv.updateStatus();
    }
    public void startEnrollProtector() throws IOException, SQLException, ClassNotFoundException{
        sv.startEnrollProtector();
    }
    public void matchUser(String userCode, String protectorCode) throws IOException, SQLException{
        sv.matchUser(userCode, protectorCode);
    }
    public void getLog(String userCode){
        sv.getLog(userCode);
    }
    public boolean authorCheck(String[] code){
        if(code.length == 0){
            return false;
        }
        else{
            return true;
        }
    }
}
