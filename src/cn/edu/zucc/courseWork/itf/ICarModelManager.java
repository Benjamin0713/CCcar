package cn.edu.zucc.courseWork.itf;

import cn.edu.zucc.courseWork.model.CCModel;
import cn.edu.zucc.courseWork.util.BaseException;

import java.util.List;

public interface ICarModelManager {
    public List<CCModel> loadAll() throws BaseException;
    public void addModel(String type, String name, String brand, String cap,String tran,String site,String price) throws BaseException;
}
