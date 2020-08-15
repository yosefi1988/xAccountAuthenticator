package ir.sajjadyosefi.android.xTubeless.networkLayout.retrofit;

import android.graphics.Movie;

import ir.sajjadyosefi.android.xTubeless.classes.model.network.request.accounting.LoginRequest;
//import ir.sajjadyosefi.android.xTubeless.classes.modelY.request.common.ContactUsRequest;
import ir.sajjadyosefi.android.xTubeless.classes.model.request.ContactUsRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by sajjad on 11/7/2018.
 */

public interface ApiServiceTubeless {



    @POST("Api/User/Login")
    Call<Object> login(@Body LoginRequest request);


    @POST("Api/Message/SendMessage")
    Call<Object> contactUs(@Body ContactUsRequest request);

    @POST("Api/User/userImageProfileAndAvatar")
    Call<Object> profileImages(@Body LoginRequest request);



}
