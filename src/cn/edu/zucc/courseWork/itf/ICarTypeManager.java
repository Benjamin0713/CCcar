package cn.edu.zucc.courseWork.itf;

import cn.edu.zucc.courseWork.model.CCType;
import cn.edu.zucc.courseWork.util.BaseException;

import java.util.List;

public interface ICarTypeManager {
    public List<CCType> loadAll() throws BaseException;
    public void addType(String name, String desc) throws BaseException;
    public void delete(CCType type) throws BaseException;
    public void changetype(String type_id, String name, String desc) throws BaseException;

}
