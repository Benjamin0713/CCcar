package cn.edu.zucc.courseWork.model;

import java.util.Date;

public class CCOrder {
    public CCOrder(){}

    private int Order_user_id;
    private int Order_coupon_id;
    private int Order_promotion_id;
    private int Order_car_id;
    private int Order_id;
    private int Pick_Net_id;
    private Date Pick_time;
    private int Return_Net_id;
    private Date Return_time;
    private Date Pick_total_time;
    private float Ori_amount;
    private float Set_amount;
    private String Order_state;

    public int getOrder_user_id() {
        return Order_user_id;
    }

    public void setOrder_user_id(int order_user_id) {
        Order_user_id = order_user_id;
    }

    public int getOrder_coupon_id() {
        return Order_coupon_id;
    }

    public void setOrder_coupon_id(int order_coupon_id) {
        Order_coupon_id = order_coupon_id;
    }

    public int getOrder_promotion_id() {
        return Order_promotion_id;
    }

    public void setOrder_promotion_id(int order_promotion_id) {
        Order_promotion_id = order_promotion_id;
    }

    public int getOrder_car_id() {
        return Order_car_id;
    }

    public void setOrder_car_id(int order_car_id) {
        Order_car_id = order_car_id;
    }

    public int getOrder_id() {
        return Order_id;
    }

    public void setOrder_id(int order_id) {
        Order_id = order_id;
    }

    public int getPick_Net_id() {
        return Pick_Net_id;
    }

    public void setPick_Net_id(int pick_Net_id) {
        Pick_Net_id = pick_Net_id;
    }

    public Date getPick_time() {
        return Pick_time;
    }

    public void setPick_time(Date pick_time) {
        Pick_time = pick_time;
    }

    public int getReturn_Net_id() {
        return Return_Net_id;
    }

    public void setReturn_Net_id(int return_Net_id) {
        Return_Net_id = return_Net_id;
    }

    public Date getReturn_time() {
        return Return_time;
    }

    public void setReturn_time(Date return_time) {
        Return_time = return_time;
    }

    public Date getPick_total_time() {
        return Pick_total_time;
    }

    public void setPick_total_time(Date pick_total_time) {
        Pick_total_time = pick_total_time;
    }

    public float getOri_amount() {
        return Ori_amount;
    }

    public void setOri_amount(float ori_amount) {
        Ori_amount = ori_amount;
    }

    public float getSet_amount() {
        return Set_amount;
    }

    public void setSet_amount(float set_amount) {
        Set_amount = set_amount;
    }

    public String getOrder_state() {
        return Order_state;
    }

    public void setOrder_state(String order_state) {
        Order_state = order_state;
    }
}
