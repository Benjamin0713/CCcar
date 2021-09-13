package cn.edu.zucc.courseWork.control;

import cn.edu.zucc.courseWork.itf.ICouponManager;
import cn.edu.zucc.courseWork.model.*;
import cn.edu.zucc.courseWork.util.BaseException;
import cn.edu.zucc.courseWork.util.BusinessException;
import cn.edu.zucc.courseWork.util.DBUtil;
import cn.edu.zucc.courseWork.util.DbException;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class CouponManager implements ICouponManager {
    public List<CCCoupon> loadAllshop() throws BaseException {
        List<CCCoupon> result = new ArrayList<CCCoupon>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT coupon_id,content,money,start_date,end_date,Net_name\n" +
                    "from coupon a,net_information b\n" +
                    "where a.Net_id=b.Net_id";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                CCCoupon coupon = new CCCoupon();
                coupon.setCoupon_id(rs.getInt(1));
                coupon.setCoupon_content(rs.getString(2));
                coupon.setCoupon_money(rs.getFloat(3));
                coupon.setCoupon_start_date(rs.getTimestamp(4));
                coupon.setCoupon_end_date(rs.getTimestamp(5));
                coupon.setNetname(rs.getString(6));
                result.add(coupon);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return result;
    }
    public List<CCHoldCoupon> loadAllHold() throws BaseException {
        List<CCHoldCoupon> result = new ArrayList<CCHoldCoupon>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT hold_id,coupon_id,hold_time,content,money,start_date,end_date,Net_name\n" +
                    "from couponhold a,net_information b\n" +
                    "where a.Net_id=b.Net_id and user_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            int user_id= CCUser.currentLoginUser.getUser_id();
//            System.out.println(user_id);
            pst.setInt(1,user_id);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                CCHoldCoupon c = new CCHoldCoupon();
                c.setHold_id(rs.getInt(1));
//                c.setUser_id(rs.getInt(2));
                c.setCoupon_id(rs.getInt(2));
                c.setHold_time(rs.getTimestamp(3));
                c.setContent(rs.getString(4));
                c.setMoney(rs.getFloat(5));
                c.setStart_date(rs.getTimestamp(6));
                c.setEnd_date(rs.getTimestamp(7));
                c.setNetname(rs.getString(8));
                result.add(c);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return result;
    }
    public void addCoupon(String context, String money, String start_date, String end_date,String Net_id) throws BaseException {
        // 添加记录
        if (context == null || "".equals(context)) throw new BusinessException("内容至少写点东西");
        if (money == null || "".equals(money)) throw new BusinessException("金额不能设为空");
        if (start_date == null || "".equals(start_date)) throw new BusinessException("起始时间不能为空");
        if (end_date == null || "".equals(end_date)) throw new BusinessException("结束时间不能为空");
        if (Net_id ==null||"".equals(Net_id)) throw new BusinessException("优惠网点不能为空");
        int Net_now=CCStaff.currentLoginStaff.getStaff_Net_id();
//        System.out.println(Net_now);

        if(start_date.compareTo(end_date)>0) throw new BusinessException("时间设置错误");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from coupon where content=? and money=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, context);
            pst.setString(2, money);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                rs.close();
                pst.close();
                throw new BusinessException("该优惠券已存在");
            }
            rs.close();
            pst.close();
            sql="select Net_id from net_information where Net_name=?";
            pst=conn.prepareStatement(sql);
            pst.setString(1,Net_id);
            rs=pst.executeQuery();
            if(!rs.next()){
                rs.close();pst.close();
                throw new BusinessException("网点不存在");
            }
            int netid=rs.getInt(1);
            if(netid!=Net_now) throw new BusinessException("只能添加自己所在网点的优惠券");
            rs.close();pst.close();
            sql = "insert into coupon(content,money,start_date,end_date,Net_id) values(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, context);
            pst.setString(2, money);
            pst.setString(3, start_date);
            pst.setString(4, end_date);
            pst.setInt(5,netid);
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
    public void holdcoupon(CCCoupon coupon) throws BaseException {
        Connection conn=null;
        try {
            conn = DBUtil.getConnection();
            String sql="select * from coupon where coupon_id =?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,coupon.getCoupon_id());
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("该优惠券不存在");
            }
            rs.close();
            pst.close();
            sql="select * from coupon_hold where user_id=? and coupon_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,CCUser.getCurrentLoginUser().getUser_id());
            pst.setInt(2,coupon.getCoupon_id());
            rs=pst.executeQuery();
            if(rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("你已经领取过该优惠券");
            }
            rs.close();
            pst.close();

            sql="insert into coupon_hold(user_id,coupon_id,hold_time) values(?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,CCUser.currentLoginUser.getUser_id());
            pst.setInt(2,coupon.getCoupon_id());
            pst.setTimestamp(3,new java.sql.Timestamp(System.currentTimeMillis()));
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
    public void delete(CCCoupon coupon) throws BaseException {
        // 删除
        Connection conn=null;
        try {
            conn = DBUtil.getConnection();
            String sql="select * from coupon where coupon_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,coupon.getCoupon_id());
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next()){
                rs.close();pst.close();
                throw new BusinessException("该优惠券不存在");
            }
            rs.close();pst.close();
            sql="select * from orderlist where coupon_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,coupon.getCoupon_id());
            rs=pst.executeQuery();
            if(rs.next()){
                throw new BusinessException("该优惠券无法删除");
            }
            rs.close();pst.close();
            sql="delete from coupon where coupon_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,coupon.getCoupon_id());
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
    public void changecou(String coupon_id, String content, String money
                          ,String start,String end) throws BaseException{
        if(coupon_id==null||"".equals(coupon_id)) throw new BusinessException("修改编号不能为空");
        if(start.compareTo(end)>0) throw new BusinessException("时间设置错误");
        Boolean contmod=false;Boolean monmod=false;Boolean startmod=false;Boolean endmod=false;
        Connection conn=null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from coupon where coupon_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(coupon_id));
            ResultSet rs = pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("该优惠券不存在");
            }else {
                if(!(content==null||"".equals(content))&&(!content.equals(rs.getString(2)))) contmod=true;
                if(!(money==null||"".equals(money))&&(!money.equals(rs.getString(3)))) monmod=true;
                if(!(start==null||"".equals(start))&&(!start.equals(rs.getString(4)))) startmod=true;
                if(!(end==null||"".equals(end))&&(!end.equals(rs.getString(5)))) endmod=true;
                rs.close();pst.close();
                if(monmod==true){
                    sql="update coupon set money=? where coupon_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1,money);
                    pst.setInt(2, Integer.parseInt(coupon_id));
                    pst.execute();
                    pst.close();
                }
                if(contmod==true){
                    sql="update coupon set content=? where coupon_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1,content);
                    pst.setInt(2, Integer.parseInt(coupon_id));
                    pst.execute();
                    pst.close();
                }
                if(startmod==true){
                    sql="update coupon set start_date=? where coupon_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1,start);
                    pst.setInt(2, Integer.parseInt(coupon_id));
                    pst.execute();
                    pst.close();
                }
                if(endmod==true){
                    sql="update coupon set end_date=? where coupon_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1,end);
                    pst.setInt(2, Integer.parseInt(coupon_id));
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
