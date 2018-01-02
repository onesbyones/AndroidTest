package com.example.activitytest;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationHandler;

/**测试输入输出流的调试代码-->调试前需要先把当前活动设置为启动页
 *
 * Created by hqh on 2017/12/6.
 */

public class FileOutputActivityTest extends BaseActivity {

    private EditText fileoutput;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        super.setContentView(R.layout.fileoutput_layout);

        this.fileoutput = (EditText)super.findViewById(R.id.file_output_edit1);

        //读取本地文件
        String tempStr = this.load();
        if (!tempStr.isEmpty()){
            this.fileoutput.setText(tempStr);
            Toast.makeText(this, "the test.txt content is: " + tempStr, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        String tempInput = this.fileoutput.getText().toString();
        this.save(tempInput);
    }

    public void save(String inputText){
        FileWriter fo = null;
        try{
            fo = new FileWriter("/mnt/sdcard/test.txt", true);
            fo.write(inputText + "\n");
        }catch (IOException e){
            Log.d("fileoutputactivity", e.getMessage());
        }finally {
            try{
                if (fo != null){
                    fo.close();
                }
            }catch (IOException e){
                Log.e("fileoutputactivity", e.getMessage());
            }


        }
    }

    public String load(){
        FileReader fr = null;
        BufferedReader br = null;
        //创建一个可以缓冲的字符串
        StringBuffer sb = new StringBuffer();
        try{
            fr = new FileReader("/mnt/sdcard/test.txt");
            br = new BufferedReader(fr);
            String tempStr = "";
            while ((tempStr = br.readLine()) !=null ){
                sb.append(tempStr);
            }
        }catch (IOException io){
            Log.e("", io.getMessage());
        } finally {
            try{
                if (fr != null){
                    fr.close();
                }
            } catch(IOException io){
                Log.e("", io.getMessage());
            }
        }
        return  sb.toString();
    }

}
