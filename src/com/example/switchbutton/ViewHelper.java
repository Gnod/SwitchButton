package com.example.switchbutton;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

public class ViewHelper {
    
    public static void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        
        if(p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int height = p.height;
        int heightSpec;
        if(height > 0) {
            heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        } else {
            heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(widthSpec, heightSpec);
    }
    
    public static void showDialog(Context c, String title, String contents,
            String positiveHint, DialogInterface.OnClickListener pListener,
            String neutralHint, DialogInterface.OnClickListener nlListener,
            String negativeHint, DialogInterface.OnClickListener nListener) {
        
        Builder builder = new AlertDialog.Builder(c);
        builder.setCancelable(false);
        if(title != null)
            builder.setTitle(title);
        if(contents != null)
            builder.setMessage(contents);
        if(positiveHint !=null)
            builder.setPositiveButton(positiveHint, pListener);
        if(neutralHint != null)
            builder.setNeutralButton(neutralHint, nlListener);
        if(negativeHint != null)
            builder.setNegativeButton(negativeHint, nListener);
        builder.show();
    }
    
    public static void showDialog(Context c, String title, 
            String positiveHint, DialogInterface.OnClickListener pListener,
            String negativeHint, DialogInterface.OnClickListener nListener){
        showDialog(c, title, null, 
                positiveHint, pListener, null, null, negativeHint, nListener);
    }
    
    public static void showDialog(Context c, String title, 
            String positiveHint, DialogInterface.OnClickListener pListener){
        showDialog(c, title, null, 
                positiveHint, pListener, null, null, null, null);
    }
    
}
