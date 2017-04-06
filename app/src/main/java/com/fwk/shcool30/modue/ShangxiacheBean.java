package com.fwk.shcool30.modue;

/**
 * Created by fanwenke on 2017/3/31.
 */

public class ShangxiacheBean {
    private int UserId;//幼儿ID
    private int KgId;//园所ID
    private int BusOrderId;//发车记录
    private String SACardNo;//卡号
    private int ConnectStation;//接上车ID
    private int ConnectEndStation;//接下车ID
    private int SendStartStation;//送上车ID
    private int SendStation;//送下车ID
    private int NetWork;//网络保存状态:0未上传  1已上传
    private int UserType;// 幼儿上下车类型：1上车，2下车

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getKgId() {
        return KgId;
    }

    public void setKgId(int kgId) {
        KgId = kgId;
    }

    public int getBusOrderId() {
        return BusOrderId;
    }

    public void setBusOrderId(int busOrderId) {
        BusOrderId = busOrderId;
    }

    public String getSACardNo() {
        return SACardNo;
    }

    public void setSACardNo(String SACardNo) {
        this.SACardNo = SACardNo;
    }

    public int getConnectStation() {
        return ConnectStation;
    }

    public void setConnectStation(int connectStation) {
        ConnectStation = connectStation;
    }

    public int getConnectEndStation() {
        return ConnectEndStation;
    }

    public void setConnectEndStation(int connectEndStation) {
        ConnectEndStation = connectEndStation;
    }

    public int getSendStartStation() {
        return SendStartStation;
    }

    public void setSendStartStation(int sendStartStation) {
        SendStartStation = sendStartStation;
    }

    public int getSendStation() {
        return SendStation;
    }

    public void setSendStation(int sendStation) {
        SendStation = sendStation;
    }

    public int getNetWork() {
        return NetWork;
    }

    public void setNetWork(int netWork) {
        NetWork = netWork;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }
}
