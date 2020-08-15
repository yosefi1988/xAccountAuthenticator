package ir.sajjadyosefi.android.xTubeless.utility;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import ir.sajjadyosefi.android.xTubeless.R;
import ir.sajjadyosefi.android.xTubeless.activity.account.profile.ImagePresenter;
import ir.sajjadyosefi.android.xTubeless.classes.model.exception.TubelessException;
import static ir.sajjadyosefi.android.xTubeless.activity.account.profile.MainActivityProfile.PERMISSION_REQUEST_GALLERY_PHOTO;
import static ir.sajjadyosefi.android.xTubeless.activity.account.profile.MainActivityProfile.PERMISSION_REQUEST_TAKE_PHOTO;

public class DialogUtil {

    public static void showConnectionLostFullScreenDialog(Context context, final ProgressBar progressBar, final Runnable runnable) {
        final BottomSheetDialog dialog = new BottomSheetDialog(context);
        TubelessException.ShowSheetFullScreenDialog(context,dialog ,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(runnable,5);
                dialog.dismiss();
                if (progressBar != null)
                    progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    public static void showConnectionLostDialog(Context context, final ProgressBar progressBar, final Runnable runnable) {
        final BottomSheetDialog dialog = new BottomSheetDialog(context);
        TubelessException.ShowSheetDialog(context,dialog ,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(runnable,5);
                dialog.dismiss();
                if (progressBar != null)
                    progressBar.setVisibility(View.VISIBLE);
            }
        });
    }



    public static void ShowMessageDialog(final Context context, String title, String text ){
        ShowMessageDialog(context, title, text , null);
    }
    public static void ShowMessageDialog(final Context context, String title, String text ,boolean isGallery){
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.layout_dialog, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialoglayout);
        final AlertDialog alertDialog1 = builder.create();

        final Button cancelBtn = (Button) dialoglayout.findViewById(R.id.buttonCancel);
        final Button okBtn = (Button) dialoglayout.findViewById(R.id.buttonOk);
        okBtn.setVisibility(View.GONE);

        TextView textViewTitle = (TextView) dialoglayout.findViewById(R.id.title);
        TextView textViewText = (TextView) dialoglayout.findViewById(R.id.textViewStatment);
        textViewText.setText(text);
        textViewTitle.setText(title);
        cancelBtn.setText(context.getString(R.string.ok));


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isGallery) {
                    ActivityCompat.requestPermissions(
                            ((Activity)context),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_REQUEST_GALLERY_PHOTO);
                }else {
                    ActivityCompat.requestPermissions(
                            ((Activity)context),
                            new String[]{Manifest.permission.CAMERA},
                            PERMISSION_REQUEST_TAKE_PHOTO);
                }
                alertDialog1.dismiss();
            }
        });


        try {
            alertDialog1.show();
        }catch (Exception ex){

        }

    }

    public static void ShowMessageDialog(final Context context, String title, String text, View.OnClickListener clickListner){
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.layout_dialog, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialoglayout);
        final AlertDialog alertDialog1 = builder.create();


        final Button cancelBtn = (Button) dialoglayout.findViewById(R.id.buttonCancel);
        final Button okBtn = (Button) dialoglayout.findViewById(R.id.buttonOk);
        okBtn.setVisibility(View.GONE);

        TextView textViewTitle = (TextView) dialoglayout.findViewById(R.id.title);
        TextView textViewText = (TextView) dialoglayout.findViewById(R.id.textViewStatment);
        textViewText.setText(text);
        textViewTitle.setText(title);
        cancelBtn.setText(context.getString(R.string.ok));

        if(clickListner != null){
//            cancelBtn.setVisibility(View.INVISIBLE);
//            alertDialog1.setCancelable(false);
            cancelBtn.setOnClickListener(clickListner);
        } else {
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog1.cancel();
                }
            });
        }


        try {
            alertDialog1.show();
        }catch (Exception ex){

        }
    }




    public static void SelectSource(Context context, ViewGroup viewGroup, ImagePresenter mImagePresenter) {

        //then we will inflate the custom alert dialog xml that we created
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.layout_dialog, viewGroup, false);
        TextView textViewStatment = dialogView.findViewById(R.id.textViewStatment);
        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
        Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);
        TextView title = dialogView.findViewById(R.id.title);

        title.setText(context.getString(R.string.avatar_picture));
        textViewStatment.setText(context.getString(R.string.select_image_source));
        buttonOk.setText(context.getString(R.string.camera));
        buttonCancel.setText(context.getString(R.string.gallery));

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final android.app.AlertDialog alertDialog = builder.create();
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                mImagePresenter.chooseGalleryClick();
            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                mImagePresenter.cameraClick();
            }
        });
        alertDialog.show();
    }

}
