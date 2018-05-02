package b.b.b.bankaccount.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import b.b.b.bankaccount.Fragments.ActivitiesFragment;
import b.b.b.bankaccount.Fragments.BalanceFragment;
import b.b.b.bankaccount.Model.Customer;
import b.b.b.bankaccount.R;
import b.b.b.bankaccount.newapi.PreferenceUtil;
import b.b.b.bankaccount.newapi.User;
import b.b.b.bankaccount.newapi.api.AccountAPI;
import b.b.b.bankaccount.newapi.models.Activity;
import b.b.b.bankaccount.newapi.network.NetworkCall;
import b.b.b.bankaccount.newapi.network.RetrofitCallback;
import b.b.b.bankaccount.newapi.views.ActivitiesActivity;
import b.b.b.bankaccount.newapi.views.TransactionActivity;
import b.b.b.bankaccount.newapi.views.TransferActivity;

public class AccountActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Customer customer;
    String type;
    private final String TYPE_EXTRA = "b.b.b.bankaccount.Type_EXTRA";
    TextView customerName;
    TextView customerMail;
    ProgressDialog dialog;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String jsonCustomer = sharedPref.getString(getString(R.string.json_customer_key), null);
        if (jsonCustomer != null) {
            customer = new Gson().fromJson(jsonCustomer, Customer.class);
        }

        if (savedInstanceState != null) {
            type = savedInstanceState.getString(TYPE_EXTRA);
            return;
        } else
            type = "Balance";

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        View headerLayout = navigationView.getHeaderView(0);
        customerName = headerLayout.findViewById(R.id.customer_name_textView);
        customerMail = headerLayout.findViewById(R.id.customer_mail_textView);
        customerName.setText(User.getAccount().getUserName());
        customerMail.setText(User.getAccount().getEmail());


        Fragment fragment = null;
        if (type.equals("Balance")) {
            fragment = new BalanceFragment();
        } else if (type.equals("Activities")) {
            fragment = new ActivitiesFragment();
        }

        if (fragment != null) {
            fragmentTransaction.replace(R.id.fragment_place_holder, fragment);
            fragmentTransaction.commit();
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(type);
        }
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ...");
        dialog.setCancelable(false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        if (id == R.id.nav_transfer) {
            openTransfer(null);
        } else if (id == R.id.nav_deposit) {
            openDeposit(null);
        } else if (id == R.id.nav_withdraw) {
            openWithdraw(null);
        } else if (id == R.id.nav_activities) {
            openMyActivities(null);
        } else if (id == R.id.nav_logout) {
            PreferenceUtil preferenceUtil = new PreferenceUtil(this);
            preferenceUtil.editValue(PreferenceUtil.DefaultKeys.PREFS_ACCOUNT_NUM, 0);
            preferenceUtil.editValue(PreferenceUtil.DefaultKeys.PREF_USER_PASS, 0);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

/*        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place_holder, fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TYPE_EXTRA, type);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void transfer(View view) {
        openTransfer(view);
    }

    public void openDeposit(View view) {
        Intent intent = new Intent(this, TransactionActivity.class);
        intent.putExtra(TransactionActivity.TRANSACTION_TYPE_KEY, TransactionActivity.DEPOSIT_TYPE);
        startActivity(intent);
    }

    public void signoutClicked(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void openMyActivities(View view) {
        getActivities();


    }

    public void getActivities() {
        dialog.show();
        NetworkCall.getRetrofit().create(AccountAPI.class).getActivities(User.getAccount().get_id()).enqueue(new RetrofitCallback<ArrayList<Activity>>(null, null, this) {
            @Override
            public void showError(String errorMsg) {
                Toast.makeText(AccountActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }

            @Override
            public void showItems(ArrayList<Activity> data) {
                //open activity and pass
                dialog.dismiss();
                Intent intent = new Intent(AccountActivity.this, ActivitiesActivity.class);
                intent.putExtra(ActivitiesActivity.LIST_KEY, new Gson().toJson(data));
                startActivity(intent);
                Toast.makeText(AccountActivity.this, "Your Act" + data.size(), Toast.LENGTH_LONG).show();

            }
        });
    }

    public void openWithdraw(View view) {
        Intent intent = new Intent(this, TransactionActivity.class);
        intent.putExtra(TransactionActivity.TRANSACTION_TYPE_KEY, TransactionActivity.WITHDRAW_TYPE);
        startActivity(intent);
    }

    public void openTransfer(View view) {
        startActivity(new Intent(this, TransferActivity.class));

    }
}
