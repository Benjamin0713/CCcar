package cn.edu.zucc.courseWork.itf;

import cn.edu.zucc.courseWork.model.CCCoupon;
import cn.edu.zucc.courseWork.model.CCHoldCoupon;
import cn.edu.zucc.courseWork.util.BaseException;

import java.util.List;

public interface ICouponManager {
    public List<CCCoupon> loadAllshop() throws BaseException;
    public void addCoupon(String context, String money, String start_date, String end_date,String Net_id) throws BaseException;
    public List<CCHoldCoupon> loadAllHold() throws BaseException;
    public void holdcoupon(CCCoupon coupon) throws BaseException;
}
