package nanocovax;

public class Main {

    public static void main(String[] args) {
        if (Database.varifyAdminInit())
            Signup.main(args);
        else
	        Login.main(args);
    }
}
