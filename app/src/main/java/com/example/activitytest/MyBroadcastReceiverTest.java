package com.example.activitytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**自定义广播调试代码：
 *
 * 有序广播消息，是可以被优先级搞的接收器截断，所以可以在AndroidManifest.xml中的receiver --> intent-filter标签中设置优先级：
 *
 * android:priority="100"
 *
 * 如：
 *        <receiver
             android:name=".MyBroadcastReceiverTest"
             android:enabled="true"
             android:exported="true">
             <intent-filter android:priority="100">
                <action android:name="com.example.activitytest.broadcast.MY_BROADCAST"/>
             </intent-filter>
          </receiver>
 */

/**
 * 自定义广播发送在：ThirdActivity.java中
 */

public class MyBroadcastReceiverTest extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "自定义广播接收器调试", Toast.LENGTH_SHORT).show();
        //如果需要截断有序广播(这样优先级低的接收器就收不到这种类型的广播了)，则可以使用代码：
//        abortBroadcast();
    }
}
