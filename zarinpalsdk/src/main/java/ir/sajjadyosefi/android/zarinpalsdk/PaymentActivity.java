package ir.sajjadyosefi.android.zarinpalsdk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zarrin_sdk_activity_payment);

        // اطلاعات پرداخت
        String merchantId = "YOUR_MERCHANT_ID"; // شناسه مرچنت خود را وارد کنید
        int amount = 1000; // مبلغ به ریال
        String description = "خرید محصول";
        String callbackUrl = "http://yourcallbackurl.com"; // آدرس برگشت

//        PaymentRequest paymentRequest ;//= new PaymentRequest(merchantId, amount, description, callbackUrl);
//
//        ZarinPalService zarinPalService = RetrofitClient.getInstance(getApplicationContext()).create(ZarinPalService.class);
//        Call<PaymentResponse> call = zarinPalService.createPayment(paymentRequest);
//
//        call.enqueue(new Callback<PaymentResponse>() {
//            @Override
//            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    PaymentResponse paymentResponse = response.body();
//                    if (paymentResponse.getStatus() == 100) {
//                        // انتقال به صفحه پرداخت زرین‌پال
//                        String authority = paymentResponse.getAuthority();
//                        String paymentUrl = "https://www.zarinpal.com/pg/StartPay/" + authority;
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(paymentUrl));
//                        startActivity(browserIntent);
//                    } else {
//                        Toast.makeText(PaymentActivity.this, "Payment Failed: " + paymentResponse.getStatus(), Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(PaymentActivity.this, "Payment Request Failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PaymentResponse> call, Throwable t) {
//                Toast.makeText(PaymentActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
