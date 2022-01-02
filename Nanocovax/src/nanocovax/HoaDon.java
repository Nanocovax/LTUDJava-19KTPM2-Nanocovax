package nanocovax;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;

public class HoaDon {
    String id, date, cost, debt;

    public HoaDon() {
    }

    public HoaDon(String id, String date, String cost, String debt) {
        this.id = id;
        this.date = date;
        this.cost = cost;
        this.debt = debt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDebt() {
        return debt;
    }

    public void setDebt(String debt) {
        this.debt = debt;
    }

    public static Comparator<HoaDon> IDComparatorDesc = new Comparator<HoaDon>() {

        public int compare(HoaDon s1, HoaDon s2) {
            int id1 = Integer.valueOf(s1.getId());
            int id2 = Integer.valueOf(s2.getId());

            //ascending order
            //return id1-id2;

            //descending order
            return id2 - id1;
        }
    };
    public static Comparator<HoaDon> IDComparatorAsc = new Comparator<HoaDon>() {

        public int compare(HoaDon s1, HoaDon s2) {
            int id1 = Integer.valueOf(s1.getId());
            int id2 = Integer.valueOf(s2.getId());

            //ascending order
            return id1 - id2;

            //descending order
            //return id2 - id1;
        }
    };


    public static Comparator<HoaDon> DateComparatorDesc = new Comparator<HoaDon>() {
        public int compare(HoaDon s1, HoaDon s2) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date thoihan1 = null;
            Date thoihan2 = null;
            try {
                thoihan1 = formatter.parse(s1.getDate());
                thoihan2 = formatter.parse(s2.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //descending order
            if (thoihan2.after(thoihan1))
                return 1;
            else return -1;//ascending order
        }
    };

    public static Comparator<HoaDon> DateComparatorAsc = new Comparator<HoaDon>() {
        public int compare(HoaDon s1, HoaDon s2) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date thoihan1 = null;
            Date thoihan2 = null;
            try {
                thoihan1 = formatter.parse(s1.getDate());
                thoihan2 = formatter.parse(s2.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }


            if (thoihan2.after(thoihan1))//ascending order
                return -1;
            else return 1;//descending order
        }
    };

    public static Comparator<HoaDon> DebtComparatorDesc = new Comparator<HoaDon>() {

        public int compare(HoaDon s1, HoaDon s2) {
            int dongia1 =Integer.parseInt(s1.getDebt()) ;
            int dongia2 =Integer.parseInt(s2.getDebt()) ;

            //ascending order
            //return id1-id2;

            //descending order
            return dongia2 - dongia1;
        }
    };

    public static Comparator<HoaDon> DebtComparatorAsc = new Comparator<HoaDon>() {

        public int compare(HoaDon s1, HoaDon s2) {
            int dongia1 =Integer.parseInt(s1.getDebt()) ;
            int dongia2 =Integer.parseInt(s2.getDebt()) ;

            //ascending order
            return dongia1 - dongia2;

            //descending order
            //return dongia2 - dongia1;
        }
    };
}
