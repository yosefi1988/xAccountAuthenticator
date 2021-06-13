package ir.sajjadyosefi.accountauthenticator.activity;

import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;

import ir.sajjadyosefi.accountauthenticator.R;
import ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral;
import ir.sajjadyosefi.accountauthenticator.classes.util;
import ir.sajjadyosefi.accountauthenticator.model.request.ALoginRequest;
import ir.sajjadyosefi.accountauthenticator.model.response.ALoginResponse;

import static ir.sajjadyosefi.accountauthenticator.activity.AuthenticatorActivity.PARAM_USER_NAME;
import static ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral.sServerAuthenticate;
import static ir.sajjadyosefi.accountauthenticator.activity.AuthenticatorActivity.KEY_ERROR_MESSAGE;
import static ir.sajjadyosefi.accountauthenticator.activity.AuthenticatorActivity.PARAM_USER_PASS;

public class SignUpActivity extends Activity {
    private String TAG = getClass().getSimpleName();
    private String mAccountType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAccountType = getIntent().getStringExtra(AccountGeneral.ACCOUNT_TYPE);

        setContentView(R.layout.act_register);

        findViewById(R.id.alreadyMember).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void createAccount() {
        // Validation!
        new AsyncTask<String, Void, Intent>() {

            String userName = ((TextView) findViewById(R.id.userName)).getText().toString().trim();
            String accountName = ((TextView) findViewById(R.id.accountName)).getText().toString().trim();
            String accountPassword = ((TextView) findViewById(R.id.accountPassword)).getText().toString().trim();

            @SuppressLint("StaticFieldLeak")
            @Override
            protected Intent doInBackground(String... params) {

                Log.d("TubelessSajjad", TAG + "> Started authenticating");

                final Intent intent = new Intent();
                Bundle bundle = new Bundle();
                try {
                    ALoginRequest ALoginRequest = new ALoginRequest(userName,accountPassword, util.GetAndroidId(getApplicationContext()));
                    ALoginResponse signUpUser = sServerAuthenticate.userSignUp(ALoginRequest);

                    if (accountName != null && accountName.length() > 2)
                        bundle.putString(AccountManager.KEY_ACCOUNT_NAME, accountName + "(" + signUpUser.getUserName() + ")");
                    else
                        bundle.putString(AccountManager.KEY_ACCOUNT_NAME, signUpUser.getUserName());

                    bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, mAccountType);
//                    bundle.putString(AccountManager.KEY_AUTHTOKEN, signUpUser.getAuthtoken());
//                    bundle.putString(PARAM_USER_ID, signUpUser.getUserId().toString());
                    bundle.putString(PARAM_USER_NAME, signUpUser.getUserName());
                    bundle.putString(PARAM_USER_PASS, accountPassword);
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
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        }.execute();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}
