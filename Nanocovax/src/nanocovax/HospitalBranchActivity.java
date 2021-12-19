package nanocovax;

import java.util.Date;

public class HospitalBranchActivity {
    String hospitalId, hospitalName, activity;
    Date date;
    public String getHospitalId() {
        return hospitalId;
    }
    public String getHospitalName() {
        return hospitalName;
    }
    public String getActivity() {
        return activity;
    }
    public Date getDate() {
        return date;
    }
    public void setHospitalId(String srcHospitalId) {
        hospitalId = srcHospitalId;
    }
    public void setHospitalName(String srcHospitalName) {
        hospitalName = srcHospitalName;
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
