package ir.sajjadyosefi.accountauthenticator.classes;

public interface IDeviceRegisterRequest<T,X> {
    void onResponse(T isSuccess,X intent);
}
