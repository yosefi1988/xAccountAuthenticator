package ir.sajjadyosefi.android.zarinpalsdk;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class SimpleLoggingInterceptor implements Interceptor {

    Context _context;

    public SimpleLoggingInterceptor(Context context) {
        _context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // لاگ کردن URL و Headers ریکوئست
        Log.i("ZSDK", "URL: " + request.url());
        Log.i("ZSDK", "Headers: " + request.headers());

        if (request.body() != null) {
            // لاگ کردن نوع محتوای ریکوئست
            Log.i("ZSDK", "Request Body Content Type: " + request.body().contentType());

            // خواندن بادی ریکوئست
            Buffer buffer = new Buffer();
            request.body().writeTo(buffer);
            Log.i("ZSDK", "Request Body: " + buffer.readUtf8());
        }

        // پروسس کردن ریکوئست و دریافت ریسپانس
        Response response = chain.proceed(request);

        // لاگ کردن Status Code و Headers ریسپانس
        Log.i("ZSDK", "Response Code: " + response.code());
        Log.i("ZSDK", "Response Headers: " + response.headers());

        // لاگ کردن بادی ریسپانس
        if (response.body() != null) {
            String responseBodyString = response.body().string();
            Log.i("ZSDK", "Response Body: " + responseBodyString);

            // بازسازی بادی ریسپانس برای برگرداندن به کلاینت
            ResponseBody responseBody = ResponseBody.create(response.body().contentType(), responseBodyString);
            return response.newBuilder().body(responseBody).build();
        }

        return response;
    }
}
