package com.example.application.miniSCADA.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.application.miniSCADA.Globals;
import com.example.application.miniSCADA.PLC.PlcReader;
import com.example.application.miniSCADA.Popup;
import com.example.application.miniSCADA.R;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.AnalogInput;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.Element;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.MyButton;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.Runtime;

import java.util.Timer;
import java.util.TimerTask;

public class RuntimeActivity extends AppCompatActivity {

    private Runtime runtime;
    private RelativeLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String projectName = "";
        runtime = new Runtime();

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_runtime);
        Globals.displayMetrics = getResources().getDisplayMetrics();

        layout = (RelativeLayout) findViewById(R.id.runtimeLayout);
        String deserialize = "";

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            projectName = extras.getString("projectName");
            deserialize = extras.getString("deserialize");
        }

        if(deserialize.equals("true") && !projectName.isEmpty())
            runtime.deserializeVisualisation(this, layout, projectName);
        else
            finish();

        if(runtime.getVisualisation() != null)
            periodicallyReadPlc();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                if(runtime.getActiveElement() instanceof AnalogInput){
                    ((AnalogInput) runtime.getActiveElement()).getSetpointFromPopup(data);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
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
                        new PlcReader(getApplicationContext(), runtime.getVisualisation().getElements()).execute("");
                    }
                });
            }
        };
        //wykonywanie taska co 1000ms
        timer.schedule(task,0,1000);
    }

}
