package com.example.activitytest;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.CpuUsageInfo;
import android.os.Debug;
import android.text.format.Formatter;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Administrator on 2017/8/31.
 */
@SuppressLint("NewApi")
public class MyAccessibility  extends AccessibilityService{
        private static final String TAG = "MyAccessibility";
        String [] PACKAGES = {"com.jiuyan.infashion"};
        @Override
        protected void onServiceConnected() {
            Log.i(TAG, "config success!");
            AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
            accessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
            accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
            accessibilityServiceInfo.notificationTimeout = 1000;
            setServiceInfo(accessibilityServiceInfo);
            }

        @SuppressLint("newapi")
        @Override
        public void onAccessibilityEvent(AccessibilityEvent event){
            int eventType = event.getEventType();
            String eventText = "";

            switch (eventType){
                case AccessibilityEvent.TYPE_VIEW_CLICKED:
                    Log.i(TAG, "==============Start====================");
                    eventText = "TYPE_VIEW_CLICKED";
                    AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
                    if (nodeInfo != null){
                        List<AccessibilityNodeInfo> nodeList = nodeInfo.findAccessibilityNodeInfosByViewId("com.jiuyan.infashion:id/");
                        nodeInfo.recycle();
                        for (AccessibilityNodeInfo temp : nodeList){
                            Log.i(TAG, "控件ID：" + temp.getViewIdResourceName());
                        }
                    }
                    //获取控件的text属性值
                    List<CharSequence> textSequence =  event.getText();
                    if(!textSequence.isEmpty()){
                        for (CharSequence temp: textSequence) {
                            Log.i(TAG, "控件文本：" + temp.toString());
                        }
                    }
                    Log.i(TAG, "包名【com.jiuyan.infashion】所占用的内存大小为：" + getMemortyInfoByPid(getProcessPid()) + "M");
                    Log.i(TAG, "CPU使用率：" + DataUtil.getCpuInfo());
                    Log.i(TAG, "=============end=====================");
                    break;
                case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                    eventText = "type_view_text_changed";
                    break;
            }

        }

        @Override
        public void onInterrupt(){

        }

        /**
         * 打印当前可用的控件
         */
        public void recycle(AccessibilityNodeInfo info) {
            if (info.getChildCount() == 0) {
                Log.i(TAG, "child widget----------------------------" + info.getClassName());
                Log.i(TAG, "showDialog:" + info.canOpenPopup());
                Log.i(TAG, "Text：" + info.getText());
                Log.i(TAG, "windowId:" + info.getWindowId());
            } else {
                for (int i = 0; i < info.getChildCount(); i++) {
                    if(info.getChild(i)!=null){
                        recycle(info.getChild(i));
                    }
                }
            }
        }

        /**
         * 获取进程ID
         */
        private int getProcessPid(){

            ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningServiceInfo> processList = mActivityManager.getRunningServices(Integer.MAX_VALUE);
            if(!processList.isEmpty()){
                for(ActivityManager.RunningServiceInfo tempPorcess : processList){
                    Log.d(TAG, "进程名：" + tempPorcess.service.getPackageName());
                    if ("com.jiuyan.infashion".equalsIgnoreCase(tempPorcess.service.getPackageName())){
                        int pid = tempPorcess.pid;
                        Log.d(TAG, "进程ID：" + String.valueOf(pid));
                        return pid;
                    }
                }
            }
            return -1;
        }

        private String getSystemAvaialbeMemorySize(){
            ActivityManager mActivityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
            //获得MemoryInfo对象
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo() ;
            //获得系统可用内存，保存在MemoryInfo对象上
            mActivityManager.getMemoryInfo(memoryInfo) ;
            String memSize = Formatter.formatFileSize(MyAccessibility.this, memoryInfo.availMem);
            Log.d(TAG, "系统可用内存大小【" + memSize + "】");
            return memSize;
        }

        private int getMemortyInfoByPid(int pid){
            String totalAvalableMem = getSystemAvaialbeMemorySize();
            ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            int [] pids = {pid};
            Debug.MemoryInfo[] memoryInfoList =  mActivityManager.getProcessMemoryInfo(pids);
            int memsize = memoryInfoList[0].getTotalPss() / 1024;
            Log.d(TAG, "包名【com.jiuyan.infashion】的 pid 【" + pid + "】所占用的内存大小【" + memsize + "】M");
            return memsize;
        }

        public float getProcessCpuRate()
        {
            int pid = getProcessPid();
            float totalCpuTime1 = getTotalCpuTime();
            float processCpuTime1 = getAppCpuTime(pid);
            try
            {
                Thread.sleep(360);

            }
            catch (Exception e)
            {
            }

            float totalCpuTime2 = getTotalCpuTime();
            float processCpuTime2 = getAppCpuTime(pid);

            float cpuRate = 100 * (processCpuTime2 - processCpuTime1)
                    / (totalCpuTime2 - totalCpuTime1);

            return cpuRate;
        }

        public long getTotalCpuTime()
        { // 获取系统总CPU使用时间
            String[] cpuInfos = null;
            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        new FileInputStream("/proc/stat")), 1000);
                String load = reader.readLine();
                reader.close();
                cpuInfos = load.split(" ");
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
            long totalCpu = Long.parseLong(cpuInfos[2])
                    + Long.parseLong(cpuInfos[3]) + Long.parseLong(cpuInfos[4])
                    + Long.parseLong(cpuInfos[6]) + Long.parseLong(cpuInfos[5])
                    + Long.parseLong(cpuInfos[7]) + Long.parseLong(cpuInfos[8]);
            return totalCpu;
        }

        public long getAppCpuTime(int pid)
        { // 获取应用占用的CPU时间
            String[] cpuInfos = null;
            try
            {
//                int pid = android.os.Process.myPid();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        new FileInputStream("/proc/" + pid + "/stat")), 1000);
                String load = reader.readLine();
                reader.close();
                cpuInfos = load.split(" ");
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
            long appCpuTime = Long.parseLong(cpuInfos[13])
                    + Long.parseLong(cpuInfos[14]) + Long.parseLong(cpuInfos[15])
                    + Long.parseLong(cpuInfos[16]);
            return appCpuTime;
        }

}
