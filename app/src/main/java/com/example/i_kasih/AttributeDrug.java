package com.example.i_kasih;

public class AttributeDrug {
    private int id;
    private String name, ddesc;

    public AttributeDrug(int id, String name, String ddesc) {
        this.id = id;
        this.name = name;
        this.ddesc = ddesc;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDdesc() { return ddesc; }
}
