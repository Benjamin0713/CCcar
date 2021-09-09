package cn.edu.zucc.courseWork.control;

import cn.edu.zucc.courseWork.itf.ICarModelManager;
import cn.edu.zucc.courseWork.model.CCModel;
import cn.edu.zucc.courseWork.model.CCStaff;
import cn.edu.zucc.courseWork.util.BaseException;
import cn.edu.zucc.courseWork.util.BusinessException;
import cn.edu.zucc.courseWork.util.DBUtil;
import cn.edu.zucc.courseWork.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarModelManager implements ICarModelManager {
    public List<CCModel> loadAll() throws BaseException {
        List<CCModel> result = new ArrayList<CCModel>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * from car_model";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                CCModel s = new CCModel();
                s.setModel_id(rs.getInt(1));
                s.setType_id(rs.getInt(2));
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
        // 添加记录
        if (type == null || "".equals(type)) throw new BusinessException("汽车类别不能为空");
        if (name == null || "".equals(name)) throw new BusinessException("车型名称为空");
        if (brand == null || "".equals(brand)) throw new BusinessException("汽车品牌不能为空");
        if (cap == null || "".equals(cap)) throw new BusinessException("汽车排量不能为空");
        if (tran == null || "".equals(tran)) throw new BusinessException("汽车排档不能为空");
        if (site == null || "".equals(site)) throw new BusinessException("座位数不能为空");
        if (price == null || "".equals(price)) throw new BusinessException("租车价格不能为空");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from car_model where model_name=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                rs.close();
                pst.close();
                throw new BusinessException("该车型已存在");
            }
            rs.close();
            pst.close();

            sql="select * from car_type where type_id = ?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(type));
            rs=pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("汽车类别不存在");
            }
            rs.close();
            pst.close();

            sql = "insert into car_model(type_id,model_name,brand,capacity,Transmission,site_num,price) values(?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, type);
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
}
