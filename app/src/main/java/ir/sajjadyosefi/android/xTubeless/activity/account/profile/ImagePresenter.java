package ir.sajjadyosefi.android.xTubeless.activity.account.profile;

import android.Manifest;
import android.net.Uri;

import java.io.File;


public class ImagePresenter implements IProfilePresenter {

    private final IProfileView view;
    private String selectedFile;

    public ImagePresenter(IProfileView view) {
        this.view = view;
    }


    @Override
    public void cameraClick() {
        if (!view.checkPermission(Manifest.permission.CAMERA)) {
            view.showPermissionDialog(false);
            return;
        }

        File file = view.newFile();

        if (file == null) {
            view.showErrorDialog();
            return;
        }

        view.startCamera(file);
    }

    @Override
    public void chooseGalleryClick() {
        if (!view.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            view.showPermissionDialog(true);
            return;
        }

        view.chooseGallery();
    }

    @Override
    public void saveImage(String path) {
        selectedFile = path;
    }

    @Override
    public Uri cropImage(Uri fileUri) {
        if (view != null)
            return view.startCropActivity(fileUri);
        return null;
    }

    @Override
    public String getImage() {
        return selectedFile;
    }

    @Override
    public void showPreview(File mFile) {
        view.displayImagePreview(mFile);
    }

}

