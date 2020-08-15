package ir.sajjadyosefi.android.xTubeless.classes.model.exception;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import ir.sajjadyosefi.android.xTubeless.R;
import ir.sajjadyosefi.android.xTubeless.classes.model.response.ServerResponseBase;
import ir.sajjadyosefi.android.xTubeless.classes.model.response.ServerResponseBase;


public class TubelessException extends Exception{

    public static final int TUBELESS_OPERATION_COMPLETE = 0;
    public static final int TUBELESS_OPERATION_NOT_COMPLETE = 10;
    public static final int TUBELESS_TRY_AGAIN = 11;
    public static final int TUBELESS_CHECK_INPUT_VALUES = 12;
    public static final int TUBELESS_CONTENT_IS_COPIED = 13;
    public static final int TUBELESS_PASSWORD_NOT_CORRECT = 14;
    public static final int TUBELESS_PASSWORD_IS_EMPTY = 15;


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


    @Override
    public String getMessage(){
        return message;
    }

    public TubelessException() {

    }

    @Override
    public void printStackTrace() {
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
            case TUBELESS_OPERATION_NOT_COMPLETE:{
                message = "انجام نشد.";
                break;
            }

            default:{
                message = "sajjad Error";
            }
        }
        code = errorCode;
    }

    public void handleServerMessage(Context mContext , ServerResponseBase responseX) {
        try {
            assert mContext == null;
            int resID = mContext.getResources().getIdentifier("error_message_" + (responseX.getTubelessException().getCode() * -1), "string", mContext.getPackageName());
            if (resID == 0) {
                Toast.makeText(mContext,"یک خطای پیش بینی نشده",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(mContext,"یک خطای پیش بینی نشده",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if (responseX != null) {
            handleServerMessage(mContext,responseX.getTubelessException().getCode());
        }else {
            Toast.makeText(mContext, mContext.getResources().getString(R.string.server_not_response),Toast.LENGTH_SHORT).show();
        }
    }

    public static void handleServerMessage(Context mContext, int code) {
        try {
            int resID = mContext.getResources().getIdentifier("error_message_" + code, "string", mContext.getPackageName());
            Toast.makeText(mContext, mContext.getResources().getString(resID),Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void ShowSheetDialog(Context context, BottomSheetDialog dialog, View.OnClickListener onClickListener) {
        try {
            View view = ((Activity) context).getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_connection_lost, null);
            dialog.setContentView(view);
            Button buttonTryAgain = view.findViewById(R.id.buttonTryAgain);
            buttonTryAgain.setOnClickListener(onClickListener);
            dialog.show();
        }catch (Exception ex){

            int aaaaa = 5;
            aaaaa++;
        }
    }
    public static void ShowSheetFullScreenDialog(Context context, BottomSheetDialog dialog, View.OnClickListener onClickListener) {
        try {
            View view = ((Activity) context).getLayoutInflater().inflate(R.layout.full, null);
            dialog.setContentView(view);
            Button buttonTryAgain = view.findViewById(R.id.buttonTryAgain);
            buttonTryAgain.setOnClickListener(onClickListener);
            dialog.show();
        }catch (Exception ex){
            int aaaaa = 5;
            aaaaa++;
        }
    }

    public static void ShowSheetDialogMessage(Context context, BottomSheetDialog dialog, String message , String ok, View.OnClickListener onClickListener) {
        try {
            View view = ((Activity) context).getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_connection_lost, null);
            dialog.setContentView(view);
            Button buttonTryAgain = view.findViewById(R.id.buttonTryAgain);
            TextView buttonMessage = view.findViewById(R.id.textView);
            buttonMessage.setText(message);
            buttonTryAgain.setText(ok);
            buttonTryAgain.setOnClickListener(onClickListener);
            dialog.show();
        }catch (Exception ex){

        }

    }
    public void handleServerMessage(Context context, Throwable t) {
        final BottomSheetDialog progressDialog = new BottomSheetDialog(context);
        TubelessException.ShowSheetDialogMessage(context, progressDialog, t.getMessage() , "ok" , new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.dismiss();
            }
        });
    }
}