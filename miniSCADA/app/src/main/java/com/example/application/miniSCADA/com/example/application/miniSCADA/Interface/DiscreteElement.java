package com.example.application.miniSCADA.com.example.application.miniSCADA.Interface;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.RelativeLayout;

import com.example.application.miniSCADA.*;

import Moka7.S7;

public abstract class DiscreteElement extends Element{
    private boolean status;
    private DataBlockBool statusDataBlock;
    private String onTrueImageName;
    private String onFalseImageName;
    //private transient Drawable onTrueImage;
    //private transient Drawable onFalseImage;

    public DiscreteElement(DataBlockBool statusDataBlock, int x, int y, int height, int width){
        super(x,y,height,width);
        this.status = false;
        this.statusDataBlock = statusDataBlock;

    }

    public boolean getStatus(){
        return this.status;
    }

    public String getOnTrueImage(){
        return onTrueImageName;
    }

    public String getOnFalseImage(){
        return onFalseImageName;
    }

    public void setOnTrueImage(String name){
        this.onTrueImageName = name;
    }

    public void setOnFalseImage(String name){
        this.onFalseImageName = name;
    }

    public DataBlockBool getStatusDataBlock(){
        return statusDataBlock;
    }

    public void updateStatus(){
        this.status = S7.GetBitAt(statusDataBlock.getData(),0,statusDataBlock.getBitPosition());
    }

    public abstract void updateTrueFalseImage(Context context);

}
