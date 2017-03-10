package com.example.application.miniSCADA;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import Moka7.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    S7Client client = new S7Client();

    public void readdb_val(View v){
        new PlcReader().execute("");
    }

    private class PlcReader extends AsyncTask<String, Void, String>{
        String ret = "";

        @Override
        protected String doInBackground(String... params){
            try{
                client.SetConnectionType(S7.S7_BASIC);
                int result = client.ConnectTo("10.10.101.47",0,1);

                if(result == 0) {
                    //data to read eg. (7,0,4) - reading DB7, REAL
                    byte[] data = new byte[4];
                    result = client.ReadArea(S7.S7AreaDB, 7, 0, 4, data);
                    ret = "Value of DB7.DB0: " + S7.GetFloatAt(data, 0);
                }else{
                    ret = "Err: " + S7Client.ErrorText(result);
                }
                client.Disconnect();
            }catch(Exception e){
                ret = "EXC: " + e.toString();
                Thread.interrupted();
            }
            return "executed";
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textOut = (TextView) findViewById(R.id.textView);
            textOut.setText(ret);
        }

    }

}
