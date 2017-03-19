package com.example.application.miniSCADA;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;


public class PopupAnalog extends Popup {
    public void onPopupShow(){
        setContentView(R.layout.popup_analog);
    }

    public void onClosePopup(View view){
        finish();
    }

    public void onConfirmPopup(View view){
        Intent returnIntent = new Intent();
        EditText dbNumber_analog = (EditText) findViewById(R.id.setpointValue);
        EditText wordNumber_analog = (EditText) findViewById(R.id.wordNumber_analog);
        returnIntent.putExtra("dbNumber_status",dbNumber_analog.getText().toString());
        returnIntent.putExtra("wordNumber_status",wordNumber_analog.getText().toString());

        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
