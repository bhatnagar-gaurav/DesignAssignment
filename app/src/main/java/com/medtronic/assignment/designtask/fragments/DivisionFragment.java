package com.medtronic.assignment.designtask.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.medtronic.assignment.designtask.ContainerActivity;
import com.medtronic.assignment.designtask.R;
import com.medtronic.assignment.designtask.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.medtronic.assignment.designtask.utils.Constants.fragment_identifiers.MULTIPLICATION;
/**
 *
 *  The First Screen to be shown to the End user to enter the number
 *  Developer Name : Gaurav Bhatnagar
    Created Date : Dec 03,2017
 */

public class DivisionFragment extends Fragment {

    // UI Controls
    @Bind(R.id.edit_number_multiply)
    EditText editTextMultiply;
    @Bind(R.id.buttonMultiply)
    Button buttonMultiply;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_WHICH_GOT_DIVIDED = "ARG_DIVISION_ANSWER";

    // Local storage parameter
    private SharedPreferences appPreferences;


    public static DivisionFragment getInstance() {
        return new DivisionFragment();
    }

    public static DivisionFragment getInstance(Double divisionAnswer){
        DivisionFragment divisionFragment = new DivisionFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_WHICH_GOT_DIVIDED,divisionAnswer);
        divisionFragment.setArguments(args);
        return divisionFragment;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }


    // ---------------------------------------------------------------------------------------------
    // Fragment lifecycle
    // ---------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_division,
                container, false);
        ButterKnife.bind(this, view);
        appPreferences = ((ContainerActivity) getActivity()).getAppPreferences();
        ((ContainerActivity) getActivity()).hideSoftKeyboard();
        if (getArguments() != null){
            if (getArguments().containsKey(ARG_WHICH_GOT_DIVIDED)){
                Double divisionAnswer = getArguments().getDouble(ARG_WHICH_GOT_DIVIDED);
                editTextMultiply.setText(divisionAnswer.toString());
            }
        }

        return view;
    }


    @OnClick(R.id.buttonMultiply)
    public void multiplyNumber(){
        if (areFieldsValid()) {
            multiply();
            ((ContainerActivity) getActivity())
                    .changeFragment(MULTIPLICATION);
        }
    }

    // Validation Function for the input fields
    private boolean areFieldsValid() {

        boolean allValid = true;

        if (editTextMultiply.getText().toString().equalsIgnoreCase(Constants.EMPTY_STRING)) {
            editTextMultiply.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.red));
            editTextMultiply.setHint(R.string.empty_field_error);
            editTextMultiply.setError(getString(R.string.empty_field_error));
            allValid = false;

        }

        return allValid;
    }

    // Utility function used for passing the product to the next screen.
    private void multiply(){
        Double enteredNumber = Double.parseDouble(editTextMultiply.getText().toString());
        Double product;
        //product to be shown on next screen
        product = enteredNumber * 2;
        if (appPreferences == null)
            appPreferences = ((ContainerActivity) getActivity()).getAppPreferences();
        SharedPreferences.Editor editor = appPreferences.edit();
        editor.putString(Constants.MULTIPLICATION_ANSWER,product.toString());
        editor.apply();
    }
}
