package com.example.application.miniSCADA;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;


public class PopupLabel extends Popup{
    public void onPopupShow(){
        setContentView(R.layout.popup_label);
    }

    public void onClosePopup(View view){
        finish();
    }

    public void onConfirmPopup(View view){
        Intent returnIntent = new Intent();
        EditText text = (EditText) findViewById(R.id.setpointValue);
        returnIntent.putExtra("labelText",text.getText().toString());

        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
