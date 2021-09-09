package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCOrder;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmCheckOverList extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_cancel = new JMenu("退出该界面");


    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");
    private Object tblOrderTitle[]= CCOrder.tableTitles;
    private Object tblOrderData[][];
    DefaultTableModel tabOrderModel=new DefaultTableModel();
    private JTable dataTableOrder=new JTable(tabOrderModel);

    private CCOrder Order = null;
    List<CCOrder> Orderdata = null;

    private void reloadCouponTable() {
        try {
            Orderdata = CCcarUtil.orderManager.loadAllOver();
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
    public FrmCheckOverList(){
        this.setTitle("查看订单");
        setSize(950, 380);// 设置窗体大小

        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.menu_cancel.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);

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
        this.reloadCouponTable();
    }
}
