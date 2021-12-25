package nanocovax;

public class HospitalHistory {
    String idUser, time;
    Hospital NDT;

    HospitalHistory() {
        NDT = new Hospital();
    }

    public void setId(String srcId) {
        idUser = srcId;
    }
    public void setTime(String srcTime) {
        time = srcTime;
    }
    public void setNDT(Hospital srcNDT) {
        NDT = srcNDT;
    }

    public String getId() {
        return idUser;
    }
    public String getTime() {
        return time;
    }
    public Hospital getNDT() {
        return NDT;
    }

    public static void main(String[] args) {

    }
}
