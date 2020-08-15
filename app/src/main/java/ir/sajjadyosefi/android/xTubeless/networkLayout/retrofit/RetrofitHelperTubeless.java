package ir.sajjadyosefi.android.xTubeless.networkLayout.retrofit;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import ir.sajjadyosefi.android.xTubeless.R;
import ir.sajjadyosefi.android.xTubeless.classes.StaticValue;


import ir.sajjadyosefi.android.xTubeless.networkLayout.retrofit.DownloadUploadPicture.ImageRequest;
import ir.sajjadyosefi.android.xTubeless.networkLayout.retrofit.DownloadUploadPicture.RemoteApi;

import ir.sajjadyosefi.android.xTubeless.classes.model.request.ContactUsRequest;


import ir.sajjadyosefi.android.xTubeless.classes.model.network.request.accounting.LoginRequest;
import ir.sajjadyosefi.android.xTubeless.utility.DeviceUtil;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

import static ir.sajjadyosefi.android.xTubeless.classes.StaticValue.IDApplication;
import static ir.sajjadyosefi.android.xTubeless.networkLayout.networkLayout.Url.REST_API_IP_ADDRESS_MAIN;

public class RetrofitHelperTubeless {
    private static ApiServiceTubeless service;
    private static RetrofitHelperTubeless apiManager;

    private RetrofitHelperTubeless() {

        HostSelectionInterceptor interceptor = new HostSelectionInterceptor();
        //HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(interceptor)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(REST_API_IP_ADDRESS_MAIN)
                .addConverterFactory(GsonConverterFactory.create())
                //.addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(ApiServiceTubeless.class);

    }

    public static RetrofitHelperTubeless getInstance() {
        if (apiManager == null) {
            apiManager = new RetrofitHelperTubeless();
        }
        return apiManager;
    }


    public void loginOrRregisterMVP(LoginRequest request, Callback callback) {
        Call<Object> userCall = service.login(request);
        userCall.enqueue(callback);
    }

    public void getProfileImages(LoginRequest request, Callback callback) {
        Call<Object> userCall = service.profileImages(request);
        userCall.enqueue(callback);
    }


    public void newContactUs(ContactUsRequest request, TubelessRetrofitCallbackss callback) {
        Call<Object> userCall = service.contactUs(request);
        userCall.enqueue(callback);
    }















//    public void createUser(SearchRequest searchRequest, Callback<ServerResponse> callback) {
//        Call<ServerResponse> userCall = service.search(searchRequest);
//        userCall.enqueue(callback);
//    }.



//    public void requestUpload2(MultipartBody.Part file, RequestBody userName, RequestBody password, RequestBody androidId , RequestBody serialRequestCode , RequestBody fileType , RequestBody senderType, TubelessRetrofitCallback<Object> callback) {
//        Call<Object> userCall = service.upload2(file , userName,password,androidId, serialRequestCode , fileType , senderType);
//        userCall.enqueue(callback);
//    }




    public static final class HostSelectionInterceptor implements Interceptor {
        private volatile String host;


        @Override
        public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            String host = this.host;
            if (host != null) {
                //HttpUrl newUrl = request.url().newBuilder()
                //    .host(host)
                //    .build();
                HttpUrl newUrl = HttpUrl.parse(host);
                request = request.newBuilder()
                        .url(newUrl)
                        .build();
            }

//            SystemClock.sleep(1000 * 10);

            return chain.proceed(request);
        }
    }
}
