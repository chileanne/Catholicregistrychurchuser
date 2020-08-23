package com.sys.cub360.catholicchurch.All_models;

public class denarymodel {
    private String denary;

    public denarymodel(String denary) {
        this.denary = denary;
    }

    public String getDenary() {
        return denary;
    }

    @Override
    public String toString() {
        return getDenary();
    }
}
