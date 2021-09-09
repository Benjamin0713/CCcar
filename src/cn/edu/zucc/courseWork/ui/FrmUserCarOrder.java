package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCCar;
import cn.edu.zucc.courseWork.model.CCStaff;
import cn.edu.zucc.courseWork.model.CCUser;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmUserCarOrder extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_scrap=new JMenu("订单管理");
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JMenuItem menuItem_scrap = new JMenuItem("选择下单");
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
            Cardata = CCcarUtil.carManager.loadAllCar();
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
    public FrmUserCarOrder(){
        this.setTitle("订单管理-用户");
        setSize(550, 380);// 设置窗体大小
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.menu_scrap.add(menuItem_scrap);this.menuItem_scrap.addActionListener(this);
        this.menu_cancel.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);

        menubar.add(menu_scrap);
        menubar.add(menu_cancel);
        this.setJMenuBar(menubar);
        this.getContentPane().add(new JScrollPane(this.dataTableCar), BorderLayout.CENTER);
        this.reloadCouponTable();
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        CCUser user=new CCUser();
        user=CCUser.currentLoginUser;
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
        else if (e.getSource() == this.menuItem_scrap) {
            int i = FrmUserCarOrder.this.dataTableCar.getSelectedRow();
            if (i < 0) {
                JOptionPane.showMessageDialog(null, "请选择车辆", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                CCcarUtil.orderManager.ordercar(this.Cardata.get(i));
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        this.reloadCouponTable();
    }
}