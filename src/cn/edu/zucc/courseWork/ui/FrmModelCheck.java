package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCAdmin;
import cn.edu.zucc.courseWork.model.CCModel;
import cn.edu.zucc.courseWork.model.CCType;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmModelCheck extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_coupon=new JMenu("汽车车型管理");
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JMenuItem menuItem_couponadd = new JMenuItem("添加车型");
    private JMenuItem menuItem_coupondelete= new JMenuItem("删除车型");
    private JMenuItem menuItem_couponModify = new JMenuItem("修改车型信息");
    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");

    private JPanel statusBar = new JPanel();
    private Object tblModelTitle[]= CCModel.tableTitles;
    private Object tblModelData[][];
    DefaultTableModel tabModelModel=new DefaultTableModel();
    private JTable dataTableModel=new JTable(tabModelModel);

    private CCModel Model = null;
    List<CCModel> Modeldata = null;

    private void reloadCouponTable() {
        try {
            Modeldata = CCcarUtil.carModelManager.loadAll();
        }catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblModelData = new Object[Modeldata.size()][CCModel.tableTitles.length];
        for(int i = 0;i < Modeldata.size() ; i++)
            for(int j = 0; j< CCModel.tableTitles.length; j++)
                tblModelData[i][j] = Modeldata.get(i).getCell(j);
        tabModelModel.setDataVector(tblModelData, tblModelTitle);
        this.dataTableModel.validate();
        this.dataTableModel.repaint();
    }

    public FrmModelCheck(){
        this.setTitle("汽车管理-车型");
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
        this.getContentPane().add(new JScrollPane(this.dataTableModel), BorderLayout.CENTER);
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
            FrmAddModel dlg=new FrmAddModel(this,"添加车型",true);
            dlg.setVisible(true);
        }
        this.reloadCouponTable();
    }
}

