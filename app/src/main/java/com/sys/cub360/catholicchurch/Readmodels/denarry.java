package com.sys.cub360.catholicchurch.Readmodels;

public class denarry {
    String denary, Dioces,date,Totalno,totaalc;

    public denarry(String denary, String dioces, String date, String totalno, String totaalc) {
        this.denary = denary;
        Dioces = dioces;
        this.date = date;
        Totalno = totalno;
        this.totaalc = totaalc;
    }

    public String getDenary() {
        return denary;
    }

    public String getDioces() {
        return Dioces;
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
