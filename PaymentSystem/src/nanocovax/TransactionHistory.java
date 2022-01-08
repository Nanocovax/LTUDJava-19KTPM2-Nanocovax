package nanocovax;

import java.util.Date;

public class TransactionHistory {
    String id;
    int expense;
    Date date;

    public void setId(String id) {
        this.id = id;
    }
    public void setExpense(int expense) {
        this.expense = expense;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }
    public int getExpense() {
        return expense;
    }
    public Date getDate() {
        return date;
    }
}
