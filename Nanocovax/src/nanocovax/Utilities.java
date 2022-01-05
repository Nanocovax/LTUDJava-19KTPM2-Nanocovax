package nanocovax;

public class Utilities {
    public boolean validateIfOnlyString(String str) {
        str = str.toLowerCase();
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];
            if (!(ch >= 'a' && ch <= 'z')) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateIfOnlyNumber(String str) {
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];
            if (!(ch >= '0' && ch <= '9')) {
                return false;
            }
        }
        return true;
    }

    public static void main(String args[]) {
        String str = "312471727";
        boolean bool = validateIfOnlyNumber(str);
        if(!bool) {
            System.out.println("Given String is invalid");
        }else{
            System.out.println("Given String is valid");
        }
    }
}
