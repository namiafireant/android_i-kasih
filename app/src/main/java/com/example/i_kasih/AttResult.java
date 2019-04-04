package com.example.i_kasih;

public class AttResult {
    private int id;
    private String dimage, dname, ddesc,imprint,color,shape;

    public AttResult(int id, String dimage, String dname, String ddesc, String imprint, String color, String shape) {
        this.id = id;
        this.dimage = dimage;
        this.dname = dname;
        this.ddesc = ddesc;
        this.imprint = imprint;
        this.color = color;
        this.shape = shape;
    }

    public int    getId() { return id; }
    public String getImage() { return dimage; }
    public String getName() { return dname; }
    public String getDdesc() { return ddesc; }
    public String getImprint() { return imprint; }
    public String getColor() { return color; }
    public String getShape() { return shape; }
}
