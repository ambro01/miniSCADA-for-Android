package com.example.application.miniSCADA.PLC;

import android.content.Context;
import android.os.AsyncTask;

import com.example.application.miniSCADA.Globals;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.DiscreteElement;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.Element;

import java.util.ArrayList;

import Moka7.S7;
import Moka7.S7Client;

public class PlcReader extends AsyncTask<String, Void, String> {
    private String ret = "";
    private ArrayList<Element> elements;
    private Context context;

    public PlcReader(Context context, ArrayList<Element> ob){
        this.context = context;
        elements = ob;
    }
    @Override
    protected String doInBackground(String... params){
        if (!elements.isEmpty()) {
            try {
                Globals.s7client.SetConnectionType(S7.S7_BASIC);
                int result = Globals.s7client.ConnectTo("10.10.101.47", 0, 1);

                if (result == 0) {
                    DiscreteElement discreteElement;
                    for (Element ob: elements) {
                        discreteElement = (DiscreteElement) ob;
                        result = Globals.s7client.ReadArea(S7.S7AreaDB, discreteElement.getStatusDataBlock().getDbNumber(), discreteElement.getStatusDataBlock().getPosition(),
                                discreteElement.getStatusDataBlock().getSize(), discreteElement.getStatusDataBlock().getData());
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
       DiscreteElement discreteElement;
       for (Element ob: elements) {
           discreteElement = (DiscreteElement) ob;
           discreteElement.updateStatus();
           discreteElement.updateTrueFalseImage(context);
       }
    }
}
