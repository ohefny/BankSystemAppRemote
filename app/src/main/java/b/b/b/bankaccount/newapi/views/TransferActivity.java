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
import b.b.b.bankaccount.newapi.models.Transfer;
import b.b.b.bankaccount.newapi.network.NetworkCall;
import b.b.b.bankaccount.newapi.network.RetrofitCallback;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TransferActivity extends AppCompatActivity {

    @BindView(R.id.txtTo)
    EditText txtTo;
    @BindView(R.id.txtValue)
    EditText txtValue;
    @BindView(R.id.transfer_progress)
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        ButterKnife.bind(this);
        setTitle("Transfer");
    }

    public void makeTransfer(View view) {
        if(!validateFields())
            return;
        NetworkCall.getRetrofit().create(AccountAPI.class).makeTransfer(new Transfer(User.getAccount().get_id(),Integer.parseInt(txtTo.getText().toString()),Integer.parseInt(txtValue.getText().toString()))).enqueue(new RetrofitCallback<Account>(progressBar,(Button)view,this) {

            @Override
            public void showError(String errorMsg) {
                Toast.makeText(TransferActivity.this,errorMsg,Toast.LENGTH_LONG).show();

            }

            @Override
            public void showItems(Account data) {
                Toast.makeText(TransferActivity.this,"Successfull Transfer .. Your current balance is "+data.getBalance(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public boolean validateFields(){
        if(TextUtils.isEmpty(txtValue.getText())){
            txtValue.setError("This field Can't be empty");
            txtValue.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(txtTo.getText())){
            txtTo.setError("This field Can't be empty");
            txtTo.requestFocus();
            return false;
        }
        else if(Integer.parseInt(txtTo.getText().toString())==User.getAccount().getAccountNumber()){
            txtTo.setError("You Can't Transfer Money to Yourself");
            txtTo.requestFocus();
            return false;
        }
        return true;
    }
}
