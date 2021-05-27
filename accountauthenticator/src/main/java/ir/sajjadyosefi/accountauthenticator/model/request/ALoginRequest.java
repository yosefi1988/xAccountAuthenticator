package ir.sajjadyosefi.accountauthenticator.model.request;


import ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral;

public class ALoginRequest {

    private int IDApplicationVersion = AccountGeneral.getIDApplicationVersion();
    private String IP = AccountGeneral.getIP();
    private String AndroidID = AccountGeneral.getAndroidID();

    private String UserName;
    private String UserCode = null;
    private String Password;
    private String UserMoarefID;
    private String UserImage;
    private String ProfileImage;

    public String getAndroidID() {
        return AndroidID;
    }

    public String getIDApplicationVersion() {
        return IDApplicationVersion + "";
    }

    public String getIP() {
        return IP;
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

    public String getUserCode() {
        return UserCode;
    }



    //manual
    public ALoginRequest(String mobile, String password, String androidID) {
        UserImage = "";
        UserName = mobile;
        Password = password;
        UserMoarefID = "";
        ProfileImage = "";
    }


    //google
    public ALoginRequest(String email, String userImage) {
        UserImage = userImage;
        UserName = email;
        Password = "";
        UserMoarefID = "";
        ProfileImage = "";
    }


    //sim
    public ALoginRequest(String simID) {
        UserName = "simcard:" + simID;
        Password = null;
        UserMoarefID = "";
        UserImage = "";
        ProfileImage = "";
    }


    //Code Melli
    public ALoginRequest(String CodeMelli , int pin) {
        UserName = CodeMelli;
        Password = null;
        UserMoarefID = "";
        UserImage = "";
        ProfileImage = "";
    }

    //User Code
    public ALoginRequest(int userCode,String userCodeX) {
        UserName = null;
        Password = "";
        this.UserCode = userCodeX;
        UserMoarefID = "";
        UserImage = "";
        ProfileImage = "";
    }


}
