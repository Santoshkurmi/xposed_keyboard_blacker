package com.example.myapplication;

import android.view.View;
import android.view.ViewGroup;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class KeyboardHook implements IXposedHookLoadPackage {


    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
            XposedBridge.log("Starting the gboard");

       Class<?> keyboard =  XposedHelpers.findClass("com.google.android.libraries.inputmethod.keyboard.impl.KeyboardViewHolder",loadPackageParam.classLoader);
//       XposedHelpers.findAndHookMethod(keyboard, "addView", View.class,int.class,ViewGroup.LayoutParams.class, new XC_MethodHook() {
//           @Override
//           protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//               super.afterHookedMethod(param);
////               XposedBridge.log("Making it transparent");
//               View view = (View) param.args[0];
//               if( ((ViewGroup)view).getChildCount() ==2)  view.setAlpha(0.0f);
//
//
//           }//fter hok
//       });//find and hook method

        XposedBridge.hookAllMethods(keyboard, "g", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                View view = (View) param.args[2];
                if( ((ViewGroup)view).getChildCount() ==2)  view.setAlpha(0.0f);
            }
        });

    }//handleLoadPackage
}