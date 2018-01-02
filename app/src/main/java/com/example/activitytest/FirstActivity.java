package com.example.activitytest;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class FirstActivity extends BaseActivity {

    private static String TAG = "FirstActivity";
//    private ActivityManager mActivityManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //下边的父类方法会在Activity创建完成后自动增加上去
        super.onCreate(savedInstanceState);
        //给当前的Activity加载一个布局 -> 在AndroidMainfest.xml中注册当前Activity
        Log.d("FirstActivity", "setContentView");
        super.setContentView(R.layout.first_layout);


//        给Activity中的按钮添加一个监听并返回一个Toast信息
        Button button_1 = (Button) super.findViewById(R.id.button_1);
        button_1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        Toast.makeText(FirstActivity.this, "you click button_1", Toast.LENGTH_SHORT).show();
                        // 显示Intent，切换Activity
                        Intent intent1 = new Intent(FirstActivity.this, SecondActivity.class);
                        startActivity(intent1);
                        // 隐式Intent，切换到secondActivity-->指定这个action参数必须是secondActivity来完成
//                        Intent intent2 = new Intent("com.example.activitytest.activitytest");
//                        intent2.addCategory("com.example.activity.my_category");
//                        startActivity(intent2);
//                        销毁当前的Activity
//                        finish();
                    }
                }
        );

        //添加获取内存信息的按钮：
        Button button_mem = (Button) super.findViewById(R.id.button_mem);
        button_mem.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
//                        Toast.makeText(FirstActivity.this, "开始获取内存信息", Toast.LENGTH_SHORT).show();

                        Toast.makeText(FirstActivity.this,
                                "包名【com.jiuyan.infashion】所占用的内存大小为："
                                        + getMemortyInfoByPid(getProcessPid())
                                        + "KB", Toast.LENGTH_SHORT).show();
                    }
                }
        );

//        //获得ActivityManager服务的对象
//        mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);


        /*start**************活动生命周期学习代码***************start*/
        //activity转为onStop()状态调试代码
        Button startNormalActivity = (Button) super.findViewById(R.id.start_normal_activity);
        startNormalActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(FirstActivity.this, NormalActivity.class);
                startActivity(intent);
            }
        });

        //activity转为onPause()状态调试代码
        Button startDialogActivity = (Button)super.findViewById(R.id.start_dialog_activity);
        startDialogActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(FirstActivity.this, DialogActivity.class);
                startActivity(intent);
            }
        });
        /*end**************活动生命周期学习代码***************end*/
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
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
    /**end-当前活动的所有生命状态-end*/

    /**
     * 如果当前Activity中重写此方法，则会在当前活动被回收之前回调此方法来保存当前活动的所有数据
     *
     * 可以参考：63页
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //可以从outState中获取需要保存的数据
        String tempData = outState.getString("");
        outState.putString("data_key", tempData);
    }

    /**
     * @see 在当前Activity中创建菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    /**
     * @see 在当前Activity中的自定义菜单添加响应事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Toast.makeText(this, "you click Add item", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this, "you click Remove item", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Error!!", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


    /**
     * 获取指定应用进程所占用的内存信息
     */

    private int getMemortyInfoByPid(int pid){
        String totalAvalableMem = getSystemAvaialbeMemorySize();
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();


        int [] pids = {pid};

        Debug.MemoryInfo[] memoryInfoList =  mActivityManager.getProcessMemoryInfo(pids);

//        int memsize = memoryInfoList[0].dalvikPrivateDirty
        int memsize = memoryInfoList[0].getTotalPss();
        Log.i("FirstActivity",
                "包名【com.jiuyan.infashion】的 pid 【"
                        + pid + "】所占用的内存大小【" + memsize + "】KB");

        return memsize;
    }

    /**
     * @see 目前写死应用程序的包名，根据这个包名来获取pid
     * @return int pid
     */
    private int getProcessPid(){

        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processList = mActivityManager.getRunningAppProcesses();

        for(ActivityManager.RunningAppProcessInfo tempPorcess : processList){

            if (tempPorcess.processName.equalsIgnoreCase("com.jiuyan.infashion")){
                int tempPid = tempPorcess.pid;
                Log.i("FirstActivity", "包名【com.jiuyan.infashion】的 pid 【" + tempPid + "】");
                return tempPid;
            }

        }
        return -1;
    }


    //获得系统可用内存信息
    private String getSystemAvaialbeMemorySize(){
        ActivityManager mActivityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        //获得MemoryInfo对象
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo() ;
        //获得系统可用内存，保存在MemoryInfo对象上
        mActivityManager.getMemoryInfo(memoryInfo) ;
        String memSize = Formatter.formatFileSize(FirstActivity.this, memoryInfo.availMem);
        Log.i("FirstActivity", "系统可用内存大小【" + memSize + "】");
        return memSize;
        }


}
