package com.example.application.miniSCADA.PLC;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.application.miniSCADA.Globals;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Objects.AnalogDisplay;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Objects.DiscreteElement;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Objects.Element;

import java.util.ArrayList;

import Moka7.S7;
import Moka7.S7Client;

public class PlcReader extends AsyncTask<String, Void, String> {
    private String ret = "";
    private ArrayList<Element> elements;
    private Context context;
    private String ipAddress;

    public PlcReader(Context context, ArrayList<Element> ob, String ip){
        this.context = context;
        elements = ob;
        ipAddress = ip;
    }
    @Override
    protected String doInBackground(String... params){
        if (!elements.isEmpty()) {
            try {
                Globals.s7client.SetConnectionType(S7.S7_BASIC);
                int result = Globals.s7client.ConnectTo(ipAddress, 0, 1);

                if (result == 0) {
                    for (Element element: elements) {
                        if(element instanceof DiscreteElement) {
                            DiscreteElement discreteElement = (DiscreteElement) element;
                            result = Globals.s7client.ReadArea(S7.S7AreaDB, discreteElement.getStatusDataBlock().getDbNumber(), discreteElement.getStatusDataBlock().getPosition(),
                                    discreteElement.getStatusDataBlock().getSize(), discreteElement.getStatusDataBlock().getData());
                        } else if(element instanceof AnalogDisplay){
                            AnalogDisplay analogDisplay = (AnalogDisplay) element;
                            result = Globals.s7client.ReadArea(S7.S7AreaDB, analogDisplay.getDataBlock().getDbNumber(), analogDisplay.getDataBlock().getPosition(),
                                    analogDisplay.getDataBlock().getSize(), analogDisplay.getDataBlock().getData());
                        }
                    }
                } else {
                    ret = "Err: " + S7Client.ErrorText(result);
                }
                Globals.s7client.Disconnect();
            } catch (Exception e) {
                ret = "EXC: " + e.toString();
                Thread.interrupted();
            }
        }
        return "executed";
    }

   // @Override
   protected void onPostExecute(String result) {
       for (Element element: elements) {
           if(element instanceof DiscreteElement) {
               DiscreteElement discreteElement = (DiscreteElement) element;
               discreteElement.updateStatus();
               discreteElement.updateTrueFalseImage(context);
           } else if(element instanceof AnalogDisplay){
               AnalogDisplay analog =(AnalogDisplay) element;
               analog.updateValueFromPlc();
               analog.updateDisplayValue();
           }
       }
    }
}
