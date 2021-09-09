package cn.edu.zucc.courseWork.model;

public class CCStaff {
    public CCStaff(){}
    public static final String[] tableTitles = {"员工编号","工作网点","用户名","密码"};
    public static CCStaff currentLoginStaff=null;
    private int Staff_id;
    private int Staff_Net_id;
    private String Staff_name;
    private String Staff_pwd;

    public String getCell(int col) {
        if (col==0) return String.valueOf(Staff_id);
        else if (col==1) return String.valueOf(Staff_Net_id);
        else if (col==2) return Staff_name;
        else if (col==3) return Staff_pwd;
        else return "";
    }
    public static String[] getTabletitles() {
        return tableTitles;
    }
    public static CCStaff getCurrentLoginStaff() {
        return currentLoginStaff;
    }
    public static void setCurrentLoginStaff(CCStaff currentLoginStaff) {
        CCStaff.currentLoginStaff = currentLoginStaff;
    }
    public int getStaff_id() {
        return Staff_id;
    }

    public void setStaff_id(int staff_id) {
        Staff_id = staff_id;
    }

    public int getStaff_Net_id() {
        return Staff_Net_id;
    }

    public void setStaff_Net_id(int staff_Net_id) {
        Staff_Net_id = staff_Net_id;
    }

    public String getStaff_name() {
        return Staff_name;
    }

    public void setStaff_name(String staff_name) {
        Staff_name = staff_name;
    }

    public String getStaff_pwd() {
        return Staff_pwd;
    }

    public void setStaff_pwd(String staff_pwd) {
        Staff_pwd = staff_pwd;
    }
}
