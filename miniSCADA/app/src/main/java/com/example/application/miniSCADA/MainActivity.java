package com.example.application.miniSCADA;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.ModifyActivity;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.PreDevelop;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCreateProject(View view){
        Intent startPreDevelopActivity = new Intent(getApplicationContext(), PreDevelop.class);
        startActivity(startPreDevelopActivity);
    }

    public void onModifyProject(View view){
        Intent startModifyActivity = new Intent(getApplicationContext(), ModifyActivity.class);
        startActivity(startModifyActivity);
    }
}
