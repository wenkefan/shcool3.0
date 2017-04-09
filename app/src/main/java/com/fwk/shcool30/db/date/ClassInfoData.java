package com.fwk.shcool30.db.date;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fwk.shcool30.db.DbOpenHelper;
import com.fwk.shcool30.modue.ClassMessage;
import com.fwk.shcool30.util.LogUtils;

import java.util.List;

/**
 * Created by fanwenke on 2017/4/8.
 */

public class ClassInfoData {
    private Context mContext;
    private DbOpenHelper dbOpenHelper;

    public ClassInfoData(Context context) {
        this.mContext = context;
        this.dbOpenHelper = DbOpenHelper.getInstance(context);
    }

    public void add(List<ClassMessage.RerurnValueBean> list){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            if (list != null) {
                for (ClassMessage.RerurnValueBean bean : list){
                    int classinfoid = bean.getClassInfoID();
                    int kgid = bean.getKgId();
                    String classname = bean.getClassName();
                    String classImg = bean.getClassImg();
                    String classdes = bean.getClassDes();
                    int state = bean.getState();
                    int classtype = bean.getClassType();
                    int dispayorder = bean.getDispayOrder();
                    String classCardno = bean.getClassCardNo();
                    db.execSQL(
                            "insert into ClassInfoData(ClassInfoID, KgId, ClassName, ClassImg, ClassDes, State, ClassType, DispayOrder, ClassCardNo) values (?,?,?,?,?,?,?,?,?)",
                            new Object[]{classinfoid, kgid, classname, classImg, classdes, state, classtype, dispayorder, classCardno});
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public String query(int kgid, int classinfoid){
        LogUtils.d("quer--" + classinfoid);
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from ClassInfoData where ClassInfoID = ? and KgId = ? and State = ?",
                new String[]{String.valueOf(classinfoid),String.valueOf(kgid), String.valueOf(1)});
        if (cursor != null){
            if (cursor.moveToNext()){
                return cursor.getString(cursor.getColumnIndex("ClassName"));
            }
        }
        return null;
    }

    public boolean querAll(int kgid){
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from ClassInfoData where KgId = ?",
                new String[]{String.valueOf(kgid)});
        if (cursor != null){
            if (cursor.getCount() > 0){
                return true;
            }
        }
        return false;
    }
}
