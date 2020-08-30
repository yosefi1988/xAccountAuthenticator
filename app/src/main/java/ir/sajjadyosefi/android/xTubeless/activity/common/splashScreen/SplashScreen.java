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

import ir.sajjadyosefi.android.xTubeless.Global;
import ir.sajjadyosefi.android.xTubeless.R;
import ir.sajjadyosefi.android.xTubeless.activity.MainActivity;
import ir.sajjadyosefi.android.xTubeless.activity.MainTestActivity;
import ir.sajjadyosefi.android.xTubeless.classes.SAccounts;
import ir.sajjadyosefi.android.xTubeless.classes.model.user.User;

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
        },1500);
    }
}
