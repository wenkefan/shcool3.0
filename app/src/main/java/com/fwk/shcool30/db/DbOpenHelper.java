package com.fwk.shcool30.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fanwenke on 2017/3/27.
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static DbOpenHelper instance;

    private DbOpenHelper(Context context) {
        super(context, getUserDatabaseName(), null, DATABASE_VERSION);
    }

    public static DbOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    private static String getUserDatabaseName() {
        return "jyhd.db";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建
        db.execSQL("CREATE TABLE TeacherZT(Id integer primary key autoincrement UNIQUE,BusOrderId integer, TeacherId integer," +
                "LineId integer ,BusId integer,BusScheduleId integer,OrganizationId integer,DriverId integer," +
                "AttendanceDirections integer ,Status integer,RunningState integer,ModifierId integer," +
                "StartTime varchar(20),EndTime varchar(20),AuditStatus integer ,AuditId integer,AuditDate varchar(20),ModifyDate varchar(20),State integer,Remark varchar(400))");
        // 创建考勤用户表 //未改
        db.execSQL("create table AttendanceUser(ID integer primary key autoincrement,KgId integer,WorkerExtensionId integer , UserType integer,UserId integer,ClassInfoID integer,Note nvarchar(20),UserName nvarchar(20),Sex integer ,HeadImage nvarchar(20) ,BirthDay datetime,AutoSendMsg integer,State integer,SACardNo varchar(50),CampusNo varchar(20),MonitorInfo varchar(20))");

        //创建上下车记录表
//        db.execSQL("create table ShangXiaCarJiLu(UserId integer, KgId integer,BusOrderId integer, SACardNo varchar(20),ConnectStation integer, ConnectEndStation integer,SendStartStation integer, SendStation integer,NetWork integer, UserType integer)");
        //到站记录
        db.execSQL("create table StationCarJiLu(BusOrderId integer,StationName varchar(50),StationId integer,datatime varchar(20),IsDaozhan integer, IsworkDao integer, IsFache integer, IsworkFa integer)");
        //创建幼儿上下车记录
        db.execSQL("create table UpAndDownRecordBean(KgId integer, ClassId integer, ChildId integer, ChildName varchar(20), SACardNo varchar(20), BusOrderId integer, Shang integer, Xia integer, IsworkShang integer, IsworkXia integer, IsShang integer, IsXia integer)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
