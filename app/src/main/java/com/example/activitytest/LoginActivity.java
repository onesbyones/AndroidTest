package com.example.activitytest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**用于调试：强制用户下线功能。即通过广播的方式，强制下线--->调试时，需要把当前活动设置为启动页
 * Created by hqh on 2017/12/5.
 */

public class LoginActivity extends BaseActivity {

    private EditText accountText;
    private EditText passwordText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        super.setContentView(R.layout.login_layout);

        accountText = (EditText) super.findViewById(R.id.account);
        passwordText = (EditText) super.findViewById(R.id.password);
        loginButton = (Button) super.findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断账号、密码输入框的内容
                if (accountText.getText().toString().equalsIgnoreCase("admin") &&
                        passwordText.getText().toString().equalsIgnoreCase("1234")){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("LoginActivity", "onResume");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d("LoginActivity", "onPause");
    }
}
