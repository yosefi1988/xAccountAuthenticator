package ir.sajjadyosefi.android.xTubeless.utility;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;

public class DeviceUtil {


    public static String GetAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);

    }

}
