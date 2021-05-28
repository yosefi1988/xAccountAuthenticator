package ir.sajjadyosefi.accountauthenticator.model;

public class ATransaction {
    private String RefrenceNo;
    private String DateTime;
    private int Amount;
    private float Zarib;


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
