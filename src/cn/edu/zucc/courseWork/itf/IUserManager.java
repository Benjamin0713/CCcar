package cn.edu.zucc.courseWork.itf;

import cn.edu.zucc.courseWork.model.CCUser;
import cn.edu.zucc.courseWork.util.BaseException;

import java.util.List;

public interface IUserManager {
    public CCUser login(String name, String pwd) throws BaseException;

    public CCUser reg(String name,String sex,String pwd,String pwd2,String tell,String email,String city) throws BaseException;

    public void changePwd(CCUser user, String oldPwd, String newPwd,
                          String newPwd2) throws BaseException;
    public void changeName(CCUser user, String oldName, String newName) throws BaseException;
    public List<CCUser> loadAllshop() throws BaseException;
    public List<CCUser> loadAll() throws BaseException;
    public List<CCUser> loadCost() throws BaseException;
    public void delete(CCUser user) throws BaseException;
    public void changeInfor(CCUser user, String sex, String tell,
                            String email,String city) throws BaseException;
}
