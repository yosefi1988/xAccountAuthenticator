package ir.sajjadyosefi.android.sdkpayzarin.network;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.security.ProviderInstaller;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public class Tls12Helper {
    public static void enableTls12(Context context) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH) { // اندروید 4.4 تا 6
            try {
                ProviderInstaller.installIfNeeded(context);
                SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
                sslContext.init(null, null, null);
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
                Log.d("TLS", "TLS 1.2 enabled successfully");
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                Log.e("TLS", "Error enabling TLS 1.2", e);
            } catch (Exception e) {
                Log.e("TLS", "Google Play Services not available?", e);
            }
        }
    }
}
