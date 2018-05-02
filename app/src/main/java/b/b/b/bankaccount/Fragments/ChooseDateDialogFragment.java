package b.b.b.bankaccount.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Basma on 4/27/2018.
 */

public class ChooseDateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    String type;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this , year , month , day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month++;
        String monthString = (month<10)? "0"+String.valueOf(month): String.valueOf(month);
        String dayOfMonthString = (dayOfMonth<10)? "0"+String.valueOf(dayOfMonth): String.valueOf(dayOfMonth);
        String selectedDate = String.valueOf(year)+"-"
                +  monthString + "-"
                +dayOfMonthString + " 00:00:00";
        if(type.equals("from")){
            ActivitiesFragment.selectedFromDate =selectedDate;
            ActivitiesFragment.fromTextView.setText(selectedDate);
        }else{
            ActivitiesFragment.selectedToDate = selectedDate;
            ActivitiesFragment.toTextView.setText(selectedDate);
        }
    }

    public void setType(String t){
        type = t;
    }

}
