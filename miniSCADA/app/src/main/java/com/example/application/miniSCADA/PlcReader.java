package com.example.application.miniSCADA;

import android.os.AsyncTask;

import Moka7.S7;
import Moka7.S7Client;

public class PlcReader extends AsyncTask<String, Void, String> {
    String ret = "";
    DataBlock data;

    public PlcReader(DataBlock data){
        this.data = data;
    }
    @Override
    protected String doInBackground(String... params){
        try{
            Globals.s7client.SetConnectionType(S7.S7_BASIC);
            int result = Globals.s7client.ConnectTo("10.10.101.47",0,1);

            if(result == 0) {
                result = Globals.s7client.ReadArea(S7.S7AreaDB, data.getDbNumber(), data.getPosition(), data.getSize(), data.getData());
                ret = "Value of DB7.DBX4.0: " + S7.GetBitAt(data.getData(),0,0);
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
    protected void onPostExecute(String result) {
       // TextView textOut = (TextView) findViewById(R.id.textView);
       // textOut.setText(ret);
    }

}
