package com.example.application.miniSCADA;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifyActivity extends AppCompatActivity {
    String [] projects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        String patternText = "(\\w+).ser";
        int counter = 0;
        int tmp = 0;

        String path = getFilesDir().toString();
        File directory = new File(path);
        File[] files = directory.listFiles();

        for(int i = 0; i < files.length; i++){
            if (files[i].getName().matches(patternText))
                counter++;
        }

        if(counter > 0){
            projects = new String[counter];
            for(int i = 0; i < files.length; i++){
                if (files[i].getName().matches(patternText)){
                    projects[tmp] = files[i].getName();
                    tmp++;
                }
            }
            ListAdapter myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, projects);
            ListView projectsListView = (ListView) findViewById(R.id.listViewOfProjects);
            projectsListView.setAdapter(myAdapter);
        }
    }
}
