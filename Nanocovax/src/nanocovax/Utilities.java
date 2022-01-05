package nanocovax;

public class Utilities {
    public static boolean validateRelatedPerson(String status, String relatedPerson) {
        if (status.equals("F0")) {
            if (!relatedPerson.isEmpty())
                return false;
        }
        else if (status.contains("F")) {
            if (relatedPerson.isEmpty())
                return false;
            String statusRP = Database.getUserStatus(relatedPerson);
            if (statusRP.isEmpty())
                return false;
            if (statusRP.contains("F")) {
                if (Integer.parseInt(String.valueOf(statusRP.charAt(1))) >= Integer.parseInt(String.valueOf(status.charAt(1))))
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
        String str = "F3";
        boolean bool = validateRelatedPerson(str, "666666666");
        if(!bool) {
            System.out.println("Invalid!");
        }else{
            System.out.println("Valid!");
        }
    }
}
