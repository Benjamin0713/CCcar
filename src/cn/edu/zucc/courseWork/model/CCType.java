package cn.edu.zucc.courseWork.model;

public class CCType {
    public CCType(){}

    private int Type_id;
    private String Type_name;
    private String Type_descr;
    public String getCell(int col) {
        if (col==0) return String.valueOf(Type_id);
        else if (col==1) return Type_name;
        else if (col==2) return Type_descr;
        else return "";
    }
    public static final String[] tableTitles = {"类别编号","类别名称","类别描述"};
    public static String[] getTabletitles() {
        return tableTitles;
    }
    public int getType_id() {
        return Type_id;
    }

    public void setType_id(int type_id) {
        Type_id = type_id;
    }

    public String getType_name() {
        return Type_name;
    }

    public void setType_name(String type_name) {
        Type_name = type_name;
    }

    public String getType_descr() {
        return Type_descr;
    }

    public void setType_descr(String type_descr) {
        Type_descr = type_descr;
    }
}
