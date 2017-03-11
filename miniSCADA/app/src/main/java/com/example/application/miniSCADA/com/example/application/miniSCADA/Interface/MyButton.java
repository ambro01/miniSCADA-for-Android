package com.example.application.miniSCADA.com.example.application.miniSCADA.Interface;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.application.miniSCADA.*;

import Moka7.*;

public class MyButton extends DiscreteObject{
    private Button button;
    private boolean command;
    private DataBlockBool commandOnDataBlock;
    private DataBlockBool commandOffDataBlock;
    private String textOnTrue;
    private String textOnFalse;

    public MyButton(Activity activity, DataBlockBool statusDataBlock, int onTrueImageId, int onFalseImageId, int x, int y, DataBlockBool commandOnDataBlock,DataBlockBool commandOffDataBlock){
        super(statusDataBlock,onTrueImageId,onFalseImageId,x,y);
        button = new Button(activity);
        this.updateImage();
        this.textOnTrue = "";
        this.textOnFalse = "";
        this.commandOnDataBlock = commandOnDataBlock;
        this.commandOffDataBlock = commandOffDataBlock;
        this.button.setTextColor(Color.BLACK);
        this.button.setTextSize(12);
    }

    public Button getButton(){
        return button;
    }

    public void setTextOnTrue(String text){
        this.textOnTrue = text;
    }

    public void setTextOnFalse(String text){
        this.textOnFalse = text;
    }

    public void updateImage(){
        if (this.getStatus()) {
            button.setBackgroundResource(this.getOnTrueImageId());
            button.setText(this.textOnTrue);
        } else {
            button.setBackgroundResource(this.getOnFalseImageId());
            button.setText(this.textOnFalse);
        }
    }

    public void executeCommand(){
            S7.SetBitAt(this.commandDataBlock.getData(), 0, this.commandDataBlock.getBitPosition(), this.command);
            new PlcWriter(this.commandDataBlock).execute("");
    }

    public void updateSize(){
        button.setHeight(this.getHeight());
        button.setWidth(this.getWidth());
    }

    public void updatePosition(){
        button.setX(this.getPositionX());
        button.setY(this.getPositionY());
    }

    public void drawObject(RelativeLayout layout){
        layout.addView(this.button);
    }

    public void setTrue(){
        this.command = true;
    }

    public void setFalse(){
        this.command = false;
    }


    public void createOnClickListener(View view){
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (getStatus()) {
                    setFalse();
                    executeCommand();
                } else {
                    setTrue();
                    executeCommand();
                }
            }
        });
    }
}
