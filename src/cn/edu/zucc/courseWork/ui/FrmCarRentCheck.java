package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCCar;
import cn.edu.zucc.courseWork.model.CCOrder;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FrmCarRentCheck extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");
    private Object tblCarTitle[]= CCCar.tableTitles;
    private Object tblCarData[][];
    DefaultTableModel tabCarModel=new DefaultTableModel();
    private JTable dataTableCar=new JTable(tabCarModel);
    private Object tblOrderTitle[]= CCOrder.tableTitles;
    private Object tblOrderData[][];
    DefaultTableModel tabOrderModel=new DefaultTableModel();
    private JTable dataTableOrder=new JTable(tabOrderModel);
    private CCCar Car = null;
    List<CCCar> Cardata = null;
    List<CCOrder> Orderdata = null;
    private void reloadCarTable() {
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
    private void reloadPlanStepTabel(int planIdx){
        if(planIdx<0) return;
        Car=Cardata.get(planIdx);
        try {
            Orderdata= CCcarUtil.orderManager.loadrent(Car);
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
    public FrmCarRentCheck(){
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("汽车租用情况");
//        setSize(600, 380);// 设置窗体大小
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.menu_cancel.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);
        this.getContentPane().add(new JScrollPane(this.dataTableCar), BorderLayout.WEST);
        this.dataTableCar.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e) {
                int i=FrmCarRentCheck.this.dataTableCar.getSelectedRow();
                if(i<0) {
                    return;
                }
                FrmCarRentCheck.this.reloadPlanStepTabel(i);
            }

        });
        this.getContentPane().add(new JScrollPane(this.dataTableOrder), BorderLayout.CENTER);
        this.reloadCarTable();
        menubar.add(menu_cancel);
        this.setJMenuBar(menubar);

        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == this.menuItem_Cancel) {
            setVisible(false);
            return;
        }
    }
}
