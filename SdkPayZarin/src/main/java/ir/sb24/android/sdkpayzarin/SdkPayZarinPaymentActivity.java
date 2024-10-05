package ir.sb24.android.sdkpayzarin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

public class SdkPayZarinPaymentActivity extends AppCompatActivity {

    private String merchant = "e8a913e8-f089-11e6-8dec-005056a205be";
    private String description = "خرید تستی ";
    private String callbackurl = "http://localhost:2812/Home/VerifyByHttpClient";
    private String amount = "100000"; // مبلغ به ریال
    private String mobile = "09123678522";
    private String email = "yosefi1988@gmail.com";

    private String gateWayUrl = "https://www.zarinpal.com/pg/StartPay/";
    //private String verifyUrl = "https://api.zarinpal.com/pg/v4/payment/verify.json";

    private String authority;
    private WebView paymentWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zarrin_sdk_activity_payment);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("merchant")) {
                merchant = extras.getString("merchant");
            }
            if (extras.containsKey("description")) {
                description = extras.getString("description");
            }
            if (extras.containsKey("callbackurl")) {
                callbackurl = extras.getString("callbackurl");
            }
            if (extras.containsKey("amount")) {
                amount = extras.getString("amount");
            }
            if (extras.containsKey("mobile")) {
                mobile = extras.getString("mobile");
            }
            if (extras.containsKey("email")) {
                email = extras.getString("email");
            }
        }
        paymentWebView = findViewById(R.id.paymentWebView);
        paymentWebView.getSettings().setJavaScriptEnabled(true);
        paymentWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains(callbackurl)) {
                    if (url.contains("Status") && url.contains("NOK")) {
                        setResult(Activity.RESULT_CANCELED);
                        finish();
                        return true;
                    }else if (url.contains("Status") && url.contains("OK")){
                        Intent returnIntent = getIntent();
                        Bundle bundle = new Bundle();
                        returnIntent.putExtras(bundle);
                        setResult(Activity.RESULT_OK, getIntent());
                        finish();
                        return true;
                    }
                }
                return false;
            }
        });

        PaymentRequest paymentRequest = new PaymentRequest(merchant, amount, description, callbackurl, mobile, email);
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

                if (response.isSuccessful() && response.body() != null) {
                    PaymentResponse paymentResponse = response.body();
                    if (paymentResponse.getData().getCode() == 100) {
                        // انتقال به صفحه پرداخت زرین‌پال
                        String authority = paymentResponse.getData().getAuthority();
                        String paymentUrl = gateWayUrl + authority ;
                        paymentWebView.postUrl(paymentUrl,null);
                    } else {
                        Toast.makeText(SdkPayZarinPaymentActivity.this, "Payment Failed: " + paymentResponse.getData().getCode(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SdkPayZarinPaymentActivity.this, "Payment Request Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                Toast.makeText(SdkPayZarinPaymentActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
