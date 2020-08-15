package ir.sajjadyosefi.android.xTubeless.utility.picasso;

import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

import ir.sajjadyosefi.android.xTubeless.R;
import ir.sajjadyosefi.android.xTubeless.utility.RoundedCornersTransformation;

public class LoadImages {

    public static void loadAvatarimage(Object mFile , ImageView userProfilePhoto) {
        RoundedCornersTransformation transformation = new RoundedCornersTransformation(50000, 0);
        if (mFile instanceof File) {
            loadFromFile((File) mFile, userProfilePhoto, transformation);
        }else {
            loadFromUrl((String) mFile, userProfilePhoto, transformation);
        }
    }

    public static void loadProfileimage(Object mFile , ImageView headerCoverImage) {

        if (mFile instanceof File) {
            Picasso.get()
                    .load((File) mFile)
                    .into(headerCoverImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            headerCoverImage.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get()
                                    .load(R.drawable.jpg_paeez)
//                          .transform(transformation)
                                    .into(headerCoverImage);
                        }
                    });
        }else {
            String uri = (String) mFile;
            if (uri == null || uri.length() < 5){
                uri = "";
                Picasso.get()
                        .load(R.drawable.jpg_paeez)
//                  .transform(transformation)
                        .into(headerCoverImage);
            }else {

                Picasso.get()
                        .load((String) mFile)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(headerCoverImage, new Callback() {
                            @Override
                            public void onSuccess() {
                                headerCoverImage.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError(Exception e) {
                                //try to online
                                Picasso.get()
                                        .load((String) mFile)
                                        .into(headerCoverImage, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError(Exception e) {
                                                Picasso.get()
                                                        .load(R.drawable.jpg_paeez)
                                                        .into(headerCoverImage);
                                            }
                                        });

                            }
                        });
            }
        }
    }

    private static void loadFromFile(File mFile, ImageView userProfilePhoto, RoundedCornersTransformation transformation) {

        Picasso.get()
                .load(mFile)
                .placeholder(R.drawable.png_user)
//                    .centerInside()
                .transform(transformation)
                .into(userProfilePhoto, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load(R.drawable.png_user)
//                                    .transform(transformation)
                                .into(userProfilePhoto);
                    }
                });
    }

    private static void loadFromUrl(String mFile, ImageView userProfilePhoto, RoundedCornersTransformation transformation) {
        String uri = mFile;
        if (uri == null || uri.length() < 5){
            uri = "";
            Picasso.get()
                    .load(R.drawable.png_user)
//                  .transform(transformation)
                    .into(userProfilePhoto);
        }else {
            //try Offline
            Picasso.get()
                    .load(mFile)
                    .placeholder(R.drawable.png_user)
//                  .centerInside()
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .transform(transformation)
                    .into(userProfilePhoto, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            //Try again online if cache failed
                            Picasso.get()
                                    .load(mFile)
                                    .transform(transformation)
                                    .into(userProfilePhoto, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            Picasso.get()
                                                    .load(R.drawable.png_user)
                                                    .transform(transformation)
                                                    .into(userProfilePhoto);
                                        }
                                    });
                        }
                    });
        }
    }
}
