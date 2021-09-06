package cn.edu.zucc.courseWork.control;

import cn.edu.zucc.courseWork.itf.ICouponManager;
import cn.edu.zucc.courseWork.model.CCAdmin;
import cn.edu.zucc.courseWork.model.CCCoupon;
import cn.edu.zucc.courseWork.model.CCScrap;
import cn.edu.zucc.courseWork.util.BaseException;
import cn.edu.zucc.courseWork.util.DBUtil;
import cn.edu.zucc.courseWork.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CouponManager implements ICouponManager {
    public List<CCCoupon> loadAllshop() throws BaseException {
        List<CCCoupon> result = new ArrayList<CCCoupon>();
        Connection conn =null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from coupon";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()) {
                CCCoupon coupon=new CCCoupon();
                coupon.setCoupon_id(rs.getInt(1));
                coupon.setCoupon_content(rs.getString(2));
                coupon.setCoupon_money(rs.getFloat(3));
                coupon.setCoupon_start_date(rs.getTimestamp(4));
                coupon.setCoupon_end_date(rs.getTimestamp(5));
                result.add(coupon);
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
