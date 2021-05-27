package ir.sajjadyosefi.accountauthenticator.authentication;

import ir.sajjadyosefi.accountauthenticator.model.request.ADeviceRegisterRequest;
import ir.sajjadyosefi.accountauthenticator.model.request.ALoginRequest;
import ir.sajjadyosefi.accountauthenticator.model.response.ALoginResponse;
import ir.sajjadyosefi.accountauthenticator.model.response.AConfigResponse;

public interface ServerAuthenticate {
    public ALoginResponse userSignUp(final ALoginRequest ALoginRequest) throws Exception;

    public ALoginResponse userSignIn(final ALoginRequest ALoginRequest) throws Exception;
    public AConfigResponse deviceRegister(final ADeviceRegisterRequest aDeviceRequestRegisterRegisterRequest) throws Exception;
}
