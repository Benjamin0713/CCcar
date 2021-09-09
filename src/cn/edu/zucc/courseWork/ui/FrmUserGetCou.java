package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCHoldCoupon;
import cn.edu.zucc.courseWork.model.CCNet;
import cn.edu.zucc.courseWork.model.CCUser;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmUserGetCou extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");

    private JPanel statusBar = new JPanel();
    private Object tblHoldCouponTitle[]= CCHoldCoupon.tableTitles;
    private Object tblHoldCouponData[][];
    DefaultTableModel tabHoldCouponModel=new DefaultTableModel();
    private JTable dataTableHoldCoupon=new JTable(tabHoldCouponModel);

    private CCHoldCoupon HoldCoupon = null;
    List<CCHoldCoupon> HoldCoupondata = null;

    private void reloadCouponTable() {
        try {
            HoldCoupondata = CCcarUtil.couponManager.loadAllHold();
        }catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblHoldCouponData = new Object[HoldCoupondata.size()][CCHoldCoupon.tableTitles.length];
        for(int i = 0;i < HoldCoupondata.size() ; i++)
            for(int j = 0; j< CCHoldCoupon.tableTitles.length; j++)
                tblHoldCouponData[i][j] = HoldCoupondata.get(i).getCell(j);
        tabHoldCouponModel.setDataVector(tblHoldCouponData, tblHoldCouponTitle);
        this.dataTableHoldCoupon.validate();
        this.dataTableHoldCoupon.repaint();
    }
    public FrmUserGetCou(){
        this.setTitle("已领取的优惠券");
        setSize(900, 450);// 设置窗体大小
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.menu_cancel.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);

        menubar.add(menu_cancel);
        this.setJMenuBar(menubar);

        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.getContentPane().add(new JScrollPane(this.dataTableHoldCoupon), BorderLayout.CENTER);
        this.reloadCouponTable();
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