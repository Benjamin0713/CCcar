package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.model.CCAdmin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrmMainAdmin extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_plan=new JMenu("信息管理");
    private JMenu menu_step=new JMenu("车辆管理");
    private JMenu menu_static=new JMenu("限时促销");
    private JMenu menu_more = new JMenu("更多");

    private JMenuItem menuItem_person = new JMenuItem("查看个人信息");
    private JMenuItem menuItem_customer = new JMenuItem("查看用户信息");
    private JMenuItem menuItem_staff = new JMenuItem("查看员工信息");
    private JMenuItem menuItem_cost = new JMenuItem("查看用户消费情况");
//    private JMenuItem menuItem_logout = new JMenuItem("注销账号");
    private JMenuItem menuItem_car = new JMenuItem("查看车辆信息");
    private JMenuItem menuItem_car_rent = new JMenuItem("查看汽车租用情况");
    private JMenuItem menuItem_scrap = new JMenuItem("查看报废表");
    private JMenuItem menuItem_alloate = new JMenuItem("查看调拨表");
    private JMenuItem menuItem_procheck = new JMenuItem("查看信息");
    private JMenuItem menuItem_cancel = new JMenuItem("退出");
    //    private FrmLogin dlgLogin=null;
    private JPanel statusBar = new JPanel();

    public FrmMainAdmin(){
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("CC租车系统-管理员");

        this.menu_plan.add(menuItem_person);this.menuItem_person.addActionListener(this);
        this.menu_plan.add(menuItem_customer);this.menuItem_customer.addActionListener(this);
        this.menu_plan.add(menuItem_staff);this.menuItem_staff.addActionListener(this);
        this.menu_plan.add(menuItem_cost);this.menuItem_cost.addActionListener(this);
//        this.menu_plan.add(menuItem_logout);this.menuItem_logout.addActionListener(this);
        this.menu_step.add(menuItem_car);this.menuItem_car.addActionListener(this);
        this.menu_step.add(menuItem_car_rent);this.menuItem_car_rent.addActionListener(this);
        this.menu_step.add(menuItem_scrap);this.menuItem_scrap.addActionListener(this);
        this.menu_step.add(menuItem_alloate);this.menuItem_alloate.addActionListener(this);
        this.menu_static.add(menuItem_procheck);this.menuItem_procheck.addActionListener(this);
        this.menu_more.add(menuItem_cancel);this.menuItem_cancel.addActionListener(this);

        menubar.add(menu_plan);
        menubar.add(menu_step);
        menubar.add(menu_static);
        menubar.add(menu_more);
        this.setJMenuBar(menubar);

        CCAdmin admin = new CCAdmin();
        admin=CCAdmin.currentLoginAdmin;
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label=new JLabel("您好!"+admin.getStaff_name());//修改成   您好！+登陆用户名
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
        if (e.getSource() == this.menuItem_cancel) {
            setVisible(false);
            return;
        }else if(e.getSource()==this.menuItem_person){
            new FrmAdminInforCheck().setVisible(true);
        }else if(e.getSource()==this.menuItem_scrap){
            new FrmScrapExcel().setVisible(true);
        }else if(e.getSource()==this.menuItem_procheck){
            new FrmProAdmin().setVisible(true);
        }
    }
}
