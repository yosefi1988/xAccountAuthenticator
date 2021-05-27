package ir.sajjadyosefi.accountauthenticator.model;

import java.util.List;

import ir.sajjadyosefi.accountauthenticator.model.Store;

public class AConfig  {

    private List<Store> StoreList;
    private int DeviceId;


    public int getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(int deviceId) {
        DeviceId = deviceId;
    }

    public List<Store> getStoreList() {
        return StoreList;
    }

    public void setStoreList(List<Store> storeList) {
        StoreList = storeList;
    }


}
