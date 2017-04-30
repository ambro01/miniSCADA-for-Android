package com.example.application.miniSCADA.com.example.application.miniSCADA.Objects;

import android.app.Activity;
import android.content.Intent;

import com.example.application.miniSCADA.R;

import java.io.Serializable;
import java.util.ArrayList;

public class Visualisation implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private ArrayList<Element> elements;
    private int backgroundColor;
    private String ipAddress;
    private int[] ipAddressArray;

    public Visualisation(String name, int backgroundColor){
        this.name = name;
        this.backgroundColor = backgroundColor;
        elements = new ArrayList<Element>();
        ipAddress = "";
        ipAddressArray = new int[4];
    }

    public void setBackgrondColor(int color){
        this.backgroundColor = color;
    }

    public ArrayList<Element> getElements(){
        return elements;
    }

    public void addElement(Element element){
        elements.add(element);
    }

    public int getBackgrondColor(){
        return backgroundColor;
    }

    public String getName(){
        return name;
    }

    public String getIpAddress(){
        return ipAddress;
    }

    public int[] getIpAddressArray(){
        return ipAddressArray;
    }

    public void setIpAddress(String ip){
        ipAddress = ip;
    }

    public void setIpAddressArray(int[] ip){
        ipAddressArray = ip;
    }

    public void ipFromIpArray(){
        ipAddress = String.valueOf(ipAddressArray[0]) + "." + String.valueOf(ipAddressArray[1]) + "." +
                String.valueOf(ipAddressArray[2]) + "." + String.valueOf(ipAddressArray[3]);
    }

    public void createDataFromPopup(Activity activity, Intent intent){
        int ip[] = new int[4];

        ip[0] = Integer.parseInt(intent.getStringExtra(activity.getResources().getString(R.string.extraIp1FromPopup)));
        ip[1] = Integer.parseInt(intent.getStringExtra(activity.getResources().getString(R.string.extraIp2FromPopup)));
        ip[2] = Integer.parseInt(intent.getStringExtra(activity.getResources().getString(R.string.extraIp3FromPopup)));
        ip[3] = Integer.parseInt(intent.getStringExtra(activity.getResources().getString(R.string.extraIp4FromPopup)));

        setIpAddressArray(ip);
        ipFromIpArray();
    }

    public void deleteElement(Element element){
        elements.remove(element);
    }

}
