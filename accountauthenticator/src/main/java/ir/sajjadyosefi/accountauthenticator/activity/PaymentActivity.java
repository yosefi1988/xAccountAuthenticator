package ir.sajjadyosefi.accountauthenticator.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
import com.zarinpal.ewallets.purchase.OnCallbackVerificationPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

import ir.sajjadyosefi.accountauthenticator.R;
import ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral;
import ir.sajjadyosefi.accountauthenticator.classes.IDeviceRegisterRequest;
import ir.sajjadyosefi.accountauthenticator.classes.exception.TubelessException;
import ir.sajjadyosefi.accountauthenticator.model.request.AWalletChargeRequest;
import ir.sajjadyosefi.accountauthenticator.model.response.AWalletChargeResponse;

import static ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral.sServerAuthenticate;

public class PaymentActivity extends Activity {

    public static final int GO_TO_LOGIN = 20;

    private static boolean noUi = false;
    private static boolean paySuccess = false;
    private static Intent paymentIntent;
    private static Bundle bundle;

    private ViewGroup rootActivity;
    public Button btnPay , buttonBack ;
    EditText editTextPhone, editTextAmount,editTextDiscription;
    Context context;
    private static String phone ,amount,discription ;

    public synchronized static Intent getIntent(Context context) {
        return getIntent(context,null);
    }

    public static boolean isPaymentSuccess() {
        return paySuccess;
    }

    public static void PaymentDone() {
        try {
            noUi = false;
            paySuccess = false;
            paymentIntent = null;
            bundle = null;
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
        ZarinPal.getPurchase(context).verificationPayment(data2, new OnCallbackVerificationPaymentListener() {
            @Override
            public void onCallbackResultVerificationPayment(boolean isPaymentSuccess, String refID, PaymentRequest paymentRequest) {

                if(isPaymentSuccess){
                    Toast.makeText(context,"pay success" ,Toast.LENGTH_LONG).show();
                    paySuccess = true;
//                    AWalletChargeRequest req = new AWalletChargeRequest(usercode, amount, refID, phone+ "|" + discription);
                    AWalletChargeRequest req = new AWalletChargeRequest(amount);
                    req.setMetaData(discription);
                    chargeAccount(req);
                }else {
                    //not ok
//                    show message refID
                    Toast.makeText(context,"not ok " , Toast.LENGTH_LONG).show();
                    paySuccess = false;

                    if (paymentIntent.hasExtra("type")){
//                        if (paymentIntent.getIntExtra("type",1) == 2){
                            setResult(Activity.RESULT_CANCELED);
                            finish();
//                        }
                    }


                    //

                }
            }
        });


        if (paymentIntent != null) {
            if (paymentIntent.hasExtra("type")) {
                if (paymentIntent.getIntExtra("type", 1) == 2) {
//                    noUi = true;
//                    AWalletChargeRequest request = new AWalletChargeRequest("1000");
//                    request.setMetaData(discription);
//                    amount = request.getAmount();
//                    payment(Integer.parseInt(amount), this);
                } else {
                    setContentView(R.layout.activity_payment);
                }
//                paymentIntent = getIntent();
//                bundle = paymentIntent.getExtras();
            } else {
                setContentView(R.layout.activity_payment);
            }
        }else {
            if (getIntent().hasExtra("type")) {
                paymentIntent = getIntent();
                bundle = paymentIntent.getExtras();
                if (paymentIntent.getIntExtra("type", 1) == 2) {
                    noUi = true;
                    amount = paymentIntent.getIntExtra("amount", 1000) + "";
                    discription = paymentIntent.getStringExtra("metaData");

                    AWalletChargeRequest request = new AWalletChargeRequest(amount);
                    request.setMetaData(discription);
                    payment(Integer.parseInt(amount), this);
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


            if (btnPay != null) {
                btnPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        phone = editTextPhone.getText().toString();
                        amount = editTextAmount.getText().toString();
                        discription = editTextDiscription.getText().toString();
                        payment(Integer.parseInt(editTextAmount.getText().toString()), context);



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

    private void payment(long amount,Context context) {
        ZarinPal purches = ZarinPal.getPurchase(context);
        PaymentRequest payment = ZarinPal.getPaymentRequest();

        payment.setMerchantID("e8a913e8-f089-11e6-8dec-005056a205be");
        payment.setAmount(amount);
        payment.setDescription(AccountGeneral.getAppName());
        payment.setCallbackURL(String.format("%s://%s",AccountGeneral.getSchemezarinpalpayment(),AccountGeneral.getZarinpalpayment()));
//        MainActivity.payType = 100 ;

        purches.startPayment(payment, new OnCallbackRequestPaymentListener() {
            @Override
            public void onCallbackResultPaymentRequest(int status, String authority, Uri paymentGatewayUri, Intent intent) {

                if (status == 100){
                    //ok
                    startActivity(intent);
                }else {
//                    error in payment
//                    ((TubelessActivity)context).progressDialog.hide();
                }

            }
        });
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
    private void chargeAccount(final AWalletChargeRequest chargeRequest) {

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
            protected void onPostExecute(final AWalletChargeResponse response) {
                final BottomSheetDialog dialog = new BottomSheetDialog(context);
                if (noUi) {
                    if (response == null){
                        TubelessException.ShowSheetDialogMessage(context, dialog, context.getString(R.string.tray_again), context.getString(R.string.tray_again), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        return;
                    }
                    if (response.getTubelessException().getCode() == 200) {
                        Bundle bundle = new Bundle();
                        Gson gson = new Gson();
                        bundle.putString("ReturnData",gson.toJson(response));
                        paymentIntent.putExtras(bundle);
                        ((Activity)context).setResult(Activity.RESULT_OK, paymentIntent);
                        ((Activity)context).finish();
                    }
                } else {
                    if (response.getTubelessException().getCode() == 200) {
                        TubelessException.ShowSheetDialogMessage(context, dialog, context.getString(R.string.new_yafte_new_yafte_inserted), "ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Gson gson = new Gson();
                                bundle.putString("ReturnData",gson.toJson(response));
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
    public void chargeAcountWithoutUi(final AWalletChargeRequest request,Context context, final IDeviceRegisterRequest<Boolean,Intent> callback){
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
