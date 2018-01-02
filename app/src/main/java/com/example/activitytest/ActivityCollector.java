package com.example.activitytest;

import android.app.Activity;
import android.test.AndroidTestRunner;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hqh on 2017/12/3.
 */

public class ActivityCollector {

    public static List<Activity> list = new ArrayList<>();

    public static void addActivity(Activity activity){
        list.add(activity);
    }

    public static void removeActivity(Activity activity){
        list.remove(activity);
    }

    public static void finishAllActivity(){
        for (Activity tempActivity: list
             ) {
            if (!tempActivity.isFinishing()){
                Log.d("ActivityCollector", tempActivity.getClass().getSimpleName());
                tempActivity.finish();//这样子不会崩溃么？
            }
        }
        //销毁所有的活动后，顺便杀掉程序的进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
