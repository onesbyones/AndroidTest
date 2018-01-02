package com.example.activitytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ThirdActivity extends BaseActivity {
    private static String TAG = "ThirdActivity";

    //本地广播管理器（只能动态注册的方式注册）
    private LocalBroadcastManager localBroadcastManager;
    //内部类：本地广播接收器
    private LocalReceiver localReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.third_layout);

        //初始化一个本地广播接收器-->否则会崩溃
        this.localBroadcastManager = LocalBroadcastManager.getInstance(this);

        Button button3 = (Button) findViewById(R.id.button_3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //data_return:intent数据传递标记位
                intent.putExtra("data_return", "Hello SecondActivity by clickListener");
                //setResult():用于把当前intent的数据返回给前一个intent
                setResult(RESULT_OK, intent);
                finish();
            }

        });

        //静态注册方式：自定义广播接收器调试代码-->系统全局广播
        Button button3_2 = (Button) super.findViewById(R.id.button3_2);
        button3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.activitytest.broadcast.MY_BROADCAST");
                //发送自定义广播(标准广播)，但是没有内容--->应该可以通过intent传递数据
                sendBroadcast(intent);
                //发送自定义广播（有序广播）-->可以被优先级高的接收器截断，所以如果有多个接收器记得配置优先级
//                sendOrderedBroadcast(intent, null);
            }
        });

        //动态注册方式：自定义本地广播接收器调试代码-->更安全，只能在当前程序中接收到
        Button button3_3 = (Button) super.findViewById(R.id.button3_3);
        button3_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建指定类型的自定义本地广播
                Intent intent = new Intent("com.example.activitytest.broadcasttest_LOCALBROADCAST");
                localBroadcastManager.sendBroadcast(intent);
            }
        });

        //动态注册方式：接收器只接收指定类型的本地广播
        IntentFilter intentFilter = new IntentFilter("com.example.activitytest.broadcasttest_LOCALBROADCAST");
        //动态注册方式：创建本地广播接收器
        this.localReceiver = new LocalReceiver();
        //动态注册方式：注册本地广播
        this.localBroadcastManager.registerReceiver(this.localReceiver, intentFilter);
    }

    /**
     * 监听系统返回键
     */
    @Override
    public void onBackPressed(){
        Intent intent = new Intent();
        //data_return:intent数据传递标记位
        intent.putExtra("data_return", "Hello SecondActivity by backButton");
        setResult(RESULT_OK, intent);
        finish();
    }


    /**
     * 内部类：动态注册，用于接收自定义本地广播
     */
    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            Toast.makeText(context, "自定义本地广播", Toast.LENGTH_SHORT).show();
        }
    }

    /**start-当前活动的所有生命状态-start*/
    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        //如果活动销毁了，则取消注册本地广播
        this.localBroadcastManager.unregisterReceiver(this.localReceiver);
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
    /**end-当前活动的所有生命状态-end*/
}
