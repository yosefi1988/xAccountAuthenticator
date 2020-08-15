package ir.sajjadyosefi.android.xTubeless.activity.account.profile;

import android.net.Uri;

import java.io.File;

public interface IProfileView {

    boolean checkPermission(String permission);

    void showPermissionDialog(boolean isGallery);

    File getFilePath();

    void openSettings();

    Uri startCropActivity(Uri source);

    void startCamera(File file);

    void chooseGallery();

    File newFile();

    void showErrorDialog();

    void displayImagePreview(File mFile);

    String getRealPathFromUri(Uri contentUri);
}
