package com.example.application.miniSCADA;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;
import Moka7.S7Client;

public class Globals {
   public static S7Client s7client = new S7Client();
   public static DisplayMetrics displayMetrics;
   public static final int posX = 450;
   public static final int posY = 150;
   public static final int buttonHeight = 50;
   public static final int buttonWidth = 100;
   public static final int circleDiameter = 50;
   public static final int elementSide = 100;
<<<<<<< HEAD
   public static final int analogHeight = 25;
   public static final int analogWidth = 80;
=======
   public static final int popupHeight = 200;
   public static final int popupWidth = 400;
>>>>>>> 78fea71660cc1a62ca2ca6efd4e809b224f1afc2

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

   public static int dptoPx(int dp){
      return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
   }

}
