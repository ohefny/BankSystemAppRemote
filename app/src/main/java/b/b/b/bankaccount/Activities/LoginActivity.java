package b.b.b.bankaccount.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import b.b.b.bankaccount.R;
import b.b.b.bankaccount.newapi.PreferenceUtil;
import b.b.b.bankaccount.newapi.api.UserAPI;
import b.b.b.bankaccount.newapi.models.Account;
import b.b.b.bankaccount.newapi.models.Login;
import b.b.b.bankaccount.newapi.network.NetworkCall;
import b.b.b.bankaccount.newapi.network.RetrofitCallback;

public class LoginActivity extends AppCompatActivity {

    EditText accountNumberEditText;
    EditText passwordEditText;
    String accountNumber = null;
    String password = null;
    ProgressDialog dialog;
    private final int LOADER_ID = 126;
    LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accountNumberEditText = findViewById(R.id.account_number_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.login_message));
        dialog.setCancelable(false);

    }

    public void login(View view) {
        if(accountNumberEditText.getText().toString().equals("")){
            showAlertDialog(getString(R.string.enter_account_number));
            return;
        }
        if(passwordEditText.getText().toString().equals("")){
            showAlertDialog(getString(R.string.enter_password));
            return;
        }
        accountNumber = accountNumberEditText.getText().toString();
        password = passwordEditText.getText().toString();
        startLogin(Integer.parseInt(accountNumber),password,view);

        if(dialog!=null){
            dialog.show();
        }
    }

    private void startLogin(int accountNumber, String password,View view) {
        NetworkCall.getRetrofit().create(UserAPI.class).login(new Login(accountNumber,password)).enqueue(new RetrofitCallback<Account>(null,(Button)view,this) {
            @Override
            public void showError(String errorMsg) {
                dialog.dismiss();
                Toast.makeText(LoginActivity.this,errorMsg,Toast.LENGTH_LONG).show();
            }

            @Override
            public void showItems(Account data) {
                dialog.dismiss();
                Toast.makeText(LoginActivity.this,"Welcome "+data.getUserName(),Toast.LENGTH_LONG).show();
                PreferenceUtil preferenceUtil=new PreferenceUtil(LoginActivity.this);
                preferenceUtil.editValue(PreferenceUtil.DefaultKeys.PREFS_ACCOUNT_NUM,data.getAccountNumber());
                preferenceUtil.editValue(PreferenceUtil.DefaultKeys.PREF_USER_PASS,data.getPassword());
                LoginActivity.this.startActivity(new Intent(LoginActivity.this,AccountActivity.class));
                LoginActivity.this.finish();
            }
        });


    }


    private void showAlertDialog(String str){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(str);
        builder.setTitle(getString(R.string.app_name));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void createNewAccount(View view) {
        Intent intent = new Intent(this , CreateAccountActivity.class);
        startActivity(intent);
    }

}
