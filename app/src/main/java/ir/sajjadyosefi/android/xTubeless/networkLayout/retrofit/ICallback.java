package ir.sajjadyosefi.android.xTubeless.networkLayout.retrofit;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Response;

public interface ICallback {

    void t_afterGetResponse();
    void t_retry(Call<Object> call);
    void t_responseNull();
    void t_beforeSendRequest();
    void t_complite();
    void t_onSuccess(Object response);
}
