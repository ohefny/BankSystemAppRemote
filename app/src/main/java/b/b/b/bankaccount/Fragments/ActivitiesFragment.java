package b.b.b.bankaccount.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import b.b.b.bankaccount.Activities.AccountActivity;
import b.b.b.bankaccount.Adapters.ActivitiesAdapter;
import b.b.b.bankaccount.Model.ActivityModel;
import b.b.b.bankaccount.R;
import b.b.b.bankaccount.Utils.NetworkUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivitiesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Object> {

    FrameLayout loadingIndicator;
    RelativeLayout noDataIndicator;
    Button reloadButton;
    TextView zeroActivityIndicator;
    RelativeLayout activitiesFullLayout;

    RecyclerView activitiesRecyclerView;
    ActivitiesAdapter adapter;
    RecyclerView.LayoutManager layoutManager ;


    Button edit1;
    Button edit2;
    Button hideFilter;
    Button updateAdapter;

    Button filter;
    CardView filterLayout;
    private final int LOADER_ID = 371;

    public static String selectedFromDate = null;
    public static String selectedToDate = null;

    public static TextView fromTextView;
    public static TextView toTextView;


    public ActivitiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activities, container, false);
        loadingIndicator = view.findViewById(R.id.progress_bar_frame_layout);
        noDataIndicator = view.findViewById(R.id.activities_load_failed_indicator);
        reloadButton = view.findViewById(R.id.reload_activities_button);
        layoutManager = new LinearLayoutManager(getContext());
        activitiesRecyclerView = view.findViewById(R.id.activities_recycler_view);
        zeroActivityIndicator = view.findViewById(R.id.zero_Activity_indicator);
        activitiesFullLayout = view.findViewById(R.id.activities_full_layout);

        edit1 = view.findViewById(R.id.edit1);
        edit2 = view.findViewById(R.id.edit2);
        hideFilter = view.findViewById(R.id.hide_filter);
        updateAdapter = view.findViewById(R.id.update_adapter);
        filterLayout = view.findViewById(R.id.filter_layout);
        filter = view.findViewById(R.id.filter);
        fromTextView = view.findViewById(R.id.selected_from_time);
        toTextView = view.findViewById(R.id.selected_to_time);


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterLayout.setVisibility(View.VISIBLE);
            }
        });

        edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseDateDialogFragment chooseDateDialogFragment = new ChooseDateDialogFragment();
                chooseDateDialogFragment.setType("from");
                chooseDateDialogFragment.show(getChildFragmentManager() , "Date");
            }
        });
        edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseDateDialogFragment chooseDateDialogFragment = new ChooseDateDialogFragment();
                chooseDateDialogFragment.setType("to");
                chooseDateDialogFragment.show(getChildFragmentManager() , "Date");
            }
        });
        hideFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterLayout.setVisibility(View.GONE);
            }
        });
        updateAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter!=null){
                    if(selectedFromDate== null || selectedToDate == null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Please enter all date fields first");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return;
                    }
                    adapter.filter(selectedFromDate , selectedToDate);
                    if(adapter.getItemCount()==0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("No activities found in this time range");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return;
                    }
                }
            }
        });

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingIndicator.setVisibility(View.VISIBLE);
                getActivity().getSupportLoaderManager().restartLoader(LOADER_ID , null , ActivitiesFragment.this);
            }
        });
        getActivity().getSupportLoaderManager().restartLoader(LOADER_ID,null , this);
        return view;



    }

    @Override
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Object>(getContext()){

            @Override
            protected void onStartLoading() {
                forceLoad();
            }

            @Override
            public Object loadInBackground() {
                Object res = NetworkUtils.getAccountActivity(((AccountActivity)getActivity()).getCustomer().getAccountNumber());
                return res;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        if(!isAdded()){
            return;
        }
        if(data !=null){
            if(data instanceof ArrayList){
                adapter = new ActivitiesAdapter((ArrayList<ActivityModel>)data);
                activitiesRecyclerView.setAdapter(adapter);
                activitiesRecyclerView.setLayoutManager(layoutManager);
                activitiesRecyclerView.setHasFixedSize(true);
                loadingIndicator.setVisibility(View.GONE);
                noDataIndicator.setVisibility(View.GONE);
                activitiesFullLayout.setVisibility(View.VISIBLE);
                zeroActivityIndicator.setVisibility(View.GONE);
            }else{
                loadingIndicator.setVisibility(View.GONE);
                noDataIndicator.setVisibility(View.VISIBLE);
                activitiesFullLayout.setVisibility(View.GONE);
                zeroActivityIndicator.setVisibility(View.GONE);
            }
        }else{
            loadingIndicator.setVisibility(View.GONE);
            noDataIndicator.setVisibility(View.GONE);
            activitiesFullLayout.setVisibility(View.GONE);
            zeroActivityIndicator.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }
}
