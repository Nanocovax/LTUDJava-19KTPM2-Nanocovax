package nanocovax;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    static Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        logger.info("Main started");
        if (Database.varifyAdminInit())
            Signup.main(args);
        else
	        Login.main(args);
    }
}
