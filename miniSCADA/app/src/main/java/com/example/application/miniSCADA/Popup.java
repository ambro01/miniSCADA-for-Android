package com.example.application.miniSCADA;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
<<<<<<< HEAD
import android.view.View;

public abstract class Popup extends Activity{
=======
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.Spinner;

public class Popup extends AppCompatActivity{
>>>>>>> 78fea71660cc1a62ca2ca6efd4e809b224f1afc2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        onPopupShow();
=======
        setContentView(R.layout.popup_window);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Globals.displayMetrics = getResources().getDisplayMetrics();

        Spinner indexSpinner = (Spinner)findViewById(R.id.spinnerIndex);
        Integer[] items = new Integer[]{0,1,2,3,4,5,6,7};
        indexSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, items);
        indexSpinner.setAdapter(adapter);

>>>>>>> 78fea71660cc1a62ca2ca6efd4e809b224f1afc2
    }
    public abstract void onClosePopup(View view);
    public abstract void onConfirmPopup(View view);
    public abstract void onPopupShow();
}
