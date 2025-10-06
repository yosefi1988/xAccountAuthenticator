package ir.sajjadyosefi.android.sdkpayzarin.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://api.zarinpal.com/"; // URL پایه API
    private static Retrofit retrofit;

    public static Retrofit getInstance(Context applicationContext) {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new SimpleLoggingInterceptor(applicationContext))
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient() // فعال کردن lenient mode برای پذیرش JSONهای ناقص
                    .create();
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        //.client(new OkHttpClient())
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
            }
        }
        return retrofit;
    }

}
