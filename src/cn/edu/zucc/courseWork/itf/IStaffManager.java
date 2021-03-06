package cn.edu.zucc.courseWork.itf;

import cn.edu.zucc.courseWork.model.CCStaff;
import cn.edu.zucc.courseWork.util.BaseException;

import java.util.List;

public interface IStaffManager {
    public CCStaff reg(String staff_id,String net_id,String staffname, String pwd, String pwd2) throws BaseException;

    public CCStaff login(String name, String pwd) throws BaseException;

    public void changePwd(CCStaff staff, String oldPwd, String newPwd,
                          String newPwd2) throws BaseException;
    public List<CCStaff> loadAllshop() throws BaseException;
    public void changeName(CCStaff staff, String oldName, String newName) throws BaseException;
    public List<CCStaff> loadAll() throws BaseException;
    public void delete(CCStaff staff) throws BaseException;
    public void changeNet(CCStaff staff, String oldNet, String newNet) throws BaseException;
}
