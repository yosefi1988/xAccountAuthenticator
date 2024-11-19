package ir.sajjadyosefi.android.xTubeless.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import ir.sajjadyosefi.android.xTubeless.Global;
import ir.sajjadyosefi.android.xTubeless.R;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;
//import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

//import com.crashlytics.android.Crashlytics;
//import io.fabric.sdk.android.Fabric;

import static ir.sajjadyosefi.android.xTubeless.classes.StaticValue.NOT_LOGN_USER;


public abstract class TubelessActivity extends AppCompatActivity {

    private Context context;
    private AppCompatActivity activity;
    private ViewGroup rootActivity;
    public Dialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        activity = this;
        progressDialog = new Dialog(getContext());
        progressDialog.setContentView(R.layout.x_main_progress);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


//        Crashlytics.setUserIdentifier((Global.user == null ? NOT_LOGN_USER : Global.user.getUserId()) + "");
//        Fabric.with(this, new Crashlytics());
        progressDialog.setCancelable(false);
    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
////        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }


    public Context getContext(){
        return context;
    }
    public AppCompatActivity getActivity(){
        return activity;
    }

    @Override
    protected void onStart() {
        super.onStart();
        rootActivity = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
    }

    protected SystemBarTintManager mSystemBarTint;
    protected boolean mTranslucentStatus;
    protected boolean mTranslucentStatusSet;
    protected boolean mTranslucentNavigation;
    protected boolean mTranslucentNavigationSet;

    public abstract SystemBarTintManager getSystemBarTint() ;

    public abstract boolean hasTranslucentNavigation();

    public abstract boolean hasTranslucentStatusBar();

    public abstract BottomNavigation getBottomNavigation();

    public abstract boolean hasAppBarLayout();

    public abstract Toolbar getToolbar();
}
