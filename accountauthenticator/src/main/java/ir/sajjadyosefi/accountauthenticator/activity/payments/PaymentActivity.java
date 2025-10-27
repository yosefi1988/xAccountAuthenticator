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

    //static vars
    public static final int GO_TO_LOGIN = 20;
    private static boolean _isWalletSource = false;
    private static boolean _ActiveUi = false;
    private static boolean _isWalletCharge = false;
    private static Intent _intent;
    private static Bundle _bundle;
    private static String _phone, _amountX, _description, _textViewPrice4Shadow;
    private static int tax , portService;

    private static boolean paySuccess = false;

    //vars
        private ViewGroup rootActivity;
        public Button btnPay , buttonBack ;
        EditText editTextPhone, editTextAmount,editTextDiscription;
        TextView textViewPrice1,textViewPrice2,textViewPrice3,textViewPrice4;
        Context context;
        ActivityResultLauncher launcher;


    //static methods
        public synchronized static Intent getIntent(Context _context) {
            return getIntent(_context,null);
        }
        public synchronized static Intent getIntent(Context _context, Bundle _bundle) {
            if (_bundle == null)
                _bundle = new Bundle();
            _bundle.putString("item1","value1");
            Intent newIntent = new Intent(_context,PaymentActivity.class);
            newIntent.putExtras(_bundle);
            return newIntent;
        }
        public static Intent getStaticIntent() {
            if (paySuccess == false)
                return null;
            else {
                return _intent;
    //            noUi = false;
    //            paySuccess = false;
    //            paymentIntent = null;
    //            bundle = null;
            }
        }
        public static boolean isPaymentSuccess() {
            return paySuccess;
        }
        public static void PaymentDone() {
        try {
            _ActiveUi = false;
            paySuccess = false;
            _intent = null;
            _bundle = null;
            _isWalletSource = false;
            _isWalletCharge = false;
        }catch (Exception e){
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        _intent = getIntent();
        Uri data2 = _intent.getData();
        _bundle = _intent.getExtras();

        if (_intent.hasExtra("amount"))
            _amountX = _intent.getIntExtra("amount", 10000) + "";
        preparePayAcivityMode();

        if (_isWalletSource) {
            //تراکنش برداشت از کیف پول
            try {
                createTransaction(new ATransactionRequest("110015",_amountX));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }else {
            preparePayActivityResultActions();


            if (_intent.hasExtra("phone"))
                _phone = _intent.getStringExtra("phone");
            if (_intent.hasExtra("tax"))
                tax = Integer.parseInt(_intent.getStringExtra("tax"));
            if (_intent.hasExtra("portService"))
                portService = Integer.parseInt(_intent.getStringExtra("portService"));

            if (_ActiveUi) {
                setContentView(R.layout.activity_payment);
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
                            _amountX = s.toString();
                            calcBill();
                        }
                    }
                });
                editTextAmount.setText(_amountX);
                editTextPhone.setText(_phone);
                btnPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        _phone = editTextPhone.getText().toString();
                        _amountX = editTextAmount.getText().toString();
                        prepareDescription();
                        zarrinPayment(Integer.parseInt(_textViewPrice4Shadow) / 10, Long.parseLong(_amountX) / 10, context);
                    }
                });
            } else {
                prepareDescription();
                zarrinPayment(calcBillHide() / 10, Long.parseLong(_amountX) / 10, this);
            }
        }
    }
    private void prepareDescription() {
        StringBuilder stringBuilderMetaData = new StringBuilder();

        stringBuilderMetaData.append("موبایل پرداخت کننده:");
        stringBuilderMetaData.append(_phone);
        stringBuilderMetaData.append("\n");

        stringBuilderMetaData.append("مبلغ کاربر:");
        stringBuilderMetaData.append(_amountX);
        stringBuilderMetaData.append("ريال");
        stringBuilderMetaData.append("\n");

        stringBuilderMetaData.append("مبلغ درگاه:");

        if (_ActiveUi)
            stringBuilderMetaData.append(Integer.parseInt(_textViewPrice4Shadow));
        else
            stringBuilderMetaData.append(calcBillHide());

        stringBuilderMetaData.append("ريال");
        stringBuilderMetaData.append("\n");


        stringBuilderMetaData.append("توضیحات ");
        if (_ActiveUi)
            stringBuilderMetaData.append(editTextDiscription.getText().toString());
        else
            stringBuilderMetaData.append(_intent.getStringExtra("metaData"));
        stringBuilderMetaData.append("\n");


        stringBuilderMetaData.append("نوع رابط کاربری :");
        if (_ActiveUi)
            stringBuilderMetaData.append("با صفحه پرداخت ");
        else
            stringBuilderMetaData.append("بدون صفحه واسط پرداخت ");
        stringBuilderMetaData.append("\n");

        stringBuilderMetaData.append("برداشت از ");
        if (_isWalletSource) {
            stringBuilderMetaData.append("کیف پول ");
        } else {
            stringBuilderMetaData.append("کارت بانکی ");
        }
        stringBuilderMetaData.append("\n");

        stringBuilderMetaData.append("نوع پرداخت : ");
        if (_isWalletCharge)
            stringBuilderMetaData.append("شارژ کیف پول");
        else
            stringBuilderMetaData.append("خرید/پرداخت وجه");

        stringBuilderMetaData.append("\n");

        _description = stringBuilderMetaData.toString();
    }

    private static void preparePayAcivityMode() {
        //////////////////////    source    /////////////////
        Source source = (Source) _bundle.getSerializable("Source");
        if (source == null) {
            source = Source.Card;
        }
        if (source == Source.Wallet)
            _isWalletSource = true;

        //////////////////////  autenticatiorPaymentUi   /////////////////
        AutenticatiorPaymentUi autenticatiorPaymentUi = (AutenticatiorPaymentUi) _bundle.getSerializable("AutenticatiorPaymentUi");
        if (autenticatiorPaymentUi == null) {
            autenticatiorPaymentUi = AutenticatiorPaymentUi.AppValue;
        }
        if (autenticatiorPaymentUi == AutenticatiorPaymentUi.UserValue)
            _ActiveUi = true;

        //////////////////////    payMode    /////////////////
        PayMode payMode = (PayMode) _bundle.getSerializable("PayMode");
        if (payMode == null) {
            payMode = PayMode.DirectPay;
        }
        if (payMode == PayMode.WalletCharge)
            _isWalletCharge = true;
    }

    private void preparePayActivityResultActions() {
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        paySuccess = true;
                        Intent x = PaymentActivity.getStaticIntent();
                        if (_isWalletCharge) {
                            try {
                                createTransaction(new ATransactionRequest("110015",_amountX));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                        else {
                            if (_ActiveUi) {
                                final BottomSheetDialog dialog = new BottomSheetDialog(context);
                                TubelessException.ShowSheetDialogMessage(context, dialog, context.getString(R.string.new_yafte_new_yafte_inserted), "ok", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Gson gson = new Gson();
                                        _bundle.putString("ReturnData", "direct Payment");
                                        _intent.putExtras(_bundle);
                                        setResult(Activity.RESULT_OK, _intent);
                                        finish();
                                    }
                                });
                            } else {
                                Bundle bundle = new Bundle();
                                Gson gson = new Gson();
                                bundle.putString("ReturnData", "direct Payment");
                                _intent.putExtras(bundle);
                                ((Activity) context).setResult(Activity.RESULT_OK, _intent);
                                ((Activity) context).finish();
                            }
                        }
                    } else {
                        //Toast.makeText(getBaseContext(),"pay not success" , Toast.LENGTH_LONG).show();
                        //show message refID
                        //Toast.makeText(context,"not ok " , Toast.LENGTH_LONG).show();
                        paySuccess = false;
                        setResult(Activity.RESULT_CANCELED);
                        finish();
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void calcBill() {
        textViewPrice1.setText(_amountX + "");
        textViewPrice2.setText((((Integer.parseInt(_amountX) * tax) / 100)) + "");
        textViewPrice3.setText((((Integer.parseInt(_amountX) * portService) / 100)) + "");

        StringBuilder stringBuilderSum = new StringBuilder();
        String sumRial = (Integer.parseInt(textViewPrice1.getText().toString())) +
                (Integer.parseInt(textViewPrice2.getText().toString())) +
                (Integer.parseInt(textViewPrice3.getText().toString())) +
                "";
        stringBuilderSum.append(sumRial);
        _textViewPrice4Shadow = sumRial;
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
        int a = (((Integer.parseInt(_amountX) * tax) / 100));
        int b = (((Integer.parseInt(_amountX) * portService) / 100));
        sumVal = a + b + Integer.parseInt(_amountX);
        return sumVal;
    }

    private void zarrinPayment(long amountZarrinToman,long amountTubelessToman, Context context) {

//        Intent intent = new Intent(this, SdkPayZarinMainActivity.class);
//        //intent.putExtra("direct",false);         //is Direct Payment Without Sdk UI
//        intent.putExtra("merchant", "e8a913e8-f089-11e6-8dec-005056a205be");
//        intent.putExtra("description",(discription.length() == 0 ? "" : discription));
//        //intent.putExtra("callbackurl",String.format("%s://%s", "https", "zarinpalpayment2"));
//        intent.putExtra("callbackurl","https://sajjadyosefi.ir");
//        intent.putExtra("amount", Long.toString(amountZarrinToman * 10));
//        intent.putExtra("mobile", phone);
//        intent.putExtra("email", "yosefi1988@gmail.com");

        Intent intent = new Intent(this, SdkPayZarinMainActivity.class);
        //intent.putExtra("direct", true);         //is Direct Payment Without Sdk UI
        intent.putExtra("merchant", "e8a913e8-f089-11e6-8dec-005056a205be");
        intent.putExtra("description", (_description.length() == 0 ? "" : _description));
        //intent.putExtra("callbackurl",String.format("%s://%s", "https", "zarinpalpayment2"));
        intent.putExtra("callbackurl", "https://sajjadyosefi.ir");
        intent.putExtra("amount", Long.toString(amountZarrinToman * 100));
        intent.putExtra("mobile", "09999816652");
        intent.putExtra("email", "yosefi1988@gmail.com");

        launcher.launch(intent);

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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        _intent = null;
        paySuccess = false;
    }
    @SuppressLint("StaticFieldLeak")
    private void createTransaction( ATransactionRequest chargeRequest) {
        try {
            chargeRequest = new ATransactionRequest(Long.toString(calcBillHide()));
        } catch (Exception exception) {
            //todo remove
            chargeRequest = new ATransactionRequest("110015", calcBillHide() + "");
            exception.printStackTrace();
        }
        chargeRequest.setMetaData(_description + "\nTransactionID:" + "authority");

        if (_isWalletSource){
            chargeRequest.setPayMode(PayMode.WalletPay.name());
        }else {
            if (_isWalletCharge) {
                chargeRequest.setPayMode(PayMode.WalletCharge.name());
            } else {
                chargeRequest.setPayMode(PayMode.DirectPay.name());
            }
        }

        ATransactionRequest finalChargeRequest = chargeRequest;
        new AsyncTask<String, Void, AWalletChargeResponse>() {
            @Override
            protected AWalletChargeResponse doInBackground(String... params) {
                AWalletChargeResponse response = null;
                try {
                    response = sServerAuthenticate.createWalletTransaction(finalChargeRequest);
                } catch (Exception e) {  }
                return response;
            }

            @Override
            protected void onPostExecute(final AWalletChargeResponse aWalletChargeResponse) {
                final BottomSheetDialog dialog = new BottomSheetDialog(context);
                if (_ActiveUi) {
                    if (aWalletChargeResponse != null && aWalletChargeResponse.getTubelessException().getCode() == 200) {
                        TubelessException.ShowSheetDialogMessage(context, dialog, context.getString(R.string.new_yafte_new_yafte_inserted), "ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Gson gson = new Gson();
                                _bundle.putString("ReturnData",gson.toJson(aWalletChargeResponse));
                                _intent.putExtras(_bundle);
                                setResult(Activity.RESULT_OK, _intent);
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
                } else {
                    if (aWalletChargeResponse == null){
                        TubelessException.ShowSheetDialogMessage(context, dialog, context.getString(R.string.tray_again), context.getString(R.string.tray_again), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        return;
                    }
                    if (aWalletChargeResponse.getTubelessException().getCode() == 200 && _isWalletSource) {
                        paySuccess = true;
                    }

                    if (aWalletChargeResponse.getTubelessException().getCode() == 200) {
                        Bundle bundle = new Bundle();
                        Gson gson = new Gson();
                        bundle.putString("ReturnData",gson.toJson(aWalletChargeResponse));
                        _intent.putExtras(bundle);
                        ((Activity)context).setResult(Activity.RESULT_OK, _intent);
                        ((Activity)context).finish();
                    }
                }
            }
        }.execute();
    }
}
