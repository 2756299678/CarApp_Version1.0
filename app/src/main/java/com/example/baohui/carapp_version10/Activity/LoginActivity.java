package com.example.baohui.carapp_version10.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baohui.carapp_version10.Dao.UserDao;
import com.example.baohui.carapp_version10.R;
import com.example.baohui.carapp_version10.Bean.UserManager;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {

    private EditText etPhoneNumber;
    private EditText etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "f9987278ef15f8430aa510ca5ba93f87");
        setContentView(R.layout.activity_login);
        
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Button btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                etPhoneNumber=(EditText)findViewById(R.id.etPhoneNumber);
                etPassword=(EditText)findViewById(R.id.etPassword);

                final String username=etPhoneNumber.getText().toString();
                final String password=etPassword.getText().toString();
                if(etPhoneNumber.length()==0)
                {
                    Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
                }
                else if(etPhoneNumber.length()!=11)
                {
                    Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }
                else if(etPassword.length()==0)
                {
                    Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                }
                final BmobUser userlogin=new BmobUser();
                userlogin.setUsername(username);
                userlogin.setPassword(password);
                userlogin.login(new SaveListener<BmobUser>() {

                    public void done(BmobUser bmobUser, BmobException e) {
                        if(e==null){
                            System.out.println("登录成功"+ userlogin.getObjectId());
                            UserManager.getInstance().saveUserInfo(LoginActivity.this,username,password,userlogin.getObjectId());
                            UserDao userDao =new UserDao();
                            if(userDao.checkUser_exit(getApplicationContext()))
                            {
                                userDao.updateuser(username,password,getApplicationContext());
                            }
                            else
                            {
                                userDao.adduser(username,password,getApplicationContext());
                            }
                            Intent Intent = new Intent();
                            Intent.setClass(LoginActivity.this, MainActivity.class);
                            startActivity(Intent);
                        }else {
                            System.out.println("登录失败");
                            Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
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

    //点击登录按钮，跳转到主界面
    protected void Home_Click(View view)
    {
        Intent intent=new Intent();
        intent.setClass(LoginActivity.this,MainActivity.class);

        etPhoneNumber=(EditText)findViewById(R.id.etPhoneNumber);
        etPassword=(EditText)findViewById(R.id.etPassword);

        final String username=etPhoneNumber.getText().toString();
        final String password=etPassword.getText().toString();
        if(etPhoneNumber.length()==0)
        {
            Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
        }
        else if(etPhoneNumber.length()!=11)
        {
            Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
        }
        else if(etPassword.length()==0)
        {
            Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
        }
        else
        {
            startActivity(intent);
        }
        final BmobUser userlogin=new BmobUser();
        userlogin.setUsername(username);
        userlogin.setPassword(password);
        userlogin.login(new SaveListener<BmobUser>() {

            public void done(BmobUser bmobUser, BmobException e) {
                if(e==null){
                    System.out.println("登录成功" + userlogin.getObjectId());
                    UserManager.getInstance().saveUserInfo(LoginActivity.this,username,password,userlogin.getObjectId());
                    Intent Intent = new Intent();
                    Intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(Intent);
                }else {
                    System.out.println("登录失败");
                }
            }
        });
    }

}
