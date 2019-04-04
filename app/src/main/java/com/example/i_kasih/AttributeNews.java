package com.example.i_kasih;

public class AttributeNews {

    private String title, newimg;

    public AttributeNews(String title, String newimg) {
        this.title  = title;
        this.newimg = newimg;
    }
    public String getNewsTitle() { return title; }
    public String getNewsImg() { return newimg; }
}
