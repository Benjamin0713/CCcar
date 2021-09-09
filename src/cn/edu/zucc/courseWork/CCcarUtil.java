package cn.edu.zucc.courseWork;

import cn.edu.zucc.courseWork.control.*;
import cn.edu.zucc.courseWork.itf.*;

public class CCcarUtil {
    public static IUserManager userManager=new UserManager();//需要换成自行设计的实现类
    public static IStaffManager staffManager=new StaffManager();//需要换成自行设计的实现类
    public static IAdminManager adminManager=new AdminManager();
    public static ICouponManager couponManager=new CouponManager();
    public static ICarTypeManager carTypeManager=new CarTypeManager();
    public static INetManager netManager=new NetManager();
    public static IPromManager promManager=new PromManager();
    public static ICarManager carManager=new CarManager();
    public static ICarModelManager carModelManager=new CarModelManager();
    public static IScrapManager scrapManager=new ScrapManager();
    public static IAllocateManager allocateManager=new AllocateManager();
    public static IOrderManager orderManager=new OrderManager();
}
