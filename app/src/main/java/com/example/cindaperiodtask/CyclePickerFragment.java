package com.example.cindaperiodtask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CyclePickerFragment extends DialogFragment {
    OnNumberSelected mListener;
    Context mContext;
    int cycleDuration = 0;

    CyclePickerFragment(OnNumberSelected i){
        mListener = i;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // get context
        mContext = getActivity().getApplicationContext();
        // make dialog object
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // get the layout inflater
        LayoutInflater li = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflate our custom layout for the dialog to a View
        View view = li.inflate(R.layout.number_picker, null);
        // inform the dialog it has a custom View
        builder.setView(view);
        // and if you need to call some method of the class
        NumberPicker np = (NumberPicker) view
                .findViewById(R.id.number);
        Button btn = (Button) view.findViewById(R.id.btn);
        np.setMaxValue(50);
        np.setMinValue(1);

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                cycleDuration = newVal;

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onNumberSelected(cycleDuration);
                dismiss();
            }
        });

        return builder.create();
    }

   public void setListener(OnNumberSelected i){
        this.mListener = i;
    }

}
