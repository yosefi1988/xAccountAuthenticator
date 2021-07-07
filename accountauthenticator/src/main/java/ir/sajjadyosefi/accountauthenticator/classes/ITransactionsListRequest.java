package ir.sajjadyosefi.accountauthenticator.classes;

public interface ITransactionsListRequest<T,X> {
    void onResponse(T isSuccess, X intent);
}
