package com.garrisonthomas.currencyexchange;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class CurrencySelectionSpinner extends Activity {

    public Spinner fromSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromSpinner = (Spinner) findViewById(R.id.spinner_from);
        List<String> list = new ArrayList<>();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item,list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        fromSpinner.setAdapter(dataAdapter);

        // Spinner item selection Listener
        addListenerOnSpinnerItemSelection();

        // Button click Listener
        addListenerOnButton();

    }

    // Add spinner data

    public void addListenerOnSpinnerItemSelection(){

        fromSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    //get the selected dropdown list value

    public void addListenerOnButton() {

        fromSpinner = (Spinner) findViewById(R.id.spinner_from);

        Button btnConvert = (Button) findViewById(R.id.btn_convert);

        btnConvert.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(CurrencySelectionSpinner.this,
                        "On Button Click : " +
                                "\n" + String.valueOf(fromSpinner.getSelectedItem()),
                        Toast.LENGTH_LONG).show();
            }

        });

    }

}