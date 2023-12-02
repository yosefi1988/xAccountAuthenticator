package ir.sajjadyosefi.accountauthenticator.model.response;

import java.util.List;

import ir.sajjadyosefi.accountauthenticator.model.ATransaction;
import ir.sajjadyosefi.accountauthenticator.model.AWallet;
import ir.sajjadyosefi.accountauthenticator.model.response.base.ServerResponseBase;

public class AWalletChargeResponse extends ServerResponseBase {

    private List<ATransaction> transactionList;
    private AWallet wallet;

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
