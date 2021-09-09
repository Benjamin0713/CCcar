package cn.edu.zucc.courseWork.model;

public class CCCar {
    public CCCar(){}
    public String getCell(int col) {
        if (col==0) return String.valueOf(car_id);
        else if (col==1) return String.valueOf(model_id);
        else if (col==2) return car_license;
        else if (col==3) return String.valueOf(Net_id);
        else if (col==4) return car_state;
        else return "";
    }
    public static final String[] tableTitles = {"汽车编号","汽车车型","汽车牌照","所在网点","状态"};
    public static String[] getTabletitles() {
        return tableTitles;
    }
    public String getOrder(int col) {
        if (col==0) return String.valueOf(car_id);
        else if (col==1) return car_license;
        else if (col==2) return String.valueOf(Total_order);
        else if (col==3) return String.valueOf(Total_cost);
        else return "";
    }
    public static final String[] CostTitles = {"车辆编号","汽车牌照","总订单数","总租用费"};
    public static String[] getCosttitles() {
        return CostTitles;
    }
    private int car_id;
    private int model_id;
    private String car_license;
    private int Net_id;
    private String car_state;

    public int getTotal_order() {
        return Total_order;
    }

    public void setTotal_order(int total_order) {
        Total_order = total_order;
    }

    private int Total_order;

    public int getTotal_cost() {
        return Total_cost;
    }

    public void setTotal_cost(int total_cost) {
        Total_cost = total_cost;
    }

    private int Total_cost;
    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public int getModel_id() {
        return model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }

    public String getCar_license() {
        return car_license;
    }

    public void setCar_license(String car_license) {
        this.car_license = car_license;
    }

    public int getNet_id() {
        return Net_id;
    }

    public void setNet_id(int net_id) {
        Net_id = net_id;
    }

    public String getCar_state() {
        return car_state;
    }

    public void setCar_state(String car_state) {
        this.car_state = car_state;
    }
}
