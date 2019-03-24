package com.example.i_kasih;

import android.graphics.Bitmap;

public class AttributeDrug {
    private int id;
    private String name, ddesc;
    String dimage;/*******  try set data image   *******/

    public AttributeDrug(int id, String name, String ddesc, String dimage) {
        this.id = id;
        this.name = name;
        this.ddesc = ddesc;
        this.dimage = dimage; /*******  try set data image   *******/
    }


    public int    getId() { return id; }
    public String getName() { return name; }
    public String getDdesc() { return ddesc; }
    public String getImage() { return dimage; }/*******  try set data image   *******/
}
