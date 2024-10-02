package ir.sb24.android.sdkpayzarin;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, PaymentActivity2.class);
        intent.putExtra("paymentUrl", PaymentActivity2.requestUrl);
        startActivity(intent);
    }
}