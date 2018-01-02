package com.example.activitytest;

import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * Created by hqh on 2017/6/30.
 */

public class DataUtil {

    private static String PACKAGE_NAME = "com.jiuyan.infashion";
    private static String TAG = "performeceInfo";

    private static String MEM_CMD = "dumpsys meminfo " + PACKAGE_NAME + " grep \"Dalvik Heap\"";
    private static String CPU_CMD = "dumpsys cpuinfo | grep " + PACKAGE_NAME;




    public static String getCpuInfo(){
        String splitStr = " ";
        try {
            Process process = Runtime.getRuntime().exec(CPU_CMD);
            InputStreamReader ir = new InputStreamReader(
                    process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            while ((line = input.readLine()) != null) {
                // System.out.println(line);
                String tempLine = line.trim();
                if (tempLine.equalsIgnoreCase("")) {
                    continue;
                }

                String [] tempLines = tempLine.split(splitStr);
                Log.i(TAG + "result：", listToString(tempLines));
                return tempLines[6];
            }
        } catch (IOException e) {
            // TODO: handle exception
            Log.e(TAG, e.getMessage());
        }
        return "";
    }


    public static String getMemoryInfo(){

        return "";
    }

    public static String listToString(String[] strings){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (String temp : strings
             ) {
            sb.append(temp.toString() + ",");
        }
        sb.append("]");
        return sb.toString();
    }


//    public static void main(String[] args) throws IOException {
//        System.out.print("test");
//        Process process = Runtime.getRuntime().exec("ls -l");
//        InputStreamReader ir = new InputStreamReader(
//                process.getInputStream());
//        LineNumberReader input = new LineNumberReader(ir);
//        Log.i(TAG, input.readLine());
//
//    }
}
