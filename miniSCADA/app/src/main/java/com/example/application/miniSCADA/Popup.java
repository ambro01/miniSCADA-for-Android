package com.example.application.miniSCADA;


import android.app.Activity;
<<<<<<< HEAD
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public abstract class Popup extends Activity{

    String bitIndex;
=======
import android.os.Bundle;
import android.util.DisplayMetrics;

public class Popup extends Activity{
>>>>>>> parent of 78fea71... Temporary popup window

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        onPopupShow();
    }


    public abstract void onClosePopup(View view);

    public abstract void onConfirmPopup(View view);

    public abstract void onPopupShow();
=======

        setContentView(R.layout.popup_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.6));
    }
>>>>>>> parent of 78fea71... Temporary popup window
}
