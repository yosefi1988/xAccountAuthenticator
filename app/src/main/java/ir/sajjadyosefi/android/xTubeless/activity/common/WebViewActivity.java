package ir.sajjadyosefi.android.xTubeless.activity.common;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;


import ir.sajjadyosefi.android.xTubeless.R;
import ir.sajjadyosefi.android.xTubeless.activity.TubelessTransparentStatusBarActivity;
import ir.sajjadyosefi.android.xTubeless.classes.StaticValue;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;


/**
 * Created by Sajad on 2/11/2017.
 */
public class WebViewActivity extends TubelessTransparentStatusBarActivity {
    Context context;
    WebSettings wSettings;

    LinearLayout linearLayoutPay;
    Button button_pay;
    Button button_back;

    int count = 0;
    int progress = 0;


    public synchronized static Intent getIntent(Context context) {
        return getIntent(context,new Bundle());
    }

    public synchronized static Intent getIntent(Context context, Bundle bundle) {
        bundle.putString("item1","value1");
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtras(bundle);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(R.layout.activity_webview);


//        new PromptDialog(this)
//                .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
//                .setAnimationEnable(true)
//                .setTitleText(getString(R.string.ok))
//                .setContentText(getString(R.string.acceptRule))
//                .setPositiveListener(getString(R.string.ok), new PromptDialog.OnPositiveListener() {
//                    @Override
//                    public void onClick(PromptDialog progressDialog) {
//                        progressDialog.dismiss();
//                    }
//                }).show();

//        ColorDialog progressDialog = new ColorDialog(this);
//        progressDialog.setTitle(getString(R.string.register));
//        progressDialog.setContentText(getString(R.string.registerFooterText));
//        progressDialog.setContentImage(getResources().getDrawable(R.mipmap.ic_launcher));
//        progressDialog.setPositiveListener(getString(R.string.register), new ColorDialog.OnPositiveListener() {
//            @Override
//            public void onClick(ColorDialog progressDialog) {
//                Toast.makeText(WebViewActivity.this, progressDialog.getPositiveText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        }).setNegativeListener(getString(R.string.cancel), new ColorDialog.OnNegativeListener() {
//                @Override
//                public void onClick(ColorDialog progressDialog) {
//                    Toast.makeText(WebViewActivity.this, progressDialog.getNegativeText().toString(), Toast.LENGTH_SHORT).show();
//                    progressDialog.dismiss();
//                }
//            }).show();

        linearLayoutPay = ((LinearLayout)findViewById(R.id.linearLayout_pay));
        button_pay = ((Button)findViewById(R.id.button_pay));
        button_back = ((Button)findViewById(R.id.button_back));



        Bundle bundle = getIntent().getExtras();
        if(bundle != null){

            WebView view = new WebView(context);
            //view.getSettings().setJavaScriptEnabled(true);
            view.loadUrl("file:///android_asset/about.html");


            WebView htmlWebView = (WebView)findViewById(R.id.webView);
            WebSettings webSetting = htmlWebView.getSettings();
            webSetting.setJavaScriptEnabled(true);
            webSetting.setDisplayZoomControls(true);

            String htmlFilename = "about.html";
            if (bundle.getBoolean("isOnline") == true){
                htmlWebView.loadUrl(bundle.getString("address"));
            }else {
                if (bundle.getString("WebType").equals("about")) {
                    htmlFilename = "about.html";

                } else if (bundle.getString("WebType").equals("feedback")) {
                    htmlFilename = "feedback.html";
                } else if (bundle.getString("WebType").equals("help")) {
                    htmlFilename = "help.html";
                } else if (bundle.getString("WebType").equals("rule")) {
                    htmlFilename = "rule.html";
                } else if (bundle.getString("WebType").equals("report")) {
                    htmlFilename = "report.html";
                }
                if (bundle.get("payButton") != null){
                    if (bundle.getBoolean("payButton" , false) == true){
                        linearLayoutPay.setVisibility(View.VISIBLE);
                        button_pay.setText(R.string.accept_and_pardakht);

                        button_pay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent returnIntent = getIntent();
                                Bundle bundle = new Bundle();
//                                bundle.putSerializable("LIST1", (Serializable) fileList);
                                returnIntent.putExtras(bundle);
                                setResult(Activity.RESULT_OK, getIntent());
                                finish();
                            }
                        });

                        button_back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                setResult(Activity.RESULT_CANCELED);
                                finish();
                            }
                        });
                    }else {
                        linearLayoutPay.setVisibility(View.GONE);
                    }
                }else {
                    linearLayoutPay.setVisibility(View.GONE);
                }

                AssetManager mgr = getBaseContext().getAssets();
                try {
                    InputStream in = mgr.open(htmlFilename, AssetManager.ACCESS_BUFFER);
                    String htmlContentInStringFormat = StreamToString(in);
                    in.close();
                    htmlWebView.loadDataWithBaseURL(null, htmlContentInStringFormat, "text/html", "utf-8", null);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public SystemBarTintManager getSystemBarTint() {
        return null;
    }

    @Override
    public boolean hasTranslucentNavigation() {
        return false;
    }

    @Override
    public boolean hasTranslucentStatusBar() {
        return false;
    }

    @Override
    public BottomNavigation getBottomNavigation() {
        return null;
    }


    @Override
    public boolean hasAppBarLayout() {
        return false;
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public static String StreamToString(InputStream in) throws IOException {
        if(in == null) {
            return "";
        }
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
        }
        return writer.toString();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
