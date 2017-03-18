package com.example.application.miniSCADA;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public abstract class Popup extends Activity{

    String bitIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onPopupShow();
    }


    public abstract void onClosePopup(View view);

    public abstract void onConfirmPopup(View view);

    public abstract void onPopupShow();
}
