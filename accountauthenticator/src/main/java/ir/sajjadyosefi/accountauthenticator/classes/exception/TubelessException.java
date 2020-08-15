package ir.sajjadyosefi.accountauthenticator.classes.exception;


import android.util.Log;


public class TubelessException extends Exception{

    public static final int TUBELESS_OPERATION_COMPLETE = 0;
    public static final int TUBELESS_OPERATION_NOT_COMPLETE = 10;
    public static final int TUBELESS_TRY_AGAIN = 11;
    public static final int TUBELESS_CHECK_INPUT_VALUES = 12;
    public static final int TUBELESS_CONTENT_IS_COPIED = 13;
    public static final int TUBELESS_PASSWORD_NOT_CORRECT = 14;
    public static final int TUBELESS_PASSWORD_IS_EMPTY = 15;
    public static final int TUBELESS_USERNAME_IS_EMPTY = 16;


    public static final int PASSWORD_NOT_TRUE = -104;


    public static final int TUBELESS_RESPONSE_BODY_IS_NULL = 2001;
    public static final int TUBELESS_DATABASE_ERROR = 2002;
    public static final int DEVICE_NOT_REGISTER = 2004;


    public static final int NATIONAL_CODE_NOT_TRUE = 1001;
    public static final int NAME_NOT_TRUE = 1002;
    public static final int FAMILY_NOT_TRUE = 1003;
    public static final int FATHER_NOT_TRUE = 1004;


    private String message;
    private int code;



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    // Overrides Exception's getMessage()
    @Override
    public String getMessage(){
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public TubelessException() {

    }

    @Override
    public void printStackTrace() {
//        super.printStackTrace();
//        System.out.println(getMessage());
//        Log.w("myApp", getMessage());
        Log.e("tYafte", getMessage());
    }



    public TubelessException(int errorCode) {
        switch (errorCode){
            case NATIONAL_CODE_NOT_TRUE:{
                message = "sajjad Error : National Code Not true.";
                break;
            }

            case TUBELESS_DATABASE_ERROR:{
                message = "saajjad Error : CRUD database Error";
                break;
            }
            case TUBELESS_RESPONSE_BODY_IS_NULL:{
                message = "sajjad Error : we get null in body of response";
                break;
            }
            case TUBELESS_OPERATION_COMPLETE:{
                message = "با موفقیت انجام شد.";
                break;
            }
            case TUBELESS_CHECK_INPUT_VALUES:{
                message = "مقادریر وارد شده صحیح نیست.";
                break;
            }
            case TUBELESS_CONTENT_IS_COPIED:{
                message = "قبلا این پیام را ارسال کرده اید.";
                break;
            }
            case TUBELESS_PASSWORD_NOT_CORRECT:{
                message = "رمز عبور صحیح نیست";
                break;
            }
            case TUBELESS_PASSWORD_IS_EMPTY:{
                message = "رمز عبور کوتاه است.";
                break;
            }
            case TUBELESS_USERNAME_IS_EMPTY:{
                message = "نام کاربری صحیح نیست.";
                break;
            }
            case TUBELESS_OPERATION_NOT_COMPLETE:{
                message = "انجام نشد.";
                break;
            }
            case PASSWORD_NOT_TRUE:{
                message = "رمز صحیح نیست.";
                break;
            }

            default:{
                message = "sajjad Error";
            }
        }
        code = errorCode;
    }


}