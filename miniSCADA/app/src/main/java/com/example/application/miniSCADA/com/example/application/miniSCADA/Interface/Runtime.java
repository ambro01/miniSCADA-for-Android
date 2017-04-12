package com.example.application.miniSCADA.com.example.application.miniSCADA.Interface;

import android.app.Activity;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Runtime {
    private Visualisation visualisation;
    private Element activeElement;

    public void Runtime(){
        visualisation = null;
    }

    public void setVisualisation(Visualisation visualisation){
        this.visualisation = visualisation;
    }

    public Visualisation getVisualisation(){
        return visualisation;
    }

    public void setActiveElement(Element element){
        activeElement = element;
    }

    public Element getActiveElement(){
        return activeElement;
    }

    public void deserializeVisualisation(Activity activity, RelativeLayout layout, String name){
        File file = new File(activity.getFilesDir() + "/" + name + ".ser");
        if(file.exists()) {
            try{
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                visualisation = (Visualisation) in.readObject();
                in.close();
                fileIn.close();
                refreshBackgroundColor(layout);
                reCreateElementsAfterProjectOpen(activity,layout);
            }catch (IOException e){
                e.printStackTrace();
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    public void refreshBackgroundColor(RelativeLayout layout){
        layout.setBackgroundColor(visualisation.getBackgrondColor());
    }

    public void reCreateElementsAfterProjectOpen(Activity activity, RelativeLayout layout){
        for(Element element : visualisation.getElements()){
            if(element instanceof MyButton){
                MyButton myButton = (MyButton) element;
                myButton.reCreateElement(activity);
                myButton.updateTrueFalseImage(activity);
                myButton.drawObject(layout);
                myButton.createOnClickListener(activity, this, visualisation.getIpAddress());
            } else if(element instanceof MyLamp){
                MyLamp myLamp = (MyLamp) element;
                myLamp.reCreateElement(activity);
                myLamp.updateTrueFalseImage(activity);
                myLamp.drawObject(layout);
                myLamp.createOnClickListener(activity, this, visualisation.getIpAddress());
            } else if(element instanceof StaticElement) {
                StaticElement staticElement = (StaticElement) element;
                staticElement.reCreateElement(activity);
                staticElement.updateTrueFalseImage(activity);
                staticElement.drawObject(layout);
                staticElement.createOnClickListener(activity, this, visualisation.getIpAddress());
            } else if(element instanceof AnalogDisplay) {
                if(element instanceof AnalogInput){
                    AnalogInput analogInput = (AnalogInput) element;
                    analogInput.reCreateElement(activity);
                    analogInput.drawObject(layout);
                    analogInput.createOnClickListener(activity, this);
                } else {
                    AnalogDisplay analogDisplay = (AnalogDisplay) element;
                    analogDisplay.reCreateElement(activity);
                    analogDisplay.drawObject(layout);
                    analogDisplay.createOnClickListener(activity, this, visualisation.getIpAddress());
                }
            }
            else if(element instanceof Label){
                Label label = (Label) element;
                label.reCreateElement(activity);
                label.drawObject(layout);
                label.createOnClickListener(activity, this, visualisation.getIpAddress());
            }
        }
    }
}
