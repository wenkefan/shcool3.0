package com.fwk.shcool30.modue;

import java.io.Serializable;

/**
 * Created by fanwenke on 2017/4/5.
 */

public class UpAndDownRecordBean implements Serializable {
    private int KgId;//幼儿园ID
    private int ClassId;//班级ID
    private int ChildId;//幼儿ID
    private String ChildName;//幼儿姓名
    private String SACardNo;//卡号
    private int BusOrderId;//发车单号
    private int Shang;//上车站点
    private int Xia;//下车站点
    private int IsworkShang;//是否上传
    private int IsworkXia;//是否上传

    public int getIsworkShang() {
        return IsworkShang;
    }

    public void setIsworkShang(int isworkShang) {
        IsworkShang = isworkShang;
    }

    public int getIsworkXia() {
        return IsworkXia;
    }

    public void setIsworkXia(int isworkXia) {
        IsworkXia = isworkXia;
    }

    private int IsShang;//是否上车
    private int IsXia;//是否下车

    public int getIsShang() {
        return IsShang;
    }

    public void setIsShang(int isShang) {
        IsShang = isShang;
    }

    public int getIsXia() {
        return IsXia;
    }

    public void setIsXia(int isXia) {
        IsXia = isXia;
    }

    public int getKgId() {
        return KgId;
    }

    public void setKgId(int kgId) {
        KgId = kgId;
    }

    public int getClassId() {
        return ClassId;
    }

    public void setClassId(int classId) {
        ClassId = classId;
    }

    public int getChildId() {
        return ChildId;
    }

    public void setChildId(int childId) {
        ChildId = childId;
    }

    public String getChildName() {
        return ChildName;
    }

    public void setChildName(String childName) {
        ChildName = childName;
    }

    public String getSACardNo() {
        return SACardNo;
    }

    public void setSACardNo(String SACardNo) {
        this.SACardNo = SACardNo;
    }

    public int getBusOrderId() {
        return BusOrderId;
    }

    public void setBusOrderId(int busOrderId) {
        BusOrderId = busOrderId;
    }

    public int getShang() {
        return Shang;
    }

    public void setShang(int shang) {
        Shang = shang;
    }

    public int getXia() {
        return Xia;
    }

    public void setXia(int xia) {
        Xia = xia;
    }

}
