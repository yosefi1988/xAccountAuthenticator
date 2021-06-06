package ir.sajjadyosefi.android.xTubeless.activity.common.splashScreen;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import ir.sajjadyosefi.accountauthenticator.activity.PaymentActivity;
import ir.sajjadyosefi.accountauthenticator.activity.SignInActivity;
import ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral;
import ir.sajjadyosefi.accountauthenticator.classes.IDeviceRegister;
import ir.sajjadyosefi.accountauthenticator.model.request.ADeviceRegisterRequest;
import ir.sajjadyosefi.accountauthenticator.model.response.AConfigResponse;
import ir.sajjadyosefi.android.xTubeless.Global;
import ir.sajjadyosefi.android.xTubeless.R;
import ir.sajjadyosefi.android.xTubeless.classes.SAccounts;
import ir.sajjadyosefi.android.xTubeless.classes.model.user.User;

import static ir.sajjadyosefi.accountauthenticator.activity.AuthenticatorActivity.KEY_ERROR_MESSAGE;
import static ir.sajjadyosefi.accountauthenticator.activity.AuthenticatorActivity.PARAM_CONFIG;

//mvp
public class SplashScreen extends AppCompatActivity  {

    public static final int WALLETCHARGE_REQUEST_CODE = 01225;
    Context context;
    private static final int        PERMISSION_REQUEST_CODE     = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_splash_screen);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent x;
        if (requestCode == 661) {
            if (PaymentActivity.isPaymentSuccess()) {
                x = PaymentActivity.getPaymentIntent();

                Toast.makeText(context,"pay success" ,Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(context,"pay not ok" ,Toast.LENGTH_LONG).show();
            }

            PaymentActivity.PaymentDone();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        SAccounts sAccounts = new SAccounts(this);
        Account user = sAccounts.getUserAccount();

        if (user != null) {
            //load
            Gson gson = new Gson();
            SharedPreferences prefs = null;
            prefs =  this.getSharedPreferences("ir.sajjadyosefi.android.tubeless", MODE_PRIVATE);
            Global.user = gson.fromJson(prefs.getString("USER","") , User.class)  ;
        }else
            Global.user = null;


//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    int v = getPackageManager().getPackageInfo("com.google.android.gms", 0 ).versionCode;
//                    if(v>11020000){
//                        //show error
//                    }
////                    Toast.makeText(context,"gms version : " + v ,Toast.LENGTH_LONG).show();
//                } catch (PackageManager.NameNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//                //FirebaseCrash.log("this is log");
//                try {
//                    int a = 10/0;
//                } catch (Exception e) {
//                    //FirebaseCrash.report(e);
//                }
//                context.startActivity(new Intent(context, MainTestActivity.class));
//                context.startActivity(new Intent(context, MainActivity.class));
//
//                ((Activity)context).finish();
//
//            }
//        },3000);


        //library
        AccountGeneral.setIDApplication(2);
        AccountGeneral.setIDApplicationVersion(1);
        AccountGeneral.setStore("myket");
        AccountGeneral.setIP("55.55.55.55");
        AccountGeneral.setAndroidID("55.55.55.55");
        AccountGeneral.setAndroidID("11");

        //zarinpal
        AccountGeneral.setAppName(getString(R.string.app_name));
        AccountGeneral.setZarinpalpayment(context.getString(R.string.zarinpalpayment));
        AccountGeneral.setSchemezarinpalpayment(context.getString(R.string.schemezarinpalpayment));

        //after login
        AccountGeneral.setUserCode("112028");


        //device register
        SignInActivity signInActivity = new SignInActivity();
        ADeviceRegisterRequest aDeviceRegisterRequest = new ADeviceRegisterRequest();
        aDeviceRegisterRequest.setAndroidVersion(1);
        aDeviceRegisterRequest.setAndroidAPI(1);
        aDeviceRegisterRequest.setBoard("1");
        aDeviceRegisterRequest.setBrand("1");
        aDeviceRegisterRequest.setBuildId("1");
        aDeviceRegisterRequest.setDisplay("1");
        aDeviceRegisterRequest.setManufacturer("1");
        aDeviceRegisterRequest.setModel("1");
        aDeviceRegisterRequest.setSerial("1");
        final boolean[] flag = new boolean[1];

        signInActivity.tryDeviceRegister(aDeviceRegisterRequest, new IDeviceRegister<Boolean,Intent>() {
            @Override
            public void onResponse(Boolean isSuccess,Intent intent) {
                flag[0] = isSuccess;

                Bundle bundle = intent.getExtras();
                String config = bundle.getString(PARAM_CONFIG);
                String error = bundle.getString(KEY_ERROR_MESSAGE);
                AConfigResponse responseX2 = new Gson().fromJson(config, AConfigResponse.class);
            }
        });



        if (count == 0) {
    //Charge Activity
            Bundle bundle = new Bundle();
            bundle.putInt("type" , 1);
            Intent intent = PaymentActivity.getIntent(context,bundle);
            //intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE, AccountGeneral.ACCOUNT_TYPE);
            //intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, AccountGeneral.AUTHTOKEN_TYPE_ADMIN_USER);
            //intent.putExtra(AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
            //intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
            bundle.putParcelable(AccountManager.KEY_INTENT, intent);
            startActivityForResult(intent, WALLETCHARGE_REQUEST_CODE);
            count++;
        }

        //charge by method
        //done
//            Bundle bundle = new Bundle();
//            bundle.putInt("type", 2);
//            bundle.putInt("amount", 1000);
//            bundle.putString("metaData", "sajjad 1000");
//            Intent intent = PaymentActivity.getIntent(this, bundle);
//            //intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, AccountGeneral.AUTHTOKEN_TYPE_ADMIN_USER);
//            //intent.putExtra(AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
//            //intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
//            bundle.putParcelable(AccountManager.KEY_INTENT, intent);
//            startActivityForResult(intent, WALLETCHARGE_REQUEST_CODE);
    }
    int count = 0;
}
