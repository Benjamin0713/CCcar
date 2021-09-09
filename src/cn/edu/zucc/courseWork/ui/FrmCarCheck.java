package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCAdmin;
import cn.edu.zucc.courseWork.model.CCCar;
import cn.edu.zucc.courseWork.model.CCModel;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmCarCheck extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_coupon=new JMenu("车辆管理");
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JMenuItem menuItem_couponadd = new JMenuItem("添加车辆");
    private JMenuItem menuItem_coupondelete= new JMenuItem("删除车辆");
    private JMenuItem menuItem_couponModify = new JMenuItem("修改车辆信息");
    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");

    private JPanel statusBar = new JPanel();
    private Object tblCarTitle[]= CCCar.tableTitles;
    private Object tblCarData[][];
    DefaultTableModel tabCarModel=new DefaultTableModel();
    private JTable dataTableCar=new JTable(tabCarModel);

    private CCCar Car = null;
    List<CCCar> Cardata = null;

    private void reloadCouponTable() {
        try {
            Cardata = CCcarUtil.carManager.loadAll();
        }catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblCarData = new Object[Cardata.size()][CCCar.tableTitles.length];
        for(int i = 0;i < Cardata.size() ; i++)
            for(int j = 0; j< CCCar.tableTitles.length; j++)
                tblCarData[i][j] = Cardata.get(i).getCell(j);
        tabCarModel.setDataVector(tblCarData, tblCarTitle);
        this.dataTableCar.validate();
        this.dataTableCar.repaint();
    }

    public FrmCarCheck(){
        this.setTitle("汽车管理-车辆");
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
        this.getContentPane().add(new JScrollPane(this.dataTableCar), BorderLayout.CENTER);
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
            FrmAddCar dlg =new FrmAddCar(null, "添加车辆", true);
            dlg.setVisible(true);
        }
        this.reloadCouponTable();
    }
}
