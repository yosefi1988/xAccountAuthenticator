package ir.sb24.android.sdkpayzarin;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

            }
        }
    }

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
            boolean isDirect = extras.getBoolean("direct");

            if (isDirect) {
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
        Intent intent = new Intent(MainActivity.this, SdkPayZarinPaymentActivity.class);

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