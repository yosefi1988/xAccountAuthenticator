package ir.sajjadyosefi.accountauthenticator.classes;

public interface IDeviceRegister<T,X> {
    void onResponse(T isSuccess,X intent);
}
