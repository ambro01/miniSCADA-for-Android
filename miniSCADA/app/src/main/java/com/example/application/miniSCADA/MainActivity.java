package com.example.application.miniSCADA;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " : " +
                        listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Discrete Controls");
        listDataHeader.add("Analog Controls");
        listDataHeader.add("Static Elements");

        // Adding child data
        List<String> discreteControls = new ArrayList<String>();
        discreteControls.add("Button");
        discreteControls.add("Valve Vertical");
        discreteControls.add("Valve Horizontal");
        discreteControls.add("Lamp");


        List<String> analogControls = new ArrayList<String>();
        analogControls.add("Float Display");

        List<String> staticElements = new ArrayList<String>();
        staticElements.add("Pipe Vertical");
        staticElements.add("Pipe Horizontal");
        staticElements.add("Pipe Left-Top");
        staticElements.add("Pipe Left-Bottom");
        staticElements.add("Pipe Right-Top");
        staticElements.add("Pipe Right-Bottom");
        staticElements.add("Pipe Cross");
        staticElements.add("Pump Left-Right");
        staticElements.add("Pump Right-Left");


        listDataChild.put(listDataHeader.get(0), discreteControls); // Header, Child data
        listDataChild.put(listDataHeader.get(1), analogControls);
        listDataChild.put(listDataHeader.get(2), staticElements);
    }


    public void onClick(View view){
        Intent startDevelopActivity = new Intent(getApplicationContext(), DevelopActivity.class);
        startActivity(startDevelopActivity);
    }
}
