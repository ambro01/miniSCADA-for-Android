package com.example.application.miniSCADA;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.application.miniSCADA.PLC.DataBlockBool;

public class PopupStatus extends Popup{

    public void onPopupShow(){
        setContentView(R.layout.popup_discrete_status);
        String [] list = getResources().getStringArray(R.array.bitIndex);
        Spinner spinner = (Spinner) findViewById(R.id.bitIndex_only_status);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,  android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        DataBlockBool statusDataBlock = (DataBlockBool) getIntent().getSerializableExtra("statusDataBlock");

        EditText dbNumber;
        EditText wordNumber;
        Spinner bitNumber;

        dbNumber = (EditText) findViewById(R.id.dbNumber_only_status);
        wordNumber = (EditText) findViewById(R.id.wordNumber_only_status);
        bitNumber = (Spinner)findViewById(R.id.bitIndex_only_status);
        dbNumber.setText(String.valueOf(statusDataBlock.getDbNumber()));
        wordNumber.setText(String.valueOf(statusDataBlock.getPosition()));
        bitNumber.setSelection(statusDataBlock.getBitPosition());
    }

    public void onClosePopup(View view){
        finish();
    }

    public void onConfirmPopup(View view){
        Intent returnIntent = new Intent();
        EditText dbNumber_status = (EditText) findViewById(R.id.dbNumber_only_status);
        EditText wordNumber_status = (EditText) findViewById(R.id.wordNumber_only_status);
        returnIntent.putExtra("dbNumber_status",dbNumber_status.getText().toString());
        returnIntent.putExtra("wordNumber_status",wordNumber_status.getText().toString());

        Spinner spinner_status = (Spinner)findViewById(R.id.bitIndex_only_status);
        returnIntent.putExtra("bitNumber_status",spinner_status.getSelectedItem().toString());

        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
