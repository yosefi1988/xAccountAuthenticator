package ir.sajjadyosefi.android.xTubeless.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import ir.sajjadyosefi.accountauthenticator.activity.AuthenticatorActivity;
import ir.sajjadyosefi.accountauthenticator.activity.SignInActivity;
import ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral;
import ir.sajjadyosefi.android.xTubeless.R;

import static ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral.AUTHTOKEN_TYPE_ADMIN_USER;

public class MainTestActivity extends AppCompatActivity {

    private static final String STATE_DIALOG = "state_dialog";
    private static final String STATE_INVALIDATE = "state_invalidate";

    private String TAG = this.getClass().getSimpleName();
    private AccountManager mAccountManager;
    private AlertDialog mAlertDialog;
    private boolean mInvalidate;

    Activity context ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;


        setContentView(R.layout.activity_main_test);
        mAccountManager = AccountManager.get(this);

        findViewById(R.id.btnAddAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewAccount(context, AccountGeneral.ACCOUNT_TYPE, AUTHTOKEN_TYPE_ADMIN_USER);
            }
        });

        findViewById(R.id.btnGetAuthToken).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mInvalidate = false;
                final Account availableAccounts[] = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);

                if (availableAccounts.length == 0) {
                    Toast.makeText(context, "No accounts", Toast.LENGTH_SHORT).show();
                } else {
                    String name[] = new String[availableAccounts.length];
                    for (int i = 0; i < availableAccounts.length; i++) {
                        name[i] = availableAccounts[i].name;
                    }
                    getExistingAccountAuthToken(availableAccounts[0], AUTHTOKEN_TYPE_ADMIN_USER);
                }
            }
        });

        findViewById(R.id.btnGetAuthTokenConvenient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AccountManagerFuture<Bundle> future = mAccountManager.getAuthTokenByFeatures(
                        AccountGeneral.ACCOUNT_TYPE,
                        AUTHTOKEN_TYPE_ADMIN_USER,
                        null,
                        context,
                        null,
                        null,
                        new AccountManagerCallback<Bundle>() {
                            @Override
                            public void run(AccountManagerFuture<Bundle> future) {
                                Bundle bnd = null;
                                try {
                                    bnd = future.getResult();
                                    final String authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                                    showMessage(((authtoken != null) ? "SUCCESS!\ntoken: " + authtoken : "FAIL"));
                                    Log.d("udinicSajjad", "GetTokenForAccount Bundle is " + bnd);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    showMessage(e.getMessage());
                                }
                            }
                        }
                        , null);
            }
        });
        findViewById(R.id.btnInvalidateAuthToken).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInvalidate = true;
                final Account availableAccounts[] = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);
                if (availableAccounts.length == 0) {
                    Toast.makeText(context, "No accounts", Toast.LENGTH_SHORT).show();
                } else {
                    String name[] = new String[availableAccounts.length];
                    for (int i = 0; i < availableAccounts.length; i++) {
                        name[i] = availableAccounts[i].name;
                    }
                    invalidateAuthToken(availableAccounts[0], AUTHTOKEN_TYPE_ADMIN_USER);
                }
            }
        });



//        Intent i = new Intent( Settings.ACTION_ADD_ACCOUNT );
//        i.putExtra( Settings.EXTRA_AUTHORITIES, new String[] { AccountGeneral.ACCOUNT_TYPE } );
//        context.startActivity( i );
    }





    private void getExistingAccountAuthToken(Account account, String authTokenType) {
        final AccountManagerFuture<Bundle> future = mAccountManager.getAuthToken(account, authTokenType, null, this, null, null);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bundle bnd = future.getResult();

                    final String authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                    showMessage((authtoken != null) ? "SUCCESS!\ntoken: " + authtoken : "FAIL");
                    Log.d("udinicSajjad", "GetToken Bundle is " + bnd);
                } catch (Exception e) {
                    e.printStackTrace();
                    showMessage(e.getMessage());
                }
            }
        }).start();
    }


    private void invalidateAuthToken(final Account account, String authTokenType) {
        final AccountManagerFuture<Bundle> future = mAccountManager.getAuthToken(account, authTokenType, null, this, null,null);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bundle bnd = future.getResult();

                    final String authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                    mAccountManager.invalidateAuthToken(account.type, authtoken);
                    showMessage(account.name + " invalidated");
                } catch (Exception e) {
                    e.printStackTrace();
                    showMessage(e.getMessage());
                }
            }
        }).start();
    }

    private void addNewAccount(Context context , String accountType, String authTokenType) {

        final Intent intent = new Intent(context, SignInActivity.class);
        intent.putExtra(AccountGeneral.ACCOUNT_TYPE, accountType);
        intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
//        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        context.startActivity(intent);



//        final AccountManagerFuture<Bundle> future = mAccountManager.addAccount(accountType, authTokenType, null, null, this, new AccountManagerCallback<Bundle>() {
//            @Override
//            public void run(AccountManagerFuture<Bundle> future) {
//                try {
//                    Bundle bnd = future.getResult();
//                    showMessage("Account was created");
//                    Log.d("udinicSajjad", "AddNewAccount Bundle is " + bnd);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    showMessage(e.getMessage());
//                }
//            }
//        }, null);
    }
    private void showMessage(final String msg) {
        if (TextUtils.isEmpty(msg))
            return;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
