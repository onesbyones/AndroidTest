package com.example.activitytest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

/**
 * Created by hqh on 2018/1/7.
 * <p>
 * ***********系统内容提供器测试代码************
 * <p>
 * Android6.0及以上系统，需要运行时权限。即需要用户主动授权而不是程序申请权限就有了所有的权限
 */

public class ReadContactList extends BaseActivity {

    //给布局文件中的ListView 添加适配器-->能够自动适配查找出来的结果
    ArrayAdapter<String> adapter;

    //存放 联系人
    List<String> contactsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载活动布局
        setContentView(R.layout.readcontact_layout);
        //查找 ListView
        ListView contactListView = (ListView) findViewById(R.id.read_contact_listView);
        //配置适配器
        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.contactsList);
        //给 ListView 配置适配器
        contactListView.setAdapter(this.adapter);
        //判断用户是否已经授权 程序 读取联系人
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            //如果用户授权的权限 与 应用程序获取的权限不一致，则回调onRequestPermissionsResult(), 同时弹出权限提醒框 -->参数列表：当前活动, 申请的权限列表, 请求码
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS}, 1);
        } else {
            this.readContacts();
        }

    }


    //申请权限回调函数
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                //如果授权了
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.readContacts();
                } else {
                    Toast.makeText(this, "you denied the permission!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }

    //读取联系人方法
    private void readContacts() {
        //为 内容提供器 创建查询游标
        Cursor cursor = null;
        try {
            //通过getContentResolver()获取系统内容提供器
            cursor = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    //获取联系人姓名
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    //获取联系人电话号码
                    String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    this.contactsList.add(name + "\n" + phone);
                }
                //通知刷新 ListView
                this.adapter.notifyDataSetChanged();
            }

        } catch (Exception e) {
            Log.e("异常！！", e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

}
