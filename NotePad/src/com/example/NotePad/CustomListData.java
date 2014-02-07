package com.example.NotePad;

/**
 * Created by jeahyukkim on 2014. 1. 11..
 */
public class CustomListData {

    public String seqNo = "";
    public String title = "";
    public String dueDate = "";
    public String dueTime = "";
    public String context = "";
    public String confirm = "N";

    CustomListData(String seqNo, String title, String dueDate, String dueTime, String context, String confirm){
        this.seqNo = seqNo;
        this.title = title;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.context = context;
        this.confirm = confirm;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

}
