package cn.edu.zucc.courseWork.itf;

import cn.edu.zucc.courseWork.model.CCNet;
import cn.edu.zucc.courseWork.util.BaseException;

import java.util.List;

public interface INetManager {
    public List<CCNet> loadAll() throws BaseException;
    public void addNet(String name, String city, String add, String tell) throws BaseException;
    public void delete(CCNet net) throws BaseException;
    public void changenet(String Net_id, String name, String city,
                          String add,String tell) throws BaseException;
}
