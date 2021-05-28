package ir.sajjadyosefi.accountauthenticator.model;

import java.util.List;

public class AConfig  {

    private List<AStore> StoreList;
    private int DeviceId;


    public int getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(int deviceId) {
        DeviceId = deviceId;
    }

    public List<AStore> getStoreList() {
        return StoreList;
    }

    public void setStoreList(List<AStore> storeList) {
        StoreList = storeList;
    }


}
