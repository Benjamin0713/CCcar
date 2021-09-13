package cn.edu.zucc.courseWork.control;

import cn.edu.zucc.courseWork.itf.IOrderManager;
import cn.edu.zucc.courseWork.model.CCCar;
import cn.edu.zucc.courseWork.model.CCOrder;
import cn.edu.zucc.courseWork.model.CCStaff;
import cn.edu.zucc.courseWork.model.CCUser;
import cn.edu.zucc.courseWork.util.BaseException;
import cn.edu.zucc.courseWork.util.BusinessException;
import cn.edu.zucc.courseWork.util.DBUtil;
import cn.edu.zucc.courseWork.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderManager implements IOrderManager {
    public void ordercar(CCCar car,String date) throws BaseException {
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
            int net_id=rs.getInt(4);
            int car_id=rs.getInt(1);
            int user_id= CCUser.currentLoginUser.getUser_id();
            int model_id=rs.getInt(2);
            rs.close();
            pst.close();
            sql="select * from car_model where model_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,model_id);
            rs=pst.executeQuery();
            if(!rs.next()){
                throw new BusinessException("车型不存在");
            }
            float orginal_amount=rs.getFloat(8);
            rs.close();
            pst.close();
            if(date==null||"".equals(date)){
                sql="insert into orderlist(user_id,car_id,pick_net_id,pick_time," +
                        "Original_mount,order_state) values (?,?,?,?,?,?)";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,user_id);
                pst.setInt(2,car_id);
                pst.setInt(3,net_id);
                pst.setTimestamp(4,new java.sql.Timestamp(System.currentTimeMillis()));
//                pst.setString(5, date);
                pst.setFloat(5,orginal_amount);
                pst.setString(6,"进行中");
                pst.execute();
                pst.close();
                sql="update car_information set state='租出' where car_id=?";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,car_id);
                pst.execute();
                pst.close();
            }else {
                sql="insert into orderlist(user_id,car_id,pick_net_id,pick_time,pick_total_time," +
                        "Original_mount,order_state) values (?,?,?,?,?,?,?)";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,user_id);
                pst.setInt(2,car_id);
                pst.setInt(3,net_id);
                pst.setTimestamp(4,new java.sql.Timestamp(System.currentTimeMillis()));
                pst.setString(5, date);
                pst.setFloat(6,orginal_amount);
                pst.setString(7,"进行中");
                pst.execute();
                pst.close();
                sql="update car_information set state='租出' where car_id=?";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,car_id);
                pst.execute();
                pst.close();
            }
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
    public List<CCOrder> loadAllOver() throws BaseException{
        List<CCOrder> result = new ArrayList<CCOrder>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM (SELECT user_id,coupon_id,promotion_id,car_id,order_id,Net_id,Net_name,pick_time,return_Net_id,return_time,pick_total_time,Original_mount,Settlement_amount,order_state from orderlist a,net_information b WHERE a.pick_Net_id=b.Net_id ) d,net_information c WHERE d.return_Net_id=c.Net_id and user_id = ? and order_state='已支付'";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,CCUser.currentLoginUser.getUser_id());
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                CCOrder o = new CCOrder();
                o.setPick_Net_id(rs.getInt(6));
                o.setOrder_user_id(rs.getInt(1));
                o.setOrder_coupon_id(rs.getInt(2));
                o.setOrder_promotion_id(rs.getInt(3));
                o.setOrder_car_id(rs.getInt(4));
                o.setOrder_id(rs.getInt(5));
                o.setPicknetname(rs.getString(7));
                o.setPick_time(rs.getTimestamp(8));
                o.setReturnnetname(rs.getString(16));
                o.setReturn_time(rs.getTimestamp(10));
                o.setPick_total_time(rs.getInt(11));
                o.setOri_amount(rs.getFloat(12));
                o.setSet_amount(rs.getFloat(13));
                o.setOrder_state(rs.getString(14));
                result.add(o);
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
    public List<CCOrder> loadAll() throws BaseException{
        List<CCOrder> result = new ArrayList<CCOrder>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT user_id,coupon_id,promotion_id,car_id,order_id,Net_id,Net_name,pick_time,return_Net_id,return_time,pick_total_time,Original_mount,Settlement_amount,order_state\n" +
                    "from orderlist a,net_information b\n" +
                    "WHERE a.pick_Net_id=b.Net_id and user_id = ? and order_state='进行中'";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,CCUser.currentLoginUser.getUser_id());
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                CCOrder o = new CCOrder();
                o.setOrder_user_id(rs.getInt(1));
                o.setOrder_coupon_id(rs.getInt(2));
                o.setOrder_promotion_id(rs.getInt(3));
                o.setOrder_car_id(rs.getInt(4));
                o.setOrder_id(rs.getInt(5));
                o.setPick_Net_id(rs.getInt(6));
                o.setPicknetname(rs.getString(7));
                o.setPick_time(rs.getTimestamp(8));
                o.setReturn_Net_id(rs.getInt(9));
                o.setReturn_time(rs.getTimestamp(10));
                o.setPick_total_time(rs.getInt(11));
                o.setOri_amount(rs.getFloat(12));
                o.setSet_amount(rs.getFloat(13));
                o.setOrder_state(rs.getString(14));
                result.add(o);
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
    public List<CCOrder> loadCost(CCUser user) throws BaseException{
        List<CCOrder> result = new ArrayList<CCOrder>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM (SELECT user_id,coupon_id,promotion_id,car_id,order_id,Net_id,Net_name,pick_time,return_Net_id,return_time,pick_total_time,Original_mount,Settlement_amount,order_state from orderlist a,net_information b WHERE a.pick_Net_id=b.Net_id ) d,net_information c WHERE d.return_Net_id=c.Net_id and user_id = ?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,user.getUser_id());
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                CCOrder o = new CCOrder();
                o.setOrder_user_id(rs.getInt(1));
                o.setOrder_coupon_id(rs.getInt(2));
                o.setOrder_promotion_id(rs.getInt(3));
                o.setOrder_car_id(rs.getInt(4));
                o.setOrder_id(rs.getInt(5));
                o.setPick_Net_id(rs.getInt(6));
                o.setPicknetname(rs.getString(7));
                o.setPick_time(rs.getTimestamp(8));
                o.setReturnnetname(rs.getString(16));
                o.setReturn_time(rs.getTimestamp(10));
                o.setPick_total_time(rs.getInt(11));
                o.setOri_amount(rs.getFloat(12));
                o.setSet_amount(rs.getFloat(13));
                o.setOrder_state(rs.getString(14));
                result.add(o);
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
    public List<CCOrder> loadrent(CCCar Car) throws BaseException{
        List<CCOrder> result = new ArrayList<CCOrder>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT *\n" +
                    "FROM (SELECT user_id,coupon_id,promotion_id,car_id,order_id,Net_id,Net_name,pick_time,return_Net_id,return_time,pick_total_time,Original_mount,Settlement_amount,order_state\n" +
                    "from orderlist a,net_information b\n" +
                    "WHERE a.pick_Net_id=b.Net_id ) d,net_information c\n" +
                    "WHERE d.return_Net_id=c.Net_id and car_id = ?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,Car.getCar_id());
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                CCOrder o = new CCOrder();
                o.setOrder_user_id(rs.getInt(1));
                o.setOrder_coupon_id(rs.getInt(2));
                o.setOrder_promotion_id(rs.getInt(3));
                o.setOrder_car_id(rs.getInt(4));
                o.setOrder_id(rs.getInt(5));
                o.setPick_Net_id(rs.getInt(6));
                o.setPicknetname(rs.getString(7));
                o.setPick_time(rs.getTimestamp(8));
                o.setReturnnetname(rs.getString(16));
                o.setReturn_time(rs.getTimestamp(10));
                o.setPick_total_time(rs.getInt(11));
                o.setOri_amount(rs.getFloat(12));
                o.setSet_amount(rs.getFloat(13));
                o.setOrder_state(rs.getString(14));
                result.add(o);
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
    public void endOrder(CCOrder order,String net,String coupon,String pro) throws BaseException {
        if(net==null||"".equals(net)) throw new BusinessException("归还网点必须提供");
        Connection conn=null;
        try {
            conn = DBUtil.getConnection();
//            System.out.println(net+" "+coupon+" "+pro)
            String sql="select * from orderlist where order_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,order.getOrder_id());
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next()){
                rs.close();pst.close();
                throw new BusinessException("该订单已不存在");
            }
            rs.close();pst.close();
            int car_id=order.getOrder_car_id();
            int picknet=order.getPick_Net_id();
            int timeorder=order.getPick_total_time();
            System.out.println(timeorder);
            System.out.println(picknet);
            float orimoney=order.getOri_amount();
            sql="select Net_id from net_information where Net_name=?";
            pst=conn.prepareStatement(sql);
            pst.setString(1, net);
            rs=pst.executeQuery();
            if(!rs.next()){
                rs.close();pst.close();
                throw new BusinessException("归还网点不存在");
            }
            int Net_id=rs.getInt(1);
            rs.close();pst.close();
            int model_id;
            sql="select model_id from car_information where car_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,car_id);
            rs=pst.executeQuery();
            if(rs.next()){
                model_id=rs.getInt(1);
            }else {
                throw new BusinessException("该车辆不存在");
            }
            rs.close();pst.close();
            float coumoney=0;
            Boolean C=false;
            int netcoupon;
            if(coupon==null||"".equals(coupon)){
            }else {
                sql="select money,start_date,end_date,Net_id from couponhold where coupon_id=? and user_id=?";
                pst=conn.prepareStatement(sql);
                pst.setString(1, coupon);
                pst.setInt(2,CCUser.currentLoginUser.getUser_id());
                rs=pst.executeQuery();
                if(rs.next()){
                    if(new Date(System.currentTimeMillis()).getTime()<rs.getDate(2).getTime()||new Date(System.currentTimeMillis()).getTime()>rs.getDate(3).getTime()){
                        throw new BusinessException("优惠券无效");
                    }
                    coumoney=rs.getFloat(1);
                    C=true;
                    netcoupon=rs.getInt(4);
                    if(picknet!=netcoupon) throw new BusinessException("优惠券不属于该网点");
                }else {
                    throw new BusinessException("你没有领取该优惠券，优惠券不存在");
                }
            }
            rs.close();pst.close();
            float prodis=0;
            Boolean P=false;
            if(pro==null||"".equals(pro)){
            }else {
                sql="select Net_id,model_id,discount,begin_date,finish_date from proget where promotion_id=? and user_id=?";
                pst=conn.prepareStatement(sql);
                pst.setInt(1, Integer.parseInt(pro));
                pst.setInt(2,CCUser.currentLoginUser.getUser_id());
                rs=pst.executeQuery();
                if(rs.next()){
                    if(new Date(System.currentTimeMillis()).getTime()<rs.getDate(4).getTime()||new Date(System.currentTimeMillis()).getTime()>rs.getDate(5).getTime()){
                        throw new BusinessException("折扣无效");
                    }
                    if(rs.getInt(2)!=model_id||rs.getInt(1)!=picknet){
                        throw new BusinessException("折扣无法使用在当前订单");
                    }
                    prodis=rs.getFloat(3);
                    P=true;
                }else {
                    throw new BusinessException("你没有领取该折扣，折扣不存在");
                }
            }
            rs.close();pst.close();
            sql="select Net_id from car_information where car_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,car_id);
            rs=pst.executeQuery();
            if(rs.next()){
                if("".equals(net)){
                }else {
                    if(picknet!=Net_id){
                        sql="insert into allocation(Net_id_in,car_id,Net_id_out,allocate_time) values(?,?,?,?)";
                        pst=conn.prepareStatement(sql);
                        pst.setInt(1,Net_id);
                        pst.setInt(2,car_id);
                        pst.setInt(3, picknet);
                        pst.setTimestamp(4,new java.sql.Timestamp(System.currentTimeMillis()));
                        pst.execute();
                    }

                    sql="update car_information set state='空闲',Net_id=? where car_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setInt(1,Net_id);
                    pst.setInt(2,car_id);
                    pst.execute();
                }
            }
            rs.close();pst.close();
            int picktime=order.getPick_time().getDate();
            int returntime=new Date(System.currentTimeMillis()).getDate();
            int renttime=returntime-picktime+1;

            if(C==true&&P==true){
                if((orimoney*prodis/10*timeorder-coumoney)<0)
                    throw new BusinessException("优惠或折扣不适用");
            }
            else if(C==true&&P==false){
                if((orimoney*1*timeorder-coumoney)<0)
                    throw new BusinessException("优惠或折扣不适用");
            }

            else if(C==false&&P==true){
                if((orimoney*prodis/10*timeorder)<0)
                    throw new BusinessException("优惠或折扣不适用");
            }
            else{
                if((orimoney*1*timeorder)<0)
                    throw new BusinessException("优惠或折扣不适用");
            }
//            System.out.println(555555555);
//            System.out.println(picktime);
//            System.out.println(returntime);
            float total_amonunt;

            if(C==true&&P==true){
                total_amonunt=orimoney*prodis/10*renttime-coumoney;
                if(total_amonunt<0)
                    throw new BusinessException("优惠或折扣不适用");
            }
            else if(C==true&&P==false){
                total_amonunt=orimoney*1*renttime-coumoney;
                if(total_amonunt<0)
                    throw new BusinessException("优惠或折扣不适用");
            }

            else if(C==false&&P==true){
                total_amonunt=orimoney*prodis/10*renttime;
                if(total_amonunt<0)
                    throw new BusinessException("优惠或折扣不适用");
            }
            else{
                total_amonunt=orimoney*1*renttime;
                if(total_amonunt<0)
                    throw new BusinessException("优惠或折扣不适用");
            }
            sql="update orderlist set coupon_id=?,promotion_id=?,return_Net_id=?,return_time=?,pick_total_time=?,Settlement_amount=?,order_state='已支付' where user_id=? and order_id=?";
            pst=conn.prepareStatement(sql);
            if("".equals(coupon)){
                pst.setString(1,null);
            }else {
                pst.setInt(1, Integer.parseInt(coupon));
                String sql2="delete from coupon_hold where coupon_id=? and user_id=?";
                java.sql.PreparedStatement pst2=conn.prepareStatement(sql2);
                pst2.setInt(1, Integer.parseInt(coupon));
                pst2.setInt(2,CCUser.currentLoginUser.getUser_id());
                pst2.execute();
                pst2.close();
            }
            if("".equals(pro)){
                pst.setString(2,null);
            }else{
                pst.setInt(2, Integer.parseInt(pro));
                String sql1="delete from pro_get where promotion_id=? and user_id=?";
                java.sql.PreparedStatement pst1=conn.prepareStatement(sql1);
                pst1.setInt(1, Integer.parseInt(pro));
                pst1.setInt(2,CCUser.currentLoginUser.getUser_id());
                pst1.execute();
                pst1.close();
            }
            pst.setInt(3, Net_id);
            pst.setTimestamp(4,new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setInt(5,renttime);
            pst.setFloat(6,total_amonunt);
            pst.setInt(7,CCUser.currentLoginUser.getUser_id());
            pst.setInt(8,order.getOrder_id());
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
