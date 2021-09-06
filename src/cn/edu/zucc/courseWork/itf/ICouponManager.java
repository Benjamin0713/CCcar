package cn.edu.zucc.courseWork.itf;

import cn.edu.zucc.courseWork.model.CCCoupon;
import cn.edu.zucc.courseWork.util.BaseException;

import java.util.List;

public interface ICouponManager {
    public List<CCCoupon> loadAllshop() throws BaseException;
}
