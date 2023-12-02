
package ir.sajjadyosefi.accountauthenticator.model.response;

import ir.sajjadyosefi.accountauthenticator.model.AWallet;
import ir.sajjadyosefi.accountauthenticator.model.response.base.ServerResponseBase;

public class ALoginResponse  extends ServerResponseBase {

    private Integer UserCode;
    private String CodeMelli;
    private String UserName;
    private String Email;
    private String Mobile;
    private boolean MobileNumberConfirmed;
    private boolean IsActive;
    private boolean IsDeleted;
    private String SimcardID;
    private String Name;
    private String Family;
    private String Avatar;
    private String ProfileImage;
    private int UserTypeCode;
    private String CreateDate;
    private String TokenType;
    private AWallet wallet;

    public Integer getUserCode() {
        return UserCode;
    }

    public void setUserCode(Integer userCode) {
        UserCode = userCode;
    }

    public String getCodeMelli() {
        return CodeMelli;
    }

    public void setCodeMelli(String codeMelli) {
        CodeMelli = codeMelli;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public boolean isMobileNumberConfirmed() {
        return MobileNumberConfirmed;
    }

    public void setMobileNumberConfirmed(boolean mobileNumberConfirmed) {
        MobileNumberConfirmed = mobileNumberConfirmed;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public String getSimcardID() {
        return SimcardID;
    }

    public void setSimcardID(String simcardID) {
        SimcardID = simcardID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFamily() {
        return Family;
    }

    public void setFamily(String family) {
        Family = family;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }

    public int getUserTypeCode() {
        return UserTypeCode;
    }

    public void setUserTypeCode(int userTypeCode) {
        UserTypeCode = userTypeCode;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public AWallet getWallet() {
        return wallet;
    }

    public void setWallet(AWallet wallet) {
        this.wallet = wallet;
    }

    public void setAuthtoken(String authtokenTypeNormalUser) {
        this.TokenType = authtokenTypeNormalUser;
    }

    public String getAuthtoken() {
        return TokenType;
    }
}
