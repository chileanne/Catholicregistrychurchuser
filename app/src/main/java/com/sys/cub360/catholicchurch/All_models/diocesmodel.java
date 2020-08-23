package com.sys.cub360.catholicchurch.All_models;

public class diocesmodel {
    private String dioces;

    public diocesmodel(String dioces) {
        this.dioces = dioces;
    }

    public String getDioces() {
        return dioces;
    }

    @Override
    public String toString() {
        return getDioces();
    }
}
