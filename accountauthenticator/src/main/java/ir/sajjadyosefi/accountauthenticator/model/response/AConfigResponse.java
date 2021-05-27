package ir.sajjadyosefi.accountauthenticator.model.response;

import ir.sajjadyosefi.accountauthenticator.model.AConfig;
import ir.sajjadyosefi.accountauthenticator.model.response.base.ServerResponseBase;

public class AConfigResponse extends ServerResponseBase {

    private AConfig config;

    public AConfig getConfig() {
        return config;
    }

    public void setConfig(AConfig config) {
        this.config = config;
    }

}
