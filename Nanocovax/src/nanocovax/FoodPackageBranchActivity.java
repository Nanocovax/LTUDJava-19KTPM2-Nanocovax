package nanocovax;

import java.util.Date;

public class FoodPackageBranchActivity {
    String fpId, fpName, activity;
    Date date;
    public String getFPID() {
        return fpId;
    }
    public String getFPName() {
        return fpName;
    }
    public String getActivity() {
        return activity;
    }
    public Date getDate() {
        return date;
    }
    public void setFPId(String srcFPId) {
        fpId = srcFPId;
    }
    public void setFPName(String srcFPName) {
        fpName = srcFPName;
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
