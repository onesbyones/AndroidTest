package com.example.activitytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * 使用BaseActivity来继承AppCompatActivity，然后所有的Activity来继承BaseActivity-->这样方便管理activity
 *
 * 因为BaseActivity不需要跟用户交互，所以不需要在AndroidManifest.xml中注册
 *
 * Created by hqh on 2017/12/3.
 *
 * 增加：
 *
 * 强制下线功能调试代码
 *
 */

public class BaseActivity extends AppCompatActivity {

    private ForceOfflineReceiver offlineReceiver;

    private static String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //通过所有的活动继承BaseActivity来判断当前的活动名称
        Log.d("BaseActivity", this.getClass().getSimpleName());
        //把当前活动加入到活动管理器中，如果不把BaseActivity加入，则不能随时退出程序-->验证过
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //只为单独销毁当前活动
        ActivityCollector.removeActivity(this);
    }


    /**
     * 为了保证只有处于栈顶的活动才能接收到广播，所以在这个状态中增加：收到广播后的逻辑判断
     */
    @Override
    protected void onResume(){
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.activitytest.broadcast.FORCE_OFFLINE");
        //创建接收器实例
        offlineReceiver = new ForceOfflineReceiver();
        //注册广播接收器
        registerReceiver(offlineReceiver, intentFilter);

    }


    /**
     * 这个状态表示，已经被告警框覆盖了，活动部分可见
     */
    @Override
    protected void onPause(){
        super.onPause();
        if (offlineReceiver != null){
            unregisterReceiver(offlineReceiver);
            offlineReceiver = null;
        }

    }

    /**
     * 定义一个广播接收器：接收自定义广播。如果接收到了广播则弹出提示框
     */
    class ForceOfflineReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(final Context context, Intent intent){
            //收到广播后的弹框处理
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            //设置提示框title
            dialog.setTitle("Warning");
            dialog.setMessage("you are forced to e offline. Please try to login");
            //设置不能使用back键取消掉提示框
            dialog.setCancelable(false);
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //销毁所有活动
                    ActivityCollector.finishAllActivity();
                    //然后跳转到：LoginActivity --->实际结果是没有跳转到LoginActivity界面，原因暂时不清楚
                    Intent intent1 = new Intent(context, LoginActivity.class);
                    context.startActivity(intent1);
                }
            });
            //弹出提示框------->千万不要忘记
            dialog.show();
        }
    }

}
