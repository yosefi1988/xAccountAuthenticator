package ir.sajjadyosefi.android.xTubeless.networkLayout.retrofit.DownloadUploadPicture;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Streaming;


public interface RemoteApi {
    @Streaming  // Important
    @POST("/api/DownloadFileForAndroid")
    Call<ResponseBody> getImage(@Body ImageRequest body);

    class Factory {
        private static RemoteApi mInstance;

        public static RemoteApi create() {
            if (mInstance == null) {
                mInstance = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("http://shop.atiafkar.ir")
                        .build()
                        .create(RemoteApi.class);
            }
            return mInstance;
        }
    }
}