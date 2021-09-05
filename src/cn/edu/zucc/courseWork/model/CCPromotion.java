package cn.edu.zucc.courseWork.model;

import java.util.Date;

public class CCPromotion {
    public CCPromotion(){}

    private int Promotion_id;
    private int Promotion_model_id;
    private int Promotion_Net_id;
    private float discount;
    private int number;
    private Date begin_date;
    private Date finish_date;

    public int getPromotion_id() {
        return Promotion_id;
    }

    public void setPromotion_id(int promotion_id) {
        Promotion_id = promotion_id;
    }

    public int getPromotion_model_id() {
        return Promotion_model_id;
    }

    public void setPromotion_model_id(int promotion_model_id) {
        Promotion_model_id = promotion_model_id;
    }

    public int getPromotion_Net_id() {
        return Promotion_Net_id;
    }

    public void setPromotion_Net_id(int promotion_Net_id) {
        Promotion_Net_id = promotion_Net_id;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(Date begin_date) {
        this.begin_date = begin_date;
    }

    public Date getFinish_date() {
        return finish_date;
    }

    public void setFinish_date(Date finish_date) {
        this.finish_date = finish_date;
    }
}
