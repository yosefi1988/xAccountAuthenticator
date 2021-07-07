package ir.sajjadyosefi.accountauthenticator.model;

public class ATransaction {

    private String RefrenceNo;
    private int Amount;
    private float Zarib;
    private String DateTime;

    //in response list
    private String ID;
    private String CreatorFullName;
    private int TTC;
    private String TTN;
    private String image;
    private String icon;
    private String title;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCreatorFullName() {
        return CreatorFullName;
    }

    public void setCreatorFullName(String creatorFullName) {
        CreatorFullName = creatorFullName;
    }

    public int getTTC() {
        return TTC;
    }

    public void setTTC(int TTC) {
        this.TTC = TTC;
    }

    public String getTTN() {
        return TTN;
    }

    public void setTTN(String TTN) {
        this.TTN = TTN;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRefrenceNo() {
        return RefrenceNo;
    }

    public void setRefrenceNo(String refrenceNo) {
        RefrenceNo = refrenceNo;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public float getZarib() {
        return Zarib;
    }

    public void setZarib(float zarib) {
        Zarib = zarib;
    }
}
