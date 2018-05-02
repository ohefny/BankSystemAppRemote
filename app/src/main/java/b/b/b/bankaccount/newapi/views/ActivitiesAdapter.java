package b.b.b.bankaccount.newapi.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import b.b.b.bankaccount.R;
import b.b.b.bankaccount.newapi.models.Activity;

/**
 * Created by ohefny on 5/1/18.
 */

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ActivitiesHolder> {

    private final OnActivityClickedListener listener;
    private ArrayList<Activity> list=new ArrayList<>();

    public ActivitiesAdapter(ArrayList<Activity>list,OnActivityClickedListener listener) {
        this.list=list;
        this.listener=listener;
    }

    @Override
    public ActivitiesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item,parent,false);
        return new ActivitiesHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivitiesHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ActivitiesHolder extends RecyclerView.ViewHolder{
        TextView createdAt;
        TextView activityType;
        TextView amount;
        TextView balanceAfter;

        View rootView;
        public ActivitiesHolder(View itemView) {
            super(itemView);
            createdAt=itemView.findViewById(R.id.activity_date_textView);
            activityType=itemView.findViewById(R.id.activity_type_textView);
            amount=itemView.findViewById(R.id.activity_value_textView);
            rootView=itemView;
        }

        public void bind(final Activity activity) {
            createdAt.setText(activity.getCreatedAt());
            activityType.setText(activity.getActivityTypeString());
            amount.setText("$ "+Math.abs(activity.getBalanceAfter()-activity.getBalanceBefore()));
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
         //           listener.onActivityClicked(activity);
                }
            });
        }
    }
    public  interface OnActivityClickedListener{
        void onActivityClicked(Activity activity);
    }
}
