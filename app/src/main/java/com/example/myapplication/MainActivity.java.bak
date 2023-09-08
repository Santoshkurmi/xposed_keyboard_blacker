package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.FloatProperty;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Locale;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainActivity implements IXposedHookLoadPackage {

    public final String PACKAGE = "com.android.systemui";
    public final String CLASS_STATUS_BAR = PACKAGE+".statusbar.phone.StatusBar";
    public final String CLASS_STATUS_BAR_VIEW = PACKAGE+".statusbar.phone.PhoneStatusBarView";
//    public Object mStatusBar;
//    public Context mContext;
    public ViewGroup mStatusBarView;
    public TextView power;
    public LinearLayout centerLayout;
    public int number = 0;
    public int clock=0;
    public final String BATTERY_PATH = "/sys/class/power_supply/mtk-gauge/";
    public final File current_file = new File(BATTERY_PATH+"average_current_get");
//    public final File voltage_file = new File(BATTERY_PATH+"voltage_now");
    BufferedReader current_reader;
    BufferedReader voltage_reader;
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {




        if (!loadPackageParam.packageName.equals(PACKAGE)) return;
        XposedBridge.log("Founded the systemui reference");
        final Class<?> statusBarClass = XposedHelpers.findClass(CLASS_STATUS_BAR,loadPackageParam.classLoader);

        XposedBridge.hookAllMethods(statusBarClass, "makeStatusBarView", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                XposedBridge.log("I am statusbar");
                try{
//                    mStatusBar = param.thisObject;
                    Context mContext = (Context) XposedHelpers.getObjectField(param.thisObject,"mContext");

                    power = new TextView(mContext);
                    centerLayout = new LinearLayout(mContext);
                    centerLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    centerLayout.setGravity(Gravity.CENTER);
                    centerLayout.setPadding(0,25,0,0);
                    power.setTextColor(0xffffffff);
//                    centerLayout.setBackgroundColor(0x4dff0000);
                    power.setText("1234");
                    centerLayout.addView(power);
                    //handler
//                    Handler hanlder = new Handler(Looper.getMainLooper());
//                    Runnable update = new Runnable() {
//                        @Override
//                        public void run() {
//                            View parent = (View) mStatusBarView.getParent();
//                            if ( parent.getVisibility()== View.VISIBLE ){
//                                power.setText(number+"");
//                                number = number + 1;
//                                XposedBridge.log(number+"");
//                            }
//
//                            hanlder.postDelayed(this,1000);
//                        }
//                    };//update runnabla
//                    hanlder.postDelayed(update,0);



                }//try
              catch (Exception e){
                    XposedBridge.log(e.toString());
              }//catch

            }//after hook method
        });//hook all methods



        //hooking phonestatusbarview

        XposedHelpers.findAndHookMethod(CLASS_STATUS_BAR_VIEW, loadPackageParam.classLoader, "onFinishInflate", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
//                XposedBridge.log("I am statusbarview");
                mStatusBarView = (ViewGroup) param.thisObject;
                if (power!=null){
                    mStatusBarView.addView(centerLayout);
                    XposedBridge.log("Adding power in it");
                }//if not null
                else {
                    XposedBridge.log("I think it is null right now");
                }

            }//after hoooooook method
        });;//hook setBar method


//new thigs here
        XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.policy.Clock", loadPackageParam.classLoader, "updateClock", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                try{

                    current_reader = new BufferedReader(new FileReader(current_file));
//                    voltage_reader = new BufferedReader(new FileReader(voltage_file));

//                    int current = Math.abs(Integer.parseInt(current_reader.readLine()));
//                    int voltage = Integer.parseInt(voltage_reader.readLine());
//                    XposedBridge.log(current+" "+voltage);
//                    @SuppressLint("DefaultLocale") String watt =String.format("%.2f w", current*voltage/1000000.0f);

                    power.setText( current_reader.readLine().substring(0,-1)+" mah");
//                    XposedBridge.log("CLock is doing well "+clock);
                }
                catch (Exception e){
                    XposedBridge.log(e);
                }
                finally {
                    if (current_reader !=null) current_reader.close();
//                    if (voltage_reader !=null) voltage_reader.close();
                }

            }
        });//ondeatch


    }//handleLoadPackage
}