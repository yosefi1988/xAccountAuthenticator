package ir.sajjadyosefi.android.zarinpalsdk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.IOException;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zarrin_sdk_activity_payment);

        // اطلاعات پرداخت
        String merchantId = "e8a913e8-f089-11e6-8dec-005056a205be"; // شناسه مرچنت خود را وارد کنید
        int amount = 10000; // مبلغ به ریال
        String description = "خرید محصول";
        String callbackUrl = String.format("%s://%s", "https", "zarinpalpayment2"); // آدرس برگشت

        PaymentRequest paymentRequest = new PaymentRequest(merchantId, amount, description, callbackUrl,
                "09123678522",
                "2025-04-08 00:00:00",
                "100",
                "1000",
                "50000"
                );


        ZarinPalService zarinPalService = RetrofitClient.getInstance(getApplicationContext()).create(ZarinPalService.class);
        Call<PaymentResponse> call = zarinPalService.createPayment(paymentRequest);

        call.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                Gson gson = new Gson();
                JsonElement jsonElement = gson.toJsonTree(response.body());
                String stringError = null;

                //DEBUG
                try {
                    RequestBody request;
                    ResponseBody errorBody = response.errorBody();
                    String errorBodyString = null;

                    if(BuildConfig.DEBUG)
                    {
                        Log.i( "ZSDK" + "RESPONSE Code", String.valueOf(response.code()));

                        Buffer buffer = new Buffer();

                        if (response.raw().request().body() != null) {
                            response.raw().request().body().writeTo(buffer);
                            request = response.raw().request().body();
                        }
                        Log.i( "ZSDK" + "SNO", response.raw().request().url().toString());
                        Log.i( "ZSDK" + "REQUEST", buffer.readUtf8());
                        Log.i( "ZSDK" + "RESPONSE", String.valueOf(jsonElement));

                        if (response.code() == 400) {
                            // خواندن errorBody
                            try {
                                errorBodyString = errorBody.string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            stringError = (errorBodyString);
                            Log.i( "ZSDK" + "RESPONSE", stringError);
                        }
                    }else {
                        try {
                            errorBodyString = errorBody.string();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        stringError = (errorBodyString);
                    }
                }catch (Exception exception){
                    Log.i( "ZSDK" + "exception",exception.toString());
                }




                if (response.isSuccessful()) {
                    Object data = response.body();
                    data = response.body();
                    // پردازش داده‌ها
                } else {
                    // پردازش خطای سرور
                }

                if (response.isSuccessful() && response.body() != null) {
                    PaymentResponse paymentResponse = response.body();
                    if (paymentResponse.getStatus() == 100) {
                        // انتقال به صفحه پرداخت زرین‌پال
                        String authority = paymentResponse.getAuthority();
                        String paymentUrl = "https://www.zarinpal.com/pg/StartPay/" + authority;
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(paymentUrl));
                        startActivity(browserIntent);
                    } else {
                        Toast.makeText(PaymentActivity2.this, "Payment Failed: " + paymentResponse.getStatus(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PaymentActivity2.this, "Payment Request Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                Toast.makeText(PaymentActivity2.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
