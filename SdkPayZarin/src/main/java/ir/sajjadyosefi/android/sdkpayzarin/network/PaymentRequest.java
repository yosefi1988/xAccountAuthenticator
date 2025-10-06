package ir.sajjadyosefi.android.sdkpayzarin.network;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

public class PaymentRequest {
    @SerializedName("metadata")
    public String[] metadata;

    @SerializedName("merchant_id")
    public String merchant_id;

    @SerializedName("amount")
    public String amount;

    @SerializedName("callback_url")
    public String callback_url;

    @SerializedName("description")
    public String description;


    public PaymentRequest(String merchant_id, String amount, String description, String callback_url,String mobile,String email ) {
        this.merchant_id = merchant_id;
        this.amount = amount;
        this.description = description;
        this.callback_url = callback_url;
        this.metadata = new String[2];
        if (mobile!=null)
        {
            this.metadata[0] = mobile;
        }
        if (email!=null)
        {
            this.metadata[1] = email;
        }
    }

    public String createBody() {
        String postData = "merchant_id=" + merchant_id +
                "&mobile=09123678522" +
                "&expire_at=2025-04-08 00:00:00" +
                "&max_daily_count=100" +
                "&max_monthly_count=1000" +
                "&max_amount=" + amount +
                "&amount=" + amount +
                "&description=" + description +
                //"&metadata=" + metadata +
                "&callback_url=" + callback_url;
        return postData;
    }
}
