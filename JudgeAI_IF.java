import java.util.ArrayList;

public interface JudgeAI_IF {

    UserStatus diagnose(UserStatus US);
    void match(ArrayList<WatchingApp> Watchers);
    void checkEmergency();
}
