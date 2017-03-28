package com.example.application.miniSCADA.com.example.application.miniSCADA.Interface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.application.miniSCADA.*;
import com.example.application.miniSCADA.PLC.DataBlockBool;
import com.example.application.miniSCADA.PLC.PlcWriter;

import Moka7.*;

public class MyButton extends DiscreteElement {
    private transient Button button;
    private DataBlockBool commandOnDataBlock;
    private DataBlockBool commandOffDataBlock;
    private String textOnTrue;
    private String textOnFalse;


    public MyButton(Activity activity, DataBlockBool statusDataBlock, int x, int y, int height, int width,
                    DataBlockBool commandOnDataBlock,DataBlockBool commandOffDataBlock){
        super(statusDataBlock,x,y,height,width);
        button = new Button(activity);
        this.textOnTrue = "";
        this.textOnFalse = "";
        this.commandOnDataBlock = commandOnDataBlock;
        this.commandOffDataBlock = commandOffDataBlock;
        this.button.setTextColor(Color.BLACK);
        this.button.setTextSize(14);
        this.button.setBackgroundColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            button.setStateListAnimator(null);
        }
    }

    public void setTextOnTrue(String text){
        this.textOnTrue = text;
    }

    public void setTextOnFalse(String text){
        this.textOnFalse = text;
    }

    public Button getButton(){
        return button;
    }

    public DataBlockBool getCommandOnDataBlock(){
        return commandOnDataBlock;
    }

    public DataBlockBool getCommandOffDataBlock(){
        return commandOffDataBlock;
    }

    public void setCommandOnDataBlock(DataBlockBool dataBlock){
        commandOnDataBlock = dataBlock;
    }

    public void setCommandOffDataBlock(DataBlockBool dataBlock){
        commandOffDataBlock = dataBlock;
    }

    public void reCreateElement(Activity activity){
        this.button = new Button(activity);
        this.updateTrueFalseImage(activity);
        this.button.setTextSize(14);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            button.setStateListAnimator(null);
        }
        updatePositionToElement();
        updateSizeToElement();
    }

    public void updateTrueFalseImage(Context context){
        if (this.getStatus()) {
            button.setBackground(Globals.loadImageToDrawable(context, this.getOnTrueImage()));
            button.setText(this.textOnTrue);
        } else {
            button.setBackground(Globals.loadImageToDrawable(context, this.getOnFalseImage()));
            button.setText(this.textOnFalse);
        }
    }

    public void executeCommand(){
        if(this.getStatus()) {
            S7.SetBitAt(this.commandOffDataBlock.getData(), 0, this.commandOffDataBlock.getBitPosition(), true);
            new PlcWriter(this.commandOffDataBlock).execute("");
        }else{
            S7.SetBitAt(this.commandOnDataBlock.getData(), 0, this.commandOnDataBlock.getBitPosition(), true);
            new PlcWriter(this.commandOnDataBlock).execute("");
        }
    }

    public void updatePositionToElement(){
        button.setX(this.getPositionX());
        button.setY(this.getPositionY());
    }

    public void updatePositionFromElement(){
        this.setPosition((int)button.getX(), (int)button.getY());
    }

    public void updateSizeToElement(){
        button.setHeight(this.getHeight());
        button.setWidth(this.getWidth());
    }

    public void updateSizeFromElement(){
        this.setSize(button.getHeight(), button.getWidth());
    }

    public void drawObject(RelativeLayout layout){
        layout.addView(this.button);
    }

    public void createOnLongClickListener(final Activity activity, final Develop develop){
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                develop.setActiveElement(MyButton.this);
                Intent startPopup = new Intent(activity, PopupStatusCommand.class);
                startPopup.putExtra(activity.getResources().getString(R.string.extraStatusDataBlock),getStatusDataBlock());
                startPopup.putExtra(activity.getResources().getString(R.string.extraCommandOnDataBlock),getCommandOnDataBlock());
                startPopup.putExtra(activity.getResources().getString(R.string.extraCommandOffDataBlock),getCommandOffDataBlock());
                activity.setResult(Activity.RESULT_OK,startPopup);
                activity.startActivityForResult(startPopup, 1);
                return true;
            }
        });
    }

    public void createOnTouchListener(final RelativeLayout layout){
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent me) {
                switch (me.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lparams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                        setOldPosition(me.getRawX() - lparams.leftMargin, me.getRawY() - lparams.topMargin);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
                        params.leftMargin = (int)(me.getRawX() - getOldXposition());
                        params.topMargin = (int)(me.getRawY() - getOldYposition());
                        params.rightMargin = -250;
                        params.bottomMargin = -250;
                        button.setLayoutParams(params);
                        break;
                }
                return false;
            }
        });
    }

    public void createOnClickListener(final Activity activity, final Runtime runtime){
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                executeCommand();
            }
        });
    }

    public void activeOnLongClickListener(Activity activity, Develop develop){
        createOnLongClickListener(activity, develop);
        button.setOnTouchListener(null);
    }

    public void activeOnTouchListener(RelativeLayout layout){
        createOnTouchListener(layout);
        button.setOnLongClickListener(null);
    }

    public void createDataFromPopup(Activity activity, Intent intent){
        int dbNumber;
        int wordNumber;
        int bitNumber;

        dbNumber = Integer.parseInt(intent.getStringExtra(activity.getResources().getString(R.string.extraDbNumberStatus)));
        wordNumber = Integer.parseInt(intent.getStringExtra(activity.getResources().getString(R.string.extraWordNumberStatus)));
        bitNumber = Integer.parseInt(intent.getStringExtra(activity.getResources().getString(R.string.extraBitNumberStatus)));
        setStatusDataBlock(new DataBlockBool(dbNumber,wordNumber, new byte[1], bitNumber));

        dbNumber = Integer.parseInt(intent.getStringExtra(activity.getResources().getString(R.string.extraDbNumberCommandOn)));
        wordNumber = Integer.parseInt(intent.getStringExtra(activity.getResources().getString(R.string.extraWordNumberCommandOn)));
        bitNumber = Integer.parseInt(intent.getStringExtra(activity.getResources().getString(R.string.extraBitNumberCommandOn)));
        setCommandOnDataBlock(new DataBlockBool(dbNumber,wordNumber, new byte[1], bitNumber));

        dbNumber = Integer.parseInt(intent.getStringExtra(activity.getResources().getString(R.string.extraDbNumberCommandOff)));
        wordNumber = Integer.parseInt(intent.getStringExtra(activity.getResources().getString(R.string.extraWordNumberCommandOff)));
        bitNumber = Integer.parseInt(intent.getStringExtra(activity.getResources().getString(R.string.extraBitNumberCommandOff)));
        setCommandOffDataBlock(new DataBlockBool(dbNumber,wordNumber, new byte[1], bitNumber));
    }

}
