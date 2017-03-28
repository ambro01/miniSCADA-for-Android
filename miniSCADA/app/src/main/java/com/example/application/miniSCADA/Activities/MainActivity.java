package com.example.application.miniSCADA.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.application.miniSCADA.Globals;
import com.example.application.miniSCADA.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Globals.displayMetrics = getResources().getDisplayMetrics();
    }

    public void onCreateProject(View view){
        Intent startPreDevelopActivity = new Intent(getApplicationContext(), PreDevelopActivity.class);
        startActivity(startPreDevelopActivity);
    }

    public void onModifyProject(View view){
        Intent startModifyActivity = new Intent(getApplicationContext(), ModifyActivity.class);
        startActivity(startModifyActivity);
    }

    public void onDisplayVisualisation(View view){
        Intent startPreRuntimeActivity = new Intent(getApplicationContext(), PreRuntimeActivity.class);
        startActivity(startPreRuntimeActivity);
    }

}
