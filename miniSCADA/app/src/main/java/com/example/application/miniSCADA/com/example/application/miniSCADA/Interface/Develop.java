package com.example.application.miniSCADA.com.example.application.miniSCADA.Interface;


import android.app.Activity;

import android.widget.RelativeLayout;

import com.example.application.miniSCADA.PLC.DataBlockBool;
import com.example.application.miniSCADA.Globals;
import com.example.application.miniSCADA.PLC.DataBlockReal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Develop {
    private Visualisation visualisation;
    private Element activeElement;

    public void Develop(){
        visualisation = null;
        activeElement = null;
    }

    public void setActiveElement(Element element){
        activeElement = element;
    }

    public Element getActiveElement(){
        return activeElement;
    }

    public void setVisualisation(Visualisation visu){
        visualisation = visu;
    }

    public Visualisation getVisualisation(){
        return visualisation;
    }

    //------------METHODS TO SERIALIZE AND DESERIALIZE

    public void serializeVisualisation(Activity activity){
        File file = new File(activity.getFilesDir() + "/" + visualisation.getName() + ".ser");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try{
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(visualisation);
            out.close();
            fileOut.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
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

    public void updatePositionsBeforeSaving(){
        for(Element element : visualisation.getElements()){
            element.updatePositionFromElement();
        }
    }

    public void updateSizesBeforeSaving(){
        for(Element element : visualisation.getElements()){
            element.updateSizeFromElement();
        }
    }

    public void reCreateElementsAfterProjectOpen(Activity activity, RelativeLayout layout){
        for(Element element : visualisation.getElements()){
            if(element instanceof MyButton){
                MyButton myButton = (MyButton) element;
                myButton.reCreateElement(activity);
                myButton.updateTrueFalseImage(activity);
                myButton.drawObject(layout);
                myButton.createOnTouchListener(layout);
            } else if(element instanceof MyLamp){
                MyLamp myLamp = (MyLamp) element;
                myLamp.reCreateElement(activity);
                myLamp.updateTrueFalseImage(activity);
                myLamp.drawObject(layout);
                myLamp.createOnTouchListener(layout);
            } else if(element instanceof StaticElement) {
                StaticElement staticElement = (StaticElement) element;
                staticElement.reCreateElement(activity);
                staticElement.updateTrueFalseImage(activity);
                staticElement.drawObject(layout);
                staticElement.createOnTouchListener(layout);
            } else if(element instanceof AnalogDisplay) {
                if(element instanceof AnalogInput){
                    AnalogInput analogInput = (AnalogInput) element;
                    analogInput.reCreateElement(activity);
                    analogInput.drawObject(layout);
                    analogInput.createOnTouchListener(layout);
                } else {
                    AnalogDisplay analogDisplay = (AnalogDisplay) element;
                    analogDisplay.reCreateElement(activity);
                    analogDisplay.drawObject(layout);
                    analogDisplay.createOnTouchListener(layout);
                }
            }
            else if(element instanceof Label){
                Label label = (Label) element;
                label.reCreateElement(activity);
                label.drawObject(layout);
                label.createOnTouchListener(layout);
            }
        }
    }

    public void refreshBackgroundColor(RelativeLayout layout){
        layout.setBackgroundColor(visualisation.getBackgrondColor());
    }

    public void createDiscreteControlFromName(Activity activity, RelativeLayout layout, String itemName){
        if(!itemName.isEmpty()){
            switch (itemName) {
                case "Button":
                    defaultButtonCreate(activity, layout, itemName);
                    break;
                case "Lamp":
                    defaultLampCreate(activity, layout, itemName);
                    break;
                default:
                    break;
            }
        }
    }

    public void createAnalogControlFromName(Activity activity, RelativeLayout layout, String itemName){
        if(!itemName.isEmpty()){
            switch (itemName) {
                case "Analog Input":
                    defaultAnalogInputCreate(activity, layout);
                    break;
                case "Analog Display":
                    defaultAnalogDisplayCreate(activity, layout);
                    break;
                case "Label":
                    defaultLabelCreate(activity, layout);
                    break;
                default:
                    break;
            }
        }
    }

    public void createStaticElementFromName(Activity activity, RelativeLayout layout, String itemName){
        if(!itemName.isEmpty()){
            defaultStaticElementCreate(activity, layout, itemName);
        }
    }

    public void defaultButtonCreate(Activity activity, RelativeLayout layout, String name){
        byte[] data = new byte[1];
        DataBlockBool statusDataBlock = new DataBlockBool(0,0,data,0);
        DataBlockBool commandOnDataBlock = new DataBlockBool(0,0,data,1);
        DataBlockBool commandOffDataBlock = new DataBlockBool(0,0,data,2);
        MyButton myButton = new MyButton(activity, statusDataBlock, Globals.dptoPx(Globals.posX), Globals.dptoPx(Globals.posY),
                Globals.dptoPx(Globals.buttonHeight), Globals.dptoPx(Globals.buttonWidth), commandOnDataBlock, commandOffDataBlock);
        myButton.setTextOnFalse("Turn ON");
        myButton.setTextOnTrue("Turn OFF");

        myButton.setOnTrueImage(name + "1.png");
        myButton.setOnFalseImage(name + "0.png");

        myButton.updateTrueFalseImage(activity);
        myButton.updatePositionToElement();
        myButton.updateSizeToElement();
        myButton.drawObject(layout);
        myButton.createOnTouchListener(layout);
        visualisation.addElement(myButton);
    }

    public void defaultLampCreate(Activity activity,RelativeLayout layout, String name){
        byte[] data = new byte[1];
        DataBlockBool statusDataBlock = new DataBlockBool(0,0,data,0);
        MyLamp mylamp = new MyLamp(activity, statusDataBlock, Globals.dptoPx(Globals.posX), Globals.dptoPx(Globals.posY),
                Globals.dptoPx(Globals.circleDiameter), Globals.dptoPx(Globals.circleDiameter));

        mylamp.setOnTrueImage(name + "1.png");
        mylamp.setOnFalseImage(name + "0.png");

        mylamp.updateTrueFalseImage(activity);
        mylamp.updatePositionToElement();
        mylamp.updateSizeToElement();
        mylamp.drawObject(layout);
        mylamp.createOnTouchListener(layout);
        visualisation.addElement(mylamp);
    }

    public void defaultStaticElementCreate(Activity activity, RelativeLayout layout, String name){
        StaticElement staticElement = new StaticElement(activity, Globals.dptoPx(Globals.posX), Globals.dptoPx(Globals.posY),
                Globals.dptoPx(Globals.elementSide), Globals.dptoPx(Globals.elementSide));

        staticElement.setImageName(name + "0.png");

        staticElement.updateTrueFalseImage(activity);
        staticElement.updatePositionToElement();
        staticElement.updateSizeToElement();
        staticElement.drawObject(layout);
        staticElement.createOnTouchListener(layout);
        visualisation.addElement(staticElement);
    }

    public void defaultAnalogInputCreate(Activity activity, RelativeLayout layout){
        byte[] data = new byte[4];
        DataBlockReal dataBlock = new DataBlockReal(0,0,data);

        AnalogInput analogInput = new AnalogInput(activity,dataBlock, Globals.dptoPx(Globals.posX), Globals.dptoPx(Globals.posY),
                Globals.dptoPx(Globals.analogHeight), Globals.dptoPx(Globals.analogWidth));

        analogInput.updatePositionToElement();
        analogInput.updateSizeToElement();
        analogInput.drawObject(layout);
        analogInput.createOnTouchListener(layout);
        visualisation.addElement(analogInput);
    }

    public void defaultAnalogDisplayCreate(Activity activity, RelativeLayout layout){
        byte[] data = new byte[4];
        DataBlockReal dataBlock = new DataBlockReal(0,0,data);

        AnalogDisplay analogDisplay = new AnalogDisplay(activity,dataBlock, Globals.dptoPx(Globals.posX), Globals.dptoPx(Globals.posY),
                Globals.dptoPx(Globals.analogHeight), Globals.dptoPx(Globals.analogWidth));

        analogDisplay.updatePositionToElement();
        analogDisplay.updateSizeToElement();
        analogDisplay.drawObject(layout);
        analogDisplay.createOnTouchListener(layout);
        visualisation.addElement(analogDisplay);
    }

    public void defaultLabelCreate(Activity activity, RelativeLayout layout){
        Label label = new Label(activity, Globals.dptoPx(Globals.posX), Globals.dptoPx(Globals.posY),
                Globals.dptoPx(Globals.analogHeight), Globals.dptoPx(Globals.analogWidth));

        label.updatePositionToElement();
        label.updateSizeToElement();
        label.drawObject(layout);
        label.createOnTouchListener(layout);
        visualisation.addElement(label);
    }
}
