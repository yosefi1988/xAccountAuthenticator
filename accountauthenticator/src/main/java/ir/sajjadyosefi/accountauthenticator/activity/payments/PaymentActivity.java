package ir.sajjadyosefi.accountauthenticator.activity.payments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
//import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
//import com.zarinpal.ewallets.purchase.OnCallbackVerificationPaymentListener;
//import com.zarinpal.ewallets.purchase.PaymentRequest;
//import com.zarinpal.ewallets.purchase.ZarinPal;


import ir.sajjadyosefi.accountauthenticator.R;
import ir.sajjadyosefi.accountauthenticator.classes.exception.TubelessException;
import ir.sajjadyosefi.accountauthenticator.model.request.ATransactionRequest;
import ir.sajjadyosefi.accountauthenticator.model.response.AWalletChargeResponse;
import ir.sajjadyosefi.android.sdkpayzarin.activities.SdkPayZarinMainActivity;

import static ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral.sServerAuthenticate;

public class PaymentActivity extends AppCompatActivity {

    public static final int GO_TO_LOGIN = 20;

    private static boolean withoutUi = false;
    private static boolean paySuccess = false;
    private static boolean _isCharge = false;
    private static boolean isDirectPayment = false;
    private static Intent paymentIntent;
    private static Bundle bundle;

    private ViewGroup rootActivity;
    public Button btnPay , buttonBack ;
    EditText editTextPhone, editTextAmount,editTextDiscription;
    TextView textViewPrice1,textViewPrice2,textViewPrice3,textViewPrice4;
    Context context;

    private int tax , portService;
    private static String phone ,amountX,discription ,textViewPrice4Shadow;

    public synchronized static Intent getIntent(Context context) {
        return getIntent(context,null);
    }

    public static boolean isPaymentSuccess() {
        return paySuccess;
    }

    public static void PaymentDone() {
        try {
            withoutUi = false;
            paySuccess = false;
            paymentIntent = null;
            bundle = null;
            _isCharge = false;
            isDirectPayment = false;
        }catch (Exception e){
        }
    }

    public static Intent getPaymentIntent() {
        if (paySuccess == false)
            return null;
        else {
            return paymentIntent;
//            noUi = false;
//            paySuccess = false;
//            paymentIntent = null;
//            bundle = null;
        }
    }

    public synchronized static Intent getIntent(Context context, Bundle bundle) {
        if (bundle == null)
            bundle = new Bundle();
        bundle.putString("item1","value1");
        Intent intent = new Intent(context,PaymentActivity.class);
        intent.putExtras(bundle);
        return intent;
    }
    ActivityResultLauncher launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        context = this;
        Uri data2 = getIntent().getData();
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        paySuccess = true;
                        Intent x = PaymentActivity.getPaymentIntent();
                        ATransactionRequest aTransactionRequest = null; // تبدیل به ریال
                        try {
                            aTransactionRequest = new ATransactionRequest(calcBillHide() + "");
                        } catch (Exception exception) {

                            //todo remove
                            aTransactionRequest = new ATransactionRequest("110015", calcBillHide() + "");
                            exception.printStackTrace();
                        }
                        aTransactionRequest.setMetaData(discription + "\nTransactionID:" + "authority");

                        if (_isCharge) {
                            aTransactionRequest.setDirectPay(false);
                        }else {
                            if (isDirectPayment) {
                                aTransactionRequest.setDirectPay(true);
                            } else {
                                aTransactionRequest.setDirectPay(false);
                            }
                        }

                        if (_isCharge)
                            createTransaction(aTransactionRequest);
                        else {
                            if (withoutUi) {
                                Bundle bundle = new Bundle();
                                Gson gson = new Gson();
                                bundle.putString("ReturnData","direct Payment");
                                paymentIntent.putExtras(bundle);
                                ((Activity)context).setResult(Activity.RESULT_OK, paymentIntent);
                                ((Activity)context).finish();
                            } else {
                                final BottomSheetDialog dialog = new BottomSheetDialog(context);
                                TubelessException.ShowSheetDialogMessage(context, dialog, context.getString(R.string.new_yafte_new_yafte_inserted), "ok", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Gson gson = new Gson();
                                        bundle.putString("ReturnData","direct Payment");
                                        paymentIntent.putExtras(bundle);
                                        setResult(Activity.RESULT_OK, paymentIntent);
                                        finish();
                                    }
                                });
                            }
                        }
                    }else {
                        //                    Toast.makeText(getBaseContext(),"pay not success" , Toast.LENGTH_LONG).show();
//                    show message refID
//                    Toast.makeText(context,"not ok " , Toast.LENGTH_LONG).show();
                        paySuccess = false;

                        if (paymentIntent.hasExtra("type")){
//                        if (paymentIntent.getIntExtra("type",1) == 2){
                            setResult(Activity.RESULT_CANCELED);
                            finish();
//                        }
                        }
                    }
                    //finish();
                });

        if (paymentIntent != null) {
            if (paymentIntent.hasExtra("type")) {
                if (paymentIntent.getIntExtra("type", 1) == 2) {
//                    withoutUi = true;
//                    AWalletChargeRequest request = new AWalletChargeRequest("10000");
//                    request.setMetaData(discription);
//                    amount = request.getAmount();
//                    payment(Integer.parseInt(amount), this);
                } else {
                    setContentView(R.layout.activity_payment);
                }
                paymentIntent = getIntent();
                bundle = paymentIntent.getExtras();
            } else {
                setContentView(R.layout.activity_payment);
            }
        }else {
            //first loading +   intent == nul
            if (getIntent().hasExtra("type")) {
                paymentIntent = getIntent();
                bundle = paymentIntent.getExtras();
                if (paymentIntent.getIntExtra("type", 1) == 2) {
                    withoutUi = true;
                    amountX = paymentIntent.getIntExtra("amount", 10000) + "";
                    phone = paymentIntent.getStringExtra("phone");
                    tax = Integer.parseInt(paymentIntent.getStringExtra("tax"));
                    _isCharge = paymentIntent.getBooleanExtra("isCharge",false);
                    isDirectPayment = paymentIntent.getBooleanExtra("isDirectPayment",false);
                    portService = Integer.parseInt(paymentIntent.getStringExtra("portService"));

                    StringBuilder stringBuilderMetaData = new StringBuilder();
                    stringBuilderMetaData.append("مبلغ کاربر:");
                    stringBuilderMetaData.append(amountX);
                    stringBuilderMetaData.append("ريال");
                    stringBuilderMetaData.append("\n");
                    stringBuilderMetaData.append("مبلغ درگاه:");
                    stringBuilderMetaData.append(calcBillHide());
                    stringBuilderMetaData.append("ريال");
                    stringBuilderMetaData.append("\n");
                    stringBuilderMetaData.append("توضیحات ");
                    stringBuilderMetaData.append(paymentIntent.getStringExtra("metaData"));
                    stringBuilderMetaData.append("\n");

                    if (paymentIntent.hasExtra("type")) {
                        stringBuilderMetaData.append("نوع رابط کاربری :");
                        if (paymentIntent.getIntExtra("type", 1) == 1) {
                            stringBuilderMetaData.append("با صفحه پرداخت ");
                        }else {
                            stringBuilderMetaData.append("بدون صفحه پرداخت ");
                        }
                        stringBuilderMetaData.append("\n");
                    }
                    if (paymentIntent.hasExtra("isCharge")) {
                        stringBuilderMetaData.append("نوع واریز ");
                        if (paymentIntent.getBooleanExtra("isCharge",false) == true) {
                            stringBuilderMetaData.append("شارژ کیف پول ");
                        }else {
                            stringBuilderMetaData.append("خرید/واریز ");
                        }
                        stringBuilderMetaData.append("\n");
                    }
                    if (paymentIntent.hasExtra("isDirectPayment")) {
                        stringBuilderMetaData.append("نوع پرداخت ");
                        if (paymentIntent.getBooleanExtra("isDirectPayment",false) == true) {
                            stringBuilderMetaData.append("پرداخت مستقیم ");
                        }else {
                            stringBuilderMetaData.append("کیف پول ");
                        }
                        stringBuilderMetaData.append("\n");
                    }
                    discription = stringBuilderMetaData.toString();

                    zarrinPayment(calcBillHide() / 10, Long.parseLong(amountX) / 10, this);
                }else {
                    setContentView(R.layout.activity_payment);
                }
            }else {
                setContentView(R.layout.activity_payment);
            }
        }

        rootActivity = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        btnPay = findViewById(R.id.btnPay);
        buttonBack = findViewById(R.id.buttonBack);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextAmount = findViewById(R.id.editTextAmount);
        editTextDiscription = findViewById(R.id.editTextDiscription);

        textViewPrice1 = findViewById(R.id.textViewPrice1);
        textViewPrice2 = findViewById(R.id.textViewPrice2);
        textViewPrice3 = findViewById(R.id.textViewPrice3);
        textViewPrice4 = findViewById(R.id.textViewPrice4);

        if (paymentIntent.hasExtra("tax"))
            tax = Integer.parseInt(paymentIntent.getStringExtra("tax"));
        if (paymentIntent.hasExtra("portService"))
            portService = Integer.parseInt(paymentIntent.getStringExtra("portService"));
        if (paymentIntent.hasExtra("isCharge"))
            _isCharge = paymentIntent.getBooleanExtra("isCharge",false);
        if (paymentIntent.hasExtra("isDirectPayment"))
            isDirectPayment = paymentIntent.getBooleanExtra("isDirectPayment",false);

        if (btnPay != null) {
            editTextAmount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().length() > 1) {
                        amountX = s.toString();
                        calcBill();
                    }
                }
            });
            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    phone = editTextPhone.getText().toString();
                    amountX = editTextAmount.getText().toString();
                    StringBuilder stringBuilderMetaData = new StringBuilder();
                    stringBuilderMetaData.append("موبایل پرداخت کننده:");
                    stringBuilderMetaData.append(phone);
                    stringBuilderMetaData.append("\n");
                    stringBuilderMetaData.append("مبلغ کاربر:");
                    stringBuilderMetaData.append(amountX);
                    stringBuilderMetaData.append("ريال");
                    stringBuilderMetaData.append("\n");
                    stringBuilderMetaData.append("مبلغ درگاه:");
                    stringBuilderMetaData.append(Integer.parseInt(textViewPrice4Shadow));
                    stringBuilderMetaData.append("ريال");
                    stringBuilderMetaData.append("\n");
                    stringBuilderMetaData.append("توضیحات ");
                    stringBuilderMetaData.append(editTextDiscription.getText().toString());
                    stringBuilderMetaData.append("\n");

                    if (paymentIntent.hasExtra("type")) {
                        stringBuilderMetaData.append("نوع رابط کاربری :");
                        if (paymentIntent.getIntExtra("type", 1) == 1) {
                            stringBuilderMetaData.append("با صفحه پرداخت ");
                        }else {
                            stringBuilderMetaData.append("بدون صفحه پرداخت ");
                        }
                        stringBuilderMetaData.append("\n");
                    }
                    if (paymentIntent.hasExtra("isCharge")) {
                        stringBuilderMetaData.append("نوع واریز ");
                        if (paymentIntent.getBooleanExtra("isCharge",false) == true) {
                            stringBuilderMetaData.append("شارژ کیف پول ");
                        }else {
                            stringBuilderMetaData.append("خرید/واریز ");
                        }
                        stringBuilderMetaData.append("\n");
                    }

                    if (paymentIntent.hasExtra("isDirectPayment")) {
                        stringBuilderMetaData.append("نوع پرداخت ");
                        if (paymentIntent.getBooleanExtra("isDirectPayment",false) == true) {
                            stringBuilderMetaData.append("پرداخت مستقیم ");
                        }else {
                            stringBuilderMetaData.append("کیف پول ");
                        }
                        stringBuilderMetaData.append("\n");
                    }

                    discription = stringBuilderMetaData.toString();
                    zarrinPayment(Integer.parseInt(textViewPrice4Shadow) / 10, Long.parseLong(amountX) / 10, context);
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    private void calcBill() {
        textViewPrice1.setText(amountX + "");
        textViewPrice2.setText((((Integer.parseInt(amountX) * tax) / 100)) + "");
        textViewPrice3.setText((((Integer.parseInt(amountX) * portService) / 100)) + "");

        StringBuilder stringBuilderSum = new StringBuilder();
        String sumRial = (Integer.parseInt(textViewPrice1.getText().toString())) +
                (Integer.parseInt(textViewPrice2.getText().toString())) +
                (Integer.parseInt(textViewPrice3.getText().toString())) +
                "";
        stringBuilderSum.append(sumRial);
        textViewPrice4Shadow = sumRial;
        stringBuilderSum.append("\n");
        stringBuilderSum.append("(");
        stringBuilderSum.append("معادل:");
        stringBuilderSum.append(((Integer.parseInt(textViewPrice1.getText().toString())) +
                (Integer.parseInt(textViewPrice2.getText().toString())) +
                (Integer.parseInt(textViewPrice3.getText().toString()))) / 10);
        stringBuilderSum.append("تومان");
        stringBuilderSum.append(")");
        textViewPrice4.setText(stringBuilderSum.toString());
    }
    private long calcBillHide() {
        long sumVal = 0;
        int a = (((Integer.parseInt(amountX) * tax) / 100));
        int b = (((Integer.parseInt(amountX) * portService) / 100));
        sumVal = a + b + Integer.parseInt(amountX);
        return sumVal;
    }

    private void zarrinPayment(long amountZarrinToman,long amountTubelessToman, Context context) {

        Intent intent = new Intent(this, SdkPayZarinMainActivity.class);
        intent.putExtra("direct",true);         //is Direct Payment Without Sdk UI
        intent.putExtra("merchant", "e8a913e8-f089-11e6-8dec-005056a205be");
        intent.putExtra("description",(discription.length() == 0 ? "" : discription));
        //intent.putExtra("callbackurl",String.format("%s://%s", "https", "zarinpalpayment2"));
        intent.putExtra("callbackurl","https://sajjadyosefi.ir");
        intent.putExtra("amount", Long.toString(amountZarrinToman * 10));
        intent.putExtra("mobile", phone);
        intent.putExtra("email", "yosefi1988@gmail.com");


        launcher.launch(intent);

    }
//    private void zarrinPayment0(long amountZarrinToman,long amountTubelessToman, Context context) {
//        //new
//        BillingClientStateListener stateListener = new BillingClientStateListener() {
//            @Override
//            public void onClientSetupFinished(ClientState clientState) {
//
//                int a = 5;
//                a++;
//            }
//            @Override
//            public void onClientServiceDisconnected() {
//
//                int b = 6;
//                b++;
//            }
//        };
//        ZarinPalBillingClient client = ZarinPalBillingClient.newBuilder(this)
//                .enableShowInvoice()
//                .setListener(stateListener)
//                .build();
//
//        Purchase purchase = Purchase.newBuilder()
//                .asPaymentRequest(
//                        "e8a913e8-f089-11e6-8dec-005056a205be",
//                        amountZarrinToman,
//                        String.format("%s://%s", "https", "zarinpalpayment2"),
////                        String.format("%s://%s", AccountGeneral.getSchemezarinpalpayment(), AccountGeneral.getZarinpalpayment()),
//                        (discription.length() == 0 ? "" : discription),
//                        phone,
//                        "yosefi1988@gmail.com"
//                ).build();
//
//
//        FutureCompletionListener<Receipt> receiptFutureCompletionListener = task -> {
//            //sdk
//            if (task.isSuccess()) {
//                boolean receipt = task.isSuccess();
//                paySuccess = true;
//                Intent x = PaymentActivity.getPaymentIntent();
//                ATransactionRequest aTransactionRequest = null; // تبدیل به ریال
//                try {
//                    aTransactionRequest = new ATransactionRequest(amountTubelessToman + "");
//                } catch (Exception exception) {
//
//                    //todo remove
//                    aTransactionRequest = new ATransactionRequest("110015", amountTubelessToman + "");
//                    exception.printStackTrace();
//                }
//
//                aTransactionRequest.setMetaData(discription + "\nTransactionID:" + task.getSuccess().getTransactionID());
//
//                if (_isCharge) {
//                    aTransactionRequest.setDirectPay(false);
//                }else {
//                    if (isDirectPayment) {
//                        aTransactionRequest.setDirectPay(true);
//                    } else {
//                        aTransactionRequest.setDirectPay(false);
//                    }
//                }
//
//                if (_isCharge)
//                    chargeAccount(aTransactionRequest);
//                else {
//                    if (withoutUi) {
//                        Bundle bundle = new Bundle();
//                        Gson gson = new Gson();
//                        bundle.putString("ReturnData","direct Payment");
//                        paymentIntent.putExtras(bundle);
//                        ((Activity)context).setResult(Activity.RESULT_OK, paymentIntent);
//                        ((Activity)context).finish();
//                    } else {
//                        final BottomSheetDialog dialog = new BottomSheetDialog(context);
//                        TubelessException.ShowSheetDialogMessage(context, dialog, context.getString(R.string.new_yafte_new_yafte_inserted), "ok", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                                Gson gson = new Gson();
//                                bundle.putString("ReturnData","direct Payment");
//                                paymentIntent.putExtras(bundle);
//                                setResult(Activity.RESULT_OK, paymentIntent);
//                                finish();
//                            }
//                        });
//
//                    }
//                }
//            } else {
////                    Toast.makeText(getBaseContext(),"pay not success" , Toast.LENGTH_LONG).show();
////                    show message refID
////                    Toast.makeText(context,"not ok " , Toast.LENGTH_LONG).show();
//                paySuccess = false;
//
//                if (paymentIntent.hasExtra("type")){
////                        if (paymentIntent.getIntExtra("type",1) == 2){
//                    setResult(Activity.RESULT_CANCELED);
//                    finish();
////                        }
//                }
//            }
//        };
//        client.launchBillingFlow(purchase,receiptFutureCompletionListener);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GO_TO_LOGIN){
            if (resultCode == Activity.RESULT_OK){

            }else {
                finish();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void createTransaction(final ATransactionRequest chargeRequest) {
        new AsyncTask<String, Void, AWalletChargeResponse>() {
            @Override
            protected AWalletChargeResponse doInBackground(String... params) {
                AWalletChargeResponse response = null;
                try {
                    response = sServerAuthenticate.createWalletTransaction(chargeRequest);
                } catch (Exception e) {  }
                return response;
            }

            @Override
            protected void onPostExecute(final AWalletChargeResponse aWalletChargeResponse) {
                final BottomSheetDialog dialog = new BottomSheetDialog(context);
                if (withoutUi) {
                    if (aWalletChargeResponse == null){
                        TubelessException.ShowSheetDialogMessage(context, dialog, context.getString(R.string.tray_again), context.getString(R.string.tray_again), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        return;
                    }
                    if (aWalletChargeResponse.getTubelessException().getCode() == 200) {
                        Bundle bundle = new Bundle();
                        Gson gson = new Gson();
                        bundle.putString("ReturnData",gson.toJson(aWalletChargeResponse));
                        paymentIntent.putExtras(bundle);
                        ((Activity)context).setResult(Activity.RESULT_OK, paymentIntent);
                        ((Activity)context).finish();
                    }
                } else {
                    if (aWalletChargeResponse != null && aWalletChargeResponse.getTubelessException().getCode() == 200) {
                        TubelessException.ShowSheetDialogMessage(context, dialog, context.getString(R.string.new_yafte_new_yafte_inserted), "ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Gson gson = new Gson();
                                bundle.putString("ReturnData",gson.toJson(aWalletChargeResponse));
                                paymentIntent.putExtras(bundle);
                                setResult(Activity.RESULT_OK, paymentIntent);
                                finish();
                            }
                        });
                    } else {
                        TubelessException.ShowSheetDialogMessage(context, dialog, context.getString(R.string.tray_again), context.getString(R.string.tray_again), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                    }
                }
            }
        }.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        paymentIntent = null;
        paySuccess = false;
    }
}
