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
import java.util.List;

public class FrmCouponuser extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_coupon=new JMenu("优惠券管理");
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JMenuItem menuItem_couponget = new JMenuItem("领取");
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
        tblCouponData = new Object[Coupondata.size()][CCUser.tableTitles.length];
        for(int i = 0;i < Coupondata.size() ; i++)
            for(int j = 0;j<CCScrap.tableTitles.length;j++)
                tblCouponData[i][j] = Coupondata.get(i).getCell(j);
        tabCouponModel.setDataVector(tblCouponData, tblCouponTitle);
        this.dataTableCoupon.validate();
        this.dataTableCoupon.repaint();
    }

    public FrmCouponuser(){
        this.setTitle("优惠券-用户");
        setSize(680, 480);// 设置窗体大小
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.menu_coupon.add(menuItem_couponget);this.menuItem_couponget.addActionListener(this);
        this.menu_cancel.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);

        menubar.add(menu_coupon);
        menubar.add(menu_cancel);
        this.setJMenuBar(menubar);

        this.getContentPane().add(new JScrollPane(this.dataTableCoupon), BorderLayout.CENTER);
        this.reloadCouponTable();
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        CCUser user = new CCUser();
        user = CCUser.currentLoginUser;
        JLabel label=new JLabel("您好!"+user.getUser_name());//修改成   您好！+登陆用户名
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == this.menuItem_Cancel) {
            setVisible(false);
            return;
        }
    }
}