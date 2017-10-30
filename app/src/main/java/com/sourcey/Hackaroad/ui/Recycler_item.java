package com.sourcey.Hackaroad.ui;

/**
 * Created by Jeong on 2017-10-27.
 */

public class Recycler_item {
    String date;
    String content;


    Recycler_item(String date, String content){
        this.date = date;
        this.content = content;

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

}
