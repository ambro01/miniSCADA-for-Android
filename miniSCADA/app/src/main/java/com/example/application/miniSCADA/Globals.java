package com.example.application.miniSCADA;


import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import Moka7.S7Client;

public class Globals {
   public static S7Client s7client = new S7Client();
   public static final int posX = 50;
   public static final int posY = 30;

   public static void loadImage (Context context, ImageView image, String name){
      // load image
      try {
         // get input stream
         InputStream ims = context.getAssets().open(name);
         // load image as Drawable
         Drawable d = Drawable.createFromStream(ims, null);
         // set image to ImageView
         image.setImageDrawable(d);
      }
      catch(IOException ex) {
         return;
      }

   }
}
