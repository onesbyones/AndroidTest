package com.example.activitytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 通过静态注册方式，接收输入法改变的广播
 *
 * 注意：
 * 1、需要在AndroidManifest.xml中添加 receiver 标签
 * 2、需要在receiver标签中添加 intent-filter 标签。如：
 *         <receiver
                android:name=".NetworkChangeReceiver"
                android:enabled="true"
                 android:exported="true">
                 <intent-filter>
                 <action android:name="android.intent.action.INPUT_METHOD_CHANGED"/>
                 </intent-filter>
            </receiver>
 * 3、需要添加权限
 *
 * 4、无论何种方式的广播接收器的onReceive()方法中不要添加过多的逻辑或者进行任何耗时，广播接收器是不允许开启线程的，
 * 当onReceive()方法运行较长时间而没有结束时程序会崩溃
 */

public class InputMethodChangeReceiverTest extends BroadcastReceiver {

    //接收输入法变更的广播
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "输入法切换了", Toast.LENGTH_SHORT).show();
    }
}
