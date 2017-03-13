package com.example.application.miniSCADA.com.example.application.miniSCADA.Interface;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.application.miniSCADA.R;
import com.jrummyapps.android.colorpicker.ColorPickerDialog;
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;


public class PreDevelop extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_develop);
    }

    public void onFinalCreateProject(View view){
        String projectName;
        TextView name = (TextView) findViewById(R.id.nameOfNewPoject);
        projectName = name.getText().toString();

        Intent startDevelopActivity = new Intent(getApplicationContext(), DevelopActivity.class);
        startDevelopActivity.putExtra("projectName", projectName);
        startDevelopActivity.putExtra("deserialize", "false");
        startActivity(startDevelopActivity);
    }

}
