package com.example.application.miniSCADA;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

public class PopupAnalogSetpoint extends Popup {
    public void onPopupShow(){
        setContentView(R.layout.popup_analog);
    }

    public void onClosePopup(View view){
        finish();
    }

    public void onConfirmPopup(View view){
        Intent returnIntent = new Intent();
        EditText value = (EditText) findViewById(R.id.setpointValue);
        returnIntent.putExtra("setpointValue",value.getText().toString());

        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
