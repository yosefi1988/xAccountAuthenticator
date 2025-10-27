package ir.sajjadyosefi.accountauthenticator.model.request;


import ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral;
import kotlin.jvm.Throws;

public class ATransactionRequest {

    private int IDApplicationVersion = AccountGeneral.getIDApplicationVersion();
    private String IP = AccountGeneral.getIP();
    private String AndroidID = AccountGeneral.getAndroidID();

    private String UserCode = null;
    private String Amount;
    private String payMode;
    private String metaData;

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public ATransactionRequest(String amount) throws Exception {
        if (AccountGeneral.getUserCode() == null)
            throw new Exception("UserCode == null");
        UserCode = AccountGeneral.getUserCode();
        Amount = amount;
    }
    public ATransactionRequest(String userCode , String amount) {
        UserCode = userCode;
        Amount = amount;
    }

    public String getAndroidID() {
        return AndroidID;
    }

    public String getIDApplicationVersion() {
        return IDApplicationVersion + "";
    }

    public String getIP() {
        return IP;
    }

    public String getUserCode() {
        return UserCode;
    }

    public String getAmount() {
        return Amount;
    }

}
