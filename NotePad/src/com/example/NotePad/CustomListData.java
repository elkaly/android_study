package com.example.NotePad;

/**
 * Created by jeahyukkim on 2014. 1. 11..
 */
public class CustomListData {
    public String key = "";
    public String text = "";


    CustomListData(String key, String text){
        this.key = key;
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
