package ir.sb24.android.sdkpayzarin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ZarinPalService {
    //https://api.zarinpal.com/pg/v4/payment/request.json
    //https://api.zarinpal.com/

    @POST("/pg/v4/payment/request.json") // مسیر API برای پرداخت
    Call<PaymentResponse> createPayment(@Body PaymentRequest paymentRequest);

}