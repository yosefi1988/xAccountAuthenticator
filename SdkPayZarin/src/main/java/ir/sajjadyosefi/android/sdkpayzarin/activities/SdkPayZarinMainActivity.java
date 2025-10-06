package ir.sajjadyosefi.android.sdkpayzarin.activities;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import ir.sajjadyosefi.android.sdkpayzarin.R;

public class SdkPayZarinMainActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> paymentLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paymentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent returnIntent = result.getData();
                        Bundle bundle = new Bundle();
                        returnIntent.putExtras(bundle);
                        setResult(Activity.RESULT_OK, getIntent());
                    }else {
                        setResult(Activity.RESULT_CANCELED);
                    }
                    finish();
                }
        );
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("direct")) {
            boolean isDirectPaymentWithoutSdkUI = extras.getBoolean("direct");

            if (isDirectPaymentWithoutSdkUI) {
                startZarinPaymentActivity(extras);
            } else {
                setContentView(R.layout.activity_main);
                startZarinPaymentActivity(extras);
            }
        } else {
            finish();
        }


    }
    private void startZarinPaymentActivity(Bundle extras) {
        Intent intent = new Intent(SdkPayZarinMainActivity.this, SdkPayZarinPaymentActivity.class);

        if (extras.containsKey("merchant")) {
            intent.putExtra("merchant", extras.getString("merchant"));
        }
        if (extras.containsKey("description")) {
            intent.putExtra("description", extras.getString("description"));
        }
        if (extras.containsKey("callbackurl")) {
            intent.putExtra("callbackurl", extras.getString("callbackurl"));
        }
        if (extras.containsKey("amount")) {
            intent.putExtra("amount", extras.getString("amount"));
        }
        if (extras.containsKey("mobile")) {
            intent.putExtra("mobile", extras.getString("mobile"));
        }
        if (extras.containsKey("email")) {
            intent.putExtra("email", extras.getString("email"));
        }
        paymentLauncher.launch(intent);
    }
}