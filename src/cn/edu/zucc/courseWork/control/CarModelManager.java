package cn.edu.zucc.courseWork.control;

import cn.edu.zucc.courseWork.itf.ICarModelManager;
import cn.edu.zucc.courseWork.model.CCCar;
import cn.edu.zucc.courseWork.model.CCModel;
import cn.edu.zucc.courseWork.model.CCStaff;
import cn.edu.zucc.courseWork.util.BaseException;
import cn.edu.zucc.courseWork.util.BusinessException;
import cn.edu.zucc.courseWork.util.DBUtil;
import cn.edu.zucc.courseWork.util.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarModelManager implements ICarModelManager {
    public List<CCModel> loadAll() throws BaseException {
        List<CCModel> result = new ArrayList<CCModel>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select *\n" +
                    "FROM car_model a,car_type b\n" +
                    "WHERE a.type_id=b.type_id";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                CCModel s = new CCModel();
                s.setModel_id(rs.getInt(1));
                s.setTypename(rs.getString(11));
                s.setModel_name(rs.getString(3));
                s.setModel_brand(rs.getString(4));
                s.setModel_capacity(rs.getFloat(5));
                s.setModel_transmission(rs.getString(6));
                s.setSite_num(rs.getInt(7));
                s.setPrice(rs.getFloat(8));
                s.setPicture(rs.getByte(9));
                result.add(s);
            }
            rs.close();
            pst.close();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        }
        finally{
            if(conn!=null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return result;
    }
    public void addModel(String type, String name, String brand, String cap,String tran,String site,String price) throws BaseException {
        // ????????????
        if (type == null || "".equals(type)) throw new BusinessException("????????????????????????");
        if (name == null || "".equals(name)) throw new BusinessException("??????????????????");
        if (brand == null || "".equals(brand)) throw new BusinessException("????????????????????????");
        if (cap == null || "".equals(cap)) throw new BusinessException("????????????????????????");
        if (tran == null || "".equals(tran)) throw new BusinessException("????????????????????????");
        if (site == null || "".equals(site)) throw new BusinessException("?????????????????????");
        if (price == null || "".equals(price)) throw new BusinessException("????????????????????????");
//        if(!tran.equals("?????????")||!tran.equals("?????????")) throw new BusinessException("???????????????????????????");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from car_model where model_name=? and brand=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2,brand);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                rs.close();
                pst.close();
                throw new BusinessException("??????????????????");
            }
            rs.close();
            pst.close();

            sql="select type_id from car_type where type_name = ?";
            pst=conn.prepareStatement(sql);
            pst.setString(1, type);
            rs=pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("?????????????????????");
            }
            int typeid=rs.getInt(1);
            rs.close();
            pst.close();

            sql = "insert into car_model(type_id,model_name,brand,capacity,Transmission,site_num,price) values(?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, typeid);
            pst.setString(2, name);
            pst.setString(3, brand);
            pst.setString(4, cap);
            pst.setString(5,tran);
            pst.setString(6,site);
            pst.setString(7,price);
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            throw new DbException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    public void delete(CCModel model) throws BaseException {
        // ??????
        Connection conn=null;
        try {
            conn = DBUtil.getConnection();
            String sql="select * from car_model where model_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,model.getModel_id());
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next()){
                rs.close();pst.close();
                throw new BusinessException("??????????????????");
            }
            rs.close();pst.close();
            sql="select * from car_information where model_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,model.getModel_id());
            rs=pst.executeQuery();
            if(rs.next()){
                rs.close();pst.close();
                throw new BusinessException("?????????????????????");
            }
            rs.close();pst.close();
//            sql="select * from orderlist where model_id=?";
//            pst=conn.prepareStatement(sql);
//            pst.setInt(1,model.getModel_id());
//            rs=pst.executeQuery();
//            if(rs.next()){
//                throw new BusinessException("?????????????????????");
//            }
//            rs.close();pst.close();
            sql="delete from car_model where model_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,model.getModel_id());
            pst.execute();
            pst.close();
        }catch (SQLException e){
            throw new DbException(e);
        }finally {
            if(conn!=null){
                try{
                    conn.close();
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
    public void changemodel(String model_id, String type, String name,String brand,
                          String cap,String tran,String site,String price) throws BaseException{
        if(model_id==null||"".equals(model_id)) throw new BusinessException("????????????????????????");
//        if(!tran.equals("?????????")||!tran.equals("?????????")) throw new BusinessException("???????????????????????????");
        Boolean namemod=false;Boolean typemod=false;Boolean brandmod=false;Boolean capmod=false;
        Boolean tranmod=false;Boolean sitemod=false;Boolean pricemod=false;
        Connection conn=null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT *\n" +
                    "FROM car_model a,car_type b\n" +
                    "where a.type_id=b.type_id and model_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(model_id));
            ResultSet rs = pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("??????????????????");
            }else {
                if(!(type==null||"".equals(type))&&(!type.equals(rs.getString(11)))) typemod=true;
                if(!(name==null||"".equals(name))&&(!name.equals(rs.getString(3)))) namemod=true;
                if(!(brand==null||"".equals(brand))&&(!brand.equals(rs.getString(4)))) brandmod=true;
                if(!(cap==null||"".equals(cap))&&(!cap.equals(rs.getString(5)))) capmod=true;
                if(!(tran==null||"".equals(tran))&&(!tran.equals(rs.getString(6)))) tranmod=true;
                if(!(site==null||"".equals(site))&&(!site.equals(rs.getString(7)))) sitemod=true;
                if(!(price==null||"".equals(price))&&(!price.equals(rs.getString(8)))) pricemod=true;
                rs.close();pst.close();
                if(typemod==true){
                    sql="select type_id from car_type where type_name=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1, type);
                    rs=pst.executeQuery();
                    if(!rs.next()){
                        rs.close();pst.close();
                        throw new BusinessException("??????????????????");
                    }
                    int typeid=rs.getInt(1);
                    rs.close();pst.close();
                    sql="update car_model set type_id=? where model_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setInt(1,typeid);
                    pst.setInt(2, Integer.parseInt(model_id));
                    pst.execute();
                    pst.close();
                }
                if(namemod==true){
                    sql="select * from car_model where model_name=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1, name);
                    rs=pst.executeQuery();
                    if(rs.next()){
                        rs.close();pst.close();
                        throw new BusinessException("?????????????????????");
                    }
                    rs.close();pst.close();
                    sql="update car_model set model_name=? where model_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1,name);
                    pst.setInt(2, Integer.parseInt(model_id));
                    pst.execute();
                    pst.close();
                }
                if(brandmod==true){
                    sql="update car_model set brand=? where model_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1,brand);
                    pst.setInt(2, Integer.parseInt(model_id));
                    pst.execute();
                    pst.close();
                }
                if(capmod==true){
                    sql="update car_model set capacity=? where model_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1,cap);
                    pst.setInt(2, Integer.parseInt(model_id));
                    pst.execute();
                    pst.close();
                }
                if(tranmod==true){
                    sql="update car_model set Transmission=? where model_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1,tran);
                    pst.setInt(2, Integer.parseInt(model_id));
                    pst.execute();
                    pst.close();
                }
                if(sitemod==true){
                    sql="update car_model set site_num=? where model_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1,site);
                    pst.setInt(2, Integer.parseInt(model_id));
                    pst.execute();
                    pst.close();
                }
                if(pricemod==true){
                    sql="update car_model set price=? where model_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1,price);
                    pst.setInt(2, Integer.parseInt(model_id));
                    pst.execute();
                    pst.close();
                }
            }
            rs.close();
            pst.close();

        }catch (SQLException e){
            throw new DbException(e);
        }finally {
            if(conn!=null){
                try{
                    conn.close();
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}
