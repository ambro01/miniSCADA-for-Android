package com.example.application.miniSCADA;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import Moka7.S7Client;

public class Globals {
   public static S7Client s7client = new S7Client();
   public static final int posX = 450;
   public static final int posY = 150;
   public static final int buttonHeight = 10;
   public static final int buttonWidth = 30;

   public static void loadImage (Context context, ImageView image, String name){
      try {
         InputStream ims = context.getAssets().open(name);
         Drawable d = Drawable.createFromStream(ims, null);
         image.setImageDrawable(d);
      }
      catch(IOException ex) {
         return;
      }
   }

   public static Drawable loadImageToDrawable(Context context, String name){
      try {
         InputStream ims = context.getAssets().open(name);
         return Drawable.createFromStream(ims, null);
      }
      catch(IOException ex) {
         return null;
      }
   }

}
