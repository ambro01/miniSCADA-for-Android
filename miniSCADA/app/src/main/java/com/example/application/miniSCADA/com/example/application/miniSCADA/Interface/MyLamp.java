package com.example.application.miniSCADA.com.example.application.miniSCADA.Interface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.application.miniSCADA.Globals;
import com.example.application.miniSCADA.PLC.DataBlockBool;
import com.example.application.miniSCADA.PopupStatus;
import com.example.application.miniSCADA.PopupStatusCommand;
import com.example.application.miniSCADA.R;

public class MyLamp extends DiscreteElement{
    private transient ImageView image;

    public MyLamp(Activity activity, DataBlockBool statusDataBlock, int x, int y, int height, int width){
        super(statusDataBlock,x,y,height,width);
        image = new ImageView(activity);
        image.setBackgroundColor(Color.RED);
    }

    public void reCreateElement(Activity activity){
        this.image = new ImageView(activity);
        this.updateTrueFalseImage(activity);

        updatePositionToElement();
        updateSizeToElement();
    }

    public void updateTrueFalseImage(Context context){
        if (this.getStatus()) {
            image.setBackground(Globals.loadImageToDrawable(context, this.getOnTrueImage()));
        } else {
            image.setBackground(Globals.loadImageToDrawable(context, this.getOnFalseImage()));
        }
    }

    public void updatePositionToElement(){
        image.setX(this.getPositionX());
        image.setY(this.getPositionY());
    }

    public void updatePositionFromElement(){
        this.setPosition((int)image.getX(), (int)image.getY());
    }

    public void updateSizeToElement(){
        image.setMinimumHeight(this.getHeight());
        image.setMinimumWidth(this.getWidth());
    }

    public void updateSizeFromElement(){
        this.setSize(image.getHeight(), image.getWidth());
    }

    public void drawObject(RelativeLayout layout){
        layout.addView(image);
    }

    public void createOnTouchListener(final RelativeLayout layout){
        image.setOnTouchListener(new View.OnTouchListener() {
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
                        image.setLayoutParams(params);
                        break;
                }
                return true;
            }
        });
    }

    public void createOnLongClickListener(final Activity activity, final Develop develop){
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                develop.setActiveElement(MyLamp.this);
                Intent startPopup = new Intent(activity, PopupStatus.class);
                startPopup.putExtra(activity.getResources().getString(R.string.extraStatusDataBlock),getStatusDataBlock());
                activity.setResult(Activity.RESULT_OK,startPopup);
                activity.startActivityForResult(startPopup,1);
                return true;
            }
        });
    }

    public void createOnClickListener(Activity activity, Runtime runtime, String ip){
        //nothing to do
    }

    public void activeOnLongClickListener(Activity activity, Develop develop){
        createOnLongClickListener(activity, develop);
        image.setOnTouchListener(null);
        image.setOnClickListener(null);
    }

    public void activeOnTouchListener(RelativeLayout layout){
        createOnTouchListener(layout);
        image.setOnLongClickListener(null);
        image.setOnClickListener(null);
    }

    public void activeOnDeleteClickListener(Activity activity, final Develop develop){
        final Element element = (Element) this;
        image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                image.setBackground(null);
                develop.getVisualisation().deleteElement(element);
            }
        });
        image.setOnTouchListener(null);
        image.setOnLongClickListener(null);
    }

    public void createDataFromPopup(Activity activity, Intent intent){
        int dbNumber;
        int wordNumber;
        int bitNumber;

        dbNumber = Integer.parseInt(intent.getStringExtra(activity.getResources().getString(R.string.extraDbNumberStatus)));
        wordNumber = Integer.parseInt(intent.getStringExtra(activity.getResources().getString(R.string.extraWordNumberStatus)));
        bitNumber = Integer.parseInt(intent.getStringExtra(activity.getResources().getString(R.string.extraBitNumberStatus)));
        setStatusDataBlock(new DataBlockBool(dbNumber,wordNumber, new byte[1], bitNumber));

    }
}
