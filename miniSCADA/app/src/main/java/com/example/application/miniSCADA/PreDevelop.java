package com.example.application.miniSCADA;

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


import com.example.application.miniSCADA.R;
import com.jrummyapps.android.colorpicker.ColorPickerDialog;
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;


public class PreDevelop extends AppCompatActivity implements ColorPickerDialogListener {
    private int col;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_develop);
    }

    public void selectColor(View view){

        ColorPickerDialog.newBuilder().setColor(Color.WHITE).setShowAlphaSlider(true).show(this);
    }

    @Override
    public void onColorSelected(int dialogId, @ColorInt int color) {
        col = color;
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}
