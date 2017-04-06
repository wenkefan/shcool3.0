package com.fwk.shcool30.modue;

import java.io.Serializable;

/**
 * Created by fanwenke on 16/12/23.
 */

public class StaBean implements Serializable {
    private int id;
    private String strid;
    private String name;
    private int type;
    private int order;//排序

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStrid() {
        return strid;
    }

    public void setStrid(String strid) {
        this.strid = strid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StaBean{" +
                "id=" + id +
                ", strid='" + strid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
