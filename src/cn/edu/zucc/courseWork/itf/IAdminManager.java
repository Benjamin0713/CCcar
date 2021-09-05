package cn.edu.zucc.courseWork.itf;

import cn.edu.zucc.courseWork.model.CCAdmin;
import cn.edu.zucc.courseWork.model.CCStaff;
import cn.edu.zucc.courseWork.util.BaseException;

public interface IAdminManager {
    public CCAdmin reg(String staff_id, String staffname, String pwd, String pwd2) throws BaseException;

    public CCAdmin login(String userid, String pwd) throws BaseException;

    public void changePwd(CCAdmin admin, String oldPwd, String newPwd,
                          String newPwd2) throws BaseException;
}
