package cn.edu.zucc.courseWork.itf;

import cn.edu.zucc.courseWork.model.CCProGet;
import cn.edu.zucc.courseWork.model.CCPromotion;
import cn.edu.zucc.courseWork.util.BaseException;

import java.util.List;

public interface IPromManager {
    public List<CCPromotion> loadAllshop() throws BaseException;
    public void addPro(String net, String model, String dis, String num,String startdate,String enddate) throws BaseException;
    public List<CCProGet> loadAllHold() throws BaseException;
    public void holdpro(CCPromotion pro) throws BaseException;
}
