package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCCoupon;
import cn.edu.zucc.courseWork.model.CCScrap;
import cn.edu.zucc.courseWork.model.CCStaff;
import cn.edu.zucc.courseWork.model.CCUser;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class FrmCouponstaff extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_coupon=new JMenu("优惠券管理");
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JMenuItem menuItem_couponadd = new JMenuItem("添加");
    private JMenuItem menuItem_coupondelete= new JMenuItem("删除");
    private JMenuItem menuItem_couponModify = new JMenuItem("修改");
    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");

    private JPanel statusBar = new JPanel();
    private Object tblCouponTitle[]= CCCoupon.tableTitles;
    private Object tblCouponData[][];
    DefaultTableModel tabCouponModel=new DefaultTableModel();
    private JTable dataTableCoupon=new JTable(tabCouponModel);

    private CCCoupon Coupon = null;
    List<CCCoupon> Coupondata = null;

    private void reloadCouponTable() {
        try {
            Coupondata = CCcarUtil.couponManager.loadAllshop();
        }catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblCouponData = new Object[Coupondata.size()][CCCoupon.tableTitles.length];
        for(int i = 0;i < Coupondata.size() ; i++)
            for(int j = 0; j< CCCoupon.tableTitles.length; j++)
                tblCouponData[i][j] = Coupondata.get(i).getCell(j);
        tabCouponModel.setDataVector(tblCouponData, tblCouponTitle);
        this.dataTableCoupon.validate();
        this.dataTableCoupon.repaint();
    }

    public FrmCouponstaff(){
        this.setTitle("优惠券-员工");
        setSize(700, 480);// 设置窗体大小
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.menu_coupon.add(menuItem_couponadd);this.menuItem_couponadd.addActionListener(this);
        this.menu_coupon.add(menuItem_coupondelete);this.menuItem_coupondelete.addActionListener(this);
        this.menu_coupon.add(menuItem_couponModify);this.menuItem_couponModify.addActionListener(this);
        this.menu_cancel.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);

        menubar.add(menu_coupon);
        menubar.add(menu_cancel);
        this.setJMenuBar(menubar);
        this.getContentPane().add(new JScrollPane(this.dataTableCoupon), BorderLayout.CENTER);
        this.reloadCouponTable();
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        CCStaff staff = new CCStaff();
        staff = CCStaff.currentLoginStaff;
        JLabel label=new JLabel("您好!"+staff.getStaff_name());//修改成   您好！+登陆用户名
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == this.menuItem_Cancel) {
            setVisible(false);
            return;
        }else if(e.getSource()==this.menuItem_couponadd){
            FrmAddCoupon dlg =new FrmAddCoupon(null, "添加优惠券", true);
            dlg.setVisible(true);
        }
        this.reloadCouponTable();
    }
}
