package cn.edu.zucc.courseWork.model;

public class CCAdmin {
    public CCAdmin(){}
    //管理员视图
    public static final String[] tableTitles = {"管理员编号","用户名","密码"};
    public static CCAdmin currentLoginAdmin=null;
    private int Staff_id;
    private String Staff_name;
    private String Staff_pwd;

    public String getCell(int col) {
        if (col==0) return String.valueOf(Staff_id);
        else if (col==1) return Staff_name;
        else if (col==2) return Staff_pwd;
        else return "";
    }
    public static String[] getTabletitles() {
        return tableTitles;
    }
    public static CCAdmin getCurrentLoginAdmin() {
        return currentLoginAdmin;
    }
    public static void setCurrentLoginAdmin(CCAdmin currentLoginAdmin) {
        CCAdmin.currentLoginAdmin = currentLoginAdmin;
    }
    public int getStaff_id() {
        return Staff_id;
    }

    public void setStaff_id(int staff_id) {
        Staff_id = staff_id;
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
