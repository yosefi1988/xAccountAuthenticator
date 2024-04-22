package ir.sajjadyosefi.accountauthenticator.model;

import java.util.List;

public class AStorePermissions {

    public boolean isPostFree() {
        return IsPostFree;
    }

    public void setPostFree(boolean postFree) {
        IsPostFree = postFree;
    }

    public boolean isViewFree() {
        return IsViewFree;
    }

    public void setViewFree(boolean viewFree) {
        IsViewFree = viewFree;
    }

    public boolean isSendImageInPost() {
        return SendImageInPost;
    }

    public void setSendImageInPost(boolean sendImageInPost) {
        SendImageInPost = sendImageInPost;
    }

    private boolean IsPostFree;
    private boolean IsViewFree;
    private boolean SendImageInPost;


}
