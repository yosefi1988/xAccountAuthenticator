package ir.sajjadyosefi.accountauthenticator.authentication;

import ir.sajjadyosefi.accountauthenticator.model.request.AChangePasswordRequest;
import ir.sajjadyosefi.accountauthenticator.model.request.ADeviceRegisterRequest;
import ir.sajjadyosefi.accountauthenticator.model.request.ALoginRequest;
import ir.sajjadyosefi.accountauthenticator.model.request.ATransactionListRequest;
import ir.sajjadyosefi.accountauthenticator.model.request.ATransactionRequest;
import ir.sajjadyosefi.accountauthenticator.model.response.ALoginResponse;
import ir.sajjadyosefi.accountauthenticator.model.response.AConfigResponse;
import ir.sajjadyosefi.accountauthenticator.model.response.ATransactionListResponse;
import ir.sajjadyosefi.accountauthenticator.model.response.AWalletChargeResponse;

public interface ServerAuthenticate {

    //1 loginProcess    in SignInActivity
    //2 submit          in AuthenticatorActivity    -- not used
    //3 getAuthToken    in Authenticator class      -- not used
    ALoginResponse userSignIn(final ALoginRequest ALoginRequest) throws Exception;

    //SignUpActivity    in SignUpActivity           -- not used
    ALoginResponse userSignUp(final ALoginRequest ALoginRequest) throws Exception;

    //ChangePasswordActivity
    ALoginResponse changePassword(final AChangePasswordRequest aChangePasswordRequest) throws Exception;

    //tryDeviceRegister     in SplashScreen
    AConfigResponse deviceRegister(final ADeviceRegisterRequest aDeviceRequestRegisterRegisterRequest) throws Exception;

    //                                              -- not used
    ATransactionListResponse transactionList(final ATransactionListRequest aTransactionListRequest) throws Exception;

    //                                              -- not used
    AWalletChargeResponse chargeWallet(final ATransactionRequest aDeviceRequestRegisterRegisterRequest) throws Exception;
}
