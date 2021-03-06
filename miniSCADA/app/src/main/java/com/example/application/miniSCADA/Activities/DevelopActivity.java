package com.example.application.miniSCADA.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
<<<<<<< HEAD
=======
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
>>>>>>> 78fea71660cc1a62ca2ca6efd4e809b224f1afc2
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
<<<<<<< HEAD
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
=======
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.application.miniSCADA.PLC.DataBlockBool;
>>>>>>> 78fea71660cc1a62ca2ca6efd4e809b224f1afc2
import com.example.application.miniSCADA.Globals;
import com.example.application.miniSCADA.R;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.Develop;
import com.example.application.miniSCADA.ExpandableListAdapter;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.Element;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.Visualisation;
import com.jrummyapps.android.colorpicker.ColorPickerDialog;
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DevelopActivity extends AppCompatActivity implements ColorPickerDialogListener {

    private Develop develop;
    private RelativeLayout layout;
<<<<<<< HEAD
=======
    private GestureDetector gestureDetector;
    private LayoutInflater layoutInflater;

>>>>>>> 78fea71660cc1a62ca2ca6efd4e809b224f1afc2

    private ExpandableListView itemsListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

<<<<<<< HEAD
=======
    private PopupWindow popUpWindow;
>>>>>>> 78fea71660cc1a62ca2ca6efd4e809b224f1afc2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String projectName = "";
        develop = new Develop();
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_develop);
        Globals.displayMetrics = getResources().getDisplayMetrics();

        layout = (RelativeLayout) findViewById(R.id.developLayout);
        String deserialize = "";

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            projectName = extras.getString(getString(R.string.extraProjectName));
            deserialize = extras.getString(getString(R.string.extraDeserialize));
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

<<<<<<< HEAD
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                develop.getActiveElement().createDataFromPopup(this, data);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
=======
    //------------------BUTTON ACTIONS------------------------

    public void onPopupShow(View view){
       // startActivity(new Intent(this, Popup.class));
        layoutInflater = (LayoutInflater) getApplication().getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.popup_window,null);
        popUpWindow = new PopupWindow(container,Globals.dptoPx(Globals.popupWidth), Globals.dptoPx(Globals.popupHeight), false);
        popUpWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
    }

    public void onClosePopup(){
        popUpWindow.dismiss();
>>>>>>> 78fea71660cc1a62ca2ca6efd4e809b224f1afc2
    }

    //------------------BUTTON ACTIONS------------------------

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
                element.activeOnLongClickListener(this, develop);
            }
        }
    }

    public void onBackgroundColorChange(View view){
        onColorSelect(view);
    }

    //-----------METHODS TO LIST VIEW ----------------------

    public void onItemsClick(View view){
        itemsListView.setVisibility(View.VISIBLE);
        itemsListView.bringToFront();
    }

    public void onEmptyImageClick(View view){
        itemsListView.setVisibility(View.INVISIBLE);
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
                        develop.createAnalogControlFromName(DevelopActivity.this, layout, itemName);
                        break;
                    case "Static Elements":
                        develop.createStaticElementFromName(DevelopActivity.this, layout, itemName);
                        break;
                }
                parent.collapseGroup(groupPosition);
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
        discreteControls.add("Lamp");

        List<String> analogControls = new ArrayList<String>();
        analogControls.add("Analog Input");
        analogControls.add("Analog Display");
        analogControls.add("Label");

        List<String> staticElements = new ArrayList<String>();
        staticElements.add("Valve Vertical");
        staticElements.add("Valve Horizontal");
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
}
