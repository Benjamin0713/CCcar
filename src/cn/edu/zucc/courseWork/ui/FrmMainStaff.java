package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.model.CCStaff;

import javax.swing.*;
import java.awt.*;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrmMainStaff extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_person=new JMenu("个人信息");
    private JMenu menu_coupon=new JMenu("优惠券");
    private JMenu menu_car=new JMenu("汽车管理");
    private JMenu menu_more = new JMenu("更多");

    private JMenuItem menuItem_Check = new JMenuItem("查看信息");
//    private JMenuItem menuItem_Modify = new JMenuItem("修改信息");
//    private JMenuItem menuItem_Logout = new JMenuItem("注销账号");
    private JMenuItem menuItem_couponcheck = new JMenuItem("查看信息");
//    private JMenuItem menuItem_couponadd = new JMenuItem("添加");
//    private JMenuItem menuItem_coupondelete= new JMenuItem("删除");
//    private JMenuItem menuItem_couponModify = new JMenuItem("修改");
    private JMenuItem menuItem_car = new JMenuItem("查看汽车信息");
//    private JMenuItem menuItem_scrap = new JMenuItem("汽车报废");
    private JMenuItem menuItem_scrap_check = new JMenuItem("查看报废表");
    private JMenuItem menuItem_Cancel = new JMenuItem ("退出");
    //    private FrmLogin dlgLogin=null;
    private JPanel statusBar = new JPanel();

    public FrmMainStaff(){
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("CC租车系统-工作人员");

        this.menu_person.add(menuItem_Check);this.menuItem_Check.addActionListener(this);
//        this.menu_person.add(menuItem_Modify);this.menuItem_Modify.addActionListener(this);
//        this.menu_person.add(menuItem_Logout);this.menuItem_Logout.addActionListener(this);
        this.menu_coupon.add(menuItem_couponcheck);this.menuItem_couponcheck.addActionListener(this);
//        this.menu_coupon.add(menuItem_couponadd);this.menuItem_couponadd.addActionListener(this);
//        this.menu_coupon.add(menuItem_coupondelete);this.menuItem_coupondelete.addActionListener(this);
//        this.menu_coupon.add(menuItem_couponModify);this.menuItem_couponModify.addActionListener(this);
        this.menu_car.add(menuItem_car);this.menuItem_car.addActionListener(this);
//        this.menu_car.add(menuItem_scrap);this.menuItem_scrap.addActionListener(this);
        this.menu_car.add(menuItem_scrap_check);this.menuItem_scrap_check.addActionListener(this);
        this.menu_more.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);
        menubar.add(menu_person);
        menubar.add(menu_coupon);
        menubar.add(menu_car);
        menubar.add(menu_more);
        this.setJMenuBar(menubar);

        //状态栏
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        CCStaff staff = new CCStaff();
        staff = CCStaff.currentLoginStaff;
        JLabel label=new JLabel("您好!"+staff.getStaff_name());//修改成   您好！+登陆用户名
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.menuItem_Check){
            new FrmStaffInforCheck().setVisible(true);
        }else if (e.getSource() == this.menuItem_Cancel) {
            setVisible(false);
            return;
        }else if(e.getSource()==this.menuItem_couponcheck){
            new FrmCouponstaff().setVisible(true);
        }else if(e.getSource()==this.menuItem_car){
            new FrmScrapstaff().setVisible(true);
        }else if(e.getSource()==this.menuItem_scrap_check){
            new FrmScrapExcel().setVisible(true);
        }
    }
}
