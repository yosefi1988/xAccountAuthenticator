package ir.sajjadyosefi.android.xTubeless.classes;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;

import ir.sajjadyosefi.accountauthenticator.activity.AuthenticatorActivity;
import ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral;

import static ir.sajjadyosefi.android.xTubeless.classes.StaticValue.NOT_LOGN_USER;


public class SAccounts {

    private final Context context;
    //var
    private AccountManager mAccountManager;

    //val
    public final static String SAMANIUM_AUTH_TYPE = "AUTH_TYPE";
    public final static String SAMANIUM_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String SAMANIUM_ACCOUNT_NAME = "ACCOUNT_NAME";

    public SAccounts(Context context) {
        this.context = context;
        mAccountManager = AccountManager.get(context);
    }

    private AccountManager getAccountManager() {
        return mAccountManager;
    }

    public int performAccount(String name , int UserID , String password) {
        SAccounts sAccounts = new SAccounts(context);
        if (!sAccounts.hasUserAccount()){
            if (password == null){
                sAccounts.createAccount(name,UserID);
            }else {
                sAccounts.createAccount(name,UserID,password);
            }
            return UserID;
        }else {
            int userid = sAccounts.getUserAccountID();
            return userid;
        }
    }
    public int performAccount(String name , int UserID) {
        return performAccount(name,UserID,null);
    }


    public Account getUserAccount() {
        Account availableAccounts[] = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);

        if (availableAccounts.length != 0)
            return availableAccounts[0];
        else
            return null;
    }

    public int getUserAccountID() {
        try {
            if (hasUserAccount()) {
                return Integer.parseInt(mAccountManager.getUserData(getUserAccount(), AuthenticatorActivity.PARAM_USER_ID));
            } else {
                return NOT_LOGN_USER;
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

    public String getUserAccountName() {
        if (hasUserAccount()) {
            return getUserAccount().name;
        }else {
            return null;
        }
    }
    public Account getAccountX() {
        if (hasUserAccount()) {
            return getUserAccount();
        }else {
            return null;
        }
    }

    public boolean hasUserAccount() {
        if (getUserAccount() == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean removeAccount(Account account) {
        try {
            mAccountManager.removeAccount(account, null, null);
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    private void createAccount(String name,int UserID) {
        final Account account = new Account(name, AccountGeneral.ACCOUNT_TYPE) ;
        Bundle data = new Bundle();
        data.putString("UserID", String.valueOf(UserID));
        this.getAccountManager().addAccountExplicitly(account, "tubeless",data);
    }
    private void createAccount(String name,int UserID,String password) {
        final Account account = new Account(name, AccountGeneral.ACCOUNT_TYPE) ;
        Bundle data = new Bundle();
        data.putString("UserID", String.valueOf(UserID));
        data.putString("UserPassword",password);
        this.getAccountManager().addAccountExplicitly(account, "tubeless",data);
    }

    public String getUserAccountPassword() {
        if (hasUserAccount()) {
            return mAccountManager.getUserData(getUserAccount(), "UserPassword");
        }else {
            return null;
        }
    }
}
