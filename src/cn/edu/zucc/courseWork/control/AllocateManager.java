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
            String sql = "SELECT *\n" +
                    "from (SELECT Net_name as innet,car_id,Net_id_out,allocate_id,allocate_time\n" +
                    "from allocation a,net_information b\n" +
                    "WHERE a.Net_id_in=b.Net_id ) d,net_information c\n" +
                    "WHERE d.Net_id_out=c.Net_id";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                CCAllocation a=new CCAllocation();
                a.setInnetname(rs.getString(1));
                a.setCar_id(rs.getInt(2));
                a.setOutnetname(rs.getString(7));
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
