package ir.sajjadyosefi.android.xTubeless.activity.common.splashScreen;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import ir.sajjadyosefi.accountauthenticator.activity.SignInActivity;
import ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral;
import ir.sajjadyosefi.accountauthenticator.classes.IDeviceRegister;
import ir.sajjadyosefi.accountauthenticator.model.request.ADeviceRegisterRequest;
import ir.sajjadyosefi.accountauthenticator.model.response.AConfigResponse;
import ir.sajjadyosefi.android.xTubeless.Global;
import ir.sajjadyosefi.android.xTubeless.R;
import ir.sajjadyosefi.android.xTubeless.activity.MainActivity;
import ir.sajjadyosefi.android.xTubeless.activity.MainTestActivity;
import ir.sajjadyosefi.android.xTubeless.classes.SAccounts;
import ir.sajjadyosefi.android.xTubeless.classes.model.user.User;

import static ir.sajjadyosefi.accountauthenticator.activity.AuthenticatorActivity.KEY_ERROR_MESSAGE;
import static ir.sajjadyosefi.accountauthenticator.activity.AuthenticatorActivity.PARAM_CONFIG;

//mvp
public class SplashScreen extends AppCompatActivity  {

    Context context;
    private static final int        PERMISSION_REQUEST_CODE     = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_splash_screen);
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


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    int v = getPackageManager().getPackageInfo("com.google.android.gms", 0 ).versionCode;
                    if(v>11020000){
                        //show error
                    }
//                    Toast.makeText(context,"gms version : " + v ,Toast.LENGTH_LONG).show();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                //FirebaseCrash.log("this is log");
                try {
                    int a = 10/0;
                } catch (Exception e) {
                    //FirebaseCrash.report(e);
                }
                context.startActivity(new Intent(context, MainTestActivity.class));
                context.startActivity(new Intent(context, MainActivity.class));

                ((Activity)context).finish();

            }
        },3000);


        AccountGeneral.setIDApplication(2);
        AccountGeneral.setIDApplicationVersion(1);
        AccountGeneral.setStore("myket");
        AccountGeneral.setIP("55.55.55.55");
        AccountGeneral.setAndroidID("55.55.55.55");
        AccountGeneral.setAndroidID("9");


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

    }
}
