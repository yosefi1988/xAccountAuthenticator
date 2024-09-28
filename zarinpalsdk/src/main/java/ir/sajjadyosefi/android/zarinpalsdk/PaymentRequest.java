package ir.sajjadyosefi.android.zarinpalsdk;

public class PaymentRequest {
    private String merchant_id; // شناسه مرچنت
    private int amount; // مبلغ
    private String description; // توضیحات
    private String callback_url; // آدرس برگشت
    private String mobile;
    private String expire_at;
    private String max_daily_count;
    private String max_monthly_count;
    private String max_amount;

    public PaymentRequest(String merchant_id, int amount, String description, String callback_url, String mobile, String expire_at, String max_daily_count, String max_monthly_count, String max_amount) {
        this.merchant_id = merchant_id;
        this.amount = amount;
        this.description = description;
        this.callback_url = callback_url;
        this.mobile = mobile;
        this.expire_at = expire_at;
        this.max_daily_count = max_daily_count;
        this.max_monthly_count = max_monthly_count;
        this.max_amount = max_amount;
    }

    // Getter و Setter ها
}
