import java.io.IOException;
import java.sql.SQLException;

public interface ServerIF {
    QueryHandler QH = new QueryHandler();

    void startEnroll() throws IOException, SQLException;
    void updateStatus() throws IOException, SQLException;
    void startEnrollProtector() throws IOException, SQLException, ClassNotFoundException;
    void matchUser(String userCode, String protectorCode)throws IOException, SQLException;
    void getLog(String protectorCode);
}
