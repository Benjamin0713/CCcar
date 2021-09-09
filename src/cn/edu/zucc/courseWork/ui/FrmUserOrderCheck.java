package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCCar;
import cn.edu.zucc.courseWork.model.CCOrder;
import cn.edu.zucc.courseWork.model.CCUser;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class FrmUserOrderCheck extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelName = new JLabel("还车网点");
    private JLabel labelprice = new JLabel("使用优惠券");
    private JLabel labelstart = new JLabel("使用折扣");

    private JTextField edtName = new JTextField(9);
    private JTextField edtprice = new JTextField(9);
    private JTextField edtstart = new JTextField(9);

    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");
    private Object tblOrderTitle[]= CCOrder.tableTitles;
    private Object tblOrderData[][];
    DefaultTableModel tabOrderModel=new DefaultTableModel();
    private JTable dataTableOrder=new JTable(tabOrderModel);

    private CCOrder Order = null;
    List<CCOrder> Orderdata = null;

    private void reloadCouponTable() {
        try {
            Orderdata = CCcarUtil.orderManager.loadAll();
        }catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblOrderData = new Object[Orderdata.size()][CCOrder.tableTitles.length];
        for(int i = 0;i < Orderdata.size() ; i++)
            for(int j = 0; j< CCOrder.tableTitles.length; j++)
                tblOrderData[i][j] = Orderdata.get(i).getCell(j);
        tabOrderModel.setDataVector(tblOrderData, tblOrderTitle);
        this.dataTableOrder.validate();
        this.dataTableOrder.repaint();
    }
    public FrmUserOrderCheck(){
        this.setTitle("查看订单");
        setSize(950, 380);// 设置窗体大小
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labelprice);
        workPane.add(edtprice);
        workPane.add(labelstart);
        workPane.add(edtstart);;
        this.getContentPane().add(workPane, BorderLayout.NORTH);

        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.btnOk.addActionListener(this);
        this.btnCancel.addActionListener(this);
//        this.menu_scrap.add(menuItem_scrap);this.menuItem_scrap.addActionListener(this);
        this.menu_cancel.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);

//        menubar.add(menu_scrap);
        menubar.add(menu_cancel);
        this.setJMenuBar(menubar);
        this.getContentPane().add(new JScrollPane(this.dataTableOrder), BorderLayout.CENTER);
        this.reloadCouponTable();

        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == this.menuItem_Cancel) {
            setVisible(false);
            return;
        }
        else if (e.getSource() == this.btnOk) {
            int i = FrmUserOrderCheck.this.dataTableOrder.getSelectedRow();
            String net=this.edtName.getText();
            String coupon=this.edtprice.getText();
            String pro=this.edtstart.getText();
            if (i < 0) {
                JOptionPane.showMessageDialog(null, "请选择订单", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                CCcarUtil.orderManager.endOrder(this.Orderdata.get(i),net,coupon,pro);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        this.reloadCouponTable();
    }
}