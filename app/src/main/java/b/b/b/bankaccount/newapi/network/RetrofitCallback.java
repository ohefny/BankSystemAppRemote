package b.b.b.bankaccount.newapi.network;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.IOException;

import b.b.b.bankaccount.Activities.LoginActivity;
import b.b.b.bankaccount.newapi.User;
import b.b.b.bankaccount.newapi.models.Account;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ohefny on 5/1/18.
 */

public abstract class RetrofitCallback<T> implements Callback<T> {


    private  ProgressBar progressBar;
    private  Button actionButton;
    private final AppCompatActivity activity;
    public RetrofitCallback(ProgressBar progressBar, Button actionButton,AppCompatActivity activity) {
        this.progressBar = progressBar;
        this.actionButton = actionButton;
        this.activity=activity;
        if (actionButton != null)
            actionButton.setEnabled(false);
        if(progressBar!=null)
            progressBar.setVisibility(View.VISIBLE);

    }
    public RetrofitCallback(AppCompatActivity activity) {
        this.activity=activity;

    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        if (actionButton != null)
            actionButton.setEnabled(true);
        if (response.isSuccessful()){
            T data=response.body();
            showItems(data);
            if(data instanceof Account)
                User.setAccount((Account)data);
        }
        else {
            try {
                String err = response.errorBody().string();
                Log.d("Failed", err);
                showError(err);
                if(response.code()==401){
                    Intent intent=new Intent(activity, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                    activity.finish();
                }
            } catch (IOException e) {
                e.printStackTrace();
                showError("Please Check Your Network Connection");
            }
        }

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        if (actionButton != null)
            actionButton.setEnabled(true);
        showError("Please Check Your Network Connection");

    }

    public abstract void showError(String errorMsg);

    public abstract void showItems(T data);
}
