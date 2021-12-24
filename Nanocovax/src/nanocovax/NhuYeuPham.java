package nanocovax;

public class NhuYeuPham {
    int id_nyp;
    String tengoi;
    int thoihan;
    int dongia, gioihan;

    public NhuYeuPham() {
    }

    public NhuYeuPham(int id_nyp, String tengoi, int thoihan, int dongia, int gioihan) {
        this.id_nyp = id_nyp;
        this.tengoi = tengoi;
        this.thoihan = thoihan;
        this.dongia = dongia;
        this.gioihan = gioihan;
    }

    public int getId_nyp() {
        return id_nyp;
    }

    public void setId_nyp(int id_nyp) {
        this.id_nyp = id_nyp;
    }

    public String getTengoi() {
        return tengoi;
    }

    public void setTengoi(String tengoi) {
        this.tengoi = tengoi;
    }

    public int getThoihan() {
        return thoihan;
    }

    public void setThoihan(int thoihan) {
        this.thoihan = thoihan;
    }

    public int getDongia() {
        return dongia;
    }

    public void setDongia(int dongia) {
        this.dongia = dongia;
    }

    public int getGioihan() {
        return gioihan;
    }

    public void setGioihan(int gioihan) {
        this.gioihan = gioihan;
    }
}
