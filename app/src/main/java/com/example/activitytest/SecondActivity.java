package com.example.activitytest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends BaseActivity {

    private static String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //创建活动
        super.onCreate(savedInstanceState);
        //加载布局
        super.setContentView(R.layout.second_layout);
        //给Activity中的按钮添加一个监听并返回一个Toast信息
        Button button_2 = (Button) super.findViewById(R.id.button_2);
        button_2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        Toast.makeText(SecondActivity.this, "you click button_2", Toast.LENGTH_SHORT).show();
                        // 隐式Intent，切换Activity-->通过调用系统浏览器访问URL
                        Intent intent1 = new Intent(Intent.ACTION_VIEW);
                        intent1.setData(Uri.parse("http://www.baidu.com"));
                        startActivity(intent1);
//                        销毁当前的Activity
//                        finish();
                    }
                }
        );

        Button button_2_1 = (Button) super.findViewById(R.id.button_2_1);
        button_2_1.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Toast.makeText(SecondActivity.this, "you click button_2_1", Toast.LENGTH_SHORT).show();
                        // 隐式Intent -->调用系统拨号的Activity
                        Intent intent1 = new Intent(Intent.ACTION_DIAL);
                        intent1.setData(Uri.parse("tel:10086"));
                        startActivity(intent1);
                    }
                }
        );

        //用于启动ThirdActivity
        Button button_2_2 = (Button) super.findViewById(R.id.button_2_2);
        button_2_2.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                        //startActivityForResult()启动intent后，如果intent被销毁掉了则会回调onActivityResult()方法
                        startActivityForResult(intent, 1);
                    }

                }
        );

        //用于调试：在任意的活动上退出应用程序
        Button button_2_3 = (Button)super.findViewById(R.id.button_2_3);
        button_2_3.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        ActivityCollector.finishAllActivity();
                    }
        });

    }


    /**
     * 用于接收ThirdActivity返回给SecondActivity的数据
     * @param requestCode 确定数据来源：判断是不是从ThirdActivity中返回的
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    String returnData = data.getStringExtra("data_return");
                    Log.d("SecondeActivity", returnData);
                }
                break;
            default:
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
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
    /**end-当前活动的所有生命状态-end*/
}
