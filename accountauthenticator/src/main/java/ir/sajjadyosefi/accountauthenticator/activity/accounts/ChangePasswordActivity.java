package ir.sajjadyosefi.accountauthenticator.activity.accounts;

import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import ir.sajjadyosefi.accountauthenticator.R;
import ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral;
import ir.sajjadyosefi.accountauthenticator.classes.SAccounts;
import ir.sajjadyosefi.accountauthenticator.classes.exception.TubelessException;
import ir.sajjadyosefi.accountauthenticator.classes.util;
import ir.sajjadyosefi.accountauthenticator.model.request.AChangePasswordRequest;
import ir.sajjadyosefi.accountauthenticator.model.response.ALoginResponse;

import static android.Manifest.permission.READ_PHONE_STATE;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.ARG_ACCOUNT_NAME;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.ARG_AUTH_TYPE;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.KEY_ERROR_MESSAGE;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_USER_CODE;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_USER_NAME;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_USER_OBJECT;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_USER_PASS;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_USER_TYPE;
import static ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral.sServerAuthenticate;
import static ir.sajjadyosefi.accountauthenticator.classes.exception.TubelessException.TUBELESS_PASSWORD_IS_EMPTY;

public class ChangePasswordActivity extends Activity {

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
    private static ChangePasswordActivity loginActivity;

    //singletone
    public synchronized static ChangePasswordActivity getInstance() {
        if (loginActivity == null) {
            loginActivity = new ChangePasswordActivity();
        }
        return loginActivity;
    }

    //default constractor
    public ChangePasswordActivity() {
    }

    public synchronized static Intent getIntent(Context context) {
        return getIntent(context, null);
    }

    public synchronized static Intent getIntent(Context context, Bundle bundle) {
        bundle.putString("item1", "value1");
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        activity = this;
        intentxxxxxxx = getIntent();
        setContentView(R.layout.act_change_password);

        progressDialog = new Dialog(context);
        progressDialog.setContentView(R.layout.x_main_progress);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        String accountName = intentxxxxxxx.getStringExtra(ARG_ACCOUNT_NAME);
        String userCode = intentxxxxxxx.getStringExtra(PARAM_USER_CODE);
        mAuthTokenType = intentxxxxxxx.getStringExtra(ARG_AUTH_TYPE);
        if (mAuthTokenType == null)
            mAuthTokenType = AccountGeneral.AUTHTOKEN_TYPE_ADMIN_USER;

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bundle bundle = intentxxxxxxx.getExtras();

                    if (bundle != null) {
                        for (String key : bundle.keySet()) {
//                            Log.e(TAG, key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
                            if (key!= null)
                                if((key.equals(ARG_ACCOUNT_NAME) || key.equals(PARAM_USER_CODE) || key.equals(ARG_AUTH_TYPE) || key.equals(ARG_IS_ADDING_NEW_ACCOUNT)))
                                    continue;
                                else
                                    intentxxxxxxx.removeExtra(key);
                        }
                    }

                    tyrChangePassword(intentxxxxxxx,userCode);
                } catch (Exception ex) {
                    Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                    hideProgressBar();
                }
            }
        });
        findViewById(R.id.forgetPasseord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
        finishActivityAndReturnData(intent);
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


    private Intent changePasswordProcess(AChangePasswordRequest aLoginRequest , Intent intent) {
        Bundle bundle = new Bundle();
        ALoginResponse passwordRequest = null;

        try {
            passwordRequest = sServerAuthenticate.changePassword(aLoginRequest);
        } catch (Exception e) {
            bundle.putString(KEY_ERROR_MESSAGE, e.getMessage());
            intent.putExtras(bundle);
            return intent;
        }

            Gson gson = new Gson();
            bundle.putString(PARAM_USER_OBJECT, gson.toJson(passwordRequest));
            bundle.remove(KEY_ERROR_MESSAGE);

        intent.putExtras(bundle);
        return intent;
    }

    @SuppressLint("StaticFieldLeak")
    public void tyrChangePassword(final Intent intent,String userCode) throws TubelessException {
        showProgressBar();
        final String oldPassword = ((TextView) findViewById(R.id.oldPassword)).getText().toString();
        final String newPassword = ((TextView) findViewById(R.id.newPassword)).getText().toString().trim();
        final String newPassword2 = ((TextView) findViewById(R.id.newPassword2)).getText().toString();

        if (oldPassword.length() < 1){
            throw new TubelessException(TUBELESS_PASSWORD_IS_EMPTY);
        }
        if (newPassword.length() < 1){
            throw new TubelessException(TUBELESS_PASSWORD_IS_EMPTY);
        }
        if (newPassword2.length() < 1){
            throw new TubelessException(TUBELESS_PASSWORD_IS_EMPTY);
        }

        new AsyncTask<String, Void, Intent>() {
            @Override
            protected Intent doInBackground(String... params) {
                AChangePasswordRequest aChangePasswordRequest = new AChangePasswordRequest(oldPassword,newPassword,userCode, util.GetAndroidId(getApplicationContext()));
                return changePasswordProcess(aChangePasswordRequest, intent);
            }

            @Override
            protected void onPostExecute(Intent intent) {
                hideProgressBar();
                if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
                    Toast.makeText(getBaseContext(), intent.getStringExtra(KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                } else {
                    finishActivityAndReturnData(intent);
                }
            }
        }.execute();
    }



    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
