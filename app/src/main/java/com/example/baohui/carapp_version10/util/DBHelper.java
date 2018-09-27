package com.example.baohui.carapp_version10.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BaoHui on 2018/6/14.
 */
public class DBHelper extends SQLiteOpenHelper {
    //数据库名，表名
    private static final String DB_NAME="carapp.db";
    private static final String TBL_USER_NAME="user";
    private static final String TBL_phone_NAME="phone";
    private static final String TBL_health_NAME="health";
    private static final String TBL_moni_NAME="moni";



    private SQLiteDatabase db;

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }
    //创建表初始化
    private void init(){
        // 数据库操作语句
        String userSQL = "create table user(\n" +
                "   uid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "   username TEXT,\n" +
                "   password TEXT,\n" +
                "   nickname TEXT,\n" +
                "   sex TEXT,\n" +
                "   heavy TEXT\n" +
                ")";
        String phoneSQL = "create table phone(\n" +
                "   pid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "   phone TEXT\n" +
                ")";
        String healthSQL= "create table health(\n" +
                "   hid INTEGER  PRIMARY KEY AUTOINCREMENT,\n" +
                "   xinlv INTEGER NOT NULL,\n" +
                "   xueyahigh INTEGER NOT NULL,\n" +
                "   xueyalow INTEGER NOT NULL,\n" +
                "   sudu INTEGER NOT NULL,\n" +
                "   jiasudu INTEGER NOT NULL\n"+
                ")";
        String moniSQL = "create table moni(\n" +
                "   mid INTEGER  PRIMARY KEY AUTOINCREMENT,\n" +
                "   kind TEXT default 'primary'\n" +
                ")";

        // 创建表
        try{
            db.execSQL(userSQL);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("用户表创建失败");
        }
        try{
            // 书籍表
            db.execSQL(phoneSQL);

        }
        catch (Exception e){
           e.printStackTrace();
            System.out.println("联系人表创建失败");
        }
        try{
            // 种类表
            db.execSQL(healthSQL);

        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("健康表创建失败");
        }
        try{
            // 笔记表
            db.execSQL(moniSQL);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("模拟表创建失败");
        }
    }



    public DBHelper(Context context)
    {
        super(context,DB_NAME,null,1);
        db = this.getWritableDatabase();
        System.out.println("创建数据库");
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        this.db=db;
        init();
        System.out.println("创建表");
    }

    //执行方法
    public boolean executeSQL(String sql){
        try{
            db.execSQL(sql);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //删除方法
    public void del(int id,String tablename,String idname){
        System.out.println("数据库删除方法");

            System.out.println("进行删除");
            //获得dateBase实例
            SQLiteDatabase db=getWritableDatabase();
            //删除
            try{
                db.delete(tablename,idname+"=?",new String[]{String.valueOf(id)});
            }catch(Exception e)
            {
                e.printStackTrace();
            }


    }

    //关闭方法
    public void close(){
        System.out.println("数据库关闭方法");
        if(db!=null){
            db.close();
        }
    }

    //查询方法
    public Cursor query(String name){
        System.out.println("数据库查询方法");
        //获取DateBase实例
        SQLiteDatabase db=getWritableDatabase();
        Cursor c=db.query(name, null, null, null, null, null , null);
        return c;
    }

    //获取一条记录
    public String getStringOf(String sql){
        String x = "";
        try{
            Cursor cursor = db.rawQuery(sql ,null);
            while(cursor.moveToNext()){
                x = cursor.getString(0);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return x;
    }

    //获取一条记录
    public int getIntegerof(String sql){
        int i=-1;
        try{
            Cursor cursor = db.rawQuery(sql ,null);
            while(cursor.moveToNext()){
                i = cursor.getInt(0);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}
