package com.example.application.miniSCADA.Activities;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.example.application.miniSCADA.PLC.DataBlockBool;
import com.example.application.miniSCADA.Globals;
import com.example.application.miniSCADA.PLC.PlcReader;
import com.example.application.miniSCADA.R;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.Develop;
import com.example.application.miniSCADA.ExpandableListAdapter;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.Element;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.MyButton;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.Visualisation;
import com.jrummyapps.android.colorpicker.ColorPickerDialog;
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DevelopActivity extends AppCompatActivity implements ColorPickerDialogListener {

    private Develop develop;
    private RelativeLayout layout;
    private GestureDetector gestureDetector;

    private ExpandableListView itemsListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String projectName = "";
        develop = new Develop();
        gestureDetector = new GestureDetector(this, new SingleTapConfirm());
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_develop);
        Globals.displayMetrics = getResources().getDisplayMetrics();

        layout = (RelativeLayout) findViewById(R.id.develop_root);
        String deserialize = "";

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            projectName = extras.getString("projectName");
            deserialize = extras.getString("deserialize");
        }

        if(deserialize.equals("false")  && !projectName.isEmpty()){
            Visualisation visualisation = new Visualisation(projectName, Color.rgb(190,190,190));
            develop.setVisualisation(visualisation);
            develop.refreshBackgroundColor(layout);
        } else if(deserialize.equals("true") && !projectName.isEmpty()){
            develop.deserializeVisualisation(this, layout, projectName);
        }
        prepareListView();
    }

    //------------------BUTTON ACTIONS------------------------

    public void onButtonCreate(View view){
        byte[] data = new byte[1];
        DataBlockBool statusDataBlock = new DataBlockBool(7,4,data,0);
        DataBlockBool commandOnDataBlock = new DataBlockBool(7,4,data,1);
        DataBlockBool commandOffDataBlock = new DataBlockBool(7,4,data,2);
        MyButton myButton = new MyButton(this, statusDataBlock, Globals.dptoPx(Globals.posX), Globals.dptoPx(Globals.posY),
                Globals.dptoPx(Globals.buttonHeight), Globals.dptoPx(Globals.buttonWidth), commandOnDataBlock, commandOffDataBlock);
        myButton.setTextOnFalse("Turn ON");
        myButton.setTextOnTrue("Turn OFF");
        myButton.setOnTrueImage("Button1.png");
        myButton.setOnFalseImage("Button0.png");
        myButton.updateTrueFalseImage(this);

        myButton.updatePositionToElement();
        myButton.drawObject(this.layout);
        myButton.createOnTouchListener(layout);
        develop.getVisualisation().addElement(myButton);
    }

    public void onSaveProject(View view){
        develop.updatePositionsBeforeSaving();
        develop.updateSizesBeforeSaving();
        develop.serializeVisualisation(this);
    }

    public void onTouchListenerSelect(View view){
        if(develop.getVisualisation().getElements().size() > 0){
            for (Element element : develop.getVisualisation().getElements()){
                element.activeOnTouchListener(layout);
            }
        }
    };

    public void onLongClickListenerSelect(View view){
        if(develop.getVisualisation().getElements().size() > 0){
            for (Element element : develop.getVisualisation().getElements()){
                element.activeOnLongClickListener();
            }
        }
    };



    public void onBackgroundColorChange(View view){
        onColorSelect(view);
    }

    //-----------METHODS TO LIST VIEW ----------------------

    public void onItemsClick(View view){
        itemsListView.setVisibility(View.VISIBLE);
    }

    public void onEmptyImageClick(View view){
        itemsListView.setVisibility(View.INVISIBLE);
    }

    public void prepareListView(){
        ExpandableListAdapter listAdapter;
        itemsListView = (ExpandableListView) findViewById(R.id.itemsListView);
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        itemsListView.setAdapter(listAdapter);
        itemsListView.setVisibility(View.INVISIBLE);

        itemsListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {;
                return false;
            }
        });

        itemsListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        itemsListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        itemsListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String itemName = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                switch (listDataHeader.get(groupPosition)){
                    case "Discrete Controls":
                        develop.createDiscreteControlFromName(DevelopActivity.this, layout, itemName);
                        break;
                    case "Analog Controls":
                        break;
                    case "Static Elements":
                        develop.createStaticElementFromName(DevelopActivity.this, layout, itemName);
                        break;
                }
                itemsListView.setVisibility(View.INVISIBLE);
                return false;
            }
        });
    }

    public void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add("Discrete Controls");
        listDataHeader.add("Analog Controls");
        listDataHeader.add("Static Elements");

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


        listDataChild.put(listDataHeader.get(0), discreteControls);
        listDataChild.put(listDataHeader.get(1), analogControls);
        listDataChild.put(listDataHeader.get(2), staticElements);
    }

    //---------------METHODS TO COLOR CHANGE

    public void onColorSelect(View view){
        ColorPickerDialog.newBuilder().setColor(Color.WHITE).setShowAlphaSlider(true).show(this);
    }

    @Override
    public void onColorSelected(int dialogId, @ColorInt int color) {
        develop.getVisualisation().setBackgrondColor(color);
        develop.refreshBackgroundColor(layout);
    }

    @Override
    public void onDialogDismissed(int dialogId) {
    }

    //------------------METHODS WITH PLC -------------------------

    public void periodicallyReadPlc(){
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        new PlcReader(getApplicationContext(), develop.getVisualisation().getElements()).execute("");
                    }
                });
            }
        };
        //wykonywanie taska co 1000ms
        timer.schedule(task,0,1000);
    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }
}
