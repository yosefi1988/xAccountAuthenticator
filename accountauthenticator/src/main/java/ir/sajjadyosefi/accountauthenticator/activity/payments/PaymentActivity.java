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

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.zarinpal.ZarinPalBillingClient;
import com.zarinpal.billing.purchase.Purchase;
import com.zarinpal.client.BillingClientStateListener;
import com.zarinpal.client.ClientState;
import com.zarinpal.provider.core.future.FutureCompletionListener;
import com.zarinpal.provider.core.future.TaskResult;
import com.zarinpal.provider.model.response.Receipt;
//import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
//import com.zarinpal.ewallets.purchase.OnCallbackVerificationPaymentListener;
//import com.zarinpal.ewallets.purchase.PaymentRequest;
//import com.zarinpal.ewallets.purchase.ZarinPal;


import ir.sajjadyosefi.accountauthenticator.R;
import ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral;
import ir.sajjadyosefi.accountauthenticator.classes.IDeviceRegisterRequest;
import ir.sajjadyosefi.accountauthenticator.classes.exception.TubelessException;
import ir.sajjadyosefi.accountauthenticator.model.request.ATransactionRequest;
import ir.sajjadyosefi.accountauthenticator.model.response.AWalletChargeResponse;

import static ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral.sServerAuthenticate;

public class PaymentActivity extends Activity {

    public static final int GO_TO_LOGIN = 20;

    private static boolean withoutUi = false;
    private static boolean paySuccess = false;
    private static boolean isCharge = false;
    private static boolean isDirectPayment = false;
    private static Intent paymentIntent;
    private static Bundle bundle;

    private ViewGroup rootActivity;
    public Button btnPay , buttonBack ;
    EditText editTextPhone, editTextAmount,editTextDiscription;
    TextView textViewPrice1,textViewPrice2,textViewPrice3,textViewPrice4;
    Context context;

    private int tax , portService;
    private static String phone ,amountX,discription ;

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
            isCharge = false;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);



        //
        context = this;
        Uri data2 = getIntent().getData();

        if (paymentIntent != null) {
            if (paymentIntent.hasExtra("type")) {
                if (paymentIntent.getIntExtra("type", 1) == 2) {
//                    withoutUi = true;
//                    AWalletChargeRequest request = new AWalletChargeRequest("1000");
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
                    amountX = paymentIntent.getIntExtra("amount", 1000) + "";
                    discription = paymentIntent.getStringExtra("metaData");
                    tax = Integer.parseInt(paymentIntent.getStringExtra("tax"));
                    isCharge = paymentIntent.getBooleanExtra("isCharge",false);
                    isDirectPayment = paymentIntent.getBooleanExtra("isDirectPayment",false);
                    portService = Integer.parseInt(paymentIntent.getStringExtra("portService"));
                    zarrinPayment(calcBillHide(),Long.parseLong(amountX), this);
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
            isCharge = paymentIntent.getBooleanExtra("isCharge",false);
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
                    amountX = s.toString();
                    calcBill();
                }
            });
            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    phone = editTextPhone.getText().toString();
                    amountX = editTextAmount.getText().toString();
                    discription = editTextDiscription.getText().toString();
                    zarrinPayment(Integer.parseInt(textViewPrice4.getText().toString()),Long.parseLong(amountX), context);
                }
            });
        }

//        if (Global.IDUser == NOT_LOGN_USER ){
//        if (Global.user == null || Global.user.getUserId() == NOT_LOGN_USER ){
//            Bundle bundle = new Bundle();
//            Intent intent = SignInActivity.getIntent(context,bundle);
//            bundle.putParcelable(AccountManager.KEY_INTENT, intent);
//            startActivityForResult(intent, GO_TO_LOGIN);
//        }


//            Bundle bundle = new Bundle();
//            Gson gson = new Gson();
//            bundle.putString("ReturnData", gson.toJson("response"));
//            intent.putExtras(bundle);
//            setResult(Activity.RESULT_OK, intent);
//
//            finish();



    }

    @SuppressLint("SetTextI18n")
    private void calcBill() {
        textViewPrice1.setText(amountX);
        textViewPrice2.setText((((Integer.parseInt(amountX) * tax) / 100)) + "");
        textViewPrice3.setText((((Integer.parseInt(amountX) * portService) / 100)) + "");
        textViewPrice4.setText((
                (Integer.parseInt(textViewPrice1.getText().toString())) +
                (Integer.parseInt(textViewPrice2.getText().toString())) +
                (Integer.parseInt(textViewPrice3.getText().toString()))
        ) + "");
    }
    private long calcBillHide() {
        long sumVal = 0;
        int a = (((Integer.parseInt(amountX) * tax) / 100));
        int b = (((Integer.parseInt(amountX) * portService) / 100));
        sumVal = a + b + Integer.parseInt(amountX);
        return sumVal;
    }

    private void zarrinPayment(long amountZarrin,long amountTubeless, Context context) {
        //new
        BillingClientStateListener stateListener = new BillingClientStateListener() {
            @Override
            public void onClientSetupFinished(ClientState clientState) {

                int a = 5;
                a++;
            }
            @Override
            public void onClientServiceDisconnected() {

                int b = 6;
                b++;
            }
        };
        ZarinPalBillingClient client = ZarinPalBillingClient.newBuilder(this)
                .enableShowInvoice()
                .setListener(stateListener)
                .build();

        Purchase purchase = Purchase.newBuilder()
                .asPaymentRequest(
                        "e8a913e8-f089-11e6-8dec-005056a205be",
                        amountZarrin,
//                        String.format("%s://%s","return5", "zarinpalpayment5" ),
                        String.format("%s://%s", AccountGeneral.getSchemezarinpalpayment(), AccountGeneral.getZarinpalpayment()),
                        (discription.length() == 0 ? "" : discription),
                        phone,
                        "yosefi1988@gmail.com"
                ).build();

        FutureCompletionListener<Receipt> receiptFutureCompletionListener = new FutureCompletionListener<Receipt>() {
            @Override
            public void onComplete(TaskResult<Receipt> task) {

                //sdk
//                Toast.makeText(getBaseContext(),"pay Complete" , Toast.LENGTH_LONG).show();
                if (task.isSuccess()) {
//                    Toast.makeText(getBaseContext(),"pay success" , Toast.LENGTH_LONG).show();
                    boolean receipt = task.isSuccess();

//                    Toast.makeText(context,"pay success" ,Toast.LENGTH_LONG).show();
                    paySuccess = true;
                    Intent x = PaymentActivity.getPaymentIntent();
                    ATransactionRequest aTransactionRequest = null; // تبدیل به ریال
                    try {
                        aTransactionRequest = new ATransactionRequest(amountTubeless + "0");
                    } catch (Exception exception) {

                        //todo remove
                        aTransactionRequest = new ATransactionRequest("110015", amountTubeless + "0");
                        exception.printStackTrace();
                    }
                    aTransactionRequest.setMetaData(discription);

                    if (isCharge)
                        aTransactionRequest.setDirectPay(false);
                    else {
                        if (isDirectPayment) {
                            aTransactionRequest.setDirectPay(true);
                        } else {
                            aTransactionRequest.setDirectPay(false);
                        }
                    }

                    if (isCharge)
                        chargeAccount(aTransactionRequest);
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
                } else {
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
                //PaymentActivity.PaymentDone();
            }
        };
        client.launchBillingFlow(purchase,receiptFutureCompletionListener);
    }

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
    private void chargeAccount(final ATransactionRequest chargeRequest) {

        new AsyncTask<String, Void, AWalletChargeResponse>() {
            @Override
            protected AWalletChargeResponse doInBackground(String... params) {
                AWalletChargeResponse response = null;
                try {
                    response = sServerAuthenticate.chargeWallet(chargeRequest);
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

    @SuppressLint("StaticFieldLeak")
    public void chargeAcountWithoutUi(final ATransactionRequest request, Context context, final IDeviceRegisterRequest<Boolean,Intent> callback){
        //showProgressBar();

//        new AsyncTask<String, Void, Intent>() {
//            @Override
//            protected Intent doInBackground(String... params) {
//                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                AConfigResponse aConfig = null;
//
//                try {
////                    aConfig = sServerAuthenticate.deviceRegister(aDeviceRegisterRequest);
//
////                    bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
////                    bundle.putString(AccountManager.KEY_AUTHTOKEN, signInUser.getAuthtoken());
////                    bundle.putString(PARAM_USER_ID, signInUser.getUserId().toString());
//
//                    Gson gson = new Gson();
//                    bundle.putString(PARAM_CONFIG, gson.toJson(aConfig));
//                    bundle.remove(KEY_ERROR_MESSAGE);
//                    intent.putExtras(bundle);
//                } catch (Exception e) {
//                    bundle.putString(KEY_ERROR_MESSAGE, e.getMessage());
//                }
//                return intent;
//            }
//
//            @Override
//            protected void onPostExecute(Intent intent) {
//
//                if (intent.hasExtra(PARAM_CONFIG))
//                    callback.onResponse(true,intent);
//                else
//                    callback.onResponse(false,intent);
//
//                //start activity for result
//                //retData(intent);
//            }
//        }.execute();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    //    @Override
//    protected void onStart() {
//        super.onStart();
//
//
//        final BottomSheetDialog dialog = new BottomSheetDialog(context);
//
//        TubelessException.ShowSheetDialogMessage(context, dialog, context.getString(R.string.new_yafte_new_yafte_inserted), "ok", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Bundle bundle = new Bundle();
//                Gson gson = new Gson();
//                bundle.putString("ReturnData",gson.toJson("response"));
//                intent.putExtras(bundle);
//                setResult(Activity.RESULT_OK, intent);
//
//                finish();
//            }
//        });
//    }
}
