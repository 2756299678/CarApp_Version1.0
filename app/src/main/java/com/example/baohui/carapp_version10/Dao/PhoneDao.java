package com.example.baohui.carapp_version10.Dao;

import android.content.Context;
import android.database.Cursor;

import com.example.baohui.carapp_version10.util.DBHelper;

/**
 * Created by BaoHui on 2018/6/28.
 */

public class PhoneDao {
    /**
     *  检测数据库中是否有存在数据
     * @param context
     * @return
     */
    public boolean checkUser_exit(Context context)
    {
        boolean  f= false;
        // 数据库服务
        DBHelper dbheaper = new DBHelper(context);
        //SQLiteDatabase db=dbheaper.getDb();
        // String sql="select username from user";
        try{
            Cursor cursor = dbheaper.query("phone");
            while(cursor.moveToNext()){
                f=true;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("验证用户名出错");
        }
        finally {
            dbheaper.close();
        }
        return f;
    }

    /**
     * 添加手机号
     * @param phonenumber
     * @param context
     */
    public  void addphone(String phonenumber, Context context)
    {
        // 数据库服务
        DBHelper dbheaper = new DBHelper(context);
        // 插入到book
        try{
            dbheaper.executeSQL(String.format("insert into phone(phone) values('%s')", phonenumber));
            System.out.println("手机号添加成功！");
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("手机号添加失败！");
        }
        finally {
            dbheaper.close();
        }
    }
    /**
     * 更新手机号
     * @param phonenumber
     * @param context
     */
    public  void updatephone(String phonenumber, Context context)
    {
        // 数据库服务
        DBHelper dbheaper = new DBHelper(context);
        // 插入到book
        try{
            dbheaper.executeSQL(String.format("update phone set phone='%s' where pid='%s'", phonenumber,1));
            System.out.println("手机号更新成功！");
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("手机号更新失败！");
        }
        finally {
            dbheaper.close();
        }
    }
    /**
     * 调取紧急联系人信息
     * @param context
     * @return
     */
    public String find_phone(Context context)
    {
        String str= "";
        DBHelper dbheaper = new DBHelper(context);
        try{
            str=dbheaper.getStringOf(String.format("select phone from phone where pid='%s'", 1));
        } catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("验证用户名出错");
        }
        finally {
            dbheaper.close();
        }
        return str;
    }
}
