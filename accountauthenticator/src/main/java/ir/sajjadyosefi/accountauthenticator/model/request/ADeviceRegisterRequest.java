package ir.sajjadyosefi.accountauthenticator.model.request;

import ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral;

public class ADeviceRegisterRequest {

    private String DeviceId;
    private int AndroidAPI;
    private String AndroidVersion;
    private int ApplicationId;
    private String Board;
    private String Brand;
    private String BuildId;
    private String Display;
    private String IP;
    private String Manufacturer;
    private String Model;
    private String Serial;
    private String Store;

    public ADeviceRegisterRequest() {
        ApplicationId = AccountGeneral.getIDApplication();
        Store = AccountGeneral.getStore();
        this.IP = AccountGeneral.getIP();
        this.DeviceId = AccountGeneral.getAndroidID();
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public int getAndroidAPI() {
        return AndroidAPI;
    }

    public void setAndroidAPI(int androidAPI) {
        AndroidAPI = androidAPI;
    }

    public String getAndroidVersion() {
        return AndroidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        AndroidVersion = androidVersion;
    }

    public int getApplicationId() {
        return ApplicationId;
    }

    public String getBoard() {
        return Board;
    }

    public void setBoard(String board) {
        Board = board;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getBuildId() {
        return BuildId;
    }

    public void setBuildId(String buildId) {
        BuildId = buildId;
    }

    public String getDisplay() {
        return Display;
    }

    public void setDisplay(String display) {
        Display = display;
    }

    public String getIP() {
        return IP;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getSerial() {
        return Serial;
    }

    public void setSerial(String serial) {
        Serial = serial;
    }

    public String getStore() {
        return Store;
    }

}
