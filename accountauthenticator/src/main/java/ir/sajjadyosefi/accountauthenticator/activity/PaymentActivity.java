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
import ir.sajjadyosefi.accountauthenticator.classes.IDeviceRegister;
import ir.sajjadyosefi.accountauthenticator.classes.exception.TubelessException;
import ir.sajjadyosefi.accountauthenticator.model.request.AWalletChargeRequest;
import ir.sajjadyosefi.accountauthenticator.model.response.AConfigResponse;
import ir.sajjadyosefi.accountauthenticator.model.response.AWalletChargeResponse;

import static ir.sajjadyosefi.accountauthenticator.activity.AuthenticatorActivity.KEY_ERROR_MESSAGE;
import static ir.sajjadyosefi.accountauthenticator.activity.AuthenticatorActivity.PARAM_CONFIG;
import static ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral.sServerAuthenticate;

public class PaymentActivity extends Activity {

    private ViewGroup rootActivity;
    public static final int GO_TO_LOGIN = 20;
    public static boolean noUi = false;
    public Button btnPay  ;
    Intent intent;

    EditText editTextPhone, editTextAmount,editTextDiscription;
    private static String phone ,amount,discription ;

    public synchronized static Intent getIntent(Context context) {
        return getIntent(context,null);
    }

    public synchronized static Intent getIntent(Context context, Bundle bundle) {
        bundle.putString("item1","value1");
        Intent intent = new Intent(context,PaymentActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Uri data2 = getIntent().getData();
        ZarinPal.getPurchase(getApplicationContext()).verificationPayment(data2, new OnCallbackVerificationPaymentListener() {
            @Override
            public void onCallbackResultVerificationPayment(boolean isPaymentSuccess, String refID, PaymentRequest paymentRequest) {


                if(isPaymentSuccess){
                    Toast.makeText(getApplicationContext(),"pay success" ,Toast.LENGTH_LONG).show();
//                    AWalletChargeRequest req = new AWalletChargeRequest(usercode, amount, refID, phone+ "|" + discription);
                    AWalletChargeRequest req = new AWalletChargeRequest(amount);
                    req.setMetaData("sssssssssss");
                    chargeAccount(req);
                }else {
                    //not ok
//                    show message refID
                    Toast.makeText(getApplicationContext(),"not ok " , Toast.LENGTH_LONG).show();

                    if (getIntent().hasExtra("type")){
                        if (getIntent().getIntExtra("type",1) == 2){
                            finish();
                        }
                    }

                }
            }
        });


        intent = getIntent();
        if (intent.hasExtra("type")){
            if (getIntent().getIntExtra("type",1) == 2){
                noUi = true;
                AWalletChargeRequest request = new AWalletChargeRequest("1000");
                request.setMetaData("sssssssssss");
                amount = request.getAmount();
                payment(Integer.parseInt(amount),this);
            }else {
                setContentView(R.layout.activity_payment);
            }
        }

        rootActivity = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        btnPay = findViewById(R.id.btnPay);
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
                    payment(Integer.parseInt(editTextAmount.getText().toString()), getApplicationContext());
                }
            });
        }

//        if (Global.IDUser == NOT_LOGN_USER ){
//        if (Global.user == null || Global.user.getUserId() == NOT_LOGN_USER ){
//            Bundle bundle = new Bundle();
//            Intent intent = SignInActivity.getIntent(getApplicationContext(),bundle);
//            bundle.putParcelable(AccountManager.KEY_INTENT, intent);
//            startActivityForResult(intent, GO_TO_LOGIN);
//        }






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
//                    ((TubelessActivity)getApplicationContext()).progressDialog.hide();
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
            protected void onPostExecute(AWalletChargeResponse response) {
                final BottomSheetDialog dialog = new BottomSheetDialog(getApplicationContext());
                if (noUi) {
                    if (response.getTubelessException().getCode() == 200) {
                        finish();
                        //todo باید به فراخواننده بگه که موفق بود یا نه
                        //البته ابنجا موفق هستش
                    }
                } else {
                    if (response.getTubelessException().getCode() == 200) {
                        TubelessException.ShowSheetDialogMessage(getApplicationContext(), dialog, getApplicationContext().getString(R.string.new_yafte_new_yafte_inserted), "ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        });
                    } else {
                        TubelessException.ShowSheetDialogMessage(getApplicationContext(), dialog, getApplicationContext().getString(R.string.tray_again), getApplicationContext().getString(R.string.tray_again), new View.OnClickListener() {
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

    @SuppressLint("StaticFieldLeak")
    public void chargeAcountWithoutUi(final AWalletChargeRequest request,Context context, final IDeviceRegister<Boolean,Intent> callback){
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

}
