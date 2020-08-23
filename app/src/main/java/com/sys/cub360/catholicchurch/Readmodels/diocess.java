package com.sys.cub360.catholicchurch.Readmodels;

public class diocess {
    String Dioces, Province,date,Totalno,totaalc;

    public diocess(String dioces, String province, String date, String totalno, String totaalc) {
        Dioces = dioces;
        Province = province;
        this.date = date;
        Totalno = totalno;
        this.totaalc = totaalc;
    }

    public String getDioces() {
        return Dioces;
    }

    public String getProvince() {
        return Province;
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
