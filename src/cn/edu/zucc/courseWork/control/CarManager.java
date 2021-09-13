package cn.edu.zucc.courseWork.control;

import cn.edu.zucc.courseWork.itf.ICarManager;
import cn.edu.zucc.courseWork.model.CCCar;
import cn.edu.zucc.courseWork.model.CCModel;
import cn.edu.zucc.courseWork.model.CCNet;
import cn.edu.zucc.courseWork.model.CCUser;
import cn.edu.zucc.courseWork.util.BaseException;
import cn.edu.zucc.courseWork.util.BusinessException;
import cn.edu.zucc.courseWork.util.DBUtil;
import cn.edu.zucc.courseWork.util.DbException;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarManager implements ICarManager {
    public List<CCCar> loadAll() throws BaseException {
        List<CCCar> result = new ArrayList<CCCar>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select car_id,model_name,License,Net_name,state\n" +
                    "from car_model a,car_information b,net_information c\n" +
                    "where a.model_id=b.model_id and b.Net_id=c.Net_id and state not in('租出','报废')";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                CCCar s = new CCCar();
                s.setCar_id(rs.getInt(1));
                s.setModel_name(rs.getString(2));
                s.setCar_license(rs.getString(3));
                s.setNet_name(rs.getString(4));
                s.setCar_state(rs.getString(5));
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
    public List<CCCar> loadAllCar() throws BaseException {
        List<CCCar> result = new ArrayList<CCCar>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * \n" +
                    "from car_information \n" +
                    "where state not in('租出','报废')";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                CCCar s = new CCCar();
                s.setCar_id(rs.getInt(1));
                s.setModel_id(rs.getInt(2));
                s.setCar_license(rs.getString(3));
                s.setNet_id(rs.getInt(4));
                s.setCar_state(rs.getString(5));
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
    public void addCar(String model, String licen, String net, String state) throws BaseException {
        // 添加记录
        if (model == null || "".equals(model)) throw new BusinessException("汽车车型不能为空");
        if (licen == null || "".equals(licen)) throw new BusinessException("牌照不能设为空");
        if (net == null || "".equals(net)) throw new BusinessException("所属网点不能为空");
        if (state == null || "".equals(state)) throw new BusinessException("汽车状态不能为空");
        Connection conn = null;
//        if((!state.equals("空闲"))||(!state.equals("报废"))||(!state.equals("租出"))) throw new BusinessException("状态设置为空闲、租出、报废中一种");
//        String vehicleNoStyle = "^[\u4e00-\u9fa5]{1}[A-Z0-9]{6}$";
//        Pattern pattern = Pattern.compile(vehicleNoStyle);
//        Matcher matcher = pattern.matcher(licen.getText().trim());

        try {
            conn = DBUtil.getConnection();
            String sql = "select * from car_information where License=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, licen);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                rs.close();
                pst.close();
                throw new BusinessException("车辆已存在");
            }
            rs.close();
            pst.close();

            sql="select model_id from car_model where model_name = ?";
            pst=conn.prepareStatement(sql);
            pst.setString(1, model);
            rs=pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("汽车车型不存在");
            }
            int modelid=rs.getInt(1);
            rs.close();
            pst.close();

            sql="select Net_id from net_information where Net_name = ?";
            pst=conn.prepareStatement(sql);
            pst.setString(1, net);
            rs=pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("该网点不存在");
            }
            int netid=rs.getInt(1);
            rs.close();
            pst.close();

            sql = "insert into car_information(model_id,License,Net_id,state) values(?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, modelid);
            pst.setString(2, licen);
            pst.setInt(3, netid);
            pst.setString(4, state);
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
    public void delete(CCCar car) throws BaseException {
        // 删除
        Connection conn=null;
        try {
            conn = DBUtil.getConnection();
            String sql="select * from car_information where car_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,car.getCar_id());
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next()){
                rs.close();pst.close();
                throw new BusinessException("车辆不存在");
            }
            rs.close();pst.close();
            sql="select * from orderlist where car_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,car.getCar_id());
            rs=pst.executeQuery();
            if(rs.next()){
                throw new BusinessException("该车无法删除");
            }
            rs.close();pst.close();
            sql="delete from car_information where car_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,car.getCar_id());
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
    public List<CCCar> loadrent() throws BaseException{
        List<CCCar> result = new ArrayList<CCCar>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from carrent ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                CCCar car = new CCCar();
                car.setCar_id(rs.getInt(1));
                car.setCar_license(rs.getString(2));
                car.setTotal_cost(rs.getInt(3));
                car.setTotal_order(rs.getInt(4));
                result.add(car);
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
    public void changecar(String car_id, String model, String license,
                            String net,String state) throws BaseException{
        if(car_id==null||"".equals(car_id)) throw new BusinessException("修改编号不能为空");
        if(!(license==null||"".equals(license))){
            String vehicleNoStyle = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{1}(([A-HJ-Z]{1}[A-HJ-NP-Z0-9]{5})|([A-HJ-Z]{1}(([DF]{1}[A-HJ-NP-Z0-9]{1}[0-9]{4})|([0-9]{5}[DF]{1})))|([A-HJ-Z]{1}[A-D0-9]{1}[0-9]{3}警)))|([0-9]{6}使)|((([沪粤川云桂鄂陕蒙藏黑辽渝]{1}A)|鲁B|闽D|蒙E|蒙H)[0-9]{4}领)|(WJ[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼·•]{1}[0-9]{4}[TDSHBXJ0-9]{1})|([VKHBSLJNGCE]{1}[A-DJ-PR-TVY]{1}[0-9]{5})";
//            "^[\u4e00-\u9fa5]{1}[A-Z0-9]{6}$";
            Pattern pattern = Pattern.compile(vehicleNoStyle);
            Matcher matcher = pattern.matcher(license.trim());
            if (!matcher.matches()) {
                throw new BusinessException("车牌输入有误");
            }
        }
//        if(!state.equals("空闲")||!state.equals("报废")||!state.equals("租出")) throw new BusinessException("状态设置为空闲、租出、报废中一种");
        Boolean modelmod=false;Boolean licensmod=false;Boolean netmod=false;Boolean statemod=false;
        Connection conn=null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT *\n" +
                    "FROM car_information a,net_information b,car_model c\n" +
                    "where a.model_id=c.model_id and a.Net_id=b.Net_id and  car_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(car_id));
            ResultSet rs = pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("该车辆不存在");
            }else {
                if(!(model==null||"".equals(model))&&(!model.equals(rs.getString(13)))) modelmod=true;
                if(!(license==null||"".equals(license))&&(!license.equals(rs.getString(3)))) licensmod=true;
                if(!(net==null||"".equals(net))&&(!net.equals(rs.getString(7)))) netmod=true;
                if(!(state==null||"".equals(state))&&(!state.equals(rs.getString(5)))) statemod=true;
                rs.close();pst.close();
                if(modelmod==true){
                    sql="select model_id from car_model where model_name=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1, model);
                    rs=pst.executeQuery();
                    if(!rs.next()){
                        rs.close();pst.close();
                        throw new BusinessException("该车型不存在");
                    }
                    int modelid=rs.getInt(1);
                    rs.close();pst.close();
                    sql="update car_information set model_id=? where car_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setInt(1,modelid);
                    pst.setInt(2, Integer.parseInt(car_id));
                    pst.execute();
                    pst.close();
                }
                if(licensmod==true){
                    sql = "select * from car_information where License=?";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, license);
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        rs.close();
                        pst.close();
                        throw new BusinessException("该车牌的车辆已存在");
                    }
                    rs.close();
                    pst.close();
                    sql="update car_information set License=? where car_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1,license);
                    pst.setInt(2, Integer.parseInt(car_id));
                    pst.execute();
                    pst.close();
                }
                if(netmod==true){
                    sql="select Net_id from net_information where Net_name=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1, net);
                    rs=pst.executeQuery();
                    if(!rs.next()){
                        rs.close();pst.close();
                        throw new BusinessException("该网点不存在");
                    }
                    int netd=rs.getInt(1);
                    rs.close();pst.close();
                    sql="update car_information set Net_id=? where car_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setInt(1,netd);
                    pst.setInt(2, Integer.parseInt(car_id));
                    pst.execute();
                    pst.close();
                }
                if(statemod==true){
                    sql="update car_information set state=? where car_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1,state);
                    pst.setInt(2, Integer.parseInt(car_id));
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
    public List<CCCar> load(String net,String model) throws BaseException {
        List<CCCar> result = new ArrayList<CCCar>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            if((net==null||"".equals(net))&&(model==null||"".equals(model))){
                String sql = "select car_id,model_name,License,Net_name,state\n" +
                        "from car_model a,car_information b,net_information c\n" +
                        "where a.model_id=b.model_id and b.Net_id=c.Net_id and  state not in('租出','报废')";
                java.sql.PreparedStatement pst=conn.prepareStatement(sql);
                java.sql.ResultSet rs=pst.executeQuery();
                while(rs.next()) {
                    CCCar s = new CCCar();
                    s.setCar_id(rs.getInt(1));
                    s.setModel_name(rs.getString(2));
                    s.setCar_license(rs.getString(3));
                    s.setNet_name(rs.getString(4));
                    s.setCar_state(rs.getString(5));
                    result.add(s);
                }
                rs.close();
                pst.close();
            }else if((net==null||"".equals(net))&&(!(model==null||"".equals(model)))){
//                String sql = "select model_id from car_model where model_name=?";
//                java.sql.PreparedStatement pst=conn.prepareStatement(sql);
//                pst.setString(1, model);
//                java.sql.ResultSet rs=pst.executeQuery();
//                if(!rs.next()) {
//                    rs.close();pst.close();
//                    throw new BusinessException("车型名称有误");
//                }
//                int model_id=rs.getInt(1);
//                rs.close();pst.close();
                String sql = "select car_id,model_name,License,Net_name,state\n" +
                        "from car_model a,car_information b,net_information c\n" +
                        "where a.model_id=b.model_id and b.Net_id=c.Net_id and state not in('租出','报废') and model_name=?";
                java.sql.PreparedStatement pst=conn.prepareStatement(sql);
                pst.setString(1, model);
                java.sql.ResultSet rs=pst.executeQuery();
                    while(rs.next()) {
                        CCCar s = new CCCar();
                        s.setCar_id(rs.getInt(1));
                        s.setModel_name(rs.getString(2));
                        s.setCar_license(rs.getString(3));
                        s.setNet_name(rs.getString(4));
                        s.setCar_state(rs.getString(5));
                        result.add(s);
                    }

                rs.close();
                pst.close();
            }else if((model==null||"".equals(model))&&(!(net==null||"".equals(net)))){
//                String sql="select Net_id from net_information where Net_name=?";
//                java.sql.PreparedStatement pst=conn.prepareStatement(sql);
//                pst.setString(1,net);
//                java.sql.ResultSet rs=pst.executeQuery();
//                if(!rs.next()){
//                    rs.close();pst.close();
//                    throw new BusinessException("网点名称有误");
//                }
//                int net_id=rs.getInt(1);
//                rs.close();pst.close();
                String sql = "select car_id,model_name,License,Net_name,state\n" +
                        "from car_model a,car_information b,net_information c\n" +
                        "where a.model_id=b.model_id and b.Net_id=c.Net_id and  state not in('租出','报废') and Net_name=?";
                java.sql.PreparedStatement pst=conn.prepareStatement(sql);
                pst.setString(1, net);
                java.sql.ResultSet rs=pst.executeQuery();
                    while(rs.next()) {
                        CCCar s = new CCCar();
                        s.setCar_id(rs.getInt(1));
                        s.setModel_name(rs.getString(2));
                        s.setCar_license(rs.getString(3));
                        s.setNet_name(rs.getString(4));
                        s.setCar_state(rs.getString(5));
                        result.add(s);
                    }
                rs.close();
                pst.close();
            }else {
//                String sql = "select model_id from car_model where model_name=?";
//                java.sql.PreparedStatement pst=conn.prepareStatement(sql);
//                pst.setString(1, model);
//                java.sql.ResultSet rs=pst.executeQuery();
//                if(!rs.next()) {
//                    rs.close();pst.close();
//                    throw new BusinessException("车型名称有误");
//                }
//                int model_id=rs.getInt(1);
//                rs.close();pst.close();
//                sql="select Net_id from net_information where Net_name=?";
//                pst=conn.prepareStatement(sql);
//                pst.setString(1,net);
//                rs=pst.executeQuery();
//                if(!rs.next()){
//                    rs.close();pst.close();
//                    throw new BusinessException("网点名称有误");
//                }
//                int net_id=rs.getInt(1);
//                rs.close();pst.close();
                String sql = "select car_id,model_name,License,Net_name,state\n" +
                        "from car_model a,car_information b,net_information c\n" +
                        "where a.model_id=b.model_id and b.Net_id=c.Net_id and  state not in('租出','报废') and Net_name=? and model_name=?";
                java.sql.PreparedStatement pst=conn.prepareStatement(sql);
                pst.setString(1, net);
                pst.setString(2, model);
                java.sql.ResultSet rs=pst.executeQuery();
                    while(rs.next()) {
                        CCCar s = new CCCar();
                        s.setCar_id(rs.getInt(1));
                        s.setModel_name(rs.getString(2));
                        s.setCar_license(rs.getString(3));
                        s.setNet_name(rs.getString(4));
                        s.setCar_state(rs.getString(5));
                        result.add(s);
                    }

                rs.close();
                pst.close();
            }
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
}
