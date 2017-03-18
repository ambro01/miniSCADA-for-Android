package com.example.application.miniSCADA;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.Spinner;

public class Popup extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    }
}
