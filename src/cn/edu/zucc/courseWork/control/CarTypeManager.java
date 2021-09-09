package cn.edu.zucc.courseWork.control;

import cn.edu.zucc.courseWork.itf.ICarTypeManager;
import cn.edu.zucc.courseWork.model.CCNet;
import cn.edu.zucc.courseWork.model.CCType;
import cn.edu.zucc.courseWork.util.BaseException;
import cn.edu.zucc.courseWork.util.BusinessException;
import cn.edu.zucc.courseWork.util.DBUtil;
import cn.edu.zucc.courseWork.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarTypeManager implements ICarTypeManager {
    public List<CCType> loadAll() throws BaseException {
        List<CCType> result = new ArrayList<CCType>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * from car_type";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                CCType s = new CCType();
                s.setType_id(rs.getInt(1));
                s.setType_name(rs.getString(2));
                s.setType_descr(rs.getString(3));
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
    public void addType(String name, String desc) throws BaseException {
        // 添加记录
        if (name == null || "".equals(name)) throw new BusinessException("类别名称不能为空");
        if (desc == null || "".equals(desc)) throw new BusinessException("请补充类别描述");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from car_type where type_name = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                rs.close();
                pst.close();
                throw new BusinessException("该类别已存在");
            }
            rs.close();
            pst.close();

            sql = "insert into car_type(type_name,description) values(?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, desc);
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
