package ir.sajjadyosefi.android.xTubeless.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;

import ir.sajjadyosefi.android.xTubeless.R;

import static ir.sajjadyosefi.android.xTubeless.activity.common.WebViewActivity.StreamToString;

//import com.astuetz.PagerSlidingTabStripDefault;

/**
 * Created by sajjad on 08/14/2016.
 */
// In this case, the fragment displays simple text based on the page
@SuppressLint("ValidFragment")
public class tab1Fragment extends Fragment {
    public static Context context;
    public static int mIndex;

    public static final String ARG_PAGE = "ARG_PAGE";


    public tab1Fragment() {

    }

    public tab1Fragment newInstance(Context context) {
        this.context = context ;
        Bundle args = new Bundle();
//        args.putInt(ARG_PAGE, page);
//        args.putInt(ARG_LIST, list);
//        args.putInt(ARG_HEADER, headerId);
        tab1Fragment fragment = new tab1Fragment();
        fragment.setArguments(args);
        //this.values = context.getSharedPreferences(Statics.MAHAN, 0);
        return fragment;
    }

    public tab1Fragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.blank_fragment, container, false);

        WebView htmlWebView = (WebView)view.findViewById(R.id.webView);
        WebSettings webSetting = htmlWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(true);
        String htmlFilename = "tab1.html";

        AssetManager mgr = getContext().getAssets();
        try {
            InputStream in = mgr.open(htmlFilename, AssetManager.ACCESS_BUFFER);
            String htmlContentInStringFormat = StreamToString(in);
            in.close();
            htmlWebView.loadDataWithBaseURL(null, htmlContentInStringFormat, "text/html", "utf-8", null);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }

}