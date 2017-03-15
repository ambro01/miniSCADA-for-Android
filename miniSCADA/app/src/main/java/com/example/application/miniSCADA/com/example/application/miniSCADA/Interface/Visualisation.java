package com.example.application.miniSCADA.com.example.application.miniSCADA.Interface;

import java.io.Serializable;
import java.util.ArrayList;

public class Visualisation implements Serializable{
    private static final long serialVersionUID = 1L;
    String name;
    ArrayList<Element> elements;
    int backgroundColor;

    public Visualisation(String name, int backgroundColor){
        this.name = name;
        this.backgroundColor = backgroundColor;
        elements = new ArrayList<Element>();
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

}
