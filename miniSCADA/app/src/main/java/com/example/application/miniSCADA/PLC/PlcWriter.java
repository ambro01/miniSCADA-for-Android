package com.example.application.miniSCADA.PLC;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.application.miniSCADA.Globals;
import com.example.application.miniSCADA.com.example.application.miniSCADA.Objects.DiscreteElement;

import Moka7.S7;
import Moka7.S7Client;


public class PlcWriter extends AsyncTask<String, Void, String> {
    String ret = "";
    private DataBlock data;
    private String ipAddress;

    public PlcWriter(DataBlock data, String ip){
        this.data = data;
        ipAddress = ip;
    }

    @Override
    protected String doInBackground(String... params){
        try{
            Globals.s7client.SetConnectionType(S7.S7_BASIC);
            int result = Globals.s7client.ConnectTo(ipAddress,0,1);
            if(result == 0) {
                if (data instanceof DataBlockBool){
                    byte[] dataTemp = new byte[1];
                    Globals.s7client.ReadArea(S7.S7AreaDB, data.getDbNumber(), data.getPosition(), data.getSize(), dataTemp);
                    S7.SetBitAt(dataTemp, 0, ((DataBlockBool) data).getBitPosition(), true);
                    result = Globals.s7client.WriteArea(S7.S7AreaDB, data.getDbNumber(), data.getPosition(), data.getSize(), dataTemp);
                } else {
                    result = Globals.s7client.WriteArea(S7.S7AreaDB, data.getDbNumber(), data.getPosition(), data.getSize(), data.getData());
                }
            }else{
                ret = "Err: " + S7Client.ErrorText(result);
            }
            Globals.s7client.Disconnect();
        }catch(Exception e){
            ret = "EXC: " + e.toString();
            Thread.interrupted();
        }
        return "executed";
    }

    @Override
    protected void onPreExecute() {

    }

}