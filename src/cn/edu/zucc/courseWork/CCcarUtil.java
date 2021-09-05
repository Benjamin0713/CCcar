package cn.edu.zucc.courseWork;

import cn.edu.zucc.courseWork.control.AdminManager;
import cn.edu.zucc.courseWork.control.StaffManager;
import cn.edu.zucc.courseWork.control.UserManager;
import cn.edu.zucc.courseWork.itf.IAdminManager;
import cn.edu.zucc.courseWork.itf.IStaffManager;
import cn.edu.zucc.courseWork.itf.IUserManager;

public class CCcarUtil {
    public static IUserManager userManager=new UserManager();//需要换成自行设计的实现类
    public static IStaffManager staffManager=new StaffManager();//需要换成自行设计的实现类
    public static IAdminManager adminManager=new AdminManager();
}
