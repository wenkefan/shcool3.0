package com.fwk.shcool30.modue;

import java.io.Serializable;

/**
 * Created by fanwenke on 2017/4/6.
 */

public class StationCarJiLuBean implements Serializable {
    int BusOrderId;
    String StationName;
    int StationId;
    String datatime;
    int IsFache;
    int IsDaozhan;
    int IsworkFa;
    int IsworkDao;

    public int getIsDaozhan() {
        return IsDaozhan;
    }

    public void setIsDaozhan(int isDaozhan) {
        IsDaozhan = isDaozhan;
    }

    public int getIsworkFa() {
        return IsworkFa;
    }

    public void setIsworkFa(int isworkFa) {
        IsworkFa = isworkFa;
    }

    public int getIsworkDao() {
        return IsworkDao;
    }

    public void setIsworkDao(int isworkDao) {
        IsworkDao = isworkDao;
    }

    public int getIsFache() {
        return IsFache;
    }

    public void setIsFache(int isFache) {
        IsFache = isFache;
    }

    int Iswork;

    public int getIswork() {
        return Iswork;
    }

    public void setIswork(int iswork) {
        Iswork = iswork;
    }

    public int getBusOrderId() {
        return BusOrderId;
    }

    public void setBusOrderId(int busOrderId) {
        BusOrderId = busOrderId;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }

    public int getStationId() {
        return StationId;
    }

    public void setStationId(int stationId) {
        StationId = stationId;
    }

    public String getDatatime() {
        return datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }
}
