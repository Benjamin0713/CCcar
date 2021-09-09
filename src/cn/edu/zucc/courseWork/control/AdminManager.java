package cn.edu.zucc.courseWork.control;

import cn.edu.zucc.courseWork.itf.IAdminManager;
import cn.edu.zucc.courseWork.model.CCAdmin;
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
import java.util.List;

public class AdminManager implements IAdminManager {
    public CCAdmin reg(String staff_id,String staffname, String pwd, String pwd2) throws BaseException {
        if(staffname == null || "".equals(staffname)) throw new BusinessException("用户名不能为空");
        if(staff_id==null||"".equals(staff_id)) throw new BusinessException("管理员编号不能为空");
        if(pwd == null || "".equals(pwd)) throw new BusinessException("密码不能为空");
        if(!pwd.equals(pwd2)) throw new BusinessException("两次密码不一致");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            //检查用户是否已存在
            String sql = "select name from admin where name = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,staffname);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("管理员已经存在");
            }
            rs.close();
            pst.close();
            sql = "insert into admin(staff_id,name,pwd) values(?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setString(1,staff_id);
            pst.setString(2,staffname);
            pst.setString(3,pwd);
            pst.execute();
            CCAdmin admin = new CCAdmin();
            admin.setStaff_id(Integer.parseInt(staff_id));
            admin.setStaff_name(staffname);
            admin.setStaff_pwd(pwd);
            return admin;
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
    public CCAdmin login(String name, String pwd) throws BaseException {
        // 用户登录检验
        if(name == null || "".equals(name)) throw new BusinessException("账号不能为空");
        if(pwd == null || "".equals(pwd)) throw new BusinessException("密码不能为空");
        Connection conn=null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select name,pwd from admin where name = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            ResultSet rs = pst.executeQuery();
            if(!rs.next()){
//                rs.close();
//                pst.close();
                throw new BusinessException("管理员不存在");
            }
            if(!pwd.equals(rs.getString(2))) throw new BusinessException("密码不正确");
            rs.close();
            pst.close();
//            rs.close();
//            pst.close();
//            sql = "select pwd from admin where pwd = ?";
//            pst=conn.prepareStatement(sql);
//            pst.setString(1,pwd);
//            rs = pst.executeQuery();
//            if(!rs.next()){
//                rs.close();
//                pst.close();
//                throw new BusinessException("密码错误");
//            }
            CCAdmin admin = new CCAdmin();
            admin.setStaff_pwd(pwd);
            admin.setStaff_name(name);
            return admin;
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
    public void changePwd(CCAdmin admin, String oldPwd, String newPwd,
                          String newPwd2) throws BaseException{
            if(!oldPwd.equals(admin.getStaff_pwd())) throw new BusinessException("原密码错误");
            if(oldPwd.equals(newPwd)) throw new BusinessException("新密码不能与原密码一致");
            if(!newPwd.equals(newPwd2)) throw new BusinessException("与第一次输入新密码不一致");
            Connection conn=null;
            try {
                conn = DBUtil.getConnection();
                String sql = "select * from staff where name=?";
                PreparedStatement pst = conn.prepareStatement(sql);
                String staff_name=admin.getStaff_name();
                pst.setString(1,staff_name);
                ResultSet rs = pst.executeQuery();
                if(!rs.next()){
                    rs.close();
                    pst.close();
                    throw new BusinessException("登录用户不存在");
                }
                rs.close();
                pst.close();
                sql = "update staff set pwd=? where name=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1,newPwd);
                pst.setString(2,staff_name);
                pst.execute();
                pst.close();

                admin.setStaff_name(admin.getStaff_name());
                admin.setStaff_pwd(newPwd);
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
    public void changeName(CCAdmin admin, String oldName, String newName) throws BaseException{
        if(!oldName.equals(admin.getStaff_name())) throw new BusinessException("原用户名输入错误");
        if(oldName.equals(newName)) throw new BusinessException("新用户名不能与原用户一致");
        Connection conn=null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from admin where pwd=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,admin.getStaff_pwd());
            ResultSet rs = pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("登录管理员不存在");
            }
            int staff_id=rs.getInt(1);
            rs.close();
            pst.close();
            sql = "update admin set name=? where pwd=? and staff_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,newName);
            pst.setString(2,admin.getStaff_pwd());
            pst.setInt(3,staff_id);
            pst.execute();
            pst.close();
//            staff.setStaff_pwd(staff.getStaff_pwd());
//            staff.setStaff_name(newName);
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
    public List<CCAdmin> loadAllshop() throws BaseException{
        List<CCAdmin> result = new ArrayList<CCAdmin>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from admin where name = ?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,CCAdmin.currentLoginAdmin.getStaff_name());
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                CCAdmin admin=new CCAdmin();
                admin.setStaff_id(rs.getInt(1));
                admin.setStaff_name(rs.getString(2));
                admin.setStaff_pwd(rs.getString(3));
                result.add(admin);
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
