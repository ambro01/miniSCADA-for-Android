package com.example.application.miniSCADA.com.example.application.miniSCADA.Interface;


import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;

public class Visualisation implements Serializable{
    String name;
    ArrayList<Element> elements;
    int backgrondColor;

    public Visualisation(String name, int backgrondColor){
        this.name = name;
        this.backgrondColor = backgrondColor;
        elements = new ArrayList<Element>();
    }

    public void setBackgrondColor(int color){
        this.backgrondColor = color;
    }

    public ArrayList<Element> getElements(){
        return elements;
    }

    public void addElement(Element element){
        elements.add(element);
    }

    public int getBackgrondColor(){
        return backgrondColor;
    }

    public String getName(){
        return name;
    }

}
