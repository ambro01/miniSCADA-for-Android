package com.example.application.miniSCADA.PLC;

import android.os.AsyncTask;

import com.example.application.miniSCADA.Globals;

import Moka7.S7;
import Moka7.S7Client;


public class PlcWriter extends AsyncTask<String, Void, String> {
    String ret = "";
    DataBlock data;

    public PlcWriter(DataBlock data){
        this.data = data;
    }

    @Override
    protected String doInBackground(String... params){
        try{
            Globals.s7client.SetConnectionType(S7.S7_BASIC);
            int result = Globals.s7client.ConnectTo("10.10.101.47",0,1);
            if(result == 0) {
                System.out.println("------------------");
                result = Globals.s7client.WriteArea(S7.S7AreaDB, data.getDbNumber(), data.getPosition(), data.getSize(), data.getData());
                System.out.println(result);
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