package cn.edu.zucc.courseWork.itf;

import cn.edu.zucc.courseWork.model.CCCar;
import cn.edu.zucc.courseWork.model.CCOrder;
import cn.edu.zucc.courseWork.model.CCUser;
import cn.edu.zucc.courseWork.util.BaseException;

import java.util.List;

public interface IOrderManager {
    public void ordercar(CCCar car,String date) throws BaseException;
    public List<CCOrder> loadAll() throws BaseException;
    public void endOrder(CCOrder order,String net,String coupon,String pro) throws BaseException;
    public List<CCOrder> loadAllOver() throws BaseException;
    public List<CCOrder> loadCost(CCUser user) throws BaseException;
    public List<CCOrder> loadrent(CCCar Car) throws BaseException;
}
