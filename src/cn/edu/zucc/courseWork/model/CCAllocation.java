package cn.edu.zucc.courseWork.model;

import java.util.Date;

public class CCAllocation {
    public CCAllocation(){}
    public String getCell(int col) {
        if (col==0) return String.valueOf(Net_id_in);
        else if (col==1) return String.valueOf(car_id);
        else if (col==2) return String.valueOf(Net_id_out);
        else if (col==3) return String.valueOf(allocate_id);
        else if (col==4) return String.valueOf(allocate_time);
        else return "";
    }
    public static final String[] tableTitles = {"调入网点","车辆编号","调出网点","调拨编号","调拨时间"};
    public static String[] getTabletitles() {
        return tableTitles;
    }
    private int Net_id_in;
    private int Net_id_out;
    private int car_id;
    private int allocate_id;
    private Date allocate_time;

    public int getNet_id_in() {
        return Net_id_in;
    }

    public void setNet_id_in(int net_id_in) {
        Net_id_in = net_id_in;
    }

    public int getNet_id_out() {
        return Net_id_out;
    }

    public void setNet_id_out(int net_id_out) {
        Net_id_out = net_id_out;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public Date getAllocate_time() {
        return allocate_time;
    }

    public void setAllocate_time(Date allocate_time) {
        this.allocate_time = allocate_time;
    }

    public int getAllocate_id() {
        return allocate_id;
    }

    public void setAllocate_id(int allocate_id) {
        this.allocate_id = allocate_id;
    }
}
