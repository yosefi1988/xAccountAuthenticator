package ir.sajjadyosefi.accountauthenticator.authentication;

import ir.sajjadyosefi.accountauthenticator.model.request.ADeviceRegisterRequest;
import ir.sajjadyosefi.accountauthenticator.model.request.ALoginRequest;
import ir.sajjadyosefi.accountauthenticator.model.request.ATransactionListRequest;
import ir.sajjadyosefi.accountauthenticator.model.request.AWalletChargeRequest;
import ir.sajjadyosefi.accountauthenticator.model.response.ALoginResponse;
import ir.sajjadyosefi.accountauthenticator.model.response.AConfigResponse;
import ir.sajjadyosefi.accountauthenticator.model.response.ATransactionListResponse;
import ir.sajjadyosefi.accountauthenticator.model.response.AWalletChargeResponse;

public interface ServerAuthenticate {
    public ALoginResponse userSignUp(final ALoginRequest ALoginRequest) throws Exception;

    public ALoginResponse userSignIn(final ALoginRequest ALoginRequest) throws Exception;
    public AConfigResponse deviceRegister(final ADeviceRegisterRequest aDeviceRequestRegisterRegisterRequest) throws Exception;
    public ATransactionListResponse transactionList(final ATransactionListRequest aTransactionListRequest) throws Exception;
    public AWalletChargeResponse chargeWallet(final AWalletChargeRequest aDeviceRequestRegisterRegisterRequest) throws Exception;
}
