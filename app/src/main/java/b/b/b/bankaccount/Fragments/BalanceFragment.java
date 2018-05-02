package b.b.b.bankaccount.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import b.b.b.bankaccount.R;
import b.b.b.bankaccount.newapi.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceFragment extends Fragment{


    TextView balanceTextView;
    FrameLayout loadingIndicator;
    RelativeLayout noDataIndicator;
    RelativeLayout balance;
    private final int LOADER_ID = 624;

    Button reloadButton;

    public BalanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        balanceTextView = view.findViewById(R.id.balance_textView);
        loadingIndicator = view.findViewById(R.id.progress_bar_frame_layout);
        noDataIndicator = view.findViewById(R.id.balance_load_failed_indicator);
        reloadButton = view.findViewById(R.id.reload_balance_button);
        balance= view.findViewById(R.id.balance_layout);
        //getActivity().getSupportLoaderManager().restartLoader(LOADER_ID, null , this);
        return view;


    }

    @Override
    public void onResume() {
        super.onResume();
        balanceTextView.setText("$"+User.getAccount().getBalance()+"");
    }
}
