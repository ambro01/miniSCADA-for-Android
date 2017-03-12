package com.example.application.miniSCADA;

import android.os.AsyncTask;

import com.example.application.miniSCADA.com.example.application.miniSCADA.Interface.DiscreteElement;

import java.util.ArrayList;

import Moka7.S7;
import Moka7.S7Client;

public class PlcReader extends AsyncTask<String, Void, String> {
    String ret = "";
    ArrayList<DiscreteElement> discreteElements;

    public PlcReader(ArrayList<DiscreteElement> ob){
        discreteElements = ob;
    }
    @Override
    protected String doInBackground(String... params){
        if (!discreteElements.isEmpty()) {
            try {
                Globals.s7client.SetConnectionType(S7.S7_BASIC);
                int result = Globals.s7client.ConnectTo("10.10.101.47", 0, 1);

                if (result == 0) {
                    for (DiscreteElement ob: discreteElements) {
                        result = Globals.s7client.ReadArea(S7.S7AreaDB, ob.getStatusDataBlock().getDbNumber(), ob.getStatusDataBlock().getPosition(), ob.getStatusDataBlock().getSize(), ob.getStatusDataBlock().getData());
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
       for (DiscreteElement ob: discreteElements) {
           ob.updateStatus();
           ob.updateImage();
       }
    }
}
