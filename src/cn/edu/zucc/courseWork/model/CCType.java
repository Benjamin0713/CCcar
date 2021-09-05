package cn.edu.zucc.courseWork.model;

public class CCType {
    public CCType(){}

    private int Type_id;
    private String Type_name;
    private String Type_descr;

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
