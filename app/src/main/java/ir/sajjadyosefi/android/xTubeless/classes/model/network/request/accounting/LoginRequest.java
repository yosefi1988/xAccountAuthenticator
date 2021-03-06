package ir.sajjadyosefi.android.xTubeless.classes.model.network.request.accounting;

import ir.sajjadyosefi.android.xTubeless.classes.StaticValue;

public class LoginRequest {

    private int IDApplicationVersion  = StaticValue.IDApplicationVersion;
    private String UserName;
    private String Password;
    private String AndroidID;
    private String Email;
    private String MobileNumber;
    private String UserMoarefID;
    private String UserImage;
    private String ProfileImage;


    public int getIDApplicationVersion() {
        return IDApplicationVersion;
    }

    public void setIDApplicationVersion(int IDApplicationVersion) {
        this.IDApplicationVersion = IDApplicationVersion;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAndroidID() {
        return AndroidID;
    }

    public void setAndroidID(String androidID) {
        AndroidID = androidID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getUserMoarefID() {
        return UserMoarefID;
    }

    public void setUserMoarefID(String userMoarefID) {
        UserMoarefID = userMoarefID;
    }

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }



    //manual
    public LoginRequest(String userName, String password, String androidID) {
        UserName = userName;
        Password = password;
        AndroidID = androidID;

        Email = "";
        MobileNumber = "";
        UserMoarefID = "";
        UserImage = "";
        ProfileImage = "";
    }


    //google
    public LoginRequest(String email, String userImage) {
        Email = email;
        UserImage = userImage;

        UserName = email;
        Password = "";
        AndroidID = "";
        MobileNumber = "";
        UserMoarefID = "";
        ProfileImage = "";
    }


    //sim
    public LoginRequest(String mobileNumber) {
        MobileNumber = mobileNumber;

        Email = "";
        UserImage = "";
        UserName = mobileNumber;
        Password = "";
        AndroidID = "";
        UserMoarefID = "";
        ProfileImage = "";
    }


}
