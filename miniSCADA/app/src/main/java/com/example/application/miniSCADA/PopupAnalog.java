package com.example.application.miniSCADA;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.example.application.miniSCADA.PLC.DataBlockReal;


public class PopupAnalog extends Popup {
    public void onPopupShow(){
        setContentView(R.layout.popup_analog);

        DataBlockReal dataBlock = (DataBlockReal) getIntent().getSerializableExtra("dataBlock");

        EditText dbNumber = (EditText) findViewById(R.id.dbNumber_analog);
        EditText wordNumber = (EditText) findViewById(R.id.wordNumber_analog);

        dbNumber.setText(String.valueOf(dataBlock.getDbNumber()));
        wordNumber.setText(String.valueOf(dataBlock.getPosition()));
    }

    public void onClosePopup(View view){
        finish();
    }

    public void onConfirmPopup(View view){
        Intent returnIntent = new Intent();
        EditText dbNumber_analog = (EditText) findViewById(R.id.dbNumber_analog);
        EditText wordNumber_analog = (EditText) findViewById(R.id.wordNumber_analog);
        returnIntent.putExtra("dbNumber_analog",dbNumber_analog.getText().toString());
        returnIntent.putExtra("wordNumber_analog",wordNumber_analog.getText().toString());

        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
