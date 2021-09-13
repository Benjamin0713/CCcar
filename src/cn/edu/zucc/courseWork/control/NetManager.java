package cn.edu.zucc.courseWork.control;

import cn.edu.zucc.courseWork.itf.INetManager;
import cn.edu.zucc.courseWork.model.CCNet;
import cn.edu.zucc.courseWork.model.CCStaff;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(tell);
        if(m.matches()==false) throw new BusinessException("手机号码格式错误");
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
    public void delete(CCNet net) throws BaseException {
        // 删除
        Connection conn=null;
        try {
            conn = DBUtil.getConnection();
            String sql="select * from net_information where Net_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,net.getNet_id());
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next()){
                rs.close();pst.close();
                throw new BusinessException("该网点不存在");
            }
            rs.close();pst.close();
            sql="select * from car_information where Net_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,net.getNet_id());
            rs=pst.executeQuery();
            if(rs.next()){
                rs.close();pst.close();
                throw new BusinessException("该网点无法删除");
            }
            rs.close();pst.close();
            sql="select * from staff where Net_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,net.getNet_id());
            rs=pst.executeQuery();
            if(rs.next()){
                rs.close();pst.close();
                throw new BusinessException("该网点无法删除");
            }
            rs.close();pst.close();
            sql="delete from net_information where Net_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,net.getNet_id());
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
    public void changenet(String Net_id, String name, String city,
                          String add,String tell) throws BaseException{
        if(Net_id==null||"".equals(Net_id)) throw new BusinessException("修改编号不能为空");
        Boolean namemod=false;Boolean citymod=false;Boolean addmod=false;Boolean tellmod=false;
        if(!(tell==null||"".equals(tell))){
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(tell);
            if(m.matches()==false) throw new BusinessException("手机号码格式错误");
        }
        Connection conn=null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from net_information where Net_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(Net_id));
            ResultSet rs = pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("该网点不存在");
            }else {
                if(!(name==null||"".equals(name))&&(!name.equals(rs.getString(2)))) namemod=true;
                if(!(city==null||"".equals(city))&&(!city.equals(rs.getString(3)))) citymod=true;
                if(!(add==null||"".equals(add))&&(!add.equals(rs.getString(4)))) addmod=true;
                if(!(tell==null||"".equals(tell))&&(!tell.equals(rs.getString(5)))) tellmod=true;
                rs.close();pst.close();
                if(namemod==true){
                    sql="select * from net_information where Net_name=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1, name);
                    rs=pst.executeQuery();
                    if(rs.next()){
                        rs.close();pst.close();
                        throw new BusinessException("该名称已经存在");
                    }
                    rs.close();pst.close();
                    sql="update net_information set Net_name=? where Net_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1,name);
                    pst.setInt(2, Integer.parseInt(Net_id));
                    pst.execute();
                    pst.close();
                }
                if(citymod==true){
                    sql="update net_information set city=? where Net_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1,city);
                    pst.setInt(2, Integer.parseInt(Net_id));
                    pst.execute();
                    pst.close();
                }
                if(addmod==true){
                    sql="update net_information set address=? where Net_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1,add);
                    pst.setInt(2, Integer.parseInt(Net_id));
                    pst.execute();
                    pst.close();
                }
                if(tellmod==true){
                    sql="update net_information set tellphone=? where Net_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1,tell);
                    pst.setInt(2, Integer.parseInt(Net_id));
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
