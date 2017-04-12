package com.example.application.miniSCADA;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class PopupPlc extends Popup{

    public void onPopupShow(){
        setContentView(R.layout.popup_plc_configuration);
        Bundle extras = getIntent().getExtras();
        String text = extras.getString(getString(R.string.extraIp1ToPopup));
        EditText editText = (EditText) findViewById(R.id.ip_1);
        editText.setText(text);
        text = extras.getString(getString(R.string.extraIp2ToPopup));
        editText = (EditText) findViewById(R.id.ip_2);
        editText.setText(text);
        text = extras.getString(getString(R.string.extraIp3ToPopup));
        editText = (EditText) findViewById(R.id.ip_3);
        editText.setText(text);
        text = extras.getString(getString(R.string.extraIp4ToPopup));
        editText = (EditText) findViewById(R.id.ip_4);
        editText.setText(text);
    }

    public void onClosePopup(View view){
        finish();
    }

    public void onConfirmPopup(View view){
        Intent returnIntent = new Intent();
        EditText text = (EditText) findViewById(R.id.ip_1);
        returnIntent.putExtra(getString(R.string.extraIp1FromPopup),text.getText().toString());
        text = (EditText) findViewById(R.id.ip_2);
        returnIntent.putExtra(getString(R.string.extraIp2FromPopup),text.getText().toString());
        text = (EditText) findViewById(R.id.ip_3);
        returnIntent.putExtra(getString(R.string.extraIp3FromPopup),text.getText().toString());
        text = (EditText) findViewById(R.id.ip_4);
        returnIntent.putExtra(getString(R.string.extraIp4FromPopup),text.getText().toString());
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
