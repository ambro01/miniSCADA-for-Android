package com.example.application.miniSCADA;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.Element;

public class Popup extends Activity{

    String bitIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        getWindow().setLayout(Globals.dptoPx(Globals.popupWidth), Globals.dptoPx(Globals.popupHeight));

        RadioGroup radioButton = (RadioGroup) findViewById(R.id.radioGroup);

        bitIndex = (String) getIntent().getSerializableExtra("Bits");
        if(bitIndex.equals("yes")){
            radioButton.setVisibility(View.VISIBLE);
        } else if(bitIndex.equals("no")){
            radioButton.setVisibility(View.INVISIBLE);
        }
    }

    public void onClosePopup(View view){
        finish();
    }

    public void onConfirmPopup(View view){
        EditText dbNumber = (EditText) findViewById(R.id.dbNumber);
        EditText wordNumber = (EditText) findViewById(R.id.wordNumber);

        Intent returnIntent = new Intent();
        returnIntent.putExtra("dbNumber",dbNumber.getText().toString());
        returnIntent.putExtra("worldNumber",wordNumber.getText().toString());

        if(bitIndex.equals("yes")){
            RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
            int selectedIndex = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) findViewById(selectedIndex);

            returnIntent.putExtra("bitNumber",radioButton.getText().toString());
        } else {
            returnIntent.putExtra("bitNumber","-1");
        }

        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
