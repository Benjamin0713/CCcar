package cn.edu.zucc.courseWork.model;

import java.util.Date;

public class CCScrap {
//    汽车报废
    public CCScrap(){}
    public String getCell(int col) {
        if (col==0) return String.valueOf(car_id);
        else if (col==1) return String.valueOf(staff_id);
        else if (col==2) return String.valueOf(Scrap_id);
        else if (col==3) return String.valueOf(Scrap_time);
        else if (col==4) return Scrap_content;
        else return "";
    }
    public static final String[] tableTitles = {"汽车编号","员工编号","报废编号","报废时间","内容"};
    public static String[] getTabletitles() {
        return tableTitles;
    }
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
