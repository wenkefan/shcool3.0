package com.fwk.shcool30.db.date;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fwk.shcool30.db.DbOpenHelper;
import com.fwk.shcool30.modue.UpAndDownRecordBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanwenke on 2017/4/5.
 */

public class UpAndDownRecordData {

    private Context mContext;
    private DbOpenHelper dbOpenHelper;

    public UpAndDownRecordData(Context context) {
        this.mContext = context;
        this.dbOpenHelper = DbOpenHelper.getInstance(context);
    }

    //上车记录
    public void add(UpAndDownRecordBean been) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {

            if (been != null) {
                int KgId = been.getKgId();//幼儿园ID
                int ClassId = been.getClassId();//班级ID
                int ChildId = been.getChildId();//幼儿ID
                String ChildName = been.getChildName();//幼儿姓名
                String SACardNo = been.getSACardNo();//卡号
                int BusOrderId = been.getBusOrderId();//发车单号
                int Shang = been.getShang();//上车站点
                int Xia = been.getXia();//下车站点
                int IsworkShang = been.getIsworkShang();//是否上传
                int IsworkXia = been.getIsworkXia();//是否上传
                int IsShang = been.getIsShang();
                int IsXia = been.getIsXia();
                db.execSQL(
                        "insert into UpAndDownRecordBean(KgId, ClassId, ChildId, ChildName, SACardNo, BusOrderId, Shang, Xia, IsworkShang, IsworkXia, IsShang, IsXia) values (?,?,?,?,?,?,?,?,?,?,?,?)",
                        new Object[]{KgId, ClassId, ChildId, ChildName, SACardNo, BusOrderId, Shang, Xia, IsworkShang, IsworkXia, IsShang, IsXia});
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    //下车记录
    public void updateXia(int Xia, int IsXia, int isworkxia, String SACardNo, int BusOrderId, int isshang) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("update UpAndDownRecordBean set Xia = ?, IsXia = ?, IsworkXia = ? where SACardNo = ? and BusOrderId = ? and IsShang = ? ",
                    new Object[]{Xia, IsXia,isworkxia, SACardNo, BusOrderId, isshang});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    //按照站点查找
    public List<UpAndDownRecordBean> queryStationShangList(int BusOrderId, int stationId) {
        List<UpAndDownRecordBean> list = new ArrayList<>();

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from UpAndDownRecordBean where BusOrderid = ? and Shang = ?",
                new String[]{String.valueOf(BusOrderId), String.valueOf(stationId)});
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                UpAndDownRecordBean been = new UpAndDownRecordBean();
                been.setKgId(cursor.getInt(cursor.getColumnIndex("KgId")));//幼儿园ID
                been.setClassId(cursor.getInt(cursor.getColumnIndex("ClassId")));//班级ID
                been.setChildId(cursor.getInt(cursor.getColumnIndex("ChildId")));//幼儿ID
                been.setChildName(cursor.getString(cursor.getColumnIndex("ChildName")));//幼儿姓名
                been.setSACardNo(cursor.getString(cursor.getColumnIndex("SACardNo")));//卡号
                been.setBusOrderId(cursor.getInt(cursor.getColumnIndex("BusOrderId")));//发车单号
                been.setShang(cursor.getInt(cursor.getColumnIndex("Shang")));//上车站点
                been.setXia(cursor.getInt(cursor.getColumnIndex("Xia")));//下车站点
                been.setIsworkShang(cursor.getInt(cursor.getColumnIndex("IsworkShang")));//是否上传
                been.setIsworkXia(cursor.getInt(cursor.getColumnIndex("IsworkXia")));//是否上传
                been.setIsShang(cursor.getInt(cursor.getColumnIndex("IsShang")));
                been.setIsXia(cursor.getInt(cursor.getColumnIndex("IsXia")));
                list.add(been);
            }
        }
        cursor.close();
        db.close();
        return list;
    }

    //按照站点查找
    public List<UpAndDownRecordBean> queryStationXiaList(int BusOrderId, int stationId) {
        List<UpAndDownRecordBean> list = new ArrayList<>();

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from UpAndDownRecordBean where BusOrderid = ? and Xia = ?",
                new String[]{String.valueOf(BusOrderId), String.valueOf(stationId)});
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                UpAndDownRecordBean been = new UpAndDownRecordBean();
                been.setKgId(cursor.getInt(cursor.getColumnIndex("KgId")));//幼儿园ID
                been.setClassId(cursor.getInt(cursor.getColumnIndex("ClassId")));//班级ID
                been.setChildId(cursor.getInt(cursor.getColumnIndex("ChildId")));//幼儿ID
                been.setChildName(cursor.getString(cursor.getColumnIndex("ChildName")));//幼儿姓名
                been.setSACardNo(cursor.getString(cursor.getColumnIndex("SACardNo")));//卡号
                been.setBusOrderId(cursor.getInt(cursor.getColumnIndex("BusOrderId")));//发车单号
                been.setShang(cursor.getInt(cursor.getColumnIndex("Shang")));//上车站点
                been.setXia(cursor.getInt(cursor.getColumnIndex("Xia")));//下车站点
                been.setIsworkShang(cursor.getInt(cursor.getColumnIndex("IsworkShang")));//是否上传
                been.setIsworkXia(cursor.getInt(cursor.getColumnIndex("IsworkXia")));//是否上传
                been.setIsShang(cursor.getInt(cursor.getColumnIndex("IsShang")));
                been.setIsXia(cursor.getInt(cursor.getColumnIndex("IsXia")));
                list.add(been);
            }
        }
        cursor.close();
        db.close();
        return list;
    }

    //按照上车查找
    public List<UpAndDownRecordBean> queryCarList(int BusOrderId, int IsShang, int IsXia) {
        List<UpAndDownRecordBean> list = new ArrayList<>();

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from UpAndDownRecordBean where BusOrderid = ? and IsShang = ? and IsXia = ?",
                new String[]{String.valueOf(BusOrderId), String.valueOf(IsShang), String.valueOf(IsXia)});
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                UpAndDownRecordBean been = new UpAndDownRecordBean();
                been.setKgId(cursor.getInt(cursor.getColumnIndex("KgId")));//幼儿园ID
                been.setClassId(cursor.getInt(cursor.getColumnIndex("ClassId")));//班级ID
                been.setChildId(cursor.getInt(cursor.getColumnIndex("ChildId")));//幼儿ID
                been.setChildName(cursor.getString(cursor.getColumnIndex("ChildName")));//幼儿姓名
                been.setSACardNo(cursor.getString(cursor.getColumnIndex("SACardNo")));//卡号
                been.setBusOrderId(cursor.getInt(cursor.getColumnIndex("BusOrderId")));//发车单号
                been.setShang(cursor.getInt(cursor.getColumnIndex("Shang")));//上车站点
                been.setXia(cursor.getInt(cursor.getColumnIndex("Xia")));//下车站点
                been.setIsworkShang(cursor.getInt(cursor.getColumnIndex("IsworkShang")));//是否上传
                been.setIsworkXia(cursor.getInt(cursor.getColumnIndex("IsworkXia")));//是否上传
                been.setIsShang(cursor.getInt(cursor.getColumnIndex("IsShang")));
                been.setIsXia(cursor.getInt(cursor.getColumnIndex("IsXia")));
                list.add(been);
            }
        }
        cursor.close();
        db.close();
        return list;
    }

    //查询是否要下车
    public UpAndDownRecordBean queryChild(int BusOrderId, String sacardno) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from UpAndDownRecordBean where BusOrderId = ? and SACardNo = ? and IsShang = ?",
                new String[]{String.valueOf(BusOrderId), sacardno, String.valueOf(1)});
        if (cursor != null) {
            if (cursor.moveToNext()) {
                UpAndDownRecordBean been = new UpAndDownRecordBean();
                been.setKgId(cursor.getInt(cursor.getColumnIndex("KgId")));//幼儿园ID
                been.setClassId(cursor.getInt(cursor.getColumnIndex("ClassId")));//班级ID
                been.setChildId(cursor.getInt(cursor.getColumnIndex("ChildId")));//幼儿ID
                been.setChildName(cursor.getString(cursor.getColumnIndex("ChildName")));//幼儿姓名
                been.setSACardNo(cursor.getString(cursor.getColumnIndex("SACardNo")));//卡号
                been.setBusOrderId(cursor.getInt(cursor.getColumnIndex("BusOrderId")));//发车单号
                been.setShang(cursor.getInt(cursor.getColumnIndex("Shang")));//上车站点
                been.setXia(cursor.getInt(cursor.getColumnIndex("Xia")));//下车站点
                been.setIsworkShang(cursor.getInt(cursor.getColumnIndex("IsworkShang")));//是否上传
                been.setIsworkXia(cursor.getInt(cursor.getColumnIndex("IsworkXia")));//是否上传
                been.setIsShang(cursor.getInt(cursor.getColumnIndex("IsShang")));
                been.setIsXia(cursor.getInt(cursor.getColumnIndex("IsXia")));
                return been;
            }
        }
        cursor.close();
        db.close();
        return null;
    }
    //查询是否已经下车
    public UpAndDownRecordBean queryToUpCar(int BusOrderId, String sacardno) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from UpAndDownRecordBean where BusOrderId = ? and SACardNo = ? and IsXia = ?",
                new String[]{String.valueOf(BusOrderId), sacardno, String.valueOf(1)});
        if (cursor != null) {
            if (cursor.moveToNext()) {
                UpAndDownRecordBean been = new UpAndDownRecordBean();
                been.setKgId(cursor.getInt(cursor.getColumnIndex("KgId")));//幼儿园ID
                been.setClassId(cursor.getInt(cursor.getColumnIndex("ClassId")));//班级ID
                been.setChildId(cursor.getInt(cursor.getColumnIndex("ChildId")));//幼儿ID
                been.setChildName(cursor.getString(cursor.getColumnIndex("ChildName")));//幼儿姓名
                been.setSACardNo(cursor.getString(cursor.getColumnIndex("SACardNo")));//卡号
                been.setBusOrderId(cursor.getInt(cursor.getColumnIndex("BusOrderId")));//发车单号
                been.setShang(cursor.getInt(cursor.getColumnIndex("Shang")));//上车站点
                been.setXia(cursor.getInt(cursor.getColumnIndex("Xia")));//下车站点
                been.setIsworkShang(cursor.getInt(cursor.getColumnIndex("IsworkShang")));//是否上传
                been.setIsworkXia(cursor.getInt(cursor.getColumnIndex("IsworkXia")));//是否上传
                been.setIsShang(cursor.getInt(cursor.getColumnIndex("IsShang")));
                been.setIsXia(cursor.getInt(cursor.getColumnIndex("IsXia")));
                return been;
            }
        }
        cursor.close();
        db.close();
        return null;
    }

    //查看是否已经上车
    public boolean queryShangche(int kgid,int BusorderId,int childId){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from UpAndDownRecordBean where KgId = ? and BusOrderId = ? and ChildId = ? and IsShang = ?",
                new String[]{String.valueOf(kgid),String.valueOf(BusorderId), String.valueOf(childId), String.valueOf(1)});
        if (cursor != null) {
            if (cursor.moveToNext()) {
                return true;
            }
        }
        cursor.close();
        db.close();
        return false;
    }
}
