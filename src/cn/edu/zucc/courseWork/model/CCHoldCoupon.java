package cn.edu.zucc.courseWork.model;

import java.util.Date;

public class CCHoldCoupon {
    public CCHoldCoupon(){}
    private int hold_id;
    private int user_id;
    private int coupon_id;
    private Date hold_time;
    private String content;
    private float money;
    private int Net_id;
    private Date start_date;
    private Date end_date;
    private String netname;
    public String getCell(int col) {
        if (col==0) return String.valueOf(hold_id);
//        else if (col==1) return String.valueOf(user_id);
        else if (col==1) return String.valueOf(coupon_id);
        else if (col==2) return String.valueOf(hold_time);
        else if (col==3) return content;
        else if (col==4) return String.valueOf(money);
        else if (col==5) return String.valueOf(start_date);
        else if (col==6) return String.valueOf(end_date);
        else if(col==7) return netname;
        else return "";
    }
    public static final String[] tableTitles = {"序号","优惠券编号","领取时间","内容","减免金额","开始时间","结束时间","优惠网点"};
    public static String[] getTabletitles() {
        return tableTitles;
    }

    public int getHold_id() {
        return hold_id;
    }

    public void setHold_id(int hold_id) {
        this.hold_id = hold_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public Date getHold_time() {
        return hold_time;
    }

    public void setHold_time(Date hold_time) {
        this.hold_time = hold_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getNet_id() {
        return Net_id;
    }

    public void setNet_id(int net_id) {
        Net_id = net_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getNetname() {
        return netname;
    }

    public void setNetname(String netname) {
        this.netname = netname;
    }
}
