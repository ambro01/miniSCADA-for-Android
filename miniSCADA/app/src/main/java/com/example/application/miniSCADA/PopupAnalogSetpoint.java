package com.example.application.miniSCADA;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PopupAnalogSetpoint extends Popup {
    public void onPopupShow(){
        setContentView(R.layout.popup_analog_setpoint);
        Bundle extras = getIntent().getExtras();
        String inputValue = extras.getString("inputValue");
        EditText editText = (EditText) findViewById(R.id.setpointValue);
        editText.setText(inputValue);
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
