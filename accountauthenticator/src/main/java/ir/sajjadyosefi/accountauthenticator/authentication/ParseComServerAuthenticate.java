package ir.sajjadyosefi.accountauthenticator.authentication;

import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ir.sajjadyosefi.accountauthenticator.classes.Config;
import ir.sajjadyosefi.accountauthenticator.classes.exception.TubelessException;
import ir.sajjadyosefi.accountauthenticator.model.request.AChangePasswordRequest;
import ir.sajjadyosefi.accountauthenticator.model.request.ADeviceRegisterRequest;
import ir.sajjadyosefi.accountauthenticator.model.request.ALoginRequest;
import ir.sajjadyosefi.accountauthenticator.model.request.ATransactionListRequest;
import ir.sajjadyosefi.accountauthenticator.model.request.AWalletChargeRequest;
import ir.sajjadyosefi.accountauthenticator.model.response.ALoginResponse;
import ir.sajjadyosefi.accountauthenticator.model.response.AConfigResponse;
import ir.sajjadyosefi.accountauthenticator.model.response.ATransactionListResponse;
import ir.sajjadyosefi.accountauthenticator.model.response.AWalletChargeResponse;

import static ir.sajjadyosefi.accountauthenticator.authentication.AccountGeneral.AUTHTOKEN_TYPE_NORMAL_USER;


public class ParseComServerAuthenticate implements ServerAuthenticate {
    @Override
    public ALoginResponse userSignUp(ALoginRequest ALoginRequest) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "http://test.sajjadyosefi.ir/Api/User/Login";
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("AndroidID", ALoginRequest.getAndroidID()));
        params.add(new BasicNameValuePair("IDApplicationVersion", ALoginRequest.getIDApplicationVersion() + ""));

        params.add(new BasicNameValuePair("UserName", ALoginRequest.getUserName()));
        params.add(new BasicNameValuePair("Password", ALoginRequest.getPassword()));
//        params.add(new BasicNameValuePair("Email", loginRequest.getEmail()));
        params.add(new BasicNameValuePair("UserImage", ALoginRequest.getUserImage()));
//        params.add(new BasicNameValuePair("MobileNumber", loginRequest.getMobileNumber()));
        params.add(new BasicNameValuePair("ProfileImage", ALoginRequest.getProfileImage()));
        params.add(new BasicNameValuePair("UserMoarefID", ALoginRequest.getUserMoarefID()));
        httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

        HttpResponse httpResponse = httpClient.execute(httpPost);

        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            try {
                String responseString = EntityUtils.toString(httpResponse.getEntity());
                if (httpResponse.getStatusLine().getStatusCode() != 200) {
                    ParseComError error = new Gson().fromJson(responseString, ParseComError.class);
                    throw new Exception("Error signing-in ["+error.code+"] - " + error.error);
                }

                Log.d("sajjadx",responseString);
                Log.d("sajjadx",responseString.substring(1));
                Log.d("sajjadx",responseString.substring(1,responseString.length()-1));


                ALoginResponse loggedUser = new Gson().fromJson(responseString.substring(1,responseString.length()-1).replace("\\" ,""), ALoginResponse.class);
//                User loggedUser = new Gson().fromJson(responseString, User.class);
//                Gson gson = new Gson();
//                JsonElement jsonElement = gson.toJsonTree(httpResponse.getEntity());
//                User object = gson.fromJson(jsonElement, User.class);

                //loggedUser.setAuthtoken(loggedUser.getUserId() + "");
                return loggedUser;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }
//    public String userSignUp(String name, String email, String pass, String authType) throws Exception {
//        String url = "https://api.parse.com/1/users";
//
//        DefaultHttpClient httpClient = new DefaultHttpClient();
//        HttpPost httpPost = new HttpPost(url);
//
//        httpPost.addHeader("X-Parse-Application-Id","XUafJTkPikD5XN5HxciweVuSe12gDgk2tzMltOhr");
//        httpPost.addHeader("X-Parse-REST-API-Key", "8L9yTQ3M86O4iiucwWb4JS7HkxoSKo7ssJqGChWx");
//        httpPost.addHeader("Content-Type", "application/json");
//
//        String user = "{\"username\":\"" + email + "\",\"password\":\"" + pass + "\",\"phone\":\"415-392-0202\"}";
//        HttpEntity entity = new StringEntity(user);
//        httpPost.setEntity(entity);
//
//        String authtoken = null;
//        try {
//            HttpResponse response = httpClient.execute(httpPost);
//            String responseString = EntityUtils.toString(response.getEntity());
//
//            if (response.getStatusLine().getStatusCode() != 201) {
//                ParseComError error = new Gson().fromJson(responseString, ParseComError.class);
//                throw new Exception("Error creating user["+error.code+"] - " + error.error);
//            }
//
//
//            User createdUser = new Gson().fromJson(responseString, User.class);
//
//            authtoken =" createdUser.sessionToken";
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return authtoken;
//    }
    @Override
    public ALoginResponse userSignIn(ALoginRequest ALoginRequest) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = Config.MAINHOST + "/Api/User/Login";
//        String url = "http://192.168.1.5:80/Api/User/Login";
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("AndroidID", ALoginRequest.getAndroidID()));
        params.add(new BasicNameValuePair("IDApplicationVersion", ALoginRequest.getIDApplicationVersion() + ""));
        params.add(new BasicNameValuePair("IP", ALoginRequest.getIP()));

        params.add(new BasicNameValuePair("UserCode", ALoginRequest.getUserCode()));

        if (ALoginRequest.getUserCode() != null && ALoginRequest.getUserCode().length() > 3) {
            params.add(new BasicNameValuePair("Password", "x"));
        } else {
            params.add(new BasicNameValuePair("Password", ALoginRequest.getPassword()));
        }
        params.add(new BasicNameValuePair("UserName", ALoginRequest.getUserName()));
        params.add(new BasicNameValuePair("UserMoarefID", ALoginRequest.getUserMoarefID()));
        params.add(new BasicNameValuePair("UserImage", ALoginRequest.getUserImage()));
        params.add(new BasicNameValuePair("ProfileImage", ALoginRequest.getProfileImage()));
        params.add(new BasicNameValuePair("MetaData", "Testbazar"));            //todo check bazar CafeBazar
        httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

        HttpResponse httpResponse = httpClient.execute(httpPost);

        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String responseString = EntityUtils.toString(httpResponse.getEntity());

            ALoginResponse loggedUser = new Gson().fromJson(responseString.substring(1, responseString.length() - 1).replace("\\", ""), ALoginResponse.class);
            if (loggedUser.getTubelessException().getCode() == 200) {
                loggedUser.setAuthtoken(AUTHTOKEN_TYPE_NORMAL_USER);
                return loggedUser;
            }else {
                throw new TubelessException(loggedUser.getTubelessException().getCode());
            }
        }else {
            throw new Exception("Error signing-in [" + httpResponse.getStatusLine().getStatusCode() + "]");
        }
    }

    @Override
    public ALoginResponse changePassword(AChangePasswordRequest aChangePasswordRequest) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = Config.MAINHOST + "/Api/User/changepassword";
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("o", aChangePasswordRequest.getOldPassword()));
        params.add(new BasicNameValuePair("n", aChangePasswordRequest.getNewPassword()));
        params.add(new BasicNameValuePair("u", aChangePasswordRequest.getUserCode()));
        params.add(new BasicNameValuePair("a", aChangePasswordRequest.getAndroidID()));

        httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

        HttpResponse httpResponse = httpClient.execute(httpPost);

        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String responseString = EntityUtils.toString(httpResponse.getEntity());

            ALoginResponse loggedUser = new Gson().fromJson(responseString.substring(1, responseString.length() - 1).replace("\\", ""), ALoginResponse.class);
            if (loggedUser.getTubelessException().getCode() == 200) {
                return loggedUser;
            }else {
                throw new TubelessException(loggedUser.getTubelessException().getCode());
            }
        }else {
            throw new Exception("Error signing-in [" + httpResponse.getStatusLine().getStatusCode() + "]");
        }
    }

    @Override
    public AConfigResponse deviceRegister(ADeviceRegisterRequest request) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = Config.MAINHOST + "/Api/Device/RegDevice";
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("DeviceId", request.getDeviceId()));
        params.add(new BasicNameValuePair("AndroidAPI", request.getAndroidAPI() + ""));
        params.add(new BasicNameValuePair("AndroidVersion", request.getAndroidVersion() + ""));
        params.add(new BasicNameValuePair("ApplicationId", request.getApplicationId() + ""));
        params.add(new BasicNameValuePair("Board", request.getBoard() + ""));
        params.add(new BasicNameValuePair("Brand", request.getBrand() + ""));
        params.add(new BasicNameValuePair("BuildId", request.getBuildId() + ""));
        params.add(new BasicNameValuePair("Display", request.getDisplay() + ""));
        params.add(new BasicNameValuePair("IP", request.getIP() + ""));
        params.add(new BasicNameValuePair("Manufacturer", request.getManufacturer() + ""));
        params.add(new BasicNameValuePair("Model", request.getModel() + ""));
        params.add(new BasicNameValuePair("Serial", request.getSerial() + ""));
        params.add(new BasicNameValuePair("Store", request.getStore() + ""));

        httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

//        for(NameValuePair valPair : params) { //foreach loop
//            if(valPair.getValue().equals(strToCompareAgainst)) { //retrieve value of the current NameValuePair and compare
//            Log.d(valPair.toString(),valPair.toString()); //success
//            }
//        }

        HttpResponse httpResponse = httpClient.execute(httpPost);

        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String authtoken = null;
            String responseString = EntityUtils.toString(httpResponse.getEntity());

//                Log.d("sajjadx",responseString);
//                Log.d("sajjadx",responseString.substring(1));
//                Log.d("sajjadx",responseString.substring(1,responseString.length()-1));

            AConfigResponse responseX2 = new Gson().fromJson(responseString.substring(1,responseString.length()-1).replace("\\" ,""), AConfigResponse.class);

            if (responseX2.getTubelessException().getCode() == 200) {
                return responseX2;
            }else {
                throw new TubelessException(responseX2.getTubelessException().getCode());
            }
        }else {
            throw new TubelessException(httpResponse.getStatusLine().getStatusCode());
        }
    }

    @Override
    public ATransactionListResponse transactionList(ATransactionListRequest request) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = Config.MAINHOST + "/Api/Wallet/WalletTransactionList";
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("UserCode", request.getUserCode()));
        params.add(new BasicNameValuePair("pageSize", request.getPageSize() + ""));
        params.add(new BasicNameValuePair("pageIndex", request.getPageIndex() + ""));

        if (request.getApplicationId() != null)
            params.add(new BasicNameValuePair("IDApplication", request.getApplicationId() + ""));

        if (request.getTtc() != null)
            params.add(new BasicNameValuePair("Amount", request.getAmount() + ""));

        if (request.getAmountMin() != null)
            params.add(new BasicNameValuePair("AmountMin", request.getAmountMin() + ""));

        if (request.getAmountMax() != null)
            params.add(new BasicNameValuePair("AmountMax", request.getAmountMax() + ""));

        if (request.getTtc() != null) {
            params.add(new BasicNameValuePair("ttc", request.getTtc() + ""));
        }


        httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

        for(NameValuePair valPair : params) { //foreach loop
            if(valPair.getValue().equals("strToCompareAgainst")) { //retrieve value of the current NameValuePair and compare
            Log.d(valPair.toString(),valPair.toString()); //success
            }
        }

        HttpResponse httpResponse = httpClient.execute(httpPost);

        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String responseString = EntityUtils.toString(httpResponse.getEntity());

//                Log.d("sajjadx",responseString);
//                Log.d("sajjadx",responseString.substring(1));
//                Log.d("sajjadx",responseString.substring(1,responseString.length()-1));

            ATransactionListResponse responseX2 = new Gson().fromJson(responseString.substring(1,responseString.length()-1).replace("\\" ,""), ATransactionListResponse.class);

            if (responseX2.getTubelessException().getCode() == 200) {
                return responseX2;
            }else {
                throw new TubelessException(responseX2.getTubelessException().getCode());
            }
        }else {
            throw new TubelessException(httpResponse.getStatusLine().getStatusCode());
        }

    }

    @Override
    public AWalletChargeResponse chargeWallet(AWalletChargeRequest request) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = Config.MAINHOST + "/Api/Wallet/WalletChargeTransaction";
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("IP", request.getIP()));
        params.add(new BasicNameValuePair("AndroidAPI", request.getAndroidID() + ""));
        params.add(new BasicNameValuePair("IDApplicationVersion", request.getIDApplicationVersion() + ""));

        params.add(new BasicNameValuePair("metaData", request.getMetaData() + ""));
        params.add(new BasicNameValuePair("UserCode", request.getUserCode() + ""));
        params.add(new BasicNameValuePair("Amount", request.getAmount() + ""));

        httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
//        for(NameValuePair valPair : params) { //foreach loop
//            if(valPair.getValue().equals(strToCompareAgainst)) { //retrieve value of the current NameValuePair and compare
//            Log.d(valPair.toString(),valPair.toString()); //success
//            }
//        }

        HttpResponse httpResponse = httpClient.execute(httpPost);

        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String authtoken = null;
            String responseString = EntityUtils.toString(httpResponse.getEntity());

//                Log.d("sajjadx",responseString);
//                Log.d("sajjadx",responseString.substring(1));
//                Log.d("sajjadx",responseString.substring(1,responseString.length()-1));

            AWalletChargeResponse responseX2 = new Gson().fromJson(responseString.substring(1,responseString.length()-1).replace("\\" ,""), AWalletChargeResponse.class);

            if (responseX2.getTubelessException().getCode() == 200) {
                return responseX2;
            }else {
                throw new TubelessException(responseX2.getTubelessException().getCode());
            }
        }else {
            throw new TubelessException(httpResponse.getStatusLine().getStatusCode());
        }
    }

//    public String userSignIn(String user, String pass, String authType) throws Exception {
//        Log.d("udini", "userSignIn");
//
//        DefaultHttpClient httpClient = new DefaultHttpClient();
//        String url = "https://api.parse.com/1/login";
//
//
//        String query = null;
//        try {
//            query = String.format("%s=%s&%s=%s", "username", URLEncoder.encode(user, "UTF-8"), "password", pass);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        url += "?" + query;
//
//        HttpGet httpGet = new HttpGet(url);
//
//        httpGet.addHeader("X-Parse-Application-Id", "XUafJTkPikD5XN5HxciweVuSe12gDgk2tzMltOhr");
//        httpGet.addHeader("X-Parse-REST-API-Key", "8L9yTQ3M86O4iiucwWb4JS7HkxoSKo7ssJqGChWx");
//
//        HttpParams params = new BasicHttpParams();
//        params.setParameter("username", user);
//        params.setParameter("password", pass);
//        httpGet.setParams(params);
////        httpGet.getParams().setParameter("username", user).setParameter("password", pass);
//
//        String authtoken = null;
//        try {
//            HttpResponse response = httpClient.execute(httpGet);
//
//            String responseString = EntityUtils.toString(response.getEntity());
//            if (response.getStatusLine().getStatusCode() != 200) {
//                ParseComError error = new Gson().fromJson(responseString, ParseComError.class);
//                throw new Exception("Error signing-in ["+error.code+"] - " + error.error);
//            }
//
//            User loggedUser = new Gson().fromJson(responseString, User.class);
//            authtoken = loggedUser.sessionToken;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return authtoken;
//    }


    private class ParseComError implements Serializable {
        int code;
        String error;
    }
}
