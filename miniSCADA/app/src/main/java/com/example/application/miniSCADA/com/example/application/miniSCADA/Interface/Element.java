package com.example.application.miniSCADA.com.example.application.miniSCADA.Interface;

import android.app.Activity;
import android.content.Intent;
import android.widget.RelativeLayout;

import java.io.Serializable;

public abstract class Element implements Serializable{
    private static final long serialVersionUID = 2L;
    private int positionX;
    private int positionY;
    private int height;
    private int width;

    private float oldXposition;
    private float oldYposition;

    public Element(int x, int y, int height, int width){
        this.positionX  = x;
        this.positionY = y;
        this.height = height;
        this.width = width;
    }

    public void setPosition(int x, int y){
        this.positionX = x;
        this.positionY = y;
    }

    public void setSize(int height, int width){
        this.height = height;
        this.width = width;
    }

    public float getPositionX(){
        return this.positionX;
    }

    public float getPositionY(){
        return this.positionY;
    }

    public int getHeight(){
        return this.height;
    }

    public int getWidth(){
        return this.width;
    }

    public void setOldPosition(float x, float y){
        this.oldXposition = x;
        this.oldYposition = y;
    }

    public float getOldXposition(){
        return oldXposition;
    }

    public float getOldYposition(){
        return oldYposition;
    }

    public abstract void updatePositionToElement();

    public abstract void updatePositionFromElement();

    public abstract void updateSizeToElement();

    public abstract void updateSizeFromElement();

    public abstract void drawObject(RelativeLayout layout);

    public abstract void reCreateElement(Activity activity);

    public abstract void createOnTouchListener(final RelativeLayout layout);

    public abstract void createOnLongClickListener(Activity activity, Develop develop);

    public abstract void createOnClickListener(Activity activity, Runtime runtime);

    public abstract void activeOnLongClickListener(Activity activity,Develop develop);

    public abstract void activeOnTouchListener(RelativeLayout layout);

    public abstract void createDataFromPopup(Intent intent);
}
