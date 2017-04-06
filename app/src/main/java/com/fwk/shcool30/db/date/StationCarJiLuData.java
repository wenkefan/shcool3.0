package com.fwk.shcool30.db.date;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fwk.shcool30.db.DbOpenHelper;
import com.fwk.shcool30.modue.StationBean;
import com.fwk.shcool30.modue.StationCarJiLuBean;
import com.fwk.shcool30.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanwenke on 2017/4/6.
 */

public class StationCarJiLuData {

    private Context mContext;
    private DbOpenHelper dbOpenHelper;

    public StationCarJiLuData(Context context){
        this.mContext = context;
        this.dbOpenHelper = DbOpenHelper.getInstance(context);
    }

    //添加记录
    public void add(int busOrderid,StationBean.RerurnValueBean bean,String time,int isworkDao){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try{
            if (bean != null) {
                String StationName = bean.getStationName();
                int StationId = bean.getStationId();
                String datatime = time;
                db.execSQL(
                        "insert into StationCarJiLu(BusOrderId,StationName,StationId,datatime,IsDaozhan,IsworkDao,IsFache,IsworkFa) values (?,?,?,?,?,?,?,?)",
                        new Object[]{busOrderid, StationName, StationId, datatime,1, isworkDao,0,0});
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    //修改发车
    public void updataStation(int BusOrderId,int StationId){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try{
            db.execSQL("update StationCarJiLu set IsFache = ?,IsworkFa = ? where BusOrderId = ? and StationId = ? ",
                    new Object[]{1,1,BusOrderId,StationId});
            db.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }
    //修改发车
    public void updataStation(int BusOrderId,int StationId,int Iswork){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try{
            db.execSQL("update StationCarJiLu set IsFache = ?, IsworkFa = ? where BusOrderId = ? and StationId = ? ",
                    new Object[]{1, Iswork,BusOrderId,StationId});
            db.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    //获取当前站点是否记录
    public boolean queryDangqian(int BusOrderId,int StationId){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from StationCarJiLu where BusOrderId = ? and StationId = ?",
                new String[]{String.valueOf(BusOrderId),String.valueOf(StationId)});
        if (cursor != null){
            if (cursor.moveToNext()){
                return true;
            }
        }
        cursor.close();
        db.close();
        return false;
    }

    //获取记录
    public List<StationCarJiLuBean> queryJiLu(int BusOrderId){
        List<StationCarJiLuBean> list = new ArrayList<>();
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from StationCarJiLu where BusOrderId = ?",new String[]{String.valueOf(BusOrderId)});
        if (cursor != null && cursor.getCount() > 0){
            while (cursor.moveToNext()){
                StationCarJiLuBean bean = new StationCarJiLuBean();
                bean.setBusOrderId(cursor.getInt(cursor.getColumnIndex("BusOrderId")));
                bean.setStationId(cursor.getInt(cursor.getColumnIndex("StationId")));
                bean.setStationName(cursor.getString(cursor.getColumnIndex("StationName")));
                bean.setDatatime(cursor.getString(cursor.getColumnIndex("datatime")));
                bean.setIsFache(cursor.getInt(cursor.getColumnIndex("IsFache")));
                bean.setIsDaozhan(cursor.getInt(cursor.getColumnIndex("IsDaozhan")));
                bean.setIsworkDao(cursor.getInt(cursor.getColumnIndex("IsworkDao")));
                bean.setIsworkFa(cursor.getInt(cursor.getColumnIndex("IsworkFa")));
                list.add(bean);
            }
            return list;
        }
        cursor.close();
        db.close();
        return null;
    }
}
