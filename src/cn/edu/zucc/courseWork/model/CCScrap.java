package cn.edu.zucc.courseWork.model;

import java.util.Date;

public class CCScrap {
//    汽车报废
    public CCScrap(){}

    private int car_id;
    private int staff_id;
    private int Scrap_id;
    private Date Scrap_time;
    private String Scrap_content;


    public int getScrap_id() {
        return Scrap_id;
    }

    public void setScrap_id(int scrap_id) {
        Scrap_id = scrap_id;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public Date getScrap_time() {
        return Scrap_time;
    }

    public void setScrap_time(Date scrap_time) {
        Scrap_time = scrap_time;
    }

    public String getScrap_content() {
        return Scrap_content;
    }

    public void setScrap_content(String scrap_content) {
        Scrap_content = scrap_content;
    }
}
