package com.example.baohui.carapp_version10.Bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by BaoHui on 2018/6/18.
 */

public class User extends BmobUser {

    private boolean sex;
    private String connect_phone;
    private String pickname;
    private double heavy;

    public User(){}

    public String getPickname() {
        return pickname;
    }

    public void setPickname(String pickname) {
        this.pickname = pickname;
    }

    public double getHeavy() {
        return heavy;
    }

    public void setHeavy(double heavy) {
        this.heavy = heavy;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getConnect_phone() {
        return connect_phone;
    }

    public void setConnect_phone(String connect_phone) {
        this.connect_phone = connect_phone;
    }
}
