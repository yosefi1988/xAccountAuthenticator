package ir.sajjadyosefi.accountauthenticator.authentication;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import ir.sajjadyosefi.accountauthenticator.R;
import ir.sajjadyosefi.accountauthenticator.classes.exception.TubelessException;
import ir.sajjadyosefi.accountauthenticator.classes.util;
import ir.sajjadyosefi.accountauthenticator.model.LoginRequest;
import ir.sajjadyosefi.accountauthenticator.model.User;
import ir.sajjadyosefi.accountauthenticator.model.response.ServerResponseBase;
import okhttp3.internal.Util;
import retrofit2.http.QueryMap;


public class ParseComServerAuthenticate implements ServerAuthenticate {
    @Override
    public User userSignUp(LoginRequest loginRequest) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "http://test.sajjadyosefi.ir/Api/User/Login";
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("AndroidID", loginRequest.getAndroidID()));
        params.add(new BasicNameValuePair("IDApplicationVersion", loginRequest.getIDApplicationVersion() + ""));

        params.add(new BasicNameValuePair("UserName", loginRequest.getUserName()));
        params.add(new BasicNameValuePair("Password", loginRequest.getPassword()));
        params.add(new BasicNameValuePair("Email", loginRequest.getEmail()));
        params.add(new BasicNameValuePair("UserImage", loginRequest.getUserImage()));
        params.add(new BasicNameValuePair("MobileNumber", loginRequest.getMobileNumber()));
        params.add(new BasicNameValuePair("ProfileImage", loginRequest.getProfileImage()));
        params.add(new BasicNameValuePair("UserMoarefID", loginRequest.getUserMoarefID()));
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


                User loggedUser = new Gson().fromJson(responseString.substring(1,responseString.length()-1).replace("\\" ,""), User.class);
//                User loggedUser = new Gson().fromJson(responseString, User.class);
//                Gson gson = new Gson();
//                JsonElement jsonElement = gson.toJsonTree(httpResponse.getEntity());
//                User object = gson.fromJson(jsonElement, User.class);

                loggedUser.setAuthtoken(loggedUser.getUserId() + "");
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
    public User userSignIn(LoginRequest loginRequest) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "http://test.sajjadyosefi.ir/Api/User/Login";
//        String url = "http://192.168.20.106:3071/Api/User/Login";
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("AndroidID", loginRequest.getAndroidID()));
        params.add(new BasicNameValuePair("IDApplicationVersion", loginRequest.getIDApplicationVersion() + ""));

        params.add(new BasicNameValuePair("UserName", loginRequest.getUserName()));
        params.add(new BasicNameValuePair("Password", loginRequest.getPassword()));
        params.add(new BasicNameValuePair("Email", loginRequest.getEmail()));
        params.add(new BasicNameValuePair("UserImage", loginRequest.getUserImage()));
        params.add(new BasicNameValuePair("MobileNumber", loginRequest.getMobileNumber()));
        params.add(new BasicNameValuePair("ProfileImage", loginRequest.getProfileImage()));
        params.add(new BasicNameValuePair("UserMoarefID", loginRequest.getUserMoarefID()));
        httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

        for(NameValuePair valPair : params) { //foreach loop
//            if(valPair.getValue().equals(strToCompareAgainst)) { //retrieve value of the current NameValuePair and compare
                //Log.d(valPair.toString(),valPair.toString()); //success
//            }
        }

        HttpResponse httpResponse = httpClient.execute(httpPost);

        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String authtoken = null;
//            try {
                String responseString = EntityUtils.toString(httpResponse.getEntity());
                if (httpResponse.getStatusLine().getStatusCode() != 200) {
                    ParseComError error = new Gson().fromJson(responseString, ParseComError.class);
                    throw new Exception("Error signing-in ["+error.code+"] - " + error.error);
                }

//                Log.d("sajjadx",responseString);
//                Log.d("sajjadx",responseString.substring(1));
//                Log.d("sajjadx",responseString.substring(1,responseString.length()-1));

                ServerResponseBase responseX2 = new Gson().fromJson(responseString.substring(1,responseString.length()-1).replace("\\" ,""), ServerResponseBase.class);

                if (responseX2.getTubelessException().getCode() > 0) {
                    User loggedUser = new Gson().fromJson(responseString.substring(1, responseString.length() - 1).replace("\\", ""), User.class);
//                User loggedUser = new Gson().fromJson(responseString, User.class);
//                Gson gson = new Gson();
//                JsonElement jsonElement = gson.toJsonTree(httpResponse.getEntity());
//                User object = gson.fromJson(jsonElement, User.class);

                    loggedUser.setAuthtoken(loggedUser.getUserId() + "");
                    return loggedUser;
                }else {
                    throw new TubelessException(responseX2.getTubelessException().getCode());
//                    throw new Exception("Error signing-in [" + httpResponse.getStatusLine().getStatusCode() + "]");
                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }else {
//            ParseComError error = new Gson().fromJson(responseString, ParseComError.class);
//            throw new Exception("Error signing-in ["+error.code+"] - " + error.error);
            throw new Exception("Error signing-in [" + httpResponse.getStatusLine().getStatusCode() + "]");
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
