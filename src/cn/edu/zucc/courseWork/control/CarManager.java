package cn.edu.zucc.courseWork.control;

import cn.edu.zucc.courseWork.itf.ICarManager;
import cn.edu.zucc.courseWork.model.CCCar;
import cn.edu.zucc.courseWork.model.CCModel;
import cn.edu.zucc.courseWork.util.BaseException;
import cn.edu.zucc.courseWork.util.BusinessException;
import cn.edu.zucc.courseWork.util.DBUtil;
import cn.edu.zucc.courseWork.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarManager implements ICarManager {
    public List<CCCar> loadAll() throws BaseException {
        List<CCCar> result = new ArrayList<CCCar>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * from car_information where state not in('out','scrap')";
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
    public List<CCCar> loadAllCar() throws BaseException {
        List<CCCar> result = new ArrayList<CCCar>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * \n" +
                    "from car_information \n" +
                    "where state not in('out','scrap')";
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

            sql="select * from car_model where model_id = ?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(model));
            rs=pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("汽车车型不存在");
            }
            rs.close();
            pst.close();

            sql="select * from net_information where Net_id = ?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(net));
            rs=pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("该网点不存在");
            }
            rs.close();
            pst.close();

            sql = "insert into car_information(model_id,License,Net_id,state) values(?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, model);
            pst.setString(2, licen);
            pst.setString(3, net);
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
}
