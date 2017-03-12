package com.example.application.miniSCADA.com.example.application.miniSCADA.Interface;

import android.widget.RelativeLayout;

import com.example.application.miniSCADA.*;

import Moka7.S7;

public abstract class DiscreteElement extends Element{
    private boolean status;
    private DataBlockBool statusDataBlock;
    private int onTrueImageId;
    private int onFalseImageId;
    private int height;
    private int width;
    private int positionX;
    private int positionY;

    public DiscreteElement(DataBlockBool statusDataBlock, int onTrueImageId, int onFalseImageId, int x, int y){
        this.status = false;
        this.statusDataBlock = statusDataBlock;
        this.onTrueImageId = onTrueImageId;
        this.onFalseImageId = onFalseImageId;
        this.height = 10;
        this.width = 30;
        this.positionX = x;
        this.positionY = y;
    }

    public boolean getStatus(){
        return this.status;
    }

    public int getOnTrueImageId(){
        return onTrueImageId;
    }

    public int getOnFalseImageId(){
        return onFalseImageId;
    }

    public void setSize(int height, int width){
        this.height = height;
        this.width = width;
    }

    public void setPosition(int x, int y){
        this.positionX = x;
        this.positionY = y;
    }

    public int getHeight(){
        return this.height;
    }

    public int getWidth(){
        return this.width;
    }

    public int getPositionX(){
        return this.positionX;
    }

    public int getPositionY(){
        return this.positionY;
    }

    public DataBlockBool getStatusDataBlock(){
        return statusDataBlock;
    }

    public void updateStatus(){
        this.status = S7.GetBitAt(statusDataBlock.getData(),0,statusDataBlock.getBitPosition());
    }

    public abstract void updateImage();

    public abstract void updateSize();

    public abstract void updatePosition();

    public abstract void drawObject(RelativeLayout layout);
}
