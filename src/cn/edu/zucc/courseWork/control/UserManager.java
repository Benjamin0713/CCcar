package cn.edu.zucc.courseWork.control;

import cn.edu.zucc.courseWork.itf.IUserManager;
import cn.edu.zucc.courseWork.model.CCStaff;
import cn.edu.zucc.courseWork.model.CCUser;
import cn.edu.zucc.courseWork.util.BaseException;
import cn.edu.zucc.courseWork.util.BusinessException;
import cn.edu.zucc.courseWork.util.DBUtil;
import cn.edu.zucc.courseWork.util.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserManager implements IUserManager {
    public CCUser reg(String userid, String name,String sex,String pwd,String pwd2,String tell,String email,String city) throws BaseException{
        if(userid == null || "".equals(userid)) throw new BusinessException("账号不能为空");
        if(name ==null ||"".equals(name)) throw new BusinessException("用户名不能为空");
        if(sex ==null||"".equals(sex)) throw new BusinessException("性别不能为空");
        if(tell ==null||"".equals(tell)) throw new BusinessException("电话号码不能为空");
        if(email ==null||"".equals(email)) throw new BusinessException("邮箱不能为空");
        if(city==null||"".equals(city)) throw new BusinessException("所在城市不能为空");
        if(pwd == null || "".equals(pwd)) throw new BusinessException("密码不能为空");
        if(!pwd.equals(pwd2)) throw new BusinessException("两次密码不一致");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            //检查用户是否已存在
            String sql = "select user_id from user where user_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,userid);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("用户已经存在");
            }
            rs.close();
            pst.close();
            sql = "insert into user(user_id,name,sex,pwd,tell_num,email,city,register_time) values(?,?,?,?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setString(1,userid);
            pst.setString(2,name);
            pst.setString(3,sex);
            pst.setString(4,pwd);
            pst.setString(5,tell);
            pst.setString(6,email);
            pst.setString(7,city);
            pst.setTimestamp(8,new java.sql.Timestamp(System.currentTimeMillis()));
            pst.execute();
            CCUser user = new CCUser();
            user.setUser_city(city);
            user.setUser_email(email);
            user.setUser_sex(sex);
            user.setUser_tell_num(tell);
            user.setUser_name(name);
            user.setUser_register_time(new Date());
            user.setUser_id(Integer.parseInt(userid));
            user.setUser_pwd(pwd);
            return user;
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
//        return null;
    }
    public CCUser login(String name, String pwd) throws BaseException {
// 用户登录检验
        if(name == null || "".equals(name)) throw new BusinessException("账号不能为空");
        if(pwd == null || "".equals(pwd)) throw new BusinessException("密码不能为空");
        Connection conn=null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from user where name = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            ResultSet rs = pst.executeQuery();
            if(!rs.next()){
//                rs.close();
//                pst.close();
                throw new BusinessException("用户不存在");
            }
            int user_id=rs.getInt(1);
            if(!pwd.equals(rs.getString(4))) throw new BusinessException("密码错误");
            rs.close();
            pst.close();
//            rs.close();
//            pst.close();
//            sql = "select pwd from user where pwd = ?";
//            pst=conn.prepareStatement(sql);
//            pst.setString(1,pwd);
//            rs = pst.executeQuery();
//            if(!rs.next()){
//                rs.close();
//                pst.close();
//                throw new BusinessException("密码错误");
//            }
            CCUser user = new CCUser();
            user.setUser_id(user_id);
            user.setUser_pwd(pwd);
            user.setUser_name(name);
            return user;
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
// 		return null;
    }
    public void changePwd(CCUser user, String oldPwd, String newPwd,
                          String newPwd2) throws BaseException{
        if(!oldPwd.equals(user.getUser_pwd())) throw new BusinessException("原密码错误");
        if(oldPwd.equals(newPwd)) throw new BusinessException("新密码不能与原密码一致");
        if(!newPwd.equals(newPwd2)) throw new BusinessException("与第一次输入新密码不一致");
        Connection conn=null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from user where name=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            String user_name=user.getUser_name();
            pst.setString(1,user_name);
            ResultSet rs = pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("登录用户不存在");
            }
            rs.close();
            pst.close();
            sql = "update user set pwd=? where name=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,newPwd);
            pst.setString(2,user_name);
            pst.execute();
            pst.close();

            user.setUser_name(user.getUser_name());
            user.setUser_pwd(newPwd);
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
    public void changeName(CCUser user, String oldName, String newName) throws BaseException{
        if(!oldName.equals(user.getUser_name())) throw new BusinessException("原用户名输入错误");
        if(oldName.equals(newName)) throw new BusinessException("新用户名不能与原用户一致");
        Connection conn=null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from user where pwd=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,user.getUser_pwd());
            ResultSet rs = pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("登录用户不存在");
            }
            int user_id=rs.getInt(1);
            rs.close();
            pst.close();
            sql = "update user set name=? where pwd=? and user_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,newName);
            pst.setString(2,user.getUser_pwd());
            pst.setInt(3,user_id);
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
    public List<CCUser> loadAllshop() throws BaseException{
        List<CCUser> result = new ArrayList<CCUser>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from user where name = ?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,CCUser.currentLoginUser.getUser_name());
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                CCUser user = new CCUser();
                user.setUser_id(rs.getInt(1));
                user.setUser_name(rs.getString(2));
                user.setUser_sex(rs.getString(3));
                user.setUser_pwd(rs.getString(4));
                user.setUser_tell_num(rs.getString(5));
                user.setUser_email(rs.getString(6));
                user.setUser_city(rs.getString(7));
                user.setUser_register_time(rs.getTimestamp(8));
                result.add(user);
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
    public List<CCUser> loadAll() throws BaseException{
        List<CCUser> result = new ArrayList<CCUser>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from user ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                CCUser user = new CCUser();
                user.setUser_id(rs.getInt(1));
                user.setUser_name(rs.getString(2));
                user.setUser_sex(rs.getString(3));
//                user.setUser_pwd(rs.getString(4));
                user.setUser_tell_num(rs.getString(5));
                user.setUser_email(rs.getString(6));
                user.setUser_city(rs.getString(7));
                user.setUser_register_time(rs.getTimestamp(8));
                result.add(user);
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
    public List<CCUser> loadCost() throws BaseException{
        List<CCUser> result = new ArrayList<CCUser>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from usercost ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                CCUser user = new CCUser();
                user.setUser_id(rs.getInt(1));
                user.setUser_name(rs.getString(2));
                user.setTotal_order(rs.getInt(3));
                user.setTotal_cost(rs.getFloat(4));
                result.add(user);
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
