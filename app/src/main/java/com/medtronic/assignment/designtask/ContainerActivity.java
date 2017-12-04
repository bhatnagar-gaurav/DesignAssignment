package com.medtronic.assignment.designtask;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.medtronic.assignment.designtask.fragments.DivisionFragment;
import com.medtronic.assignment.designtask.fragments.MultiplicationFragment;
import com.medtronic.assignment.designtask.utils.Constants;
import com.medtronic.assignment.designtask.utils.Utilityfunctions;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.medtronic.assignment.designtask.utils.Constants.DIVISION_FRAGMENT_TAG;
import static com.medtronic.assignment.designtask.utils.Constants.MULTIPLICATION_FRAGMENT_TAG;
import static com.medtronic.assignment.designtask.utils.Constants.fragment_identifiers.DIVISION;
import static com.medtronic.assignment.designtask.utils.Constants.fragment_identifiers.MULTIPLICATION;

/**
 *
 *  The Main Container handling the interaction between the two screens.
 *  Developer Name : Gaurav Bhatnagar
 *  Created Date : Dec 03,2017
 */

public class ContainerActivity extends Activity {
    // UI Indicators specifically
    // used to show which screen is selected

    @Bind(R.id.indicator1)
    ImageView ivIndicator1;

    @Bind(R.id.indicator2)
    ImageView ivIndicator2;
    private int tabCounter = 0;

    // Title getting changed to show which screen is being shown to the end user.
    @Bind(R.id.tvTitle)
    TextView tvTitle;

    // ---------------------------------------------------------------------------------------------
    // Fields
    // ---------------------------------------------------------------------------------------------
    // Local storage parameter
    private SharedPreferences appPreferences;

    // ---------------------------------------------------------------------------------------------
    // Activity lifecycle methods
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);
        setAppPreferences(getSharedPreferences(Constants.PREF_DEFAULT,
                Activity.MODE_PRIVATE));
        ivIndicator1.setVisibility(View.VISIBLE);
        ivIndicator2.setVisibility(View.VISIBLE);
        changeFragment(DIVISION);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                changeIndicatorBackground();
                if (getFragmentManager().getBackStackEntryCount() == 0) {
                    Utilityfunctions.clearPreferences(getAppPreferences());
                    finish();
                }

            }
        });
        setActionBar();
    }

    // ---------------------------------------------------------------------------------------------
    // Public methods
    // ---------------------------------------------------------------------------------------------


    public void changeFragment(int fragmentNumber) {
        Fragment fragment;
        FragmentManager fragmentManager= getFragmentManager();
        switch (fragmentNumber) {
            case DIVISION:
                // Setting the Header for this screen
                tvTitle.setText(getString(R.string.container_title));
                if (!(getAppPreferences().getString(Constants.DIVISION_ANSWER,Constants.EMPTY_STRING)).equalsIgnoreCase(Constants.EMPTY_STRING))
                {
                    String quotientString = getAppPreferences().getString(Constants.DIVISION_ANSWER,Constants.EMPTY_STRING);
                    fragment = DivisionFragment.getInstance(Double.parseDouble(quotientString));
                    tabCounter = getFragmentManager().getBackStackEntryCount();
                }
                else{
                    fragment = DivisionFragment.getInstance();
                }
                fragmentManager.beginTransaction().addToBackStack(null)
                        .replace(R.id.flFragmentHolder, fragment,DIVISION_FRAGMENT_TAG).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

                break;
            case MULTIPLICATION:
                tvTitle.setText(getString(R.string.container_title_divide));
                if (!(getAppPreferences().getString(Constants.MULTIPLICATION_ANSWER,Constants.EMPTY_STRING)).equalsIgnoreCase(Constants.EMPTY_STRING))
                {
                    String productString = getAppPreferences().getString(Constants.MULTIPLICATION_ANSWER,Constants.EMPTY_STRING);
                    Double product  = Double.parseDouble(productString);
                    fragment = MultiplicationFragment.getInstance(product);
                    fragmentManager.beginTransaction().addToBackStack(null)
                            .replace(R.id.flFragmentHolder, fragment,MULTIPLICATION_FRAGMENT_TAG).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                    tabCounter = getFragmentManager().getBackStackEntryCount();

                }
                else{
                    Toast.makeText(this,getString(R.string.no_screen_error), Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                break;
        }

    }


    public void hideSoftKeyboard() {
        if (getWindow() != null) {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }



    public SharedPreferences getAppPreferences() {
        return appPreferences;
    }

    public void setAppPreferences(SharedPreferences appPreferences) {
        this.appPreferences = appPreferences;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_container, menu);
        return true;
    }

    protected void setActionBar() {
        if (getActionBar() != null){
            getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getActionBar().setCustomView(R.layout.custom_actionbar_orange);
        }
    }

    private void changeIndicatorBackground() {
        ChangeViewBackgroundColor(ivIndicator1, R.drawable.default_dot);
        ChangeViewBackgroundColor(ivIndicator2, R.drawable.default_dot);
        tabCounter = getFragmentManager().getBackStackEntryCount() - 1;
        int dotSelector = tabCounter % 2;

        switch (dotSelector) {
            case 0:
                ChangeViewBackgroundColor(ivIndicator1, R.drawable.selected_dot);
                break;
            case 1:
                ChangeViewBackgroundColor(ivIndicator2, R.drawable.selected_dot);
                break;
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit:{
                Utilityfunctions.clearPreferences(getAppPreferences());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAndRemoveTask();
                }
                else{
                    finish();
                }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void ChangeViewBackgroundColor(ImageView view, int resId) {
        view.setImageResource(resId);
    }

}
