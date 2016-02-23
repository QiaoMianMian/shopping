package com.android.shopping.bean;

import com.android.shopping.utils.MathUtils;
import com.android.shopping.utils.RandomUtils;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/30.
 */
public class GoodsBean implements Serializable {
    public String id;
    public String code;//期号
    public String name;
    public int category;
    public int price; //总共需要的积分(商品的价格)
    public String desc;
    public int resid;
    public int num; // 有多少人参与
    public int score;//完成度分数(10分)
    public int progress;
    public String precent;//完成度百分比
    public boolean hot;
    public int point; //用户对每个商品投入的积分
    public boolean selected;//商品选中后是否删除
    public long starttime;//开始竞拍时间(秒)
    public long endtime;//结束竞拍时间(秒)
    public long period;//竞拍时间段(秒)
    public String descurl;//图片展示链接
    public boolean claimed;

    public GoodsBean() {

    }

    public GoodsBean(String id, String name, int resid, int price, int point) {
        this.id = id;
        this.name = name;
        this.resid = resid;
        this.price = price;
        int random = RandomUtils.random(price);
        long current = System.currentTimeMillis() / 1000;
        long duration = ((100 - ((random * 100) / price)) * 864) + RandomUtils.random(864);
        setCode(MathUtils.getSimpleDate() + id);
        setNum(random);
        setScore(10 * random / price);
        setProgress((random * 100) / price);
        setPrecent((random * 100) / price + "%");
        setHot(RandomUtils.random());
        setPoint(point);
        setStarttime(current);
        setPeriod(duration);
        setEndtime(current + duration);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getResid() {
        return resid;
    }

    public void setResid(int resid) {
        this.resid = resid;
    }

    public int getNum() {
        return num;
    }

    public int getScore() {
        return score;
    }

    public String getPrecent() {
        return precent;
    }

    public boolean isHot() {
        return hot;
    }

    public int getPoint() {
        return point;
    }

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }

    public void setPrecent(String precent) {
        this.precent = precent;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", desc='" + desc + '\'' +
                ", resid=" + resid +
                ", num=" + num +
                ", score=" + score +
                ", progress=" + progress +
                ", precent='" + precent + '\'' +
                ", hot=" + hot +
                ", point=" + point +
                ", selected=" + selected +
                ", starttime=" + starttime +
                ", endtime=" + endtime +
                ", period=" + period +
                ", descurl='" + descurl + '\'' +
                ", claimed=" + claimed +
                '}';
    }
}
