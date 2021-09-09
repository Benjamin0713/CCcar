package cn.edu.zucc.courseWork.model;

import java.util.Date;

public class CCUser {
//    用户
    public static CCUser currentLoginUser=null;
    public CCUser(){}
    public String getCell(int col) {
        if (col==0) return String.valueOf(user_id);
        else if (col==1) return User_name;
        else if (col==2) return User_sex;
        else if (col==3) return User_pwd;
        else if (col==4) return User_tell_num;
        else if (col==5) return User_email;
        else if (col==6) return User_city;
        else if (col==7) return String.valueOf(User_register_time);
        else return "";
    }
    public static final String[] tableTitles = {"用户编号","用户名","性别","密码","手机号码","邮箱","所在城市","注册时间"};
    public static String[] getTabletitles() {
        return tableTitles;
    }

    public String getOrder(int col) {
        if (col==0) return String.valueOf(user_id);
        else if (col==1) return User_name;
        else if (col==2) return String.valueOf(Total_order);
        else if (col==3) return String.valueOf(Total_cost);
        else return "";
    }
    public static final String[] CostTitles = {"用户编号","用户名","总订单数","总消费"};
    public static String[] getCosttitles() {
        return CostTitles;
    }
    public static CCUser getCurrentLoginUser() {
        return currentLoginUser;
    }
    public static void setCurrentLoginUser(CCUser currentLoginUser) {
        CCUser.currentLoginUser = currentLoginUser;
    }
    private int user_id;
    private String User_name;
    private String User_sex;
    private String User_pwd;
    private String User_tell_num;
    private String User_email;
    private String User_city;
    private Date User_register_time;
    private int Total_order;
    private float Total_cost;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public String getUser_sex() {
        return User_sex;
    }

    public void setUser_sex(String user_sex) {
        User_sex = user_sex;
    }

    public String getUser_pwd() {
        return User_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        User_pwd = user_pwd;
    }

    public String getUser_tell_num() {
        return User_tell_num;
    }

    public void setUser_tell_num(String user_tell_num) {
        User_tell_num = user_tell_num;
    }

    public String getUser_email() {
        return User_email;
    }

    public void setUser_email(String user_email) {
        User_email = user_email;
    }

    public String getUser_city() {
        return User_city;
    }

    public void setUser_city(String user_city) {
        User_city = user_city;
    }

    public Date getUser_register_time() {
        return User_register_time;
    }

    public void setUser_register_time(Date user_register_time) {
        User_register_time = user_register_time;
    }

    public int getTotal_order() {
        return Total_order;
    }

    public void setTotal_order(int total_order) {
        Total_order = total_order;
    }

    public float getTotal_cost() {
        return Total_cost;
    }

    public void setTotal_cost(float total_cost) {
        Total_cost = total_cost;
    }
}
