package ir.sajjadyosefi.accountauthenticator.model.response;

import java.util.List;

import ir.sajjadyosefi.accountauthenticator.model.AConfig;
import ir.sajjadyosefi.accountauthenticator.model.ATransaction;
import ir.sajjadyosefi.accountauthenticator.model.AWallet;
import ir.sajjadyosefi.accountauthenticator.model.response.base.ServerResponseBase;

public class ATransactionListResponse extends ServerResponseBase {

    private AWallet wallet;
    private List<ATransaction> transactionList;

    public List<ATransaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<ATransaction> transactionList) {
        this.transactionList = transactionList;
    }

    public AWallet getWallet() {
        return wallet;
    }

    public void setWallet(AWallet wallet) {
        this.wallet = wallet;
    }

}
