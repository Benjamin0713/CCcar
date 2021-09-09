package cn.edu.zucc.courseWork.model;

import java.util.Date;

public class CCProGet {
    public CCProGet(){}
    private int get_id;
    private int user_id;
    private int Net_id;
    private int model_id;
    private int pro_id;
    private int Num;
    private Date get_time;
    private Date begindate;
    private Date finishdate;
    private float discount;
    public String getCell(int col) {
        if (col==0) return String.valueOf(get_id);
//        else if (col==1) return String.valueOf(user_id);
        else if (col==1) return String.valueOf(pro_id);
        else if (col==2) return String.valueOf(get_time);
        else if (col==3) return String.valueOf(Net_id);
        else if (col==4) return String.valueOf(model_id);
        else if (col==5) return String.valueOf(discount);
        else if(col==6) return String.valueOf(begindate);
        else if (col==7) return String.valueOf(finishdate);
        else return "";
    }
    public static final String[] tableTitles = {"序号","促销编号","领取时间","促销网点","促销车型","促销折扣","开始时间","结束时间"};
    public static String[] getTabletitles() {
        return tableTitles;
    }

    public int getGet_id() {
        return get_id;
    }

    public void setGet_id(int get_id) {
        this.get_id = get_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getNet_id() {
        return Net_id;
    }

    public void setNet_id(int net_id) {
        Net_id = net_id;
    }

    public int getModel_id() {
        return model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public int getNum() {
        return Num;
    }

    public void setNum(int num) {
        Num = num;
    }

    public Date getGet_time() {
        return get_time;
    }

    public void setGet_time(Date get_time) {
        this.get_time = get_time;
    }

    public Date getBegindate() {
        return begindate;
    }

    public void setBegindate(Date begindate) {
        this.begindate = begindate;
    }

    public Date getFinishdate() {
        return finishdate;
    }

    public void setFinishdate(Date finishdate) {
        this.finishdate = finishdate;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
}
