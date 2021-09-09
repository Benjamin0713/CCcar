package cn.edu.zucc.courseWork.model;

public class CCModel {
    public CCModel(){}

    private int Model_id;
    private int Type_id;
    private String Model_name;
    private String Model_brand;
    private float Model_capacity;
    private String Model_transmission;
    private int site_num;
    private float price;
    private byte picture;
    public String getCell(int col) {
        if (col==0) return String.valueOf(Model_id);
        else if (col==1) return String.valueOf(Type_id);
        else if (col==2) return Model_name;
        else if (col==3) return Model_brand;
        else if (col==4) return String.valueOf(Model_capacity);
        else if (col==5) return Model_transmission;
        else if (col==6) return String.valueOf(site_num);
        else if (col==7) return String.valueOf(price);
        else if (col==8) return String.valueOf(picture);
        else return "";
    }
    public static final String[] tableTitles = {"车型编号","汽车类别","车型名称","品牌","排量","排档","座位数","租用价格","图片"};
    public static String[] getTabletitles() {
        return tableTitles;
    }
    public int getModel_id() {
        return Model_id;
    }

    public void setModel_id(int model_id) {
        Model_id = model_id;
    }

    public int getType_id() {
        return Type_id;
    }

    public void setType_id(int type_id) {
        Type_id = type_id;
    }

    public String getModel_name() {
        return Model_name;
    }

    public void setModel_name(String model_name) {
        Model_name = model_name;
    }

    public String getModel_brand() {
        return Model_brand;
    }

    public void setModel_brand(String model_brand) {
        Model_brand = model_brand;
    }

    public float getModel_capacity() {
        return Model_capacity;
    }

    public void setModel_capacity(float model_capacity) {
        Model_capacity = model_capacity;
    }

    public String getModel_transmission() {
        return Model_transmission;
    }

    public void setModel_transmission(String model_transmission) {
        Model_transmission = model_transmission;
    }

    public int getSite_num() {
        return site_num;
    }

    public void setSite_num(int site_num) {
        this.site_num = site_num;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public byte getPicture() {
        return picture;
    }

    public void setPicture(byte picture) {
        this.picture = picture;
    }
}
