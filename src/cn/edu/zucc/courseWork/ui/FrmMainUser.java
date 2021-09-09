package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.model.CCUser;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class FrmMainUser extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_person=new JMenu("个人信息");
    private JMenu menu_coupon=new JMenu("优惠券");
    private JMenu menu_pro = new JMenu("限时促销");
    private JMenu menu_order=new JMenu("CC下单");
    private JMenu menu_more =new JMenu("更多");

    private JMenuItem menuItem_Check = new JMenuItem("查看信息");
    private JMenuItem menuItem_coupon = new JMenuItem("优惠券信息");
    private JMenuItem menuItem_promote = new JMenuItem("限时促销信息");
    private JMenuItem menuItem_checkcar = new JMenuItem("查看汽车信息");
    private JMenuItem menuItem_checkorder = new JMenuItem("查看订单");
    private JMenuItem menuItem_orderhis=new JMenuItem("历史订单");
    private JMenuItem menuItem_coupon_taken = new JMenuItem("查看已领取的优惠券");
    private JMenuItem menuItem_promote_taken = new JMenuItem("查看获得的折扣");
    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");

    private JPanel statusBar = new JPanel();

    public FrmMainUser(){
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("CC租车系统-用户");

        this.menu_person.add(menuItem_Check);this.menuItem_Check.addActionListener(this);
        this.menu_coupon.add(menuItem_coupon);this.menuItem_coupon.addActionListener(this);
        this.menu_pro.add(menuItem_promote);this.menuItem_promote.addActionListener(this);
        this.menu_order.add(menuItem_checkcar);this.menuItem_checkcar.addActionListener(this);
        this.menu_order.add(menuItem_checkorder);this.menuItem_checkorder.addActionListener(this);
        this.menu_order.add(menuItem_orderhis);this.menuItem_orderhis.addActionListener(this);
        this.menu_more.add(menuItem_coupon_taken);this.menuItem_coupon_taken.addActionListener(this);
        this.menu_more.add(menuItem_promote_taken);this.menuItem_promote_taken.addActionListener(this);
        this.menu_more.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);

        menubar.add(menu_person);
        menubar.add(menu_coupon);
        menubar.add(menu_pro);
        menubar.add(menu_order);
        menubar.add(menu_more);
        this.setJMenuBar(menubar);
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));

        CCUser user = new CCUser();
        user = CCUser.currentLoginUser;
        JLabel label=new JLabel("您好!   "+user.getUser_name());//修改成   您好！+用户名
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
        if (e.getSource() == this.menuItem_Cancel) {
            setVisible(false);
            return;
        }else if(e.getSource()==this.menuItem_Check){
            new FrmUserInforCheck().setVisible(true);
        }else if(e.getSource()==this.menuItem_coupon){
            new FrmCouponuser().setVisible(true);
        }else if(e.getSource()==this.menuItem_promote){
            new FrmProUser().setVisible(true);
        }else if(e.getSource()==this.menuItem_coupon_taken){
            new FrmUserGetCou().setVisible(true);
        }else if(e.getSource()==this.menuItem_promote_taken){
            new FrmUserGetPro().setVisible(true);
        }else if(e.getSource()==this.menuItem_checkcar){
            new FrmUserCarOrder().setVisible(true);
        }else if(e.getSource()==this.menuItem_checkorder){
            new FrmUserOrderCheck().setVisible(true);
        }else if(e.getSource()==this.menuItem_orderhis){
            new FrmCheckOverList().setVisible(true);
        }
    }
}
