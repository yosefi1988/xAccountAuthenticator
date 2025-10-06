# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class com.squareup.okio.** { *; }




# برای نگه داشتن Retrofit
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**

# برای نگه داشتن OkHttp
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**

# برای نگه داشتن Gson Converter
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

# نگه داشتن مدل‌های شما (پکیج دیتا)
-keep class ir.sajjadyosefi.android.sdkpayzarin.** { *; }
-keepclassmembers class * {
    @retrofit2.http.* <methods>;
}

# جلوگیری از حذف annotation ها
-keepattributes Signature
-keepattributes *Annotation*

