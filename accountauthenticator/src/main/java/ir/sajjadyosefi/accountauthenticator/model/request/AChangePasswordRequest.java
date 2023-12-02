package ir.sajjadyosefi.accountauthenticator.model.request;


import ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral;

public class AChangePasswordRequest {

    private int IDApplicationVersion = AccountGeneral.getIDApplicationVersion();
    private String IP = AccountGeneral.getIP();
    private String AndroidID = AccountGeneral.getAndroidID();

    private String UserName;
    private String UserCode = null;
    private String OldPassword;
    private String NewPassword;

    public AChangePasswordRequest(String oldPassword, String newPassword, String userCode, String androidId) {
        OldPassword = oldPassword;
        NewPassword = newPassword;
        AndroidID = androidId;
        UserCode = userCode;
    }

    public String getOldPassword() {
        return OldPassword;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public String getUserCode() {
        return UserCode;
    }

    public String getAndroidID() {
        return AndroidID;
    }
}
