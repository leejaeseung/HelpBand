import java.util.ArrayList;

public interface JudgeAI_IF {

    String diagnose(UserStatus US);
    void match(ArrayList<WatchingApp> Watchers);
    void checkEmergency();
}
