package ir.sajjadyosefi.android.xTubeless.utility.xUtility;

import android.content.Context;
import android.widget.Toast;

import java.util.Arrays;

public class Validation {



    public static void main(String[] args) {
        System.out.println(validatePhoneNumber("09123678522"));
        System.out.println(validatePhoneNumber("09999816652"));
        System.out.println(validatePhoneNumber("09354226320"));
        System.out.println(validatePhoneNumber("09198166520"));
    }

    public static boolean validatePhoneNumber(String phoneNo) {
        String newString = changePersianNumbers(phoneNo.trim());

        //validate phone numbers of format "1234567890"
        if (newString.matches("^09[-.\\s]?\\d{2}[-.\\s]?\\d{3}[-.\\s]?\\d{4}$")) return true;
            //validating phone number with -, . or spaces
//        else if(phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
//            //validating phone number with extension length from 3 to 5
//        else if(phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
//            //validating phone number where area code is in braces ()
//        else if(phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
//            //return false if nothing matches the input
        else return false;

    }

    public static String changePersianNumbers(String old){
        old = old.replace("۰","0");
        old = old.replace("۹","9");
        old = old.replace("۸","8");
        old = old.replace("۷","7");
        old = old.replace("۶","6");
        old = old.replace("۵","5");
        old = old.replace("۴","4");
        old = old.replace("۳","3");
        old = old.replace("۲","2");
        old = old.replace("۱","1");
        return old;
    }

}
