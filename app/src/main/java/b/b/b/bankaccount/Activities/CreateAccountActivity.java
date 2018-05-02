package b.b.b.bankaccount.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import b.b.b.bankaccount.R;
import b.b.b.bankaccount.newapi.PreferenceUtil;
import b.b.b.bankaccount.newapi.api.UserAPI;
import b.b.b.bankaccount.newapi.models.Account;
import b.b.b.bankaccount.newapi.models.Register;
import b.b.b.bankaccount.newapi.network.NetworkCall;
import b.b.b.bankaccount.newapi.network.RetrofitCallback;

public class CreateAccountActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText mailEditText;
    EditText phoneEditText;
    EditText passwordEditText;
    ProgressDialog dialog;
    private EditText accEditText;


    private final int LOADER_ID = 429;
    LoaderManager loaderManager;

    String name , mail , phone, password;
    private int accNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        accEditText=findViewById(R.id.acc_num_edit_text_in_create);
        nameEditText = findViewById(R.id.name_edit_text_in_create);
        mailEditText = findViewById(R.id.mail_edit_text_in_create);
        phoneEditText= findViewById(R.id.phone_edit_text_in_create);
        passwordEditText = findViewById(R.id.password_edit_text_in_create);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Signing up");
        dialog.setCancelable(false);
        Toolbar toolbar = findViewById(R.id.toolbar_in_register);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void Register(View view) {
        if(nameEditText.getText().toString().equals("")){
            showAlertDialog("Please fill the name field. ");
            return;
        }
        if(mailEditText.getText().toString().equals("")){
            showAlertDialog("Please fill the mail field. ");
            return;
        }
        if(phoneEditText.getText().toString().equals("")){
            showAlertDialog("Please fill the phone number field. ");
            return;
        }
        if(passwordEditText.getText().toString().equals("")){
            showAlertDialog("Please fill the password field . ");
            return;
        }
        accNum=Integer.parseInt(accEditText.getText().toString());
        name = nameEditText.getText().toString();
        mail = mailEditText.getText().toString();
        phone = phoneEditText.getText().toString();
        password = passwordEditText.getText().toString();

        /*loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(LOADER_ID ,null ,this);

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);*/
        startSignUp(accNum,name,mail,phone,password);

        if(dialog!=null){
            dialog.show();
        }
    }

    private void startSignUp(int accNum, String name, String mail, String phone, String password) {
        NetworkCall.getRetrofit().create(UserAPI.class).register(
                new Register(accNum, password,name,mail,phone)).enqueue(new RetrofitCallback<Account>(this) {
            @Override
            public void showError(String errorMsg) {
                dialog.dismiss();
                showAlertDialog(errorMsg);
            }

            @Override
            public void showItems(Account data) {
                dialog.dismiss();
                Toast.makeText(CreateAccountActivity.this,"Welcome "+data.getUserName(),Toast.LENGTH_LONG).show();
                PreferenceUtil preferenceUtil=new PreferenceUtil(CreateAccountActivity.this);
                preferenceUtil.editValue(PreferenceUtil.DefaultKeys.PREFS_ACCOUNT_NUM,data.getAccountNumber());
                preferenceUtil.editValue(PreferenceUtil.DefaultKeys.PREF_USER_PASS,data.getPassword());

                CreateAccountActivity.this.startActivity(new Intent(CreateAccountActivity.this,AccountActivity.class));
                CreateAccountActivity.this.finish();
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


    private void showCustomAlertDialog(String accountNumber , String password){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.registration_succeeded_dialog, null);
        dialogBuilder.setView(dialogView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                Intent intent = new Intent(CreateAccountActivity.this  , LoginActivity.class);
                startActivity(intent);
                CreateAccountActivity.this.finish();
            }
        });
        TextView accountNumberTextView = dialogView.findViewById(R.id.result_account_number);
        TextView passwordTextView = dialogView.findViewById(R.id.result_password);
        accountNumberTextView.setText(accountNumber);
        passwordTextView.setText(password);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Registration succeeded");
        alertDialog.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id== android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
