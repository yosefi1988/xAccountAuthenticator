package ir.sajjadyosefi.accountauthenticator.authentication;

public class AccountGeneral {

    //tubeless
    private static int IDApplicationVersion = 0;
    private static int IDApplication = 0;
    private static String Store = null;
    private static String IP = null;
    private static String AndroidID = null;
    private static String userCode;

    //zarinpal
    private static String appName = null;
    private static String zarinpalpayment = null;
    private static String schemezarinpalpayment = null;

    //android
    public static final String ACCOUNT_TYPE = "ir.sajjadyosefi.android";
    public static final String ACCOUNT_NAME = "Tubeless";

    public static final String AUTHTOKEN_TYPE_NORMAL_USER = "NORMAL USER";
    public static final String AUTHTOKEN_TYPE_NORMAL_USER_LABEL = "Normal access to a Tubeless account";
    public static final String AUTHTOKEN_TYPE_CREATOR_USER = "CREATOR USER";
    public static final String AUTHTOKEN_TYPE_CREATOR_USER_LABEL = "Creator access to a Tubeless account";
    public static final String AUTHTOKEN_TYPE_ADMIN_USER = "ADMIN USER";
    public static final String AUTHTOKEN_TYPE_ADMIN_USER_LABEL = "Admin access to a Tubeless account";


    public static final ServerAuthenticate sServerAuthenticate = new ParseComServerAuthenticate();

    public static int getIDApplicationVersion() {
        return IDApplicationVersion;
    }

    public static void setIDApplicationVersion(int IDApplicationVersion) {
        AccountGeneral.IDApplicationVersion = IDApplicationVersion;
    }

    public static int getIDApplication() {
        return IDApplication;
    }

    public static void setIDApplication(int IDApplication) {
        AccountGeneral.IDApplication = IDApplication;
    }

    public static String getStore() {
        return Store;
    }

    public static void setStore(String store) {
        Store = store;
    }

    public static String getIP() {
        return IP;
    }

    public static void setIP(String IP) {
        AccountGeneral.IP = IP;
    }

    public static String getAndroidID() {
        return AndroidID;
    }

    public static void setAndroidID(String androidID) {
        AndroidID = androidID;
    }

    public static void setAppName(String string) {
        appName = string;
    }

    public static String getAppName() {
        return appName;
    }

    public static void setZarinpalpayment(String zarinpalpayment11) {
        zarinpalpayment = zarinpalpayment11;
    }

    public static void setSchemezarinpalpayment(String return11) {
        schemezarinpalpayment = return11;
    }

    public static String getZarinpalpayment() {
        return zarinpalpayment;
    }

    public static String getSchemezarinpalpayment() {
        return schemezarinpalpayment;
    }

    public static void setUserCode(String usercode) {
        userCode = usercode;
    }

    public static String getUserCode() {
        return userCode;
    }
}
