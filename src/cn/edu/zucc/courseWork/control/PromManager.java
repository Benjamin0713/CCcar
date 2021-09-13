package cn.edu.zucc.courseWork.control;

import cn.edu.zucc.courseWork.itf.IPromManager;
import cn.edu.zucc.courseWork.model.*;
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

public class PromManager implements IPromManager {
    public List<CCPromotion> loadAll() throws BaseException {
        List<CCPromotion> result = new ArrayList<CCPromotion>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT promotion_id,Net_name,model_name,discount,Num,begin_date,finish_date\n" +
                    "from promotion a,net_information b,car_model c\n" +
                    "where a.model_id=c.model_id and a.Net_id=b.Net_id";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                CCPromotion p = new CCPromotion();
                p.setPromotion_id(rs.getInt(1));
                p.setNetname(rs.getString(2));
                p.setModelname(rs.getString(3));
                p.setDiscount(rs.getFloat(4));
                p.setNumber(rs.getInt(5));
                p.setBegin_date(rs.getDate(6));
                p.setFinish_date(rs.getDate(7));
                result.add(p);
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
    public List<CCPromotion> loadAllshop() throws BaseException {
        List<CCPromotion> result = new ArrayList<CCPromotion>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT promotion_id,Net_name,model_name,discount,Num,begin_date,finish_date from promotion a,net_information b,car_model c where a.model_id=c.model_id and a.Net_id=b.Net_id and Num>0";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                CCPromotion p = new CCPromotion();
                p.setPromotion_id(rs.getInt(1));
                p.setNetname(rs.getString(2));
                p.setModelname(rs.getString(3));
                p.setDiscount(rs.getFloat(4));
                p.setNumber(rs.getInt(5));
                p.setBegin_date(rs.getDate(6));
                p.setFinish_date(rs.getDate(7));
                result.add(p);
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

    public void addPro(String net, String model, String dis, String num, String startdate, String enddate) throws BaseException {
        // 添加记录
        if (model == null || "".equals(model)) throw new BusinessException("汽车车型不能为空");
        if (num == null || "".equals(num)) throw new BusinessException("促销数量不能设为空");
        if (net == null || "".equals(net)) throw new BusinessException("促销网点不能为空");
        if (dis == null || "".equals(dis)) throw new BusinessException("促销折扣不能为空");
        if (startdate == null || "".equals(startdate)) throw new BusinessException("请设置促销开始时间");
        if (enddate == null || "".equals(enddate)) throw new BusinessException("请设置促销结束时间");
        if(startdate.compareTo(enddate)>0) throw new BusinessException("时间设置错误");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();

            String sql = "select model_id from car_model where model_name = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, model);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next()) {
                rs.close();
                pst.close();
                throw new BusinessException("汽车车型不存在");
            }
            int modelid=rs.getInt(1);
            rs.close();
            pst.close();

            sql = "select Net_id from net_information where Net_name = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,net);
            rs = pst.executeQuery();
            if (!rs.next()) {
                rs.close();
                pst.close();
                throw new BusinessException("该网点不存在");
            }
            int netid=rs.getInt(1);
            rs.close();
            pst.close();

            sql = "select * from promotion where Net_id=? and model_id=? and discount=? and Num=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, netid);
            pst.setInt(2, modelid);
            pst.setFloat(3, Float.parseFloat(dis));
            pst.setInt(4, Integer.parseInt(num));
            rs = pst.executeQuery();
            if (rs.next()) {
                rs.close();
                pst.close();
                throw new BusinessException("该促销活动已存在");
            }
            rs.close();
            pst.close();

            sql = "insert into promotion(Net_id,model_id,discount,Num,begin_date,finish_date) values(?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, netid);
            pst.setInt(2, modelid);
            pst.setString(3, dis);
            pst.setString(4, num);
            pst.setString(5, startdate);
            pst.setString(6, enddate);
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
    public void addPronet(String net, String model, String dis, String num, String startdate, String enddate) throws BaseException {
        // 添加记录
        if (model == null || "".equals(model)) throw new BusinessException("汽车车型不能为空");
        if (num == null || "".equals(num)) throw new BusinessException("促销数量不能设为空");
        if (net == null || "".equals(net)) throw new BusinessException("促销网点不能为空");
        if (dis == null || "".equals(dis)) throw new BusinessException("促销折扣不能为空");
        if (startdate == null || "".equals(startdate)) throw new BusinessException("请设置促销开始时间");
        if (enddate == null || "".equals(enddate)) throw new BusinessException("请设置促销结束时间");
        if(startdate.compareTo(enddate)>0) throw new BusinessException("时间设置错误");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();

            String sql = "select model_id from car_model where model_name = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, model);
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next()) {
                rs.close();
                pst.close();
                throw new BusinessException("汽车车型不存在");
            }
            int modelid=rs.getInt(1);
            rs.close();
            pst.close();

            sql = "select Net_id from net_information where Net_name = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,net);
            rs = pst.executeQuery();
            if (!rs.next()) {
                rs.close();
                pst.close();
                throw new BusinessException("该网点不存在");
            }
            int netid=rs.getInt(1);
            if(netid!=CCStaff.currentLoginStaff.getStaff_Net_id()) throw new BusinessException("只能添加自己网点");
            rs.close();
            pst.close();

            sql = "select * from promotion where Net_id=? and model_id=? and discount=? and Num=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, netid);
            pst.setInt(2, modelid);
            pst.setFloat(3, Float.parseFloat(dis));
            pst.setInt(4, Integer.parseInt(num));
            rs = pst.executeQuery();
            if (rs.next()) {
                rs.close();
                pst.close();
                throw new BusinessException("该促销活动已存在");
            }
            rs.close();
            pst.close();

            sql = "insert into promotion(Net_id,model_id,discount,Num,begin_date,finish_date) values(?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, netid);
            pst.setInt(2, modelid);
            pst.setString(3, dis);
            pst.setString(4, num);
            pst.setString(5, startdate);
            pst.setString(6, enddate);
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
            String sql = "SELECT get_id,promotion_id,get_time,Net_name,model_name,discount,begin_date,finish_date\n" +
                    "from proget a,car_model b,net_information c\n" +
                    "where a.model_id=b.model_id and a.Net_id=c.Net_id and user_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, CCUser.currentLoginUser.getUser_id());
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                CCProGet c = new CCProGet();
                c.setGet_id(rs.getInt(1));
//                c.setUser_id(rs.getInt(2));
                c.setPro_id(rs.getInt(2));
                c.setGet_time(rs.getTimestamp(3));
                c.setNetname(rs.getString(4));
                c.setModelname(rs.getString(5));
                c.setDiscount(rs.getFloat(6));
//                c.setNum(rs.getInt(8));
                c.setBegindate(rs.getTimestamp(7));
                c.setFinishdate(rs.getTimestamp(8));
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
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from promotion where promotion_id =?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, pro.getPromotion_id());
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next()) {
                rs.close();
                pst.close();
                throw new BusinessException("该促销不存在");
            }
            rs.close();
            pst.close();
            sql = "select * from proget where user_id=? and promotion_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, CCUser.getCurrentLoginUser().getUser_id());
            pst.setInt(2, pro.getPromotion_id());
            rs = pst.executeQuery();
            if (rs.next()) {
                rs.close();
                pst.close();
                throw new BusinessException("你已经领取过该折扣");
            }
            rs.close();
            pst.close();

            sql = "insert into pro_get(user_id,promotion_id,get_time) values(?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, CCUser.currentLoginUser.getUser_id());
            pst.setInt(2, pro.getPromotion_id());
            pst.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
            pst.execute();
            pst.close();
            sql = "update promotion set Num=Num-1 where promotion_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, pro.getPromotion_id());
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

    public void delete(CCPromotion promotion) throws BaseException {
        // 删除
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from promotion where promotion_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, promotion.getPromotion_id());
            java.sql.ResultSet rs = pst.executeQuery();
            if (!rs.next()) {
                rs.close();
                pst.close();
                throw new BusinessException("该折扣不存在");
            }
            rs.close();
            pst.close();
            sql = "select * from orderlist where promotion_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, promotion.getPromotion_id());
            rs = pst.executeQuery();
            if (rs.next()) {
                throw new BusinessException("该促销无法删除");
            }
            rs.close();
            pst.close();
            sql = "delete from promotion where promotion_id=?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, promotion.getPromotion_id());
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

    public void changepro(String promotion_id, String net, String model,String start,
                          String end,String num, String money) throws BaseException {
        if (promotion_id == null || "".equals(promotion_id)) throw new BusinessException("修改编号不能为空");
        if(start.compareTo(end)>0) throw new BusinessException("时间设置错误");
        Boolean nummod = false;
        Boolean monmod = false;
        Boolean netmod = false;
        Boolean startmod = false;
        Boolean endmod = false;
        Boolean modelmod = false;
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT *\n" +
                    "from promotion a,car_model b,net_information c\n" +
                    "WHERE a.Net_id=c.Net_id and a.model_id=b.model_id and promotion_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(promotion_id));
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) {
                rs.close();
                pst.close();
                throw new BusinessException("该促销不存在");
            } else {
                if (!(model == null || "".equals(model)) && (!model.equals(rs.getString(10)))) modelmod = true;
                if (!(money == null || "".equals(money)) && (!money.equals(rs.getString(4)))) monmod = true;
                if (!(net == null || "".equals(net)) && (!net.equals(rs.getString(18)))) netmod = true;
                if (!(start == null || "".equals(start)) && (!start.equals(rs.getString(6)))) startmod = true;
                if (!(end == null || "".equals(end)) && (!end.equals(rs.getString(7)))) endmod = true;
                if (!(num == null || "".equals(num)) && (!num.equals(rs.getString(5)))) nummod = true;
                rs.close();
                pst.close();
                if (monmod == true) {
                    sql = "update promotion set discount=? where promotion_id=?";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, money);
                    pst.setInt(2, Integer.parseInt(promotion_id));
                    pst.execute();
                    pst.close();
                }
                if (nummod == true) {
                    sql = "update promotion set Num=? where promotion_id=?";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, num);
                    pst.setInt(2, Integer.parseInt(promotion_id));
                    pst.execute();
                    pst.close();
                }
                if (netmod == true) {
                    sql = "select Net_id from net_information where Net_name=?";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, net);
                    rs = pst.executeQuery();
                    if (!rs.next()) {
                        rs.close();
                        pst.close();
                        throw new BusinessException("该网点不存在");
                    }
                    int netid=rs.getInt(1);
                    rs.close();
                    pst.close();
                    sql = "update promotion set Net_id=? where promotion_id=?";
                    pst = conn.prepareStatement(sql);
                    pst.setInt(1, netid);
                    pst.setInt(2, Integer.parseInt(promotion_id));
                    pst.execute();
                    pst.close();
                }
                if (modelmod == true) {
                    sql = "select model_id from car_model where model_name=?";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, model);
                    rs = pst.executeQuery();
                    if (!rs.next()) {
                        rs.close();
                        pst.close();
                        throw new BusinessException("该车型不存在");
                    }
                    int modelid=rs.getInt(1);
                    rs.close();
                    pst.close();
                    sql = "update promotion set model_id=? where promotion_id=?";
                    pst = conn.prepareStatement(sql);
                    pst.setInt(1, modelid);
                    pst.setInt(2, Integer.parseInt(promotion_id));
                    pst.execute();
                    pst.close();
                }
                if (startmod == true) {
                    sql = "update promotion set begin_date=? where promotion_id=?";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, start);
                    pst.setInt(2, Integer.parseInt(promotion_id));
                    pst.execute();
                    pst.close();
                }
                if (endmod == true) {
                    sql = "update promotion set finish_date=? where promotion_id=?";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, end);
                    pst.setInt(2, Integer.parseInt(promotion_id));
                    pst.execute();
                    pst.close();
                }
            }
            rs.close();
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
}
