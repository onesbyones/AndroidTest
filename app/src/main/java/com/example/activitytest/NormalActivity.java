package com.example.activitytest;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Path;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * 用于学习：活动的生命周期
 *
 * 当前Activity包含了：TextView、EditText、Button、ImageView、ProgressBar、AlertDialog、ProgressDialog等控件测试
 * 控件不需要注册，但需要创建并监听才能触发事件
 */

public class NormalActivity extends BaseActivity implements View.OnClickListener {
    private static String TAG = "NormalActivity";
    private EditText editText;
    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.normal_layout);

        Button button1 = (Button) super.findViewById(R.id.normal_layout_button_1);
        button1.setOnClickListener(this);

        editText = (EditText)super.findViewById(R.id.normal_editText1);

        imageView = (ImageView)super.findViewById(R.id.normal_imageView1);

        //怎么判断数据加载完成，然后进度条消失掉？
        progressBar = (ProgressBar) super.findViewById(R.id.normal_prosBar1);

        Button button2 = (Button) super.findViewById(R.id.normal_button2);
        button2.setOnClickListener(this);

        Button button3 = (Button) super.findViewById(R.id.normal_button3);
        button3.setOnClickListener(this);
    }

    //不使用匿名内部类的方式来监听点击事件
    @Override
    public void onClick(View v){
        //根据button的唯一标识来判断
        switch (v.getId()){
            case R.id.normal_layout_button_1:
//                Toast.makeText(this, "实现View.OnClickListener接口来监听按钮点击事件", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(NormalActivity.this, SecondActivity.class);
//                startActivity(intent);
                /*点击按钮获取EditText中的文本*/
                String inputStr = editText.getText().toString();
                Toast.makeText(this, "您输入的文本：" + inputStr, Toast.LENGTH_LONG).show();
                /*点击按钮更换图片*/
//                imageView.setImageResource(R.drawable.img2);
                /*进度条是否可见设置*/
//                if(progressBar.getVisibility() == View.GONE){
//                    //设置进度条是否可见
//                    progressBar.setVisibility(View.VISIBLE);
//                }else{
//                    //设置进度条是否可见
//                    progressBar.setVisibility(View.GONE);
//                }
                /*进度条进度配置：按钮点一次进度增加一点*/
//                int progress = progressBar.getProgress();
//                progress = progress + 5;
//                progressBar.setProgress(progress);
                break;
            case R.id.normal_button2:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("this is a alertDialog test");
                dialog.setMessage("Something important");
                //配置：告警框不允许通过back按钮取消掉
                dialog.setCancelable(false);
                //配置OK按钮的监听事件
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(NormalActivity.this, "click OK", Toast.LENGTH_SHORT).show();
                    }
                });
                //配置cancel按钮的监听事件
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(NormalActivity.this, "click Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
                break;
            case R.id.normal_button3:
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("this is a progressDialog test");
                progressDialog.setMessage("loading...");
                //配置：progressDialog可以通过back取消
                progressDialog.setCancelable(true);
                progressDialog.show();
                break;
            default:
                break;
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
