package ir.sajjadyosefi.accountauthenticator.model.response;


import ir.sajjadyosefi.accountauthenticator.model.TubelessException;

/**
 * Created by sajjad on 10/31/2016.
 */
public class ServerResponseBase {
    private TubelessException tubelessException;

    public TubelessException getTubelessException() {
        return tubelessException;
    }

    public void setTubelessException(TubelessException tubelessException) {
        this.tubelessException = tubelessException;
    }

    public ServerStatus getServerStatus() {
        return null;
    }
}
