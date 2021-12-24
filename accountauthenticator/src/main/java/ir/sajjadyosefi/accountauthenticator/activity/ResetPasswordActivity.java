package ir.sajjadyosefi.accountauthenticator.activity;

import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import ir.sajjadyosefi.accountauthenticator.R;
import ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral;
import ir.sajjadyosefi.accountauthenticator.classes.Config;
import ir.sajjadyosefi.accountauthenticator.classes.SAccounts;
import ir.sajjadyosefi.accountauthenticator.classes.exception.TubelessException;
import ir.sajjadyosefi.accountauthenticator.classes.util;
import ir.sajjadyosefi.accountauthenticator.model.ASMS;
import ir.sajjadyosefi.accountauthenticator.model.request.AChangePasswordRequest;
import ir.sajjadyosefi.accountauthenticator.model.response.ALoginResponse;

import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.SEND_SMS;
import static android.provider.Telephony.Sms.getDefaultSmsPackage;
import static ir.sajjadyosefi.accountauthenticator.activity.AuthenticatorActivity.ARG_AUTH_TYPE;
import static ir.sajjadyosefi.accountauthenticator.activity.AuthenticatorActivity.PARAM_MOBILE;
import static ir.sajjadyosefi.accountauthenticator.activity.AuthenticatorActivity.PARAM_USER_CODE;

public class ResetPasswordActivity extends Activity {

    private final int REQ_SIGNUP = 1;
    //widget
    public Dialog progressDialog;

    //val
    private int RC_SIGN_IN = 1000;
    private String wantPermission = READ_PHONE_STATE;
    private static final int PERMISSION_REQUEST_CODE = 1;
    Context context;
    Activity activity;
    Intent intentxxxxxxx;

    //private AccountManager mAccountManager;
    private String mAuthTokenType;

    //singletone instance
    private static ResetPasswordActivity loginActivity;

    //singletone
    public synchronized static ResetPasswordActivity getInstance() {
        if (loginActivity == null) {
            loginActivity = new ResetPasswordActivity();
        }
        return loginActivity;
    }

    //default constractor
    public ResetPasswordActivity() {
    }

    public synchronized static Intent getIntent(Context context) {
        return getIntent(context, null);
    }

    public synchronized static Intent getIntent(Context context, Bundle bundle) {
        bundle.putString("item1", "value1");
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MESSAGE_CODE) {
            //2 sms bazzar
//            if (resultCode == Activity.RESULT_OK) {
//            }else {
//            }
//
            //1 sms
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideProgressBar();
                    finishActivityAndReturnData(intentxxxxxxx);
                }
            }, 1000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101:
                try {
                    if (grantResults[0] == 0) {
                        modal1(context);
                        return;
                    }
                    Toast.makeText(this, "برای ادامه به این دسترسی احتیاج است", Toast.LENGTH_LONG).show();
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                } catch (RuntimeException e2) {
                    e2.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        activity = this;
        intentxxxxxxx = getIntent();
        setContentView(R.layout.act_reset_password);

        progressDialog = new Dialog(context);
        progressDialog.setContentView(R.layout.x_main_progress);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mAuthTokenType = intentxxxxxxx.getStringExtra(ARG_AUTH_TYPE);
        if (mAuthTokenType == null)
            mAuthTokenType = AccountGeneral.AUTHTOKEN_TYPE_ADMIN_USER;

        findViewById(R.id.forgetPasseord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modal1(context);
            }
        });
    }


    public void modal1(Context mContext){
        final BottomSheetDialog dialog = new BottomSheetDialog(mContext);

        View view = ((Activity)mContext).getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_reset_password, null);
        dialog.setContentView(view);

        final Button buttonAccept = view.findViewById(R.id.accept);
        final Button buttonCancel = view.findViewById(R.id.cancel);
//        final View textViewChangeToDiscountCardHr = view.findViewById(R.id.textViewChangeToDiscountCardHr);
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                if (hasMainActivityPermission()) {
                    showProgressBar();
                    sendSMS(context, intentxxxxxxx);
//                    checkIfSimIsPresent(context);
                }else {
                    ActivityCompat.requestPermissions((ResetPasswordActivity)context,strArr,101);
                }
            }
        });
        dialog.show();
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }



    public static final String TAG = "TSR";
    String[] strArr = {SEND_SMS};

    private boolean hasMainActivityPermission() {
        for (int i = 0; i < 1; i++) {
            if (ContextCompat.checkSelfPermission(this, strArr[i]) != 0) {
                return false;
            }
        }
        return true;
    }

    protected void finishActivityAndReturnData(Intent returnIntent) {
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void showProgressBar() {
        progressDialog.show();
    }

    public void hideProgressBar() {
        progressDialog.hide();
    }

    int MESSAGE_CODE = 12;
    public void sendSMS(Context mContext, Intent intent) {
        Gson gson = new Gson();
        ASMS sms = new ASMS();

        sms.setU(intent.getStringExtra(PARAM_USER_CODE));
        sms.setD(AccountGeneral.getAndroidID());
        sms.setM(intent.getStringExtra(PARAM_MOBILE));
        sms.setT("r");
        try {

            //1 sms all
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(Config.SMSHOST, null, gson.toJson(sms), null, null);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideProgressBar();
                    finishActivityAndReturnData(intent);
                }
            }, 5000);


//            //2 sms bazzar
//            Uri uri = Uri.parse("smsto:" + Config.SMSHOST);
//            Intent intentsms = new Intent(Intent.ACTION_SENDTO, uri);
//            intentsms.putExtra("sms_body", gson.toJson(sms));
//            startActivityForResult(intentsms,MESSAGE_CODE);


        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
            hideProgressBar();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
