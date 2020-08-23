package com.sys.cub360.catholicchurch.Readmodels;

public class provinces {
    String Province,dioce,date,Totalno,totaalc;

    public provinces(String province, String dioce, String date, String totalno, String totaalc) {
        Province = province;
        this.dioce = dioce;
        this.date = date;
        Totalno = totalno;
        this.totaalc = totaalc;
    }

    public String getProvince() {
        return Province;
    }

    public String getDioce() {
        return dioce;
    }

    public String getDate() {
        return date;
    }

    public String getTotalno() {
        return Totalno;
    }

    public String getTotaalc() {
        return totaalc;
    }
}
