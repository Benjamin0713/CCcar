package cn.edu.zucc.courseWork.control;

import cn.edu.zucc.courseWork.itf.ICarTypeManager;
import cn.edu.zucc.courseWork.model.CCModel;
import cn.edu.zucc.courseWork.model.CCNet;
import cn.edu.zucc.courseWork.model.CCType;
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
    public void delete(CCType type) throws BaseException {
        // 删除
        Connection conn=null;
        try {
            conn = DBUtil.getConnection();
            String sql="select * from car_type where type_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,type.getType_id());
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next()){
                rs.close();pst.close();
                throw new BusinessException("该车类不存在");
            }
            rs.close();pst.close();
            sql="select * from car_model where type_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,type.getType_id());
            rs=pst.executeQuery();
            if(rs.next()){
                rs.close();pst.close();
                throw new BusinessException("该车类无法删除");
            }
            rs.close();pst.close();
            sql="delete from car_type where type_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,type.getType_id());
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
    public void changetype(String type_id, String name, String desc) throws BaseException{
        if(type_id==null||"".equals(type_id)) throw new BusinessException("修改编号不能为空");
        Boolean namemod=false;Boolean descmod=false;
        Connection conn=null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from car_type where type_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(type_id));
            ResultSet rs = pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("该类别不存在");
            }else {
                if(!(name==null||"".equals(name))&&(!name.equals(rs.getString(2)))) namemod=true;
                if(!(desc==null||"".equals(desc)&&(!desc.equals(rs.getString(3))))) descmod=true;
                rs.close();pst.close();
                if(namemod==true){
                    sql="update car_type set type_name=? where type_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1,name);
                    pst.setInt(2, Integer.parseInt(type_id));
                    pst.execute();
                    pst.close();
                }
                if(descmod==true){
                    sql="update car_type set description=? where type_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1,desc);
                    pst.setInt(2, Integer.parseInt(type_id));
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
