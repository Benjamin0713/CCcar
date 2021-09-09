package cn.edu.zucc.courseWork.control;

import cn.edu.zucc.courseWork.itf.IAllocateManager;
import cn.edu.zucc.courseWork.model.CCAllocation;
import cn.edu.zucc.courseWork.model.CCScrap;
import cn.edu.zucc.courseWork.util.BaseException;
import cn.edu.zucc.courseWork.util.DBUtil;
import cn.edu.zucc.courseWork.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AllocateManager implements IAllocateManager {
    public List<CCAllocation> loadAll() throws BaseException {
        List<CCAllocation> result = new ArrayList<CCAllocation>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from allocation";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                CCAllocation a=new CCAllocation();
                a.setNet_id_in(rs.getInt(1));
                a.setCar_id(rs.getInt(2));
                a.setNet_id_out(rs.getInt(3));
                a.setAllocate_id(rs.getInt(4));
                a.setAllocate_time(rs.getDate(5));
                result.add(a);
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
}
