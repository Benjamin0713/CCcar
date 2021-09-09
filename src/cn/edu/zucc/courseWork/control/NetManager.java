package cn.edu.zucc.courseWork.control;

import cn.edu.zucc.courseWork.itf.INetManager;
import cn.edu.zucc.courseWork.model.CCNet;
import cn.edu.zucc.courseWork.model.CCStaff;
import cn.edu.zucc.courseWork.util.BaseException;
import cn.edu.zucc.courseWork.util.BusinessException;
import cn.edu.zucc.courseWork.util.DBUtil;
import cn.edu.zucc.courseWork.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NetManager implements INetManager {
    public List<CCNet> loadAll() throws BaseException {
        List<CCNet> result = new ArrayList<CCNet>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * from net_information";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                CCNet s = new CCNet();
                s.setNet_id(rs.getInt(1));
                s.setNet_name(rs.getString(2));
                s.setNet_city(rs.getString(3));
                s.setNet_address(rs.getString(4));
                s.setNet_tellphone(rs.getString(5));
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
    public void addNet(String name, String city, String add, String tell) throws BaseException {
        // 添加记录
        if (name == null || "".equals(name)) throw new BusinessException("网点名称不能为空");
        if (city == null || "".equals(city)) throw new BusinessException("所在城市不能为空");
        if (add == null || "".equals(add)) throw new BusinessException("请提供详细地址");
        if (tell == null || "".equals(tell)) throw new BusinessException("请提供联系方式");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from net_information where Net_name=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                rs.close();
                pst.close();
                throw new BusinessException("该网点已存在");
            }
            rs.close();
            pst.close();

            sql = "insert into net_information(Net_name,city,address,tellphone) values(?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, city);
            pst.setString(3, add);
            pst.setString(4, tell);
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
