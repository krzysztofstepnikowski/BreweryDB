package com.example.krzysiek.brewerydb.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.krzysiek.brewerydb.HomeActivity;
import com.example.krzysiek.brewerydb.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

/**
 * Created by Krzysztof Stępnikowski on 2016-02-01.
 */
public class Dialogs extends Dialog implements Validator.ValidationListener {

    private Context context;
    private Activity activity;
    @NotEmpty(message = "Wypełnij puste pole")
    private EditText searchBeerEditText;
    private Button searchButton;
    private Button cancelButton;
    private Dialog dialog;

    private Validator validator = new Validator(this);

    public Dialogs(Activity activity) {
        super(activity);
        this.context = activity;
        this.dialog = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_box);
        getWindow().setTitle("Wyszukaj piwo");
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        searchBeerEditText = (EditText) findViewById(R.id.searchBeerEditText);
        searchButton = (Button) findViewById(R.id.searchButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {

                String searchBeers = searchBeerEditText.getText().toString();
                HomeActivity.searchBeer(searchBeers);
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {

                for (ValidationError error : errors) {
                    View view = error.getView();
                    String message = error.getCollatedErrorMessage(context);

                    // Display error messages ;)
                    if (view instanceof EditText) {
                        ((EditText) view).setError(message);
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });


    }


    @Override
    public void onValidationSucceeded() {

        String searchBeers = searchBeerEditText.getText().toString();
        HomeActivity.searchBeer(searchBeers);
    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(context);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        }

    }


}
