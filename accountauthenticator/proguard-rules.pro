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