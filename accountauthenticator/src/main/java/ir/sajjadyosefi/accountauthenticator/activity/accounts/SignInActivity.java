package ir.sajjadyosefi.accountauthenticator.activity.accounts;

import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.List;

import ir.sajjadyosefi.accountauthenticator.R;
import ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral;
import ir.sajjadyosefi.accountauthenticator.classes.Config;
import ir.sajjadyosefi.accountauthenticator.classes.IDeviceRegisterRequest;
import ir.sajjadyosefi.accountauthenticator.classes.ITransactionsListRequest;
import ir.sajjadyosefi.accountauthenticator.classes.SAccounts;
import ir.sajjadyosefi.accountauthenticator.classes.exception.TubelessException;
import ir.sajjadyosefi.accountauthenticator.classes.util;
import ir.sajjadyosefi.accountauthenticator.model.ASMS;
import ir.sajjadyosefi.accountauthenticator.model.request.ADeviceRegisterRequest;
import ir.sajjadyosefi.accountauthenticator.model.request.ALoginRequest;
import ir.sajjadyosefi.accountauthenticator.model.request.ATransactionListRequest;
import ir.sajjadyosefi.accountauthenticator.model.response.ALoginResponse;
import ir.sajjadyosefi.accountauthenticator.model.response.AConfigResponse;
import ir.sajjadyosefi.accountauthenticator.model.response.ATransactionListResponse;

import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.SEND_SMS;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.ARG_ACCOUNT_NAME;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.ARG_AUTH_TYPE;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.KEY_ERROR_MESSAGE;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_AVATAR;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_CONFIG;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_CREATED_DATE;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_EMAIL;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_FAMILY;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_IS_ACTIVE;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_IS_DELETED;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_MOBILE;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_MOBILE_NUMBER_CONFIRMED;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_NAME;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_PROFILE_IMAGE;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_SIMCARD_ID;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_TRANSACTION_LIST;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_USER_OBJECT;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_USER_CODE;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_USER_CODEMELLI;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_USER_NAME;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_USER_PASS;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_USER_TYPE;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_WALLET_AMOUNT;
import static ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral.sServerAuthenticate;
import static ir.sajjadyosefi.accountauthenticator.classes.exception.TubelessException.TUBELESS_PASSWORD_IS_EMPTY;
import static ir.sajjadyosefi.accountauthenticator.classes.exception.TubelessException.TUBELESS_USERNAME_IS_EMPTY;

public class SignInActivity extends Activity {

    private final int REQ_SIGNUP = 1;
    //widget
    Button submitBySimCard;
    Button submitByCodeMelli;
    SignInButton submitByGoogle;
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


    //simcard
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (isPermissionGranted(grantResults)) {
                    tryToLoginBySimCard(intentxxxxxxx, getPhoneNumber(context));
                } else {
                    Toast.makeText(activity, context.getString(R.string.simcardPermissionError), Toast.LENGTH_LONG).show();
                }
                break;
            case 101:
                try {
                    if (grantResults[0] == 0) {
//                    if(checkIfSimIsPresent(this)){
//                        if (isNetworkAvailable().booleanValue()) {
//                            Toast.makeText(this,"Ready",Toast.LENGTH_LONG).show();
//                        }else {
//                            Toast.makeText(this,"برای ادامه به اینترنت متصل شوید",Toast.LENGTH_LONG).show();
//                        }
//                    }else {
//                        Toast.makeText(this,"برای ادامه باید از سیم کارت استفاده کنید",Toast.LENGTH_LONG).show();
//                    }

                        showProgressBar();
                        sendSMS(intentTmp);
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

    int MESSAGE_CODE = 12;


    //Google
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MESSAGE_CODE) {
            //2 sms bazzar
            if (resultCode == Activity.RESULT_OK) {
            }else {
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideProgressBar();
                    finishLoginActivityAndReturnData(intentTmp);

                }
            }, 1000);
        }

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            try {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                GoogleSignInAccount account = task.getResult(ApiException.class);
                String name = account.getDisplayName();
                String email = account.getEmail();
                Uri photoUrl = account.getPhotoUrl();

                tryToLoginByMail(
                        intentxxxxxxx,
                        email,
                        photoUrl == null ? "" : photoUrl.toString());
            } catch (Exception ex) {

            }
        }
        // The sign up activity returned that the user has successfully created an account
        if (requestCode == REQ_SIGNUP && resultCode == RESULT_OK) {
            finishLoginProcess(data,true);
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    //singletone instance
    private static SignInActivity loginActivity;

    //singletone
    public synchronized static SignInActivity getInstance() {
        if (loginActivity == null) {
            loginActivity = new SignInActivity();
        }
        return loginActivity;
    }

    //default constractor
    public SignInActivity() {
    }

    public synchronized static Intent getIntent(Context context) {
        return getIntent(context, null);
    }

    public synchronized static Intent getIntent(Context context, Bundle bundle) {
        bundle.putString("item1", "value1");
        Intent intent = new Intent(context, SignInActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        activity = this;
        intentxxxxxxx = getIntent();
        setContentView(R.layout.act_login);

        progressDialog = new Dialog(context);
        progressDialog.setContentView(R.layout.x_main_progress);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        submitBySimCard = findViewById(R.id.submitBySimCard);
        submitByCodeMelli = findViewById(R.id.submitByCodeMelli);
        submitByGoogle = findViewById(R.id.submitByGoogle);

        String accountName = intentxxxxxxx.getStringExtra(ARG_ACCOUNT_NAME);
        mAuthTokenType = intentxxxxxxx.getStringExtra(ARG_AUTH_TYPE);
        if (mAuthTokenType == null)
            mAuthTokenType = AccountGeneral.AUTHTOKEN_TYPE_ADMIN_USER;

        if (accountName != null) {
            ((TextView) findViewById(R.id.accountName)).setText(accountName);
        }

        findViewById(R.id.textViewForgetpass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("type" , 1);
                Intent intent = ResetPasswordActivity.getIntent(context,bundle);

                intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, AccountGeneral.AUTHTOKEN_TYPE_ADMIN_USER);

                intent.putExtra(AuthenticatorActivity.PARAM_USER_CODE, "" );
                intent.putExtra(AuthenticatorActivity.PARAM_MOBILE, "");

                bundle.putParcelable(AccountManager.KEY_INTENT, intent);
                ((Activity)context).startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bundle bundle = intentxxxxxxx.getExtras();

                    if (bundle != null) {
                        for (String key : bundle.keySet()) {
//                            Log.e(TAG, key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
                            if (key!= null)
                                if((key.equals(ARG_ACCOUNT_NAME) || key.equals(ARG_AUTH_TYPE) || key.equals(ARG_IS_ADDING_NEW_ACCOUNT)))
                                    continue;
                                else
                                    intentxxxxxxx.removeExtra(key);
                        }
                    }
                    tyrToLoginByMobileNumber(intentxxxxxxx);
                } catch (Exception ex) {
                    Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                    hideProgressBar();
                }
            }
        });
        findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(getBaseContext(), SignUpActivity.class);
                signup.putExtras(intentxxxxxxx.getExtras());
                startActivityForResult(signup, REQ_SIGNUP);
            }
        });

        //Code melli
        submitByCodeMelli.setVisibility(View.GONE);
        submitByCodeMelli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, context.getString(R.string.comming_soon), Toast.LENGTH_LONG).show();
                   try {
                       tryToLoginByCodeMelli(intentxxxxxxx,0);
//                       tryToLoginByUserCode("110015",intentxxxxxxx);
                   } catch (TubelessException e) {
                       e.printStackTrace();
                   }
            }
        });

        //simcard
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            submitBySimCard.setVisibility(View.GONE);
        }
        submitBySimCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    //Toast.makeText(activity, context.getString(R.string.below_android_10), Toast.LENGTH_LONG).show();
                }else {
                    if (!checkPermission(context, wantPermission)) {
                        if (shouldShowRequestPermissionRationale(activity, wantPermission)) {
                            Toast.makeText(activity, context.getString(R.string.loginBySimcardDescription), Toast.LENGTH_LONG).show();
                        }
                        requestPermissions(activity, new String[]{wantPermission}, PERMISSION_REQUEST_CODE);

                    } else {
                        tryToLoginBySimCard(intentxxxxxxx, getPhoneNumber(context));
                    }
                }
            }
        });

        //google
        submitByGoogle.setSize(SignInButton.SIZE_STANDARD);
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        final GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        submitByGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

//        Toast.makeText(this,"1",Toast.LENGTH_LONG).show();
    }


    private void finishLoginProcess(Intent intent,boolean createAccount) {
        //todo بررسی ایجاد حساب کابری
        intent.hasExtra("MustRefresh");

        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String userTypeCode = intent.getIntExtra(PARAM_USER_TYPE, 0) + "";

        String accountUserCode = intent.getIntExtra(PARAM_USER_CODE, 0) + "";
        String accountUserName = intent.getStringExtra(PARAM_USER_NAME);
        String accountUserPass = intent.getStringExtra(PARAM_USER_PASS);

//        final Account account = new Account(accountName, AccountGeneral.ACCOUNT_TYPE);
//        mAccountManager = AccountManager.get(getBaseContext());
        SAccounts sAccounts = new SAccounts(context);

        if (intent.getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
            // Creating the account on the device
//            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
//            String authtokenType = mAuthTokenType;

            Bundle data = new Bundle();
            data.putString(PARAM_USER_CODE, accountUserCode);
            data.putString(PARAM_USER_TYPE, userTypeCode);
            data.putString(PARAM_USER_NAME, accountUserName);
            data.putString(PARAM_USER_PASS, accountUserPass);
            data.putString("authtokenType", mAuthTokenType);
            data.putString("authtoken", intent.getStringExtra(AccountManager.KEY_AUTHTOKEN));

            try {
                if (createAccount)
                    sAccounts.performAccount(accountName, data);
//                mAccountManager.addAccountExplicitly(account, accountUserPass, data);
//                mAccountManager.setAuthToken(account, authtokenType, authtoken);
            } catch (Exception ex) {
                hideProgressBar();
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
//        } else {
//            mAccountManager.setPassword(account, accountUserPass);
        }
        finishLoginActivityAndReturnData(intent);
    }

    protected void finishLoginActivityAndReturnData(Intent returnIntent) {
        hideProgressBar();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public static boolean isPermissionGranted(int[] grantResults) {
        return grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }

    public synchronized static String getPhoneNumber(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {

            SubscriptionManager sm = SubscriptionManager.from(context);
            if (ActivityCompat.checkSelfPermission(context, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }else {
                List<SubscriptionInfo> sis = sm.getActiveSubscriptionInfoList();
                if (sis.size() >= 1)
                {
                    SubscriptionInfo si1 = sis.get(0);
                    String iccId1 = si1.getIccId();
                    String phoneNum1 = si1.getNumber();

                    return phoneNum1;
                }
                if (sis.size() >= 2)
                {
                    SubscriptionInfo si2 = sis.get(1);
                    String iccId2 = si2.getIccId();
                    String phoneNum2 = si2.getNumber();

                    return phoneNum2;
                }
                // Get information about the number of SIM cards:
                int count = sm.getActiveSubscriptionInfoCount();//Current actual number of cards
                int max   = sm.getActiveSubscriptionInfoCountMax();//Current card slot number

                return String.valueOf(max);
            }

        }else {
            TelephonyManager phoneMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            if (phoneMgr.getLine1Number().equals("")) {
                Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                Settings.Secure.getString(context.getContentResolver(), Settings.Secure.NAME);

                return phoneMgr.getSubscriberId();
            } else {
                return phoneMgr.getLine1Number();
            }
        }
    }

    public static void requestPermissions(
            final @NonNull Activity activity,
            final @NonNull String[] permissions,
            final @IntRange(from = 0) int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions,requestCode);
    }

    public synchronized static boolean checkPermission(Context context , String permission){
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ContextCompat.checkSelfPermission(context, permission);
            if (result == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public synchronized static boolean shouldShowRequestPermissionRationale(Activity activity , String permission){
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    public void showProgressBar() {
        progressDialog.show();
    }

    public void hideProgressBar() {
        progressDialog.hide();
    }

    @SuppressLint("StaticFieldLeak")
    public void tryToLoginBySimCard(final Intent intent, final String simId) {
        showProgressBar();
        final String userName = simId;//((TextView) findViewById(R.id.userName)).getText().toString();
        final String accountName = "";//((TextView) findViewById(R.id.accountName)).getText().toString().trim();
        final String accountPassword = "";//((TextView) findViewById(R.id.accountPassword)).getText().toString();

        new AsyncTask<String, Void, Intent>() {
            @Override
            protected Intent doInBackground(String... params) {
                ALoginRequest aLoginRequest = new ALoginRequest(userName);
                return loginProcess(aLoginRequest, accountPassword, accountName, intent);
            }

            @Override
            protected void onPostExecute(Intent intent) {
                if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
                    Toast.makeText(getBaseContext(), intent.getStringExtra(KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                } else {
                    finishLoginProcess(intent,true);
                }
            }
        }.execute();
    }

    private Intent loginProcess(ALoginRequest aLoginRequest, String accountPassword, String accountName, Intent intent) {
        Bundle bundle = new Bundle();
        ALoginResponse signInUser = null;
        try {
            signInUser = sServerAuthenticate.userSignIn(aLoginRequest);
        } catch (Exception e) {
            bundle.putString(KEY_ERROR_MESSAGE, e.getMessage());
            intent.putExtras(bundle);
            return intent;
        }

        {   //group 1
            bundle.putInt(PARAM_USER_CODE, signInUser.getUserCode());
            bundle.putString(PARAM_USER_NAME, signInUser.getUserName());
            bundle.putString(PARAM_USER_PASS, accountPassword);

            bundle.putString(PARAM_USER_CODEMELLI, signInUser.getCodeMelli());
            bundle.putString(PARAM_EMAIL, signInUser.getEmail());
            bundle.putString(PARAM_MOBILE, signInUser.getMobile());
            bundle.putBoolean(PARAM_MOBILE_NUMBER_CONFIRMED, signInUser.isMobileNumberConfirmed());
            bundle.putBoolean(PARAM_IS_ACTIVE, signInUser.isActive());
            bundle.putBoolean(PARAM_IS_DELETED, signInUser.isDeleted());
            bundle.putString(PARAM_SIMCARD_ID, signInUser.getSimcardID());
            bundle.putString(PARAM_NAME, signInUser.getName());
            bundle.putString(PARAM_FAMILY, signInUser.getFamily());
            bundle.putString(PARAM_AVATAR, signInUser.getAvatar());
            bundle.putString(PARAM_PROFILE_IMAGE, signInUser.getProfileImage());
            bundle.putString(PARAM_CREATED_DATE, signInUser.getCreateDate());
            bundle.putLong(PARAM_WALLET_AMOUNT, signInUser.getWallet().getAmount());
            bundle.putInt(PARAM_USER_TYPE, signInUser.getUserTypeCode());             //accountType or getIntent().getStringExtra(ARG_ACCOUNT_TYPE);
        }

        {   //group 2
            bundle.putString(AccountManager.KEY_AUTHTOKEN, signInUser.getAuthtoken());
            if (accountName != null && accountName.length() > 2)
                bundle.putString(AccountManager.KEY_ACCOUNT_NAME, accountName + "(" + aLoginRequest.getUserName() + ")");
            else
                bundle.putString(AccountManager.KEY_ACCOUNT_NAME, aLoginRequest.getUserName());
        }


        {   //group 3
            Gson gson = new Gson();
            bundle.putString(PARAM_USER_OBJECT, gson.toJson(signInUser));
            bundle.remove(KEY_ERROR_MESSAGE);
        }

        intent.putExtras(bundle);
        return intent;
    }

    @SuppressLint("StaticFieldLeak")
    public void tryToLoginByMail(final Intent intent, final String email, final String photoUrl) {
        showProgressBar();

        final String userName = email;          //((TextView) findViewById(R.id.userName)).getText().toString();
        final String accountName = "";          //((TextView) findViewById(R.id.accountName)).getText().toString().trim();
        final String accountPassword = "";      //((TextView) findViewById(R.id.accountPassword)).getText().toString();

        new AsyncTask<String, Void, Intent>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            protected Intent doInBackground(String... params) {
                ALoginRequest aLoginRequest = new ALoginRequest(userName, photoUrl);
                return loginProcess(aLoginRequest, accountPassword, accountName, intent);
            }

            @Override
            protected void onPostExecute(Intent intent) {
                if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
                    Toast.makeText(getBaseContext(), intent.getStringExtra(KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                } else {
                    finishLoginProcess(intent,true);
                }
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void tyrToLoginByMobileNumber(final Intent intent) throws TubelessException {
        showProgressBar();
        final String userName = ((TextView) findViewById(R.id.userName)).getText().toString();
        final String accountName = ((TextView) findViewById(R.id.accountName)).getText().toString().trim();
        final String accountPassword = ((TextView) findViewById(R.id.accountPassword)).getText().toString();

        if (userName.length() < 1){
            throw new TubelessException(TUBELESS_USERNAME_IS_EMPTY);
        }
        if (accountPassword.length() < 1){
            throw new TubelessException(TUBELESS_PASSWORD_IS_EMPTY);
        }

        new AsyncTask<String, Void, Intent>() {
            @Override
            protected Intent doInBackground(String... params) {
                ALoginRequest aLoginRequest = new ALoginRequest(userName,accountPassword, util.GetAndroidId(getApplicationContext()));
                return loginProcess(aLoginRequest, accountPassword, accountName, intent);
            }

            @Override
            protected void onPostExecute(Intent intent) {
                if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
                    Toast.makeText(getBaseContext(), intent.getStringExtra(KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                    hideProgressBar();
                } else {
                    hideProgressBar();
                    modal1(context,intent);
                }
            }
        }.execute();
    }

    public void sendSMS(Intent intent) {
        Gson gson = new Gson();
        ASMS sms = new ASMS();
        sms.setU(String.valueOf(intent.getIntExtra(PARAM_USER_CODE, 0)));
        sms.setD(AccountGeneral.getAndroidID());
        sms.setM(intent.getStringExtra(PARAM_MOBILE));
        sms.setT("c");
        try {
            //1 sms
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(Config.SMSHOST, null,gson.toJson(sms) , null, null);
            smsManager.sendTextMessage(
                    Config.SMSHOST,
                    null,
                    String.format("%s\n%s\n%s",context.getString(R.string.smsText),"ارسال توسط اپلیکیشن:",context.getString(R.string.app_name)) ,
                    null,
                    null);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideProgressBar();
                    finishLoginProcess(intent,true);
                }
            }, 5000);


            //2 sms bazzar
//            Uri uri = Uri.parse("smsto:" + Config.SMSHOST);
//            Intent intentsms = new Intent(Intent.ACTION_SENDTO, uri);
//            intentsms.putExtra("sms_body", gson.toJson(sms));
//            startActivityForResult(intentsms,MESSAGE_CODE);

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            hideProgressBar();
            ex.printStackTrace();
        }
    }

    public static final String TAG = "TSR";
    String[] strArr = {SEND_SMS};
    Intent intentTmp = null;
//    String[] strArr = {SEND_SMS,READ_PHONE_STATE,ACCESS_NETWORK_STATE};

    public void modal1(Context mContext,Intent intent){
        final BottomSheetDialog dialog = new BottomSheetDialog(mContext);

        View view = ((Activity)mContext).getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_login, null);
        dialog.setContentView(view);

        final Button buttonAccept = view.findViewById(R.id.accept);
        final Button buttonCancel = view.findViewById(R.id.cancel);
//        final View textViewChangeToDiscountCardHr = view.findViewById(R.id.textViewChangeToDiscountCardHr);
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

//                //2 sms bazzar
//                sendSMS(intent);
//                intentTmp = intent;

                if (hasMainActivityPermission()) {
                    showProgressBar();
                    sendSMS(intent);
//                    checkIfSimIsPresent(context);
                }else {
                    ActivityCompat.requestPermissions((SignInActivity)context,strArr,101);
                    intentTmp = intent;
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


    private int checkIfSimIsPresent(Context mContext) {
        int count = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            SubscriptionManager sManager = (SubscriptionManager) mContext.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                SubscriptionInfo infoSim1 = sManager.getActiveSubscriptionInfoForSimSlotIndex(0);
                SubscriptionInfo infoSim2 = sManager.getActiveSubscriptionInfoForSimSlotIndex(1);

                if (infoSim1 != null) {
                    count++;
                }

                if (infoSim2 != null) {
                    count++;
                }
            }
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager.getSimState() != TelephonyManager.SIM_STATE_ABSENT){
                count++;
            }
        }
        return count;
    }


    private boolean hasMainActivityPermission() {
        for (int i = 0; i < 1; i++) {
            if (ContextCompat.checkSelfPermission(this, strArr[i]) != 0) {
                return false;
            }
        }
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    public void tryToLoginByCodeMelli(final Intent intent, final int pin) throws TubelessException {
        showProgressBar();
        final String userName = "4371903754";
        final String accountName = "";
        final String accountPassword = "";

        new AsyncTask<String, Void, Intent>() {
            @Override
            protected Intent doInBackground(String... params) {
                ALoginRequest aLoginRequest = new ALoginRequest(userName,pin);
                return loginProcess(aLoginRequest, accountPassword, accountName, intent);
            }

            @Override
            protected void onPostExecute(Intent intent) {
                if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
                    Toast.makeText(getBaseContext(), intent.getStringExtra(KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                    hideProgressBar();
                } else {
                    finishLoginProcess(intent,true);
                }
            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    public void tryToLoginByUserCode(final String UserCode ,final Intent intent) throws TubelessException {
        showProgressBar();
        final String userName = null;
        final String accountName = "";
        final String accountPassword = "";

        new AsyncTask<String, Void, Intent>() {
            @Override
            protected Intent doInBackground(String... params) {
                ALoginRequest aLoginRequest = new ALoginRequest(0,UserCode);
                return loginProcess(aLoginRequest, accountPassword, accountName, intent);
            }

            @Override
            protected void onPostExecute(Intent intent) {
                if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
                    Toast.makeText(getBaseContext(), intent.getStringExtra(KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                    hideProgressBar();
                } else {
                    finishLoginProcess(intent,false);
                }
            }
        }.execute();
    }


    @SuppressLint("StaticFieldLeak")
    public void tryDeviceRegister(final ADeviceRegisterRequest aDeviceRegisterRequest, final IDeviceRegisterRequest<Boolean,Intent> callback){
        new AsyncTask<String, Void, Intent>() {
            @Override
            protected Intent doInBackground(String... params) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                AConfigResponse aConfig = null;
                try {
                    aConfig = sServerAuthenticate.deviceRegister(aDeviceRegisterRequest);
//                    bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
//                    bundle.putString(AccountManager.KEY_AUTHTOKEN, signInUser.getAuthtoken());
//                    bundle.putString(PARAM_USER_ID, signInUser.getUserId().toString());
                    Gson gson = new Gson();
                    bundle.putString(PARAM_CONFIG, gson.toJson(aConfig));
                    bundle.remove(KEY_ERROR_MESSAGE);
                    intent.putExtras(bundle);
                } catch (Exception e) {
                    bundle.putString(KEY_ERROR_MESSAGE, e.getMessage());
                }
                return intent;
            }

            @Override
            protected void onPostExecute(Intent intent) {
                if (intent.hasExtra(PARAM_CONFIG))
                    callback.onResponse(true,intent);
                else
                    callback.onResponse(false,intent);
                //start activity for result
                //retData(intent);
            }
        }.execute();

    }



    @SuppressLint("StaticFieldLeak")
    public void getTransactionsList(final ATransactionListRequest transactionListRequest, final ITransactionsListRequest<Boolean,Intent> callback){
        new AsyncTask<String, Void, Intent>() {
            @Override
            protected Intent doInBackground(String... params) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                ATransactionListResponse transactionListResponse = null;
                try {
                    transactionListResponse = sServerAuthenticate.transactionList(transactionListRequest);
//                    bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
//                    bundle.putString(AccountManager.KEY_AUTHTOKEN, signInUser.getAuthtoken());
//                    bundle.putString(PARAM_USER_ID, signInUser.getUserId().toString());
                    Gson gson = new Gson();
                    bundle.putString(PARAM_TRANSACTION_LIST, gson.toJson(transactionListResponse));
                    bundle.remove(KEY_ERROR_MESSAGE);
                    intent.putExtras(bundle);
                } catch (Exception e) {
                    bundle.putString(KEY_ERROR_MESSAGE, e.getMessage());
                }
                return intent;
            }

            @Override
            protected void onPostExecute(Intent intent) {
                if (intent.hasExtra(PARAM_TRANSACTION_LIST))
                    callback.onResponse(true,intent);
                else
                    callback.onResponse(false,intent);
                //start activity for result
                //retData(intent);
            }
        }.execute();

    }

    @SuppressLint("StaticFieldLeak")
    public void getUserDirect(final String UserCode ,final ITransactionsListRequest<Boolean,Intent> callback){
        final String userName = null;
        final String accountName = "";
        final String accountPassword = "";

        new AsyncTask<String, Void, Intent>() {
            @Override
            protected Intent doInBackground(String... params) {
                ALoginRequest aLoginRequest = new ALoginRequest(0,UserCode);
                return loginProcess(aLoginRequest, accountPassword, accountName, new Intent());
            }

            @Override
            protected void onPostExecute(Intent intent) {
                if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
                    //hideProgressBar();
                    callback.onResponse(false,intent);
                } else {
                    intent.hasExtra("MustRefresh");

                    String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    String userTypeCode = intent.getIntExtra(PARAM_USER_TYPE, 0) + "";

                    String accountUserCode = intent.getIntExtra(PARAM_USER_CODE, 0) + "";
                    String accountUserName = intent.getStringExtra(PARAM_USER_NAME);
                    String accountUserPass = intent.getStringExtra(PARAM_USER_PASS);

                    if (intent.getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
                        Bundle data = new Bundle();
                        data.putString(PARAM_USER_CODE, accountUserCode);
                        data.putString(PARAM_USER_TYPE, userTypeCode);
                        data.putString(PARAM_USER_NAME, accountUserName);
                        data.putString(PARAM_USER_PASS, accountUserPass);
                        data.putString("authtokenType", mAuthTokenType);
                        data.putString("authtoken", intent.getStringExtra(AccountManager.KEY_AUTHTOKEN));

                    }
                    //hideProgressBar();
                    callback.onResponse(true,intent);
                }
            }
        }.execute();
    }
}
