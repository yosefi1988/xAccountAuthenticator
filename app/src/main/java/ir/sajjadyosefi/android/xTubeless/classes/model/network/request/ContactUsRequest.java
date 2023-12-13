package ir.sajjadyosefi.android.xTubeless.classes.model.network.request;


import ir.sajjadyosefi.android.xTubeless.classes.StaticValue;

public class ContactUsRequest {

    private int ApplicationId = StaticValue.IDApplication;
    private long SenderUserID;
    private long ReciverUserID;
    private String text;
    private String title;
    private String phoneNumber;

    public int getApplicationId() {
        return ApplicationId;
    }


    public void setSenderUserID(int senderUserID) {
        SenderUserID = senderUserID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getSenderUserID() {
        return SenderUserID;
    }

    public void setSenderUserID(long senderUserID) {
        SenderUserID = senderUserID;
    }

    public long getReciverUserID() {
        return ReciverUserID;
    }

    public void setReciverUserID(long reciverUserID) {
        ReciverUserID = reciverUserID;
    }
}
