package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainActivity2 implements IXposedHookLoadPackage {


    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
            XposedBridge.log("Starting the gboard");

       Class<?> keyboard =  XposedHelpers.findClass("com.google.android.libraries.inputmethod.keyboard.impl.KeyboardViewHolder",loadPackageParam.classLoader);
       XposedHelpers.findAndHookMethod(keyboard, "addView", View.class,int.class,ViewGroup.LayoutParams.class, new XC_MethodHook() {
           @Override
           protected void afterHookedMethod(MethodHookParam param) throws Throwable {
               super.afterHookedMethod(param);
//               XposedBridge.log("Making it transparent");
               View view = (View) param.args[0];
               if( ((ViewGroup)view).getChildCount() ==2)  view.setAlpha(0.0f);


           }//fter hok
       });//find and hook method
    }//handleLoadPackage
}