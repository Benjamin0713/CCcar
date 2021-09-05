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
