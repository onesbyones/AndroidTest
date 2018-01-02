package com.example.activitytest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**用于调试：强制用户下线功能。即通过广播的方式，强制下线
 * Created by hqh on 2017/12/5.
 */

public class MainActivity extends BaseActivity {

    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.main_layout);

        this.sendButton = (Button)super.findViewById(R.id.force_offline);
        this.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.activitytest.broadcast.FORCE_OFFLINE");
                sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("MainActivity", "onResume");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d("MainActivity", "onPause");
    }
}
