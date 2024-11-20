package ir.sajjadyosefi.accountauthenticator.classes;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;

import ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity;
import ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral;

import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_USER_PASS;

public class SAccounts {

    private final Context context;
    private AccountManager mAccountManager;
    public SAccounts(Context context) {
        this.context = context;
        mAccountManager = AccountManager.get(context);
    }
    private AccountManager getAccountManager() {
        return mAccountManager;
    }

    public int performAccount(String accountName, Bundle data) {
        SAccounts sAccounts = new SAccounts(context);
        if (!sAccounts.hasUserAccount()){
            sAccounts.createAccount(accountName,data);
            return sAccounts.getUserAccountID();
        }else {
            int userid = sAccounts.getUserAccountID();
            return userid;
        }
    }

    public Account getAccountX() {
        if (hasUserAccount()) {
            return getUserAccount();
        }else {
            return null;
        }
    }
    public Account getUserAccount() {

//        Account[] allAccounts = mAccountManager.getAccounts();
//        for (Account account : allAccounts) {
//            Log.d("Account", "Type: " + account.type + ", Name: " + account.name);
//        }

        Account availableAccounts[] = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);

        if (availableAccounts.length != 0)
            return availableAccounts[0];
        else
            return null;
    }

    public int getUserAccountID() {
        try {
            if (hasUserAccount()) {
                return Integer.parseInt(mAccountManager.getUserData(getUserAccount(), AuthenticatorActivity.PARAM_USER_CODE));
            } else {
                return 654798;
            }
        }catch (Exception ex) {
            return 0;
        }
    }
    public String getUserName() {
        if (hasUserAccount()) {
            return mAccountManager.getUserData(getUserAccount(), AuthenticatorActivity.PARAM_USER_NAME);
        }else {
            return null;
        }
    }
    public String getUserPassword() {
        if (hasUserAccount()) {
            return getAccountManager().getPassword(this.getAccountX());
        }else {
            return null;
        }
    }
    public String getUserAccountPassword() {
        if (hasUserAccount()) {
            return mAccountManager.getUserData(getUserAccount(), "UserPassword");
        }else {
            return null;
        }
    }
    public String getUserAccountName() {
        if (hasUserAccount()) {
            return getUserAccount().name;
        }else {
            return null;
        }
    }


    private boolean hasUserAccount() {
        if (getUserAccount() == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean removeAccount(Account account) {
        try {
            AccountManagerCallback<Boolean> callback = new AccountManagerCallback<Boolean>() {
                @Override
                public void run(AccountManagerFuture<Boolean> future) {
                    int a = 5 ;
                    a++;
                }
            };
            Handler handler = new Handler();


            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
                AccountManagerFuture<Boolean> result = mAccountManager.removeAccount(account, callback, handler);
                return result.isDone();
            } else {
                try {
                    mAccountManager.removeAccountExplicitly(account);
                    return true;
                }catch (Exception ex){
                    return false;
                }
            }
        }catch (Exception ex){
            return false;
        }
    }
    private void createAccount(String accountName, Bundle data) {
        final Account account = new Account(accountName, AccountGeneral.ACCOUNT_TYPE);
        data.putString("accountName",accountName);
        this.getAccountManager().addAccountExplicitly(account,data.getString(PARAM_USER_PASS), data);
        this.getAccountManager().setAuthToken(account, data.getString("authtokenType"), data.getString("authtoken"));
    }
}
