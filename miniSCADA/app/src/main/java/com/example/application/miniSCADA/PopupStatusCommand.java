package com.example.application.miniSCADA;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class PopupStatusCommand extends Popup{


    public void onPopupShow(){
        setContentView(R.layout.popup_discrete_status_command);
        String [] list = getResources().getStringArray(R.array.bitIndex);
        Spinner spinner = (Spinner) findViewById(R.id.bitIndex_both_status);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,  android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner = (Spinner) findViewById(R.id.bitIndex_both_commandTrue);
        adapter = new ArrayAdapter<>(this,  android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner = (Spinner) findViewById(R.id.bitIndex_both_commandFalse);
        adapter = new ArrayAdapter<>(this,  android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
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

        EditText dbNumber_commandTrue = (EditText) findViewById(R.id.dbNumber_both_commandTrue);
        EditText wordNumber_commandTrue = (EditText) findViewById(R.id.wordNumber_both_commandTrue);
        returnIntent.putExtra("dbNumber_commandTrue",dbNumber_commandTrue.getText().toString());
        returnIntent.putExtra("wordNumber_commandTrue",wordNumber_commandTrue.getText().toString());

        EditText dbNumber_commandFalse = (EditText) findViewById(R.id.dbNumber_both_commandFalse);
        EditText wordNumber_commandFalse = (EditText) findViewById(R.id.wordNumber_both_commandFalse);
        returnIntent.putExtra("dbNumber_commandFalse",dbNumber_commandFalse.getText().toString());
        returnIntent.putExtra("wordNumber_commandFalse",wordNumber_commandFalse.getText().toString());

        Spinner spinner_status = (Spinner)findViewById(R.id.bitIndex_both_status);
        returnIntent.putExtra("bitNumber_status",spinner_status.getSelectedItem().toString());

        Spinner spinner_commandTrue = (Spinner)findViewById(R.id.bitIndex_both_commandTrue);
        returnIntent.putExtra("bitNumber_commandTrue",spinner_commandTrue.getSelectedItem().toString());

        Spinner spinner_commandFalse = (Spinner)findViewById(R.id.bitIndex_both_commandFalse);
        returnIntent.putExtra("bitNumber_commandFalse",spinner_commandFalse.getSelectedItem().toString());

        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

}
