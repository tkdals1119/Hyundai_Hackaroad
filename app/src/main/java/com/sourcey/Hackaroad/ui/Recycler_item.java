package com.sourcey.Hackaroad.ui;

/**
 * Created by Jeong on 2017-10-27.
 */

public class Recycler_item {
    String date;
    String content;
    int img;


    Recycler_item(String date, String content, int img){
        this.date = date;
        this.content = content;
        this.img = img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
