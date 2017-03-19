package com.example.application.miniSCADA.com.example.application.miniSCADA.Interface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.application.miniSCADA.Globals;


public class StaticElement extends Element{

    private transient ImageView image;
    private String imageName;

    public StaticElement(Activity activity, int x, int y, int height, int width){
        super(x,y,height,width);
        image = new ImageView(activity);
    }

    public void setImageName(String name){
        imageName = name;
    }

    public void reCreateElement(Activity activity){
        this.image = new ImageView(activity);
        this.updateTrueFalseImage(activity);

        updatePositionToElement();
        updateSizeToElement();
    }

    public void updateTrueFalseImage(Context context){
        image.setBackground(Globals.loadImageToDrawable(context, imageName));
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

    public void createOnLongClickListener(Activity activity, Develop develop){
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //nothing to do
                return true;
            }
        });
    }

    public void activeOnLongClickListener(Activity activity, Develop develop){
        createOnLongClickListener(activity, develop);
        image.setOnTouchListener(null);
    }

    public void activeOnTouchListener(RelativeLayout layout){
        createOnTouchListener(layout);
        image.setOnLongClickListener(null);
    }

    public void createDataFromPopup(Intent intent){
        //nothing to do
    }

}
