package b.b.b.bankaccount.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

import b.b.b.bankaccount.Model.Customer;
import b.b.b.bankaccount.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key) , MODE_PRIVATE);
                String jsonCustomer = sharedPreferences.getString(getString(R.string.json_customer_key) , null);
                if(jsonCustomer!=null){
                    Intent intent = new Intent(SplashActivity.this , AccountActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SplashActivity.this , LoginActivity.class);
                    startActivity(intent);
                }
                finish();

            }
        }, 4500);
    }
}
