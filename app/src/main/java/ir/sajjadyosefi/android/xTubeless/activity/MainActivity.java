package ir.sajjadyosefi.android.xTubeless.activity;

import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity;
import ir.sajjadyosefi.accountauthenticator.activity.accounts.ChangePasswordActivity;
import ir.sajjadyosefi.accountauthenticator.activity.accounts.ResetPasswordActivity;
import ir.sajjadyosefi.accountauthenticator.activity.accounts.SignInActivity;
import ir.sajjadyosefi.accountauthenticator.activity.payments.PaymentActivity;
import ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral;
import ir.sajjadyosefi.android.xTubeless.R;
import ir.sajjadyosefi.android.xTubeless.Global;
import ir.sajjadyosefi.android.xTubeless.Adapter.FirstFragmentsAdapter;
import ir.sajjadyosefi.android.xTubeless.activity.account.profile.MainActivityProfile;
import ir.sajjadyosefi.android.xTubeless.activity.common.ContactUsActivity;
import ir.sajjadyosefi.android.xTubeless.activity.common.WebViewActivity;
import ir.sajjadyosefi.android.xTubeless.classes.Validator;

import ir.sajjadyosefi.android.xTubeless.classes.model.user.User;
import it.sephiroth.android.library.bottomnavigation.BadgeProvider;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;
import it.sephiroth.android.library.bottomnavigation.MiscUtils;
import static android.util.Log.VERBOSE;
import static ir.sajjadyosefi.accountauthenticator.activity.accounts.AuthenticatorActivity.PARAM_USER_OBJECT;
import static ir.sajjadyosefi.android.xTubeless.activity.common.splashScreen.SplashScreen.WALLETCHARGE_REQUEST_CODE;
import static ir.sajjadyosefi.android.xTubeless.networkLayout.networkLayout.Url.Telegram;

@TargetApi (Build.VERSION_CODES.KITKAT_WATCH)
public class MainActivity extends TubelessActivity implements BottomNavigation.OnMenuItemSelectionListener {

    //val
    private static int LOGIN_REQUEST_CODE = 101 ;
    private static int OPEN_PROFILE_REQUEST_CODE = 102 ;
    private static int CHANGE_PASSWORD_REQUEST_CODE = 103;


    private BottomNavigation mBottomNavigation;
    private DrawerLayout drawer_layout;
    private ViewPager viewPager;
    private FirstFragmentsAdapter firstFragmentsAdapter;
    ViewGroup root;
    public ViewGroup rootView ;

    private Toolbar toolbar;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == LOGIN_REQUEST_CODE){
                if (data.hasExtra(PARAM_USER_OBJECT)){
                    Gson gson = new Gson();
                    User user = gson.fromJson(data.getExtras().getString(PARAM_USER_OBJECT),User.class);
                    if(savedToDataBase(user)){
                        if (Global.user != null && Global.user.isAdmin()) {
                            updatedrawableMenuItems();
                        }
                    }
                    Toast.makeText(getContext(),getContext().getString(R.string.welcome) ,Toast.LENGTH_LONG).show();
                }
            }
            if (requestCode == WALLETCHARGE_REQUEST_CODE){
                if (data.hasExtra(PARAM_USER_OBJECT)){
                    Gson gson = new Gson();
                    User user = gson.fromJson(data.getExtras().getString(PARAM_USER_OBJECT),User.class);
                    if(savedToDataBase(user)){
                        if (Global.user != null && Global.user.isAdmin()) {
                            updatedrawableMenuItems();
                        }
                    }
                    Toast.makeText(getContext(),getContext().getString(R.string.welcome) ,Toast.LENGTH_LONG).show();
                }
            }
            updatedrawableMenuItems();
        }
    }


    private boolean savedToDataBase(User tmpUser) {
        tmpUser.setAdmin(tmpUser.CheckUserIsAdmin(tmpUser));
        //save to db
        Global.user = tmpUser;
        setUserTmp(tmpUser);

        if ((new Validator()).isIranianMobileNumber(tmpUser.getUserName()))
            tmpUser.setPassword(tmpUser.getPassword());
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);


        root = findViewById(R.id.CoordinatorLayout01);
        toolbar = findViewById(R.id.toolbar);
        drawer_layout = findViewById(R.id.drawer_layout);
        viewPager = findViewById(R.id.ViewPager01);

        setSupportActionBar(toolbar);
        drawableMenu(toolbar);


        final int statusbarHeight = getStatusBarHeight();
        final boolean translucentStatus = hasTranslucentStatusBar();
        final boolean translucentNavigation = hasTranslucentNavigation();

        MiscUtils.INSTANCE.log(VERBOSE, "translucentStatus: %b", translucentStatus);

        if (translucentStatus) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) root.getLayoutParams();
            params.topMargin = -statusbarHeight;

            params = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
//            params.topMargin = statusbarHeight;
        }

        if (translucentNavigation) {
            final DrawerLayout drawerLayout = getDrawerLayout();
            if (null != drawerLayout) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) drawerLayout.getLayoutParams();
                params.bottomMargin = 0;
            }
        }
        initializeBottomNavigation(savedInstanceState);
        initializeUI(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (checkIsFirstRun()){
            drawer_layout.openDrawer(Gravity.LEFT);
            setFirstRunIsDone();
        }

//        Bundle bundle = new Bundle();
//        bundle.putInt("type" , 1);
//        //bundle.putInt("amount", 10000);//ريال
//        //bundle.putString("metaData", "meta Data 10000");
//        bundle.putString("tax", "9");
//        bundle.putString("portService", "5");
//        bundle.putBoolean("isCharge", true);
//        bundle.putBoolean("isDirectPayment", false);
//        Intent intent = PaymentActivity.getIntent(getContext(),bundle);
//        //intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE, AccountGeneral.ACCOUNT_TYPE);
//        //intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, AccountGeneral.AUTHTOKEN_TYPE_ADMIN_USER);
//        //intent.putExtra(AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
//        //intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
//        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
//        startActivityForResult(intent, WALLETCHARGE_REQUEST_CODE);


//        AccountGeneral.getAndroidID();
//        AccountGeneral.getAppName();
//        AccountGeneral.getUserCode();
//        AccountGeneral.getZarinpalpayment();

//        //library
//        AccountGeneral.setIDApplication(2);
//        AccountGeneral.setIDApplicationVersion(1);
//        AccountGeneral.setStore("myket");
//        AccountGeneral.setIP("55.55.55.55");
//        AccountGeneral.setAndroidID("55.55.55.55");
//        AccountGeneral.setAndroidID("15df3b3a90dc5688");
//
//        //zarinpal
        AccountGeneral.setAppName(getString(R.string.app_name));
        AccountGeneral.setZarinpalpayment(getContext().getString(R.string.zarinpalpayment));
        AccountGeneral.setSchemezarinpalpayment(getContext().getString(R.string.schemezarinpalpayment));

        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        bundle.putInt("amount", 11000);//ريال
        bundle.putString("phone", "0912333");
        bundle.putString("metaData", "meta Data 1000");

        bundle.putString("tax", "9");
        bundle.putString("portService", "5");
        bundle.putBoolean("isCharge", false);//   <==== //use true for charge wallet
        bundle.putBoolean("isDirectPayment", true);     //valid when isCharge = false



        Intent intent = PaymentActivity.getIntent(this, bundle);
        //intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, AccountGeneral.AUTHTOKEN_TYPE_ADMIN_USER);
        //intent.putExtra(AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
        //intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        startActivityForResult(intent, WALLETCHARGE_REQUEST_CODE);

    }
    private void drawableMenu(Toolbar toolbar) {

        //add humberger
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.save, R.string.cancel);
        drawer_layout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)toolbar.getLayoutParams();

        updatedrawableMenuItems();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();

                if (id == R.id.nav_profile) {
                    Bundle bundle = new Bundle();
                    if (Global.user != null && Global.user.isAdmin())
                        bundle.putBoolean("MustRefresh" , true);
                    getActivity().startActivityForResult(MainActivityProfile.getIntent(getContext(),bundle), LOGIN_REQUEST_CODE);
                }else if (id == R.id.nav_about_us) {
                    Bundle bundle = new Bundle();
                    bundle.putString("WebType" , "about");
                    getActivity().startActivityForResult(WebViewActivity.getIntent(getContext(),bundle), LOGIN_REQUEST_CODE);
                }else  if (id == R.id.nav_login) {

                    Bundle bundle = new Bundle();
                    bundle.putInt("type" , 1);
                    Intent intent = SignInActivity.getIntent(getContext(),bundle);
                    //intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE, AccountGeneral.ACCOUNT_TYPE); todo not need
                    intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, AccountGeneral.AUTHTOKEN_TYPE_ADMIN_USER);
                    intent.putExtra(AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
                    //intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
                    bundle.putParcelable(AccountManager.KEY_INTENT, intent);
                    getActivity().startActivityForResult(intent, LOGIN_REQUEST_CODE);

                }else  if (id == R.id.nav_changepassword) {

                    Bundle bundle = new Bundle();
                    bundle.putInt("type" , 1);
                    Intent intent = ChangePasswordActivity.getIntent(getContext(),bundle);
                    //intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE, AccountGeneral.ACCOUNT_TYPE); todo not need
                    intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, AccountGeneral.AUTHTOKEN_TYPE_ADMIN_USER);
                    intent.putExtra(AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
                    intent.putExtra(AuthenticatorActivity.PARAM_USER_CODE, "110015" );//Global.user AccountGeneral.getUserCode());
                    //intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
                    bundle.putParcelable(AccountManager.KEY_INTENT, intent);
                    getActivity().startActivityForResult(intent, CHANGE_PASSWORD_REQUEST_CODE);

                }else  if (id == R.id.nav_forgetpassword) {

                    Bundle bundle = new Bundle();
                    bundle.putInt("type" , 1);
                    Intent intent = ResetPasswordActivity.getIntent(getContext(),bundle);

                    intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, AccountGeneral.AUTHTOKEN_TYPE_ADMIN_USER);
                    intent.putExtra(AuthenticatorActivity.PARAM_USER_CODE, "110015" );
                    intent.putExtra(AuthenticatorActivity.PARAM_MOBILE, "09123678522");

                    bundle.putParcelable(AccountManager.KEY_INTENT, intent);
                    getActivity().startActivityForResult(intent, CHANGE_PASSWORD_REQUEST_CODE);

                } else if (id == R.id.nav_contact_us) {

                    Bundle bundle = new Bundle();
                    bundle.putInt(ContactUsActivity.Type , ContactUsActivity.CONTACT_US);
                    getActivity().startActivity(ContactUsActivity.getIntent(getContext(),bundle));
                } else if (id == R.id.nav_telegram) {
                    //share on telegram
                    //channel
                    Uri uri = Uri.parse(Telegram);
                    Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                    likeIng.setPackage("org.telegram.messenger");
                    try {
                        startActivity(likeIng);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(Telegram)));
                    }
                }

                drawer_layout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    private void updatedrawableMenuItems() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menux = navigationView.getMenu();
        MenuItem login = menux.findItem(R.id.nav_login);
        MenuItem profile = menux.findItem(R.id.nav_profile);
        if (Global.user == null) {
            login.setVisible(true);
            profile.setVisible(false);
        }else {
            login.setVisible(false);
            profile.setVisible(true);
        }
    }


    protected void initializeBottomNavigation(final Bundle savedInstanceState) {
        if (null == savedInstanceState) {
            BottomNavigation x = getBottomNavigation();
            x.setDefaultSelectedIndex(0);
            final BadgeProvider provider = getBottomNavigation().getBadgeProvider();
            provider.show(R.id.bbn_item2);
        }
        if (null != getBottomNavigation()) {
            mBottomNavigation.setMenuItemSelectionListener(this);
        }
    }

    protected void initializeUI(final Bundle savedInstanceState) {
        if (null != viewPager) {
            getBottomNavigation().setMenuChangedListener(parent -> {
                firstFragmentsAdapter = new FirstFragmentsAdapter(getContext(),getActivity(), parent.getMenuItemCount());
                viewPager.setAdapter(firstFragmentsAdapter);
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(
                            final int position, final float positionOffset, final int positionOffsetPixels) { }

                    @Override
                    public void onPageSelected(final int position) {
                        if (getBottomNavigation() != null) {
                            if (getBottomNavigation().getSelectedIndex() != position) {
                                getBottomNavigation().setSelectedIndex(position, false);
                            }
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(final int state) { }
                });
            });

        }
    }


    public int getStatusBarHeight() {
        return getSystemBarTint().getConfig().getStatusBarHeight();
    }

    @TargetApi (19)
    public boolean hasTranslucentStatusBar() {
        if (!mTranslucentStatusSet) {
            if (Build.VERSION.SDK_INT >= 19) {
                mTranslucentStatus = ((getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) == WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else {
                mTranslucentStatus = false;
            }
            mTranslucentStatusSet = true;
        }
        return mTranslucentStatus;
    }

    @TargetApi (19)
    public boolean hasTranslucentNavigation() {
        if (!mTranslucentNavigationSet) {
            final SystemBarTintManager.SystemBarConfig config = getSystemBarTint().getConfig();
            if (Build.VERSION.SDK_INT >= 19) {
                boolean themeConfig =
                        ((getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                                == WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

                mTranslucentNavigation = themeConfig && config.hasNavigtionBar() && config.isNavigationAtBottom()
                        && config.getNavigationBarHeight() > 0;
            }
            mTranslucentNavigationSet = true;
        }
        return mTranslucentNavigation;
    }

    public int getNavigationBarHeight() {
        return getSystemBarTint().getConfig().getNavigationBarHeight();
    }

    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    public boolean hasManagedToolbarScroll() {
        return hasAppBarLayout() && findViewById(R.id.CoordinatorLayout01) instanceof CoordinatorLayout;
    }

    public boolean hasAppBarLayout() {
        return getToolbar().getParent() instanceof AppBarLayout;
    }

    public BottomNavigation getBottomNavigation() {
        if (null == mBottomNavigation) {
            mBottomNavigation = findViewById(R.id.BottomNavigation);
        }
        return mBottomNavigation;
    }

    @Override
    public SystemBarTintManager getSystemBarTint() {
        if (null == mSystemBarTint) {
            mSystemBarTint = new SystemBarTintManager(this);
        }
        return mSystemBarTint;
    }

    public DrawerLayout getDrawerLayout() {
        return drawer_layout;
    }

    private ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public void onMenuItemSelect(final int itemId, final int position, final boolean fromUser) {
        if (fromUser) {
            getBottomNavigation().getBadgeProvider().remove(itemId);
            if (null != getViewPager()) {
                getViewPager().setCurrentItem(position);
            }
        }
    }

    @Override
    public void onMenuItemReselect(@IdRes final int itemId, final int position, final boolean fromUser) {
        onMenuItemSelect(itemId,position,fromUser);
    }

    public boolean checkIsFirstRun() {
        SharedPreferences prefs = null;
        prefs =  this.getSharedPreferences("ir.sajjadyosefi.android.tubeless", MODE_PRIVATE);
        return prefs.getBoolean("firstrun", true);
    }

    public boolean setUserTmp(User user) {
        SharedPreferences prefs = null;
        prefs =  this.getSharedPreferences("ir.sajjadyosefi.android.tubeless", MODE_PRIVATE);
        try {
            Gson gson = new Gson();
            prefs.edit().putString("USER",gson.toJson(user)).commit();
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public boolean setFirstRunIsDone() {
        SharedPreferences prefs = null;
        prefs =  this.getSharedPreferences("ir.sajjadyosefi.android.tubeless", MODE_PRIVATE);

        try {
            prefs.edit().putBoolean("firstrun", false).commit();
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public void BackButtonPressed() {


        if (doubleBackToExitPressedOnce) {
            finish();
        }
        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(getContext(), getContext().getString(R.string.exit_double_tap_message),Toast.LENGTH_LONG).show();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        BackButtonPressed();
    }

}
