package com.android.shopping.bean;

/**
 * Created by Administrator on 2016/1/20.
 */
public class WinnerBean {
    public String iconUrl;
    public int iconId;
    public String name;
    public String city;
    public String ip;
    public String time;//评论时间

    public WinnerBean(int iconId, String city, String name, String ip, String time) {
        this.iconId = iconId;
        this.name = name;
        this.city = city;
        this.ip = ip;
        this.time = time;
    }
}
