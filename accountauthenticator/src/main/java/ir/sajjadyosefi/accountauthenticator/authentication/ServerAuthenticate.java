package ir.sajjadyosefi.accountauthenticator.authentication;

import ir.sajjadyosefi.accountauthenticator.model.LoginRequest;
import ir.sajjadyosefi.accountauthenticator.model.User;

public interface ServerAuthenticate {
    public User userSignUp(final LoginRequest loginRequest) throws Exception;
    public User userSignIn(final LoginRequest loginRequest) throws Exception;
}
