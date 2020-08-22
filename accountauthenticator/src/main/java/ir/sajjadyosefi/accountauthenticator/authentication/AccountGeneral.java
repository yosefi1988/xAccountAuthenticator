package ir.sajjadyosefi.accountauthenticator.authentication;

public class AccountGeneral {

    public static final int IDApplicationVersion = 106;
    public static final int IDApplication = 23;

    public static final String ACCOUNT_TYPE = "ir.sajjadyosefi.android";
    public static final String ACCOUNT_NAME = "Tubeless";

    public static final String AUTHTOKEN_TYPE_READ_ONLY = "Read only";
    public static final String AUTHTOKEN_TYPE_READ_ONLY_LABEL = "Read only access to an Tubeless account";

    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS_LABEL = "Full access to an Tubeless account";

    public static final ServerAuthenticate sServerAuthenticate = new ParseComServerAuthenticate();


}
