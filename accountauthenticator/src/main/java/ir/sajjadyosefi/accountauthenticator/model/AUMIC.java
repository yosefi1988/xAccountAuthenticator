package ir.sajjadyosefi.accountauthenticator.model;

public class AUMIC {
    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isConfirm() {
        return IsConfirm;
    }

    public void setConfirm(boolean confirm) {
        IsConfirm = confirm;
    }

    private String usercode;
    private String mobile;
    private boolean IsConfirm;
}
