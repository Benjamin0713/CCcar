package cn.edu.zucc.courseWork.itf;

import cn.edu.zucc.courseWork.model.CCUser;
import cn.edu.zucc.courseWork.util.BaseException;

public interface IUserManager {
    public CCUser login(String name, String pwd) throws BaseException;

    public CCUser reg(String userid, String name,String sex,String pwd,String pwd2,String tell,String email,String city) throws BaseException;

    public void changePwd(CCUser user, String oldPwd, String newPwd,
                          String newPwd2) throws BaseException;
}
