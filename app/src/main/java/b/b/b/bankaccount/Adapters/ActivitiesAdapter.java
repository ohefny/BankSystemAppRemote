package b.b.b.bankaccount.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import b.b.b.bankaccount.Model.ActivityModel;
import b.b.b.bankaccount.R;

/**
 * Created by Basma on 4/25/2018.
 */

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ActivityViewHolder>{

    ArrayList<ActivityModel> activities ;
    ArrayList<ActivityModel> itemsCopy;
    Context context;

    public ActivitiesAdapter(ArrayList<ActivityModel> activities) {
        this.activities = activities;
        itemsCopy = new ArrayList<>();
        itemsCopy.addAll(this.activities);
    }


    @Override
    public ActivitiesAdapter.ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int id = R.layout.activity_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(id , parent ,false);
        ActivityViewHolder viewHolder = new ActivityViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ActivitiesAdapter.ActivityViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (activities!=null)
            return activities.size();
        else
            return 0;
    }

    public void filter(String from , String to) {
        activities.clear();
        for(int i=0 ; i< itemsCopy.size(); i++){
            if(itemsCopy.get(i).getDate().compareTo(from)>=0 && itemsCopy.get(i).getDate().compareTo(to)<=0){
                activities.add(itemsCopy.get(i));
            }
        }
        notifyDataSetChanged();
    }

    public class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView typeTextView;
        TextView valueTextView;
        TextView dateTextView;
        public ActivityViewHolder(View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.activity_type_textView);
            valueTextView = itemView.findViewById(R.id.activity_value_textView);
            dateTextView = itemView.findViewById(R.id.activity_date_textView);
        }
        void bind(int pos){
            ActivityModel currentActivity = activities.get(pos);
            dateTextView.setText(currentActivity.getDate());
            switch (currentActivity.getType()){
                case "Transfer":
                    if(!currentActivity.getFromAccount().equals("null")){
                        typeTextView.setText("Transfer from ".concat(currentActivity.getFromAccount()));
                        valueTextView.setText("$".concat(String.valueOf(currentActivity.getValue())));
                    }else{
                        typeTextView.setText("Transfer to ".concat(currentActivity.getToAccount()));
                        valueTextView.setText("-$".concat(String.valueOf(currentActivity.getValue())));
                    }
                    break;
                case "Deposit":
                    typeTextView.setText("Deposit");
                    valueTextView.setText("$".concat(String.valueOf(currentActivity.getValue())));
                    break;
                case "Withdraw":
                    typeTextView.setText("Withdraw");
                    valueTextView.setText("-$".concat(String.valueOf(currentActivity.getValue())));
                    break;
            }
        }
    }
}
