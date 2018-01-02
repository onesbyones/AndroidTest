package com.example.activitytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

/**注册广播接收器的方式：动态注册、静态注册（在AndroidManifest.xml中注册）
 *
 * 注意：
 * 1、如果需要调试本代码，需要把当前活动在AndroidManifest.xml中注册为启动页
 * 2、需要在AndroidManifest.xml中注册：系统网络状态权限，否则有可能会崩溃
 * 3、活动不一定在程序启动后都会创建的，所以需要手动启动或者直接注册为启动页--->自己猜测的
 *
 * Created by hqh on 2017/12/4.
 */

public class BroadcastReceiverTestActivity extends BaseActivity {
    //创建接收指定广播类型的实例
    private IntentFilter intentFilter;

    //用动态注册广播的方式，定义一个可以监听网络变化的内部类
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.broadcasttest_layout);

        //创建广播接收器实例
        networkChangeReceiver = new NetworkChangeReceiver();

        //只接收网络变化的广播
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        //注册广播
        this.registerReceiver(networkChangeReceiver, intentFilter);
    }

    /**
     * 内部类，继承了BroadcastReceiver并重写了onReceive方法。
     *
     * 通过这种注册方式，就能收到系统广播了，然后只需要过滤出需要的广播类型就可以了
     */
    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
//            Toast.makeText(context, "network changes", Toast.LENGTH_SHORT).show();
            //创建管理网络连接的系统服务类的实例，来管理网络状态
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

            //通过网络管理器获取网络状态
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            //判断网络状态
            if (networkInfo != null && networkInfo.isAvailable()){
                Toast.makeText(BroadcastReceiverTestActivity.this, "network is available", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(BroadcastReceiverTestActivity.this, "network is unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //如果活动销毁了，则取消注册广播
        this.unregisterReceiver(networkChangeReceiver);
    }
}
