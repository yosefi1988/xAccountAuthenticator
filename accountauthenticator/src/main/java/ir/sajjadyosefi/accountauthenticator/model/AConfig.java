package ir.sajjadyosefi.accountauthenticator.model;

import java.util.List;

public class AConfig  {

    private List<AStore> StoreList;
    private List<AUMIC> umic;
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


    public List<AUMIC> getUmic() {
        return umic;
    }

    public void setUmic(List<AUMIC> umic) {
        this.umic = umic;
    }
}
