package com.example.baohui.carapp_version10.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baohui.carapp_version10.R;
import com.example.baohui.carapp_version10.Bean.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {
    private EditText etPhoneNumber;
    private EditText etPassword;
    private EditText etPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Bmob.initialize(this, "f9987278ef15f8430aa510ca5ba93f87");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //点击注册按钮，跳转到登录界面
    protected void Logon_Click(View view)
    {
        final Intent intent=new Intent();
        intent.setClass(RegisterActivity.this,LoginMainActivity.class);

        etPhoneNumber=(EditText)findViewById(R.id.etPhoneNumber);
        etPassword=(EditText)findViewById(R.id.etPassword);
        etPassword2=(EditText)findViewById(R.id.etPassword2);


        if(etPhoneNumber.length()!=11)
        {
            Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
        }
        else if(etPassword.length()<6)
        {
            Toast.makeText(getApplicationContext(), "请输入密码（长度大于六位）", Toast.LENGTH_SHORT).show();
        }
        else if(!((etPassword2.getText().toString()).equals(etPassword.getText().toString())))
        {
            Toast.makeText(getApplicationContext(), "确认密码应与密码保持一致", Toast.LENGTH_SHORT).show();
        }
        else
        {
            User u = new User();
            u.setUsername(etPhoneNumber.getText().toString());
            u.setPassword(etPassword.getText().toString());
            u.setConnect_phone("1008611");

            u.signUp(new SaveListener<BmobUser>() {
                @Override
                public void done(BmobUser bmobUser, BmobException e) {
                    if(e==null) {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

}
