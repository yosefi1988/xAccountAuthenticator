package ir.sajjadyosefi.accountauthenticator.authentication;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity;
import ir.sajjadyosefi.accountauthenticator.activity.accounts.SignInActivity;
import ir.sajjadyosefi.accountauthenticator.classes.util;
import ir.sajjadyosefi.accountauthenticator.model.request.ALoginRequest;
import ir.sajjadyosefi.accountauthenticator.model.response.ALoginResponse;

import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;
import static ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral.AUTHTOKEN_TYPE_ADMIN_USER;
import static ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral.AUTHTOKEN_TYPE_ADMIN_USER_LABEL;
import static ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral.AUTHTOKEN_TYPE_NORMAL_USER;
import static ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral.AUTHTOKEN_TYPE_NORMAL_USER_LABEL;
import static ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral.sServerAuthenticate;

public class Authenticator extends AbstractAccountAuthenticator {
    private String TAG = "TubelessAuthenticator";
    private final Context mContext;

    public Authenticator(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        Log.d("TubelessSajjad", TAG + "> addAccount");

//        final Intent intent = new Intent(mContext, AuthenticatorActivity.class);
        final Intent intent = new Intent(mContext, SignInActivity.class);
//        intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(AccountGeneral.ACCOUNT_TYPE, accountType);
        intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }


    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {

        Log.d("TubelessSajjad", TAG + "> getAuthToken");

        // If the caller requested an authToken type we don't support, then
        // return an error
        if (!authTokenType.equals(AUTHTOKEN_TYPE_NORMAL_USER) && !authTokenType.equals(AUTHTOKEN_TYPE_ADMIN_USER)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType");
            return result;
        }

        // Extract the username and password from the Account Manager, and ask
        // the server for an appropriate AuthToken.
        final AccountManager am = AccountManager.get(mContext);

        String authToken = am.peekAuthToken(account, authTokenType);

        Log.d("TubelessSajjad", TAG + "> peekAuthToken returned - " + authToken);

        // Lets give another try to authenticate the user
        if (TextUtils.isEmpty(authToken)) {
            final String password = am.getPassword(account);
            if (password != null) {
                try {
                    Log.d("TubelessSajjad", TAG + "> re-authenticating with the existing password");

                    ALoginRequest ALoginRequest = new ALoginRequest("userNamee",password, util.GetAndroidId(mContext));

                    ALoginResponse user = sServerAuthenticate.userSignIn(ALoginRequest);

                    authToken = user.getAuthtoken();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // If we get an authToken - we return it
        if (!TextUtils.isEmpty(authToken)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }

        // If we get here, then we couldn't access the user's password - so we
        // need to re-prompt them for their credentials. We do that by creating
        // an intent to display our AuthenticatorActivity.
//        final Intent intent = new Intent(mContext, AuthenticatorActivity.class);
        final Intent intent = new Intent(mContext, SignInActivity.class);

        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(AccountGeneral.ACCOUNT_TYPE, account.type);
        intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_NAME, account.name);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        if (AUTHTOKEN_TYPE_ADMIN_USER.equals(authTokenType))
            return AUTHTOKEN_TYPE_ADMIN_USER_LABEL;
        else if (AUTHTOKEN_TYPE_NORMAL_USER.equals(authTokenType))
            return AUTHTOKEN_TYPE_NORMAL_USER_LABEL;
        else
            return authTokenType + " (Label)";
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        final Bundle result = new Bundle();
        result.putBoolean(KEY_BOOLEAN_RESULT, false);
        return result;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }


    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }


}
