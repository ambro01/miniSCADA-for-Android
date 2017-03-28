package com.example.application.miniSCADA;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public abstract class Popup extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onPopupShow();
    }
    public abstract void onClosePopup(View view);
    public abstract void onConfirmPopup(View view);
    public abstract void onPopupShow();
}
