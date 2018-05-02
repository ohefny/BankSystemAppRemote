package b.b.b.bankaccount.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import b.b.b.bankaccount.Model.Account2;
import b.b.b.bankaccount.Model.ActivityModel;
import b.b.b.bankaccount.Model.Customer;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Basma on 4/23/2018.
 */

public class NetworkUtils {
    private static final String LOGIN_ENDPOINT = "https://basma-ahmed-zaky123.000webhostapp.com/login.php";
    private static final String Balance_ENDPOINT = "https://basma-ahmed-zaky123.000webhostapp.com/balance.php";
    private static final String ACCOUNT_ACTIVITY_ENDPOINT = "https://basma-ahmed-zaky123.000webhostapp.com/activity.php";
    private static final String REGISTER_ENDPOINT = "https://basma-ahmed-zaky123.000webhostapp.com/register.php";


    public static Object authenticate(String accountNumber , String password){
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("account_number", accountNumber)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(LOGIN_ENDPOINT)
                .post(formBody)
                .build();


        Response response = null;
        String responseString = "";
        try {
            response = client.newCall(request).execute();
            responseString = response.body().string();
            if( (responseString == null || responseString.equals("") ) ){
                return new IOException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new IOException();
        }

        return parseAuthenticatationResponse(responseString , accountNumber);
    }
    private static Object parseAuthenticatationResponse(String res , String account_number){
        try {
            JSONObject jsonObject = new JSONObject(res);
            String status = jsonObject.getString("status");
            if(status.equals("false")){
                return null;
            }else{
                Customer customer = new Customer();
                customer.setAccountNumber(account_number);
                customer.setCustomerID(jsonObject.getString("customer_id"));
                customer.setName(jsonObject.getString("name"));
                customer.setPhone(jsonObject.getString("phone"));
                customer.setMail(jsonObject.getString("mail"));
                return customer;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return  new IOException();
        }
    }




    public static Object getBalance(String accountNumber){
        if(accountNumber == null || accountNumber.equals("")){
            return null;
        }
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("account_number", accountNumber)
                .build();

        Request request = new Request.Builder()
                .url(Balance_ENDPOINT)
                .post(formBody)
                .build();


        Response response = null;
        String responseString = "";
        try {
            response = client.newCall(request).execute();
            responseString = response.body().string();
            if( (responseString == null || responseString.equals("") ) ){
                return new IOException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new IOException();
        }

        return parseBalanceResponse(responseString);
    }
    private static Object parseBalanceResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            String balance = jsonObject.getString("balance");
            if (balance == null || balance.equals("null")){
                return null;
            }else{
                return balance;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return new IOException();
        }
    }



    public static Object getAccountActivity(String accountNumber){
        if(accountNumber == null || accountNumber.equals("")){
            return null;
        }
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("account_number", accountNumber)
                .build();

        Request request = new Request.Builder()
                .url(ACCOUNT_ACTIVITY_ENDPOINT)
                .post(formBody)
                .build();


        Response response = null;
        String responseString = "";
        try {
            response = client.newCall(request).execute();
            responseString = response.body().string();
            if( (responseString == null || responseString.equals("") ) ){
                return new IOException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new IOException();
        }

        return parseAccountActivityResponse(responseString);
    }
    public static Object parseAccountActivityResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray activitiesArray = jsonObject.getJSONArray("activities");
            ArrayList<ActivityModel> activitiesArrayList = new ArrayList<>();
            for (int i=0 ; i<activitiesArray.length(); i++){
                JSONObject tempObject = activitiesArray.getJSONObject(i);
                ActivityModel activity = new ActivityModel();
                activity.setActivity_id(tempObject.getString("activity_id"));
                activity.setValue(Double.parseDouble( tempObject.getString("value") ) );
                activity.setDate(tempObject.getString("date"));
                activity.setType(tempObject.getString("type"));
                activity.setFromAccount(tempObject.getString("from"));
                activity.setToAccount(tempObject.getString("to"));

                activitiesArrayList.add(activity);
            }
           if(activitiesArrayList.size()==0)
               return null;
            else
                return activitiesArrayList;
        } catch (JSONException e) {
            e.printStackTrace();
            return new IOException();
        }
    }


    public static Object register(String name , String mail , String phone , String password){
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("name", name)
                .add("mail", mail)
                .add("phone", phone)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(REGISTER_ENDPOINT)
                .post(formBody)
                .build();


        Response response = null;
        String responseString = "";
        try {
            response = client.newCall(request).execute();
            responseString = response.body().string();
            if( (responseString == null || responseString.equals("") ) ){
                return new IOException();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new IOException();
        }

        return parseRegisterationResponse(responseString);
    }
    public static Object parseRegisterationResponse(String response){
        String status;
        Account2 account2 = null;
        if(response == null || response.equals("")){
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            status = jsonObject.getString("status");
            if(status.equals("true")){
                account2 = new Account2();
                account2.setAccountNumber(jsonObject.getString("account_number"));
                account2.setPassword(jsonObject.getString("password"));
                return account2;
            }else{
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return new IOException();
        }
    }

}
