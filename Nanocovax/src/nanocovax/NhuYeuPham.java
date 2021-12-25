package nanocovax;

import java.util.Comparator;

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

    public static Comparator<NhuYeuPham> IDComparatorDesc = new Comparator<NhuYeuPham>() {

        public int compare(NhuYeuPham s1, NhuYeuPham s2) {
            int id1 = s1.getId_nyp();
            int id2 = s2.getId_nyp();

            //ascending order
            //return id1-id2;

            //descending order
            return id2 - id1;
        }
    };
    public static Comparator<NhuYeuPham> IDComparatorAsc = new Comparator<NhuYeuPham>() {

        public int compare(NhuYeuPham s1, NhuYeuPham s2) {
            int id1 = s1.getId_nyp();
            int id2 = s2.getId_nyp();

            //ascending order
            return id1 - id2;

            //descending order
            //return id2 - id1;
        }
    };


    public static Comparator<NhuYeuPham> DurationComparatorDesc = new Comparator<NhuYeuPham>() {

        public int compare(NhuYeuPham s1, NhuYeuPham s2) {
            int thoihan1 = s1.getThoihan();
            int thoihan2 = s2.getThoihan();

            //ascending order
            //return id1-id2;

            //descending order
            return thoihan2 - thoihan1;
        }
    };
    public static Comparator<NhuYeuPham> DurationComparatorAsc = new Comparator<NhuYeuPham>() {

        public int compare(NhuYeuPham s1, NhuYeuPham s2) {
            int thoihan1 = s1.getThoihan();
            int thoihan2 = s2.getThoihan();

            //ascending order
            return thoihan1 - thoihan2;

            //descending order
            //return thoihan2 - thoihan1;
        }
    };

    public static Comparator<NhuYeuPham> PriceComparatorDesc = new Comparator<NhuYeuPham>() {

        public int compare(NhuYeuPham s1, NhuYeuPham s2) {
            int dongia1 = s1.getDongia();
            int dongia2 = s2.getDongia();

            //ascending order
            //return id1-id2;

            //descending order
            return dongia2 - dongia1;
        }
    };

    public static Comparator<NhuYeuPham> PriceComparatorAsc = new Comparator<NhuYeuPham>() {

        public int compare(NhuYeuPham s1, NhuYeuPham s2) {
            int dongia1 = s1.getDongia();
            int dongia2 = s2.getDongia();

            //ascending order
            return dongia1 - dongia2;

            //descending order
            //return dongia2 - dongia1;
        }
    };
}
