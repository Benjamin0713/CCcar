package cn.edu.zucc.courseWork.model;

import java.util.Date;

public class CCCoupon {
    public CCCoupon(){}
    public String getCell(int col) {
        if (col==0) return String.valueOf(Coupon_id);
        else if (col==1) return Coupon_content;
        else if (col==2) return String.valueOf(Coupon_money);
        else if (col==3) return String.valueOf(Coupon_start_date);
        else if (col==4) return String.valueOf(Coupon_end_date);
        else return "";
    }
    public static final String[] tableTitles = {"优惠券编号","内容","减免金额","开始时间","结束时间"};
    public static String[] getTabletitles() {
        return tableTitles;
    }
    private int Coupon_id;
    private String Coupon_content;
    private float Coupon_money;
    private Date Coupon_start_date;
    private Date Coupon_end_date;

    public int getCoupon_id() {
        return Coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        Coupon_id = coupon_id;
    }

    public String getCoupon_content() {
        return Coupon_content;
    }

    public void setCoupon_content(String coupon_content) {
        Coupon_content = coupon_content;
    }

    public float getCoupon_money() {
        return Coupon_money;
    }

    public void setCoupon_money(float coupon_money) {
        Coupon_money = coupon_money;
    }

    public Date getCoupon_start_date() {
        return Coupon_start_date;
    }

    public void setCoupon_start_date(Date coupon_start_date) {
        Coupon_start_date = coupon_start_date;
    }

    public Date getCoupon_end_date() {
        return Coupon_end_date;
    }

    public void setCoupon_end_date(Date coupon_end_date) {
        Coupon_end_date = coupon_end_date;
    }
}
