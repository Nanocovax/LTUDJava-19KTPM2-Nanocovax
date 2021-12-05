package nanocovax;

import java.util.Date;

public class UserBranchActivity {
    String userId, userName, activity;
    Date date;

    public String getUserID() {
        return userId;
    }
    public String getUserName() {
        return userName;
    }
    public String getActivity() {
        return activity;
    }
    public Date getDate() {
        return date;
    }
    public void setUserId(String srcUserId) {
        userId = srcUserId;
    }
    public void setUserName(String srcUserName) {
        userName = srcUserName;
    }
    public void setActivity(String srcActivity) {
        activity = srcActivity;
    }
    public void setDate(Date srcDate) {
        date = srcDate;
    }

    public static void main(String[] args) {

    }
}
