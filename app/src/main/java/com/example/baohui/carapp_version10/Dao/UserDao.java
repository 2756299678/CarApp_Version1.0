package com.example.baohui.carapp_version10.Dao;

import android.content.Context;
import android.database.Cursor;

import com.example.baohui.carapp_version10.util.DBHelper;

/**
 * Created by BaoHui on 2018/6/28.
 */

public class UserDao {

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
            Cursor cursor = dbheaper.query("user");
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
     * 添加用户
     * @param username
     * @param password
     * @param context
     */
    public  void adduser(String username,String password, Context context)
    {
        // 数据库服务
        DBHelper dbheaper = new DBHelper(context);
        // 插入到book
        try{
            dbheaper.executeSQL(String.format("insert into user(username,password) values('%s','%s')", username,password));
            System.out.println("用户添加成功！");
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("用户添加失败！");
        }
        finally {
            dbheaper.close();
        }
    }

    /**
     * 更新用户
     * @param context
     */
    public  void updateuser(String username,String password,Context context)
    {
        // 数据库服务
        DBHelper dbheaper = new DBHelper(context);
        // 插入到book
        try{
            dbheaper.executeSQL(String.format("update user set username='%s',password='%s' where uid='%s'", username,password,1));
            System.out.println("用户修改成功！");
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("用户修改失败！");
        }
        finally {
            dbheaper.close();
        }
    }
    /**
     * 更新用户
     * @param nickname
     * @param sex
     * @param heavy
     * @param context
     */
    public  void updatecnotent(String nickname,String sex,String heavy, Context context)
    {
        // 数据库服务
        DBHelper dbheaper = new DBHelper(context);
        // 插入到book
        try{
            dbheaper.executeSQL(String.format("update user set nickname='%s',sex='%s',heavy='%s' where uid='%s'", nickname,sex,heavy,1));
            System.out.println("用户修改成功！");
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("用户修改失败！");
        }
        finally {
            dbheaper.close();
        }
    }

    /**
     * 调取用户名信息
     * @param context
     * @return
     */
    public String find_phone(Context context)
    {
        String str= "";
        DBHelper dbheaper = new DBHelper(context);
        try{
            str=dbheaper.getStringOf(String.format("select username from user where uid='%s'", 1));
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

    /**
     * 调取用户名信息
     * @param context
     * @return
     */
    public String find_nickname(Context context)
    {
        String str= "";
        DBHelper dbheaper = new DBHelper(context);
        try{
            str=dbheaper.getStringOf(String.format("select nickname from user where uid='%s'", 1));
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

    /**
     * 调取用户性别
     * @param context
     * @return
     */
    public String find_sex(Context context)
    {
        String str= "";
        DBHelper dbheaper = new DBHelper(context);
        try{
            str=dbheaper.getStringOf(String.format("select sex from user where uid='%s'", 1));
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

    /**
     * 调取用户体重
     * @param context
     * @return
     */
    public String find_heavy(Context context)
    {
        String str= "";
        DBHelper dbheaper = new DBHelper(context);
        try{
            str=dbheaper.getStringOf(String.format("select heavy from user where uid='%s'", 1));
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
