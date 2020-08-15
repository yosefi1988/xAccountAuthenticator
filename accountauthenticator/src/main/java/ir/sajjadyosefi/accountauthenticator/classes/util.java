package ir.sajjadyosefi.accountauthenticator.classes;

import android.content.Context;
import android.provider.Settings;

public class util {
    public static String GetAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
    }

}
