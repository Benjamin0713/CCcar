package cn.edu.zucc.courseWork.control;

import cn.edu.zucc.courseWork.itf.IScrapManager;
import cn.edu.zucc.courseWork.model.*;
import cn.edu.zucc.courseWork.util.BaseException;
import cn.edu.zucc.courseWork.util.BusinessException;
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
    public void carscrap(CCCar car) throws BaseException {
        Connection conn=null;
        try {
            conn = DBUtil.getConnection();
            String sql="select * from car_information where car_id =?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,car.getCar_id());
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("该车辆不存在");
            }
            String state=rs.getString(5);
            if(state.equals("out")) throw new BusinessException("该车被租出，不可报废");
            rs.close();
            pst.close();
            sql="select * from scrap where car_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,car.getCar_id());
            rs=pst.executeQuery();
            if(rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("该车已报废");
            }
            rs.close();
            pst.close();
            sql="update car_information set state='scrap' where car_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,car.getCar_id());
            pst.execute();
            pst.close();
            sql="select max(Scrap_id),max(content) from scrap";
            pst=conn.prepareStatement(sql);
            int scarp_id=0;
            String content=null;
            rs=pst.executeQuery();
            if(rs.next()){
                scarp_id=rs.getInt(1)+1;
                content=rs.getString(2)+1;
            }
            pst.close();
            rs.close();
            sql="insert into scrap(car_id,staff_id,Scrap_id,Scrap_time,content) values(?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,car.getCar_id());
            pst.setInt(2, CCStaff.currentLoginStaff.getStaff_id());
            pst.setInt(3,scarp_id);
            pst.setTimestamp(4,new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setString(5,content);
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
}
