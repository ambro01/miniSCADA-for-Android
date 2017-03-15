package com.example.application.miniSCADA.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


import com.example.application.miniSCADA.Activities.DevelopActivity;
import com.example.application.miniSCADA.Globals;
import com.example.application.miniSCADA.R;


public class PreDevelopActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_develop);
        Globals.displayMetrics = getResources().getDisplayMetrics();
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
