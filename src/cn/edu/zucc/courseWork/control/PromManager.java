package cn.edu.zucc.courseWork.control;

import cn.edu.zucc.courseWork.itf.IPromManager;
import cn.edu.zucc.courseWork.model.*;
import cn.edu.zucc.courseWork.util.BaseException;
import cn.edu.zucc.courseWork.util.BusinessException;
import cn.edu.zucc.courseWork.util.DBUtil;
import cn.edu.zucc.courseWork.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PromManager implements IPromManager {
    public List<CCPromotion> loadAllshop() throws BaseException {
        List<CCPromotion> result = new ArrayList<CCPromotion>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from promotion";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                CCPromotion p=new CCPromotion();
                p.setPromotion_id(rs.getInt(1));
                p.setPromotion_Net_id(rs.getInt(2));
                p.setPromotion_model_id(rs.getInt(3));
                p.setDiscount(rs.getFloat(4));
                p.setNumber(rs.getInt(5));
                p.setBegin_date(rs.getDate(6));
                p.setFinish_date(rs.getDate(7));
                result.add(p);
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
    public void addPro(String net, String model, String dis, String num,String startdate,String enddate) throws BaseException {
        // 添加记录
        if (model == null || "".equals(model)) throw new BusinessException("汽车车型不能为空");
        if (num == null || "".equals(num)) throw new BusinessException("促销数量不能设为空");
        if (net == null || "".equals(net)) throw new BusinessException("促销网点不能为空");
        if (dis == null || "".equals(dis)) throw new BusinessException("促销折扣不能为空");
        if (startdate == null || "".equals(startdate)) throw new BusinessException("请设置促销开始时间");
        if (enddate == null || "".equals(enddate)) throw new BusinessException("请设置促销结束时间");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from promotion where Net_id=? and model_id=? and discount=? and Num=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(net));
            pst.setInt(2, Integer.parseInt(model));
            pst.setFloat(3, Float.parseFloat(dis));
            pst.setInt(4, Integer.parseInt(num));
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                rs.close();
                pst.close();
                throw new BusinessException("该促销活动已存在");
            }
            rs.close();
            pst.close();

            sql="select * from car_model where model_id = ?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(model));
            rs=pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("汽车车型不存在");
            }
            rs.close();
            pst.close();

            sql="select * from net_information where Net_id = ?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(net));
            rs=pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("该网点不存在");
            }
            rs.close();
            pst.close();

            sql = "insert into promotion(Net_id,model_id,discount,Num,begin_date,finish_date) values(?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, net);
            pst.setString(2, model);
            pst.setString(3, dis);
            pst.setString(4, num);
            pst.setString(5,startdate);
            pst.setString(6,enddate);
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
    public List<CCProGet> loadAllHold() throws BaseException {
        List<CCProGet> result = new ArrayList<CCProGet>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from proget";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                CCProGet c = new CCProGet();
                c.setGet_id(rs.getInt(1));
//                c.setUser_id(rs.getInt(2));
                c.setPro_id(rs.getInt(3));
                c.setGet_time(rs.getTimestamp(4));
                c.setNet_id(rs.getInt(5));
                c.setModel_id(rs.getInt(6));
                c.setDiscount(rs.getFloat(7));
//                c.setNum(rs.getInt(8));
                c.setBegindate(rs.getTimestamp(9));
                c.setFinishdate(rs.getTimestamp(10));
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
    public void holdpro(CCPromotion pro) throws BaseException {
        Connection conn=null;
        try {
            conn = DBUtil.getConnection();
            String sql="select * from promotion where promotion_id =?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,pro.getPromotion_id());
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("该促销不存在");
            }
            rs.close();
            pst.close();
            sql="select * from proget where user_id=? and promotion_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,CCUser.getCurrentLoginUser().getUser_id());
            pst.setInt(2,pro.getPromotion_id());
            rs=pst.executeQuery();
            if(rs.next()){
                rs.close();
                pst.close();
                throw new BusinessException("你已经领取过该折扣");
            }
            rs.close();
            pst.close();

            sql="insert into pro_get(user_id,promotion_id,get_time) values(?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setInt(1, CCUser.currentLoginUser.getUser_id());
            pst.setInt(2,pro.getPromotion_id());
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
}
