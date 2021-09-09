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

public class FrmTypeCheck extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_coupon=new JMenu("汽车类别管理");
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JMenuItem menuItem_couponadd = new JMenuItem("添加类别");
    private JMenuItem menuItem_coupondelete= new JMenuItem("删除类别");
    private JMenuItem menuItem_couponModify = new JMenuItem("修改类别信息");
    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");

    private JPanel statusBar = new JPanel();
    private Object tblTypeTitle[]= CCType.tableTitles;
    private Object tblTypeData[][];
    DefaultTableModel tabTypeModel=new DefaultTableModel();
    private JTable dataTableType=new JTable(tabTypeModel);

    private CCType Type = null;
    List<CCType> Typedata = null;

    private void reloadCouponTable() {
        try {
            Typedata = CCcarUtil.carTypeManager.loadAll();
        }catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblTypeData = new Object[Typedata.size()][CCType.tableTitles.length];
        for(int i = 0;i < Typedata.size() ; i++)
            for(int j = 0; j< CCType.tableTitles.length; j++)
                tblTypeData[i][j] = Typedata.get(i).getCell(j);
        tabTypeModel.setDataVector(tblTypeData, tblTypeTitle);
        this.dataTableType.validate();
        this.dataTableType.repaint();
    }

    public FrmTypeCheck(){
        this.setTitle("汽车管理-类别");
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
        this.getContentPane().add(new JScrollPane(this.dataTableType), BorderLayout.CENTER);
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
            FrmAddType dlg =new FrmAddType(null, "添加汽车类别", true);
            dlg.setVisible(true);
        }
        this.reloadCouponTable();
    }
}
