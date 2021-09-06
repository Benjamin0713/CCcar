package cn.edu.zucc.courseWork.control;

import cn.edu.zucc.courseWork.itf.IScrapManager;
import cn.edu.zucc.courseWork.model.CCScrap;
import cn.edu.zucc.courseWork.model.CCUser;
import cn.edu.zucc.courseWork.util.BaseException;
import cn.edu.zucc.courseWork.util.DBUtil;
import cn.edu.zucc.courseWork.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScrapManager implements IScrapManager {
    public List<CCScrap> loadAllshop() throws BaseException {
        List<CCScrap> result = new ArrayList<CCScrap>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from scrap";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                CCScrap scrap=new CCScrap();
                scrap.setCar_id(rs.getInt(1));
                scrap.setStaff_id(rs.getInt(2));
                scrap.setScrap_id(rs.getInt(3));
                scrap.setScrap_time(rs.getTimestamp(4));
                scrap.setScrap_content(rs.getString(5));
                result.add(scrap);
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
