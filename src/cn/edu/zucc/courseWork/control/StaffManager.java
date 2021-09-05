package cn.edu.zucc.courseWork.control;

import cn.edu.zucc.courseWork.itf.IStaffManager;
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

public class StaffManager implements IStaffManager {
    public CCStaff reg(String staff_id,String net_id,String staffname, String pwd, String pwd2) throws BaseException {
        if(staffname == null || "".equals(staffname)) throw new BusinessException("用户名不能为空");
        if(net_id==null||"".equals(net_id)) throw new BusinessException("所属网点不能为空");
        if(staff_id==null||"".equals(staff_id)) throw new BusinessException("工作编号不能为空");
        if(pwd == null || "".equals(pwd)) throw new BusinessException("密码不能为空");
        if(!pwd.equals(pwd2)) throw new BusinessException("两次密码不一致");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            //检查用户是否已存在
            String sql = "select name from staff where name = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,staffname);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("工作人员已经存在");
            }
            rs.close();
            pst.close();
            sql = "insert into staff(staff_id,Net_id,name,pwd) values(?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setString(1,staff_id);
            pst.setString(2,net_id);
            pst.setString(3,staffname);
            pst.setString(4,pwd);
            pst.execute();
            CCStaff staff = new CCStaff();
            staff.setStaff_id(Integer.parseInt(staff_id));
            staff.setStaff_Net_id(Integer.parseInt(net_id));
            staff.setStaff_name(staffname);
            staff.setStaff_pwd(pwd);
            return staff;
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
    public CCStaff login(String name, String pwd) throws BaseException {
        // 用户登录检验
        if(name == null || "".equals(name)) throw new BusinessException("账号不能为空");
        if(pwd == null || "".equals(pwd)) throw new BusinessException("密码不能为空");
        Connection conn=null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from staff where name = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,name);
            ResultSet rs = pst.executeQuery();
            if(!rs.next()){
//                rs.close();
//                pst.close();
                throw new BusinessException("工作人员不存在");
            }
            if(!pwd.equals(rs.getString(4))) throw new BusinessException("密码错误");
            rs.close();
            pst.close();
//            rs.close();
//            pst.close();
//            sql = "select * from staff where pwd = ?";
//            pst=conn.prepareStatement(sql);
//            pst.setString(1,pwd);
//            rs = pst.executeQuery();
//            if(!rs.next()){
//                rs.close();
//                pst.close();
//                throw new BusinessException("密码错误");
//            }
            CCStaff staff = new CCStaff();
            staff.setStaff_pwd(pwd);
            staff.setStaff_name(name);
            return staff;
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
    public void changePwd(CCStaff staff, String oldPwd, String newPwd,
                          String newPwd2) throws BaseException{
        if(!oldPwd.equals(staff.getStaff_pwd())) throw new BusinessException("原密码错误");
        if(oldPwd.equals(newPwd)) throw new BusinessException("新密码不能与原密码一致");
        if(!newPwd.equals(newPwd2)) throw new BusinessException("与第一次输入新密码不一致");
        Connection conn=null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from staff where name=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            String staff_name=staff.getStaff_name();
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

            staff.setStaff_name(staff.getStaff_name());
            staff.setStaff_pwd(newPwd);
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
    public List<CCStaff> loadAllshop() throws BaseException{//用户界面商家
        List<CCStaff> result = new ArrayList<CCStaff>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from staff where name = ?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,CCStaff.currentLoginStaff.getStaff_name());
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                CCStaff s = new CCStaff();
                s.setStaff_id(rs.getInt(1));
                s.setStaff_Net_id(rs.getInt(2));
                s.setStaff_name(rs.getString(3));
                s.setStaff_pwd(rs.getString(4));
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
}
