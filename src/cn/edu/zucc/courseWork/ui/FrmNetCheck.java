package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.*;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmNetCheck extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_coupon=new JMenu("网点管理");
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JMenuItem menuItem_couponadd = new JMenuItem("添加网点");
    private JMenuItem menuItem_coupondelete= new JMenuItem("删除网点");
    private JMenuItem menuItem_couponModify = new JMenuItem("修改网点信息");
    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");

    private JPanel statusBar = new JPanel();
    private Object tblNetTitle[]= CCNet.tableTitles;
    private Object tblNetData[][];
    DefaultTableModel tabNetModel=new DefaultTableModel();
    private JTable dataTableNet=new JTable(tabNetModel);

    private CCNet Net = null;
    List<CCNet> Netdata = null;

    private void reloadCouponTable() {
        try {
            Netdata = CCcarUtil.netManager.loadAll();
        }catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblNetData = new Object[Netdata.size()][CCNet.tableTitles.length];
        for(int i = 0;i < Netdata.size() ; i++)
            for(int j = 0; j< CCNet.tableTitles.length; j++)
                tblNetData[i][j] = Netdata.get(i).getCell(j);
        tabNetModel.setDataVector(tblNetData, tblNetTitle);
        this.dataTableNet.validate();
        this.dataTableNet.repaint();
    }

    public FrmNetCheck(){
        this.setTitle("网点管理-管理员");
        setSize(800, 580);// 设置窗体大小
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
        this.getContentPane().add(new JScrollPane(this.dataTableNet), BorderLayout.CENTER);
        this.reloadCouponTable();
        CCAdmin admin = new CCAdmin();
        admin=CCAdmin.currentLoginAdmin;
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label=new JLabel("您好!"+admin.getStaff_name());//修改成   您好！+登陆用户名
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == this.menuItem_Cancel) {
            setVisible(false);
            return;
        }else if(e.getSource()==this.menuItem_couponadd){
            FrmAddNet dlg =new FrmAddNet(null, "添加网点", true);
            dlg.setVisible(true);
        }else if(e.getSource()==this.menuItem_coupondelete){
            int i = FrmNetCheck.this.dataTableNet.getSelectedRow();
            if (i < 0) {
                JOptionPane.showMessageDialog(null, "请选择网点", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                CCcarUtil.netManager.delete(this.Netdata.get(i));
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }else if(e.getSource()==this.menuItem_couponModify){
            FrmModifyNet dlg=new FrmModifyNet(null,"修改网点",true);
            dlg.setVisible(true);
        }
        this.reloadCouponTable();
    }
}
