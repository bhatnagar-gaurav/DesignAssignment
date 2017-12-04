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

import static com.medtronic.assignment.designtask.utils.Constants.fragment_identifiers.DIVISION;


public class MultiplicationFragment extends Fragment {

    /**
     *
     *  The Second Screen to be shown to the End user
     *  showing double the value entered earlier.
     *  Developer Name : Gaurav Bhatnagar
     *  Created Date : Dec 03,2017
     */

    // UI Controls
    @Bind(R.id.edit_number_divide)
    EditText editTextDivide;
    @Bind(R.id.buttonDivide)
    Button buttonDivide;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_WHICH_GOT_MULTIPLIED = "ARG_MULTIPLICATION_ANSWER";

    // Local storage parameter
    private SharedPreferences appPreferences;

    public static MultiplicationFragment getInstance(Double product){
        MultiplicationFragment multiplicationFragment = new MultiplicationFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_WHICH_GOT_MULTIPLIED,product);
        multiplicationFragment.setArguments(args);
        return multiplicationFragment;
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
        View view = inflater.inflate(R.layout.fragment_multiplication,
                container, false);
        ButterKnife.bind(this, view);
        appPreferences = ((ContainerActivity) getActivity()).getAppPreferences();
        ((ContainerActivity) getActivity()).hideSoftKeyboard();
        if (getArguments() != null){
            if (getArguments().containsKey(ARG_WHICH_GOT_MULTIPLIED)){
                Double product = getArguments().getDouble(ARG_WHICH_GOT_MULTIPLIED);
                editTextDivide.setText(product.toString());
            }
        }
        return view;
    }



    @OnClick(R.id.buttonDivide)
    public void divideNumber(){
        if (areFieldsValid()) {
            divide();
            ((ContainerActivity) getActivity())
                    .changeFragment(DIVISION);
        }
    }

    // Validation Function for the input fields
    private boolean areFieldsValid() {

        boolean allValid = true;

        if (editTextDivide.getText().toString().equalsIgnoreCase(Constants.EMPTY_STRING)) {
            editTextDivide.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.red));
            editTextDivide.setError(getString(R.string.empty_field_error));
            editTextDivide.setHint(getString(R.string.empty_field_error));
            allValid = false;

        }

        return allValid;
    }



    // Utility function used for passing the quotient i.e. half value to the next screen.
    private void divide(){
        Double enteredNumber = Double.parseDouble(editTextDivide.getText().toString());
        Double quotient;
        //quotient to be shown on next screen
        quotient = enteredNumber / 2;
        if (appPreferences == null)
            appPreferences = ((ContainerActivity) getActivity()).getAppPreferences();
        SharedPreferences.Editor editor = appPreferences.edit();
        editor.putString(Constants.DIVISION_ANSWER,quotient.toString());
        editor.apply();
    }



}
