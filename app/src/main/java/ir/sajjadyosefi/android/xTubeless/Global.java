package ir.sajjadyosefi.android.xTubeless;

import android.os.Environment;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import java.io.File;
import ir.sajjadyosefi.android.xTubeless.networkLayout.retrofit.RetrofitHelperTubeless;
import ir.sajjadyosefi.android.xTubeless.classes.model.user.User;
//import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import static ir.sajjadyosefi.android.xTubeless.widget.CustomEditText.FONT_IRANSANS_MOBILE_NORMAL_TTF;


/**
 * Created by Sajad on 2/11/2017.
 */
public class Global extends MultiDexApplication {

    //_____________ ok ________________
    public static User user = null;
    //_________________________________

    public static RetrofitHelperTubeless apiManagerTubeless;


    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

        //font 6 ok
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath(FONT_IRANSANS_MOBILE_NORMAL_TTF)
//                .setFontAttrId(R.string.font_secondary_bold)
//                .build());


        apiManagerTubeless = RetrofitHelperTubeless.getInstance();

        //picasso cache
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);

    }


    public static final String downloadDirectory = "/DCIM/TubelessImages/";
    public static File getFileLocalPath(String fileName){
        return  new File(Environment.getExternalStorageDirectory() + "/"  + downloadDirectory + fileName);
    }


}