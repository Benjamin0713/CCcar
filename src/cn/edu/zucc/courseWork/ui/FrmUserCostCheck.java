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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FrmUserCostCheck extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_cancel = new JMenu("退出该界面");

    private Object tblUserTitle[]= CCUser.CostTitles;
    private Object tblUserData[][];
    DefaultTableModel tabUserModel=new DefaultTableModel();
    private JTable dataTableUser=new JTable(tabUserModel);

    private Object tblOrderTitle[]= CCOrder.tableTitles;
    private Object tblOrderData[][];
    DefaultTableModel tabOrderModel=new DefaultTableModel();
    private JTable dataTableOrder=new JTable(tabOrderModel);

    private CCUser curuser = null;
    List<CCUser> Userdata = null;
    List<CCOrder> Orderdata = null;
    private void reloadStaffTable() {
        try {
            Userdata = CCcarUtil.userManager.loadCost();
        }catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblUserData = new Object[Userdata.size()][CCUser.tableTitles.length];
        for(int i = 0;i < Userdata.size() ; i++)
            for(int j = 0;j<CCUser.tableTitles.length;j++)
                tblUserData[i][j] = Userdata.get(i).getOrder(j);
        tabUserModel.setDataVector(tblUserData, tblUserTitle);
        this.dataTableUser.validate();
        this.dataTableUser.repaint();
    }
    private void reloadPlanStepTabel(int planIdx){
        if(planIdx<0) return;
        curuser=Userdata.get(planIdx);
        try {
            Orderdata= CCcarUtil.orderManager.loadCost(curuser);
        } catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblOrderData =new Object[Orderdata.size()][CCOrder.tableTitles.length];
        for(int i=0;i<Orderdata.size();i++){
            for(int j=0;j<CCOrder.tableTitles.length;j++)
                tblOrderData[i][j]=Orderdata.get(i).getCell(j);
        }

        tabOrderModel.setDataVector(tblOrderData,tblOrderTitle);
        this.dataTableOrder.validate();
        this.dataTableOrder.repaint();
    }
    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");
    public FrmUserCostCheck(){
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("用户消费情况");
//        setSize(600, 380);// 设置窗体大小
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.menu_cancel.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);
        this.getContentPane().add(new JScrollPane(this.dataTableUser), BorderLayout.WEST);
        this.dataTableUser.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                int i=FrmUserCostCheck.this.dataTableUser.getSelectedRow();
                if(i<0) {
                    return;
                }
                FrmUserCostCheck.this.reloadPlanStepTabel(i);
            }

        });
        this.getContentPane().add(new JScrollPane(this.dataTableOrder), BorderLayout.CENTER);
        this.reloadStaffTable();
        menubar.add(menu_cancel);
        this.setJMenuBar(menubar);

        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == this.menuItem_Cancel) {
            setVisible(false);
            return;
        }
        this.reloadStaffTable();
    }
}
