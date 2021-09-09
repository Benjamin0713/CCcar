package cn.edu.zucc.courseWork.itf;

import cn.edu.zucc.courseWork.model.CCAllocation;
import cn.edu.zucc.courseWork.util.BaseException;

import java.util.List;

public interface IAllocateManager {
    public List<CCAllocation> loadAll() throws BaseException;
}
