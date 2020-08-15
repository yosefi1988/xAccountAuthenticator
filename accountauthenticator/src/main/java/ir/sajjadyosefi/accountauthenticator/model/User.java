
package ir.sajjadyosefi.accountauthenticator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("userImage")
    @Expose
    private String userImage;
    @SerializedName("profileImage")
    @Expose
    private String profileImage;

    @SerializedName("authtoken")
    @Expose
    private String authtoken;

    @SerializedName("balanse")
    @Expose
    private Object balanse;
    @SerializedName("tubelessException")
    @Expose
    private TubelessException tubelessException;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Object getBalanse() {
        return balanse;
    }

    public void setBalanse(Object balanse) {
        this.balanse = balanse;
    }

    public TubelessException getTubelessException() {
        return tubelessException;
    }

    public void setTubelessException(TubelessException tubelessException) {
        this.tubelessException = tubelessException;
    }


    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

}
