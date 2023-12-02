package ir.sajjadyosefi.accountauthenticator.activity;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import ir.sajjadyosefi.accountauthenticator.R;
import ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral;
import ir.sajjadyosefi.accountauthenticator.classes.util;
import ir.sajjadyosefi.accountauthenticator.model.request.ALoginRequest;
import ir.sajjadyosefi.accountauthenticator.model.response.ALoginResponse;

import static ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral.sServerAuthenticate;

public class AuthenticatorActivity extends AccountAuthenticatorActivity  {
    //used
    public final static String PARAM_USER_CODE = "USER_CODE";
    public final static String PARAM_USER_TYPE = "USER_TYPE";
    public final static String PARAM_USER_CODEMELLI = "USER_CODEMELLI";
    public final static String PARAM_USER_NAME = "USER_NAME";
    public final static String PARAM_EMAIL = "EMAIL";
    public final static String PARAM_USER_PASS = "USER_PASS";
    public final static String PARAM_MOBILE = "MOBILE";
    public final static String PARAM_MOBILE_NUMBER_CONFIRMED = "MOBILE_NUMBER_CONFIRMED";
    public final static String PARAM_SIMCARD_ID = "SIMCARD_ID";
    public final static String PARAM_NAME = "NAME";
    public final static String PARAM_FAMILY = "FAMILY";
    public final static String PARAM_AVATAR = "AVATAR";
    public final static String PARAM_PROFILE_IMAGE = "PROFILE_IMAGE";
    public final static String PARAM_IS_ACTIVE = "IS_ACTIVE";
    public final static String PARAM_IS_DELETED = "IS_DELETED";
    public final static String PARAM_CREATED_DATE = "CREATED_DATE";
    public final static String PARAM_WALLET_AMOUNT = "WALLET_AMOUNT";
    public final static String PARAM_USER_OBJECT = "USER_OBJECT";

    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";         //todo بررسی کن درست استفاده شده باشه
    //public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public static final String KEY_ERROR_MESSAGE = "ERR_MSG";
    public final static String PARAM_CONFIG = "CONFIG";
    public final static String PARAM_TRANSACTION_LIST = "TRANSACTION LIST";



    //check
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";

    private final int REQ_SIGNUP = 1;

    private final String TAG = this.getClass().getSimpleName();

    private AccountManager mAccountManager;
    private String mAuthTokenType;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        mAccountManager = AccountManager.get(getBaseContext());

        mAuth = FirebaseAuth.getInstance();

        String accountName = getIntent().getStringExtra(ARG_ACCOUNT_NAME);
        mAuthTokenType = getIntent().getStringExtra(ARG_AUTH_TYPE);
        if (mAuthTokenType == null)
            mAuthTokenType = AccountGeneral.AUTHTOKEN_TYPE_ADMIN_USER;

        if (accountName != null) {
            ((TextView)findViewById(R.id.accountName)).setText(accountName);
        }

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Since there can only be one AuthenticatorActivity, we call the sign up activity, get his results,
                // and return them in setAccountAuthenticatorResult(). See finishLogin().
                Intent signup = new Intent(getBaseContext(), SignUpActivity.class);
                signup.putExtras(getIntent().getExtras());
                startActivityForResult(signup, REQ_SIGNUP);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // The sign up activity returned that the user has successfully created an account
        if (requestCode == REQ_SIGNUP && resultCode == RESULT_OK) {
            finishLogin(data);
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("StaticFieldLeak")
    public void submit() {

        final String userName = ((TextView) findViewById(R.id.userName)).getText().toString();
        final String accountName = ((TextView) findViewById(R.id.accountName)).getText().toString().trim();
        final String accountPassword = ((TextView) findViewById(R.id.accountPassword)).getText().toString();

        final String accountType = getIntent().getStringExtra(AccountGeneral.ACCOUNT_TYPE);

        new AsyncTask<String, Void, Intent>() {

            @Override
            protected Intent doInBackground(String... params) {
                Log.d("TubelessSajjad", TAG + "> Started authenticating");


                final Intent intent = new Intent();
                Bundle bundle = new Bundle();

                ALoginResponse signInUser = null;
                try {
                    ALoginRequest ALoginRequest = new ALoginRequest(userName,accountPassword, util.GetAndroidId(getApplicationContext()));

                    signInUser = sServerAuthenticate.userSignIn(ALoginRequest);


//                    if (accountName != null && accountName.length() > 2)
//                        bundle.putString(AccountManager.KEY_ACCOUNT_NAME, accountName + "(" + signInUser.getUserName() + ")");
//                    else
//                        bundle.putString(AccountManager.KEY_ACCOUNT_NAME, signInUser.getUserName());
//
//                    bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
//                    bundle.putString(AccountManager.KEY_AUTHTOKEN, signInUser.getAuthtoken());
//                    bundle.putString(PARAM_USER_ID, signInUser.getUserId().toString());
//                    bundle.putString(PARAM_USER_NAME, signInUser.getUserName());
//                    bundle.putString(PARAM_USER_PASS, accountPassword);
                } catch (Exception e) {
                    bundle.putString(KEY_ERROR_MESSAGE, e.getMessage());
                }

                intent.putExtras(bundle);
                return intent;
            }

            @Override
            protected void onPostExecute(Intent intent) {
                if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
                    Toast.makeText(getBaseContext(), intent.getStringExtra(KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                } else {
                    finishLogin(intent);
                }
            }
        }.execute();
    }


    private void finishLogin(Intent intent) {
        Log.d("TubelessSajjad", TAG + "> finishLogin");

        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountUserID = intent.getStringExtra(PARAM_USER_CODE);
        String accountUserName = intent.getStringExtra(PARAM_USER_NAME);
        String accountUserPass = intent.getStringExtra(PARAM_USER_PASS);

        final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));

        if (getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
//            Log.d("TubelessSajjad", TAG + "> finishLogin > addAccountExplicitly");
            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            String authtokenType = mAuthTokenType;

            Bundle data = new Bundle();
            data.putString(PARAM_USER_CODE, String.valueOf(accountUserID));
            data.putString(PARAM_USER_NAME,accountUserName);

            // Creating the account on the device and setting the auth token we got
            // (Not setting the auth token will cause another call to the server to authenticate the user)
//            mAccountManager.addAccountExplicitly(account, accountUserPass, data);
//            mAccountManager.setAuthToken(account, authtokenType, authtoken);
        } else {
            Log.d("TubelessSajjad", TAG + "> finishLogin > setPassword");
            mAccountManager.setPassword(account, accountUserPass);
        }

        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }
}
