package com.example.baohui.carapp_version10.Bean;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import cn.bmob.v3.BmobUser;
/**
 * 保存用户信息管理类
 * Created by Administrator on 2018/6/7.
 */

public class UserManager {

    private static UserManager instance;

    private UserManager() {
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }


    /**
     * 保存自动登录的用户信息
     */
    public void saveUserInfo(Context context, String username, String password, String objectid) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("USER_NAME", username);
        editor.putString("PASSWORD", password);
        editor.putString("OBJECTID", objectid);
        editor.commit();
    }


    /**
     * 获取用户信息model
     *
     * @param context
     * @param
     * @param
     */
    public BmobUser getUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        BmobUser bmobUser = new BmobUser();
        bmobUser.setEmail(sp.getString("USER_NAME", ""));
        bmobUser.setPassword(sp.getString("PASSWORD", ""));
        bmobUser.setObjectId(sp.getString("OBJECTID", ""));
        return bmobUser;
    }


    /**
     * userInfo中是否有数据
     */
    public boolean hasUserInfo(Context context) {
        BmobUser bmobUser = getUserInfo(context);
        System.out.println(bmobUser.getEmail() + "检测");
        if (bmobUser != null) {
            if ((!TextUtils.isEmpty(bmobUser.getEmail()))) {//有数据
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

}
