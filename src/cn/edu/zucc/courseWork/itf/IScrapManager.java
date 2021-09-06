package cn.edu.zucc.courseWork.itf;

import cn.edu.zucc.courseWork.model.CCScrap;
import cn.edu.zucc.courseWork.util.BaseException;

import java.util.List;

public interface IScrapManager {
    public List<CCScrap> loadAllshop() throws BaseException;
}
