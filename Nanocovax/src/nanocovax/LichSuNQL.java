package nanocovax;

public class LichSuNQL {
    String id_nql, thoigian, hoatdong, id, id_nyp, id_ndt;

    public LichSuNQL() {
    }

    public LichSuNQL(String id_nql, String thoigian, String hoatdong, String id, String id_nyp, String id_ndt) {
        this.id_nql = id_nql;
        this.thoigian = thoigian;
        this.hoatdong = hoatdong;
        this.id = id;
        this.id_nyp = id_nyp;
        this.id_ndt = id_ndt;
    }

    public String getId_nql() {
        return id_nql;
    }

    public void setId_nql(String id_nql) {
        this.id_nql = id_nql;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getHoatdong() {
        return hoatdong;
    }

    public void setHoatdong(String hoatdong) {
        this.hoatdong = hoatdong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_nyp() {
        return id_nyp;
    }

    public void setId_nyp(String id_nyp) {
        this.id_nyp = id_nyp;
    }

    public String getId_ndt() {
        return id_ndt;
    }

    public void setId_ndt(String id_ndt) {
        this.id_ndt = id_ndt;
    }
}
