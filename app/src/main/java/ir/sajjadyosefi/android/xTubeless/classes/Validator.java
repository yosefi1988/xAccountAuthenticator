package ir.sajjadyosefi.android.xTubeless.classes;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;

import ir.sajjadyosefi.android.xTubeless.R;

/**
 * Created by s.yousefi on 26/03/2018.
 */

public class Validator {

    public static String message = "";



    public boolean isIranianMobileNumber(String s){
        if (s == null){
            return false;
        }else {
            String regexPattern = "^[0][9][0-9]{9,9}$";
            return s.matches(regexPattern);
        }
    }




}
