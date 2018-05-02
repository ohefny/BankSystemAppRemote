package b.b.b.bankaccount.newapi.views;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import b.b.b.bankaccount.R;
import b.b.b.bankaccount.newapi.models.Activity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivitiesActivity extends AppCompatActivity implements ActivitiesAdapter.OnActivityClickedListener {

    public static String LIST_KEY="LIST_KEY";
    @BindView(R.id.activitiesList)
    RecyclerView activitiesList;
    ArrayList<Activity>activities=new ArrayList<>();
    ActivitiesAdapter activitiesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        setTitle("My Activities");
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        activities=new Gson().fromJson(getIntent().getExtras().getString(LIST_KEY),new TypeToken<ArrayList<Activity>>(){}.getType());
        Log.d("activities",getIntent().getExtras().getString(LIST_KEY));
        activitiesAdapter=new ActivitiesAdapter(activities,this);
        activitiesList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        activitiesList.setAdapter(activitiesAdapter);
        activitiesList.addItemDecoration(new VerticalSpaceItemDecoration(15));
    }


    @Override
    public void onActivityClicked(Activity activity) {
        Toast.makeText(this,activity.getActivityTypeString(),Toast.LENGTH_LONG).show();
    }
    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int verticalSpaceHeight;

        public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;
        }
    }
}
