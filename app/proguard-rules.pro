# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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

-dontwarn **
#-keep class ir.sajjadyosefi.android.tubeless.** { *; }
#-keep class ir.sajjadyosefi.android.** { *; }
#-keep class ir.sajjadyosefi.** { *; }

-keep class ir.sajjadyosefi.** {*;}
-keep class * extends ir.sajjadyosefi.android.** {*;}

-keep class it.sephiroth.** {*;}
-keep class * extends it.sephiroth.android.** {*;}

#-keep class com.orm.**{*;}

#-libraryjars libs/litepal-1.5.1.jar
-dontwarn org.litepal.**
-keep class org.litepal.** {*; }

# without this line, I was having ClassCastException
#-keepattributes Signature, *Annotation*

-keep class com.zarinpal.*





##3
-keep class ir.sajjadyosefi.android.samanbanksdkmodule.model.** { *; }
-dontwarn ir.sajjadyosefi.android.samanbanksdkmodule.model.**


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

