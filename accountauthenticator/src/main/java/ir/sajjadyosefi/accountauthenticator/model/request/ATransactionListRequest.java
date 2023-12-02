package ir.sajjadyosefi.accountauthenticator.model.request;

import ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral;

public class ATransactionListRequest {

    private String UserCode;
    private String pageSize;
    private String pageIndex;

    private String Date;
    private String DateFrom;
    private String DateTo;
    private Integer ApplicationId;
    private String Amount;
    private String AmountMin;
    private String AmountMax;
    private String ttc;



    public String getUserCode() {
        return UserCode;
    }

    public String getPageSize() {
        return pageSize;
    }

    public String getPageIndex() {
        return pageIndex;
    }

    public String getDate() {
        return Date;
    }

    public String getDateFrom() {
        return DateFrom;
    }

    public String getDateTo() {
        return DateTo;
    }

    public Integer getApplicationId() {
        return ApplicationId;
    }

    public String getAmount() {
        return Amount;
    }

    public String getAmountMin() {
        return AmountMin;
    }

    public String getAmountMax() {
        return AmountMax;
    }

    public String getTtc() {
        return ttc;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setDateFrom(String dateFrom) {
        DateFrom = dateFrom;
    }

    public void setDateTo(String dateTo) {
        DateTo = dateTo;
    }

    public void setApplicationId(int applicationId) {
        ApplicationId = applicationId;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public void setAmountMin(String amountMin) {
        AmountMin = amountMin;
    }

    public void setAmountMax(String amountMax) {
        AmountMax = amountMax;
    }

    public void setTtc(String ttc) {
        this.ttc = ttc;
    }


    public ATransactionListRequest(String userCode, String pageSize, String pageIndex) {
        UserCode = userCode;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }


}
