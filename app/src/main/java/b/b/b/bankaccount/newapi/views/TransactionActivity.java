package b.b.b.bankaccount.newapi.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import b.b.b.bankaccount.R;
import b.b.b.bankaccount.newapi.User;
import b.b.b.bankaccount.newapi.api.AccountAPI;
import b.b.b.bankaccount.newapi.models.Account;
import b.b.b.bankaccount.newapi.models.Transaction;
import b.b.b.bankaccount.newapi.network.NetworkCall;
import b.b.b.bankaccount.newapi.network.RetrofitCallback;

public class TransactionActivity extends AppCompatActivity {

    public static int DEPOSIT_TYPE = 1;
    public static int WITHDRAW_TYPE = 2;
    public static String TRANSACTION_TYPE_KEY = "TRANSACTION TYPE";
    private int type;
    EditText txtAmount;
    ProgressBar progressBar;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        txtAmount=(EditText)findViewById(R.id.txtValue);
        progressBar=(ProgressBar)findViewById(R.id.transaction_progress);
        btn=(Button)findViewById(R.id.btnTransaction);
        initType();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initType();
    }

    private void initType() {
        type = getIntent().getExtras().getInt(TRANSACTION_TYPE_KEY, 1);
        if (type == DEPOSIT_TYPE) {
            setTitle("Deposit");
            btn.setText("Deposit");
        }
        else{
            btn.setText("Withdraw");
            setTitle("Withdraw");
        }
    }

    public void makeTransaction(View view) {
        if(!validateFields())
            return;
        if(type==DEPOSIT_TYPE)
            makeDeposite((Button)view);
        else
            makeWithdraw((Button)view);

    }

    private void makeWithdraw(Button view) {
        NetworkCall.getRetrofit().create(AccountAPI.class).makeWithdraw(new Transaction(User.getAccount().get_id(),Integer.parseInt(txtAmount.getText().toString()))).enqueue(new RetrofitCallback<Account>(progressBar,view,this) {
            @Override
            public void showError(String errorMsg) {
                Toast.makeText(TransactionActivity.this,errorMsg,Toast.LENGTH_LONG).show();
            }

            @Override
            public void showItems(Account data) {
                Toast.makeText(TransactionActivity.this,"Successfull Withdraw .. Your current balance is "+data.getBalance(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void makeDeposite(Button view) {
        NetworkCall.getRetrofit().create(AccountAPI.class).makeDeposit(new Transaction(User.getAccount().get_id(),Integer.parseInt(txtAmount.getText().toString()))).enqueue(new RetrofitCallback<Account>(progressBar,view,this) {
            @Override
            public void showError(String errorMsg) {
                Toast.makeText(TransactionActivity.this,errorMsg,Toast.LENGTH_LONG).show();
            }

            @Override
            public void showItems(Account data) {
                Toast.makeText(TransactionActivity.this,"Successfull Deposit .. Your current balance is "+data.getBalance(),Toast.LENGTH_LONG).show();
            }
        });

    }


    public boolean validateFields(){
        if(TextUtils.isEmpty(txtAmount.getText())){
            txtAmount.setError("This field Can't be empty");
            txtAmount.requestFocus();
            return false;
        }
        return true;
    }
}
