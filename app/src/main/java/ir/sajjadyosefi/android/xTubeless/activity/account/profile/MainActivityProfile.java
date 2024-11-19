package ir.sajjadyosefi.android.xTubeless.activity.account.profile;

import android.Manifest;
import android.accounts.Account;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.internal.Primitives;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.io.File;
import java.lang.reflect.Type;


import ir.sajjadyosefi.android.xTubeless.Global;
import ir.sajjadyosefi.android.xTubeless.R;
import ir.sajjadyosefi.android.xTubeless.activity.TubelessTransparentStatusBarActivity;
import ir.sajjadyosefi.accountauthenticator.classes.SAccounts;
import ir.sajjadyosefi.android.xTubeless.classes.model.exception.TubelessException;
import ir.sajjadyosefi.android.xTubeless.classes.model.network.request.accounting.LoginRequest;
import ir.sajjadyosefi.android.xTubeless.classes.model.network.response.ServerResponseBase;
import ir.sajjadyosefi.android.xTubeless.classes.model.user.User;

import ir.sajjadyosefi.android.xTubeless.utility.DeviceUtil;
import ir.sajjadyosefi.android.xTubeless.utility.DialogUtil;
import ir.sajjadyosefi.android.xTubeless.utility.file.UriUtil;
import ir.sajjadyosefi.android.xTubeless.utility.picasso.LoadImages;
import ir.sajjadyosefi.android.xTubeless.utility.xUtility.AndroidOs;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;
import retrofit2.Call;
import retrofit2.Response;

import static ir.sajjadyosefi.android.xTubeless.classes.model.exception.TubelessException.TUBELESS_RESPONSE_BODY_IS_NULL;
import static ir.sajjadyosefi.android.xTubeless.utility.DialogUtil.SelectSource;

public class MainActivityProfile extends TubelessTransparentStatusBarActivity implements IProfileView {

    public static final int PERMISSION_REQUEST_TAKE_PHOTO = 10010;
    public static final int PERMISSION_REQUEST_GALLERY_PHOTO = 10020;

    private static final int AVATAR_SELECTED = 1;
    private static final int PROFILE_SELECTED = 2;
    private static final int NOT_SELECTED = 0;
    private static int SELECTED_IMAGE = NOT_SELECTED;

    Context context;
    Activity activity;

    Button marginx;
    EditText ueditTextNameUserId;
    EditText editTextName;
    EditText editTextEmail;

    ImageView headerProfileImage;
    ImageButton userAvatarPhoto ;
    TextView userProfileShortBio;
    TextView txtProgress;
    Button uploadFileProgress;
    Button btnUploadFileWithoutProgress;

    private ImagePresenter mImagePresenter;

    File mPhotoFile;

    public synchronized static Intent getIntent(Context context, Bundle bundle) {
        bundle.putString("item1","value1");
        bundle.putString("item2","value1");
        Intent intent = new Intent(context, MainActivityProfile.class);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);
        marginx = findViewById(R.id.margin);
        ueditTextNameUserId = findViewById(R.id.ueditTextNameUserId);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        headerProfileImage = findViewById(R.id.header_cover_image);
        userAvatarPhoto = findViewById(R.id.user_profile_photo);
        userProfileShortBio = findViewById(R.id.user_profile_name);
        txtProgress = findViewById(R.id.textViewProgress);
        uploadFileProgress = findViewById(R.id.upload_file_progress);
        btnUploadFileWithoutProgress = findViewById(R.id.btn_upload_file_without_progress);

        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);

        if (hasBackKey && hasHomeKey) {
            marginx.setVisibility(View.GONE);

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Window w = getWindow();
                w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                marginx.setVisibility(View.INVISIBLE);
            }
        }

        context = this;
        activity = this;
        mImagePresenter = new ImagePresenter(this);
        if (Global.user == null){
            finish();
        }else {
            ueditTextNameUserId.setText(Global.user.getUserId() == 0 ? "" : Global.user.getUserId() + "");
            editTextName.setText(Global.user.getUserName() == null ? "" : Global.user.getUserName());
            editTextEmail.setText(Global.user.getEmail() == null ? "" : Global.user.getEmail());
        }
        getImageFromSevice();
    }

    @Override
    public SystemBarTintManager getSystemBarTint() {
        return null;
    }

    @Override
    public boolean hasTranslucentNavigation() {
        return false;
    }

    @Override
    public boolean hasTranslucentStatusBar() {
        return false;
    }

    @Override
    public BottomNavigation getBottomNavigation() {
        return null;
    }

    @Override
    public boolean hasAppBarLayout() {
        return false;
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    private String folder = null;
    private boolean withTimeStamp = true;

    @Override
    @SuppressLint("ResourceAsColor")
    public Uri startCropActivity(Uri source){
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0){
            switch (requestCode) {
                case PERMISSION_REQUEST_GALLERY_PHOTO:
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        //GRANTED
                        mImagePresenter.chooseGalleryClick();
                    }else {
                        //DENIED
                        showSettingsDialog();
                    }
                    break;
                case PERMISSION_REQUEST_TAKE_PHOTO:
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        //GRANTED
                        mImagePresenter.cameraClick();
                    }else {
                        //DENIED
                        showSettingsDialog();
                    }
                    break;
                default:
                    break;
            }
        }else {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    //@OnClick({R.id.user_profile_photo,R.id.buttonSignOut,R.id.buttonBack,R.id.header_cover_image, R.id.upload_file_progress, R.id.btn_upload_file_without_progress})
    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.buttonBack:
//                finish();
//                break;
//            case R.id.buttonSignOut:
//                SAccounts sAccounts = new SAccounts(getContext());
//                Account user = sAccounts.getUserAccount();
//
//                //int sdkVersion = android.os.Build.VERSION.SDK_INT;
//                //System.out.println("نسخه API: " + sdkVersion);
//
//
//                if (sAccounts.removeAccount(user)){
//                    Global.user = null;
//                }
//
//                setResult(Activity.RESULT_OK, getIntent());
//                finish();
//                break;
//            case R.id.user_profile_photo:
//                selectImage(this);
//                SELECTED_IMAGE = AVATAR_SELECTED;
//                break;
//            case R.id.header_cover_image:
//                selectImage(this);
//                SELECTED_IMAGE = PROFILE_SELECTED;
//                break;
//        }
    }


    private void selectImage(Context context) {
        txtProgress.setText("");
        SelectSource(context,findViewById(android.R.id.content) , mImagePresenter);
    }

    @Override
    public boolean checkPermission(String permisson) {
        return AndroidOs.checkPermission(context , permisson);
    }

    @Override
    public void showPermissionDialog(boolean isGallery) {

    }


    @Override
    public File getFilePath() {
        return getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    @Override
    public void openSettings() {

    }


    @Override
    public void startCamera(File file) {
    }

    @Override
    public void chooseGallery() {
    }

    @Override
    public File newFile() {
        return null;
    }


    @Override
    public void showErrorDialog() {
        Toast.makeText(getApplicationContext(), getString(R.string.error_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayImagePreview(File mFile) {
    }

    private void getImageFromSevice() {
        LoginRequest request =
                new LoginRequest(Global.user.getUserName(), "" , DeviceUtil.GetAndroidId(context));
        retrofit2.Callback callback = new retrofit2.Callback() {

            @Override
            public void onResponse(Call call, Response response) {
                Gson gson = new Gson();
                JsonElement jsonElement = gson.toJsonTree(response.body());
                ServerResponseBase responseX = null;

                try {
                    if (response.body() == null){
                        new TubelessException(TUBELESS_RESPONSE_BODY_IS_NULL);
                    }

                    responseX = gson.fromJson(jsonElement.getAsString(), ServerResponseBase.class);
                    if (response.body() != null ) {
                        if (responseX.getTubelessException().getCode() != 0) {
                            if (responseX.getTubelessException().getCode() > 0) {
                                if (call != null && response != null) {
                                    Object object = gson.fromJson(jsonElement.getAsString(), (Type) User.class);
                                    User tmpUser = Primitives.wrap(User.class).cast(object);

                                    Global.user.setProfileImage(tmpUser.getProfileImage());
                                    Global.user.setUserImage(tmpUser.getUserImage());

                                    if (true){

                                        String profileImage = Global.user.getProfileImage();
                                        String avatarImage = Global.user.getUserImage();

                                        LoadImages.loadProfileimage(profileImage, headerProfileImage);
                                        LoadImages.loadAvatarimage(avatarImage, userAvatarPhoto);

                                    }else {
                                        String profileImage = Global.user.getProfileImage();
                                        String avatarImage = Global.user.getUserImage();

                                        LoadImages.loadProfileimage(profileImage, headerProfileImage);
                                        LoadImages.loadAvatarimage(avatarImage, userAvatarPhoto);
                                    }
                                }
                            } else {
                                new TubelessException(responseX.getTubelessException().getCode());
                            }
                        }else {
                            new TubelessException(responseX.getTubelessException().getCode());
                        }
                    }else {
                        new TubelessException(TUBELESS_RESPONSE_BODY_IS_NULL);
                    }
                }catch (Exception sException) {
                    int a = 5 ;
                    a++;
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                LoadImages.loadAvatarimage(new String(), userAvatarPhoto);
                LoadImages.loadProfileimage(new String(), headerProfileImage);
            }

            private void retry(Call call) {
                call.clone().enqueue(this);
            }
        };
        Global.apiManagerTubeless.getProfileImages(request, callback);
    }

    @Override
    public String getRealPathFromUri(Uri contentUri) {
        String realpath =  UriUtil.getPath(this,contentUri);            ///storage/5FFB-19EA/DCIM/100ANDRO/DSC_0185.JPG
        return realpath;
    }
    public void showSettingsDialog() {
        DialogUtil.ShowMessageDialog(context,context.getString(R.string.permission_title),context.getString(R.string.WeNeedYourDeviceInfo));
    }
}
