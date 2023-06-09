package com.example.two_51_64.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBManager extends SQLiteOpenHelper {

    SQLiteDatabase db;

    static String db_name = "RiddlesDB.db";
    static int db_version = 1;


    public DBManager(@Nullable Context context){
        super(context,db_name,null,db_version);
        db = getWritableDatabase();
    }


    //打开数据库
    public SQLiteDatabase openDb() {
        return db = getWritableDatabase();
    }

    //关闭数据库
    public void closeDb(){
        db.close();
    }


    //建立数据表
    public boolean createTable(String sql){
        openDb();
        try {

            db.execSQL(sql);
            closeDb();
        } catch (SQLException e) {
            closeDb();
            return false;
        }
        return true;
    }


    //  输出表的名字判断是否存在
    public boolean isExist(String tableName){
        String sql = "select * from " + tableName;
        openDb();
        try {
            db.rawQuery(sql,null);
            closeDb();
        }
        catch (Exception ex){
            closeDb();
            return false;
        }
        return true;
    }



    //  插入数据
    public boolean insertDB(String table, ContentValues cv){
        openDb();
        try {
            db.insert(table,null,cv);
            closeDb();
            return true;
        }catch (Exception ex){
            closeDb();
            return false;
        }
    }


    //  查询数据
    public Cursor queryDB(String table, String[] cols, String argwhere, String [] args, String group, String having, String order, String litmity){
        Cursor c;
        openDb();
        try {
            c = db.query(table,cols,argwhere,args,group,having,order,litmity);
        } catch (Exception e) {
            return null;
        }
        return c;
    }


    public void delTable(String tableName){
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
    }




    //  删除数据
    public boolean del(String tableName,String pages,String[] aa){
        openDb();
        try {
            db.delete(tableName,pages,aa);
            closeDb();
        }catch (Exception ex){
            closeDb();
            return false;
        }
        return true;
    }


    // 更新数据
    public boolean updata(String tablename,ContentValues cv,String id,String[] a){
        openDb();
        try {
            db.update(tablename,cv,id,a);
            closeDb();

        }catch (Exception ex){
            closeDb();
            return false;
        }
        return true;
    }








    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
