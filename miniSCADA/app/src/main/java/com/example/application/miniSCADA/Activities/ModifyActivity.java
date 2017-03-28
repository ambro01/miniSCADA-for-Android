package com.example.application.miniSCADA.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.example.application.miniSCADA.Globals;
import com.example.application.miniSCADA.R;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifyActivity extends AppCompatActivity {
    ArrayList<String> projects;
    private int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Globals.displayMetrics = getResources().getDisplayMetrics();

        ListView projectsListView = (ListView) findViewById(R.id.listViewOfProjects);
        refreshListView(projectsListView);

        itemPosition = -1;

        projectsListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        projectsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                        view.setSelected(true);
                        itemPosition = position;
                    }
                }
        );
    }

    public void refreshListView(ListView listView){
        projects = new ArrayList<String>();
        String patternText = "\\w+.ser";
        String path = getFilesDir().toString();
        File directory = new File(path);
        File[] files = directory.listFiles();

        for(int i = 0; i < files.length; i++){
            if (files[i].getName().matches(patternText)){
                projects.add(files[i].getName());
            }
        }
        ListAdapter myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, projects);
        listView.setAdapter(myAdapter);
    }

    public void onDeleteProject(View view){
        ListView projectsListView = (ListView) findViewById(R.id.listViewOfProjects);
        if(itemPosition >= 0){
            String name = projectsListView.getItemAtPosition(itemPosition).toString();
            File file = new File(getFilesDir() + "/" + name);
            if(file.exists()) {
                file.delete();
                refreshListView(projectsListView);
            }
        }
        itemPosition = -1;
    }

    public void onOpenProjectToModify(View view){
        ListView projectsListView = (ListView) findViewById(R.id.listViewOfProjects);
        if(itemPosition >= 0){
            String fullName = projectsListView.getItemAtPosition(itemPosition).toString();
            Pattern pattern = Pattern.compile("((\\w+)(.ser))");
            Matcher matcher = pattern.matcher(fullName);
            if(matcher.find()){
                String name = matcher.group(2);
                Intent startDevelopActivity = new Intent(getApplicationContext(), DevelopActivity.class);
                startDevelopActivity.putExtra(getString(R.string.extraProjectName), name);
                startDevelopActivity.putExtra(getString(R.string.extraDeserialize), "true");
                startActivity(startDevelopActivity);
            }
        }
    }
}
