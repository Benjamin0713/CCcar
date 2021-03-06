package cn.edu.zucc.courseWork.model;

public class CCNet {
    private int Net_id;
    private String Net_name;
    private String Net_city;
    private String Net_address;
    private String Net_tellphone;
    private int Net_count;
    public String getCell(int col) {
        if (col==0) return String.valueOf(Net_id);
        else if (col==1) return Net_name;
        else if (col==2) return Net_city;
        else if (col==3) return Net_address;
        else if (col==4) return Net_tellphone;
        else return "";
    }
    public static final String[] tableTitles = {"网点编号","网点名称","所在城市","地址","联系电话"};
    public static String[] getTabletitles() {
        return tableTitles;
    }
    public CCNet(){
    }

    public int getNet_id() {
        return Net_id;
    }

    public void setNet_id(int net_id) {
        Net_id = net_id;
    }

    public String getNet_name() {
        return Net_name;
    }

    public void setNet_name(String net_name) {
        Net_name = net_name;
    }

    public int getNet_count() {
        return Net_count;
    }

    public void setNet_count(int net_count) {
        Net_count = net_count;
    }

    public String getNet_tellphone() {
        return Net_tellphone;
    }

    public void setNet_tellphone(String net_tellphone) {
        Net_tellphone = net_tellphone;
    }

    public String getNet_address() {
        return Net_address;
    }

    public void setNet_address(String net_address) {
        Net_address = net_address;
    }

    public String getNet_city() {
        return Net_city;
    }

    public void setNet_city(String net_city) {
        Net_city = net_city;
    }
}
