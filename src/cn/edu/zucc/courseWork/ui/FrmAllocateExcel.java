package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCAllocation;
import cn.edu.zucc.courseWork.model.CCScrap;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmAllocateExcel extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");

    private Object tblAllocationTitle[]= CCAllocation.tableTitles;
    private Object tblAllocationData[][];
    DefaultTableModel tabAllocationModel=new DefaultTableModel();
    private JTable dataTableAllocation=new JTable(tabAllocationModel);

    private CCAllocation Allocation = null;
    List<CCAllocation> Allocationdata = null;

    private void reloadScrapTable() {
        try {
            Allocationdata = CCcarUtil.allocateManager.loadAll();
        }catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblAllocationData = new Object[Allocationdata.size()][CCAllocation.tableTitles.length];
        for(int i = 0;i < Allocationdata.size() ; i++)
            for(int j = 0;j<CCAllocation.tableTitles.length;j++)
                tblAllocationData[i][j] = Allocationdata.get(i).getCell(j);
        tabAllocationModel.setDataVector(tblAllocationData, tblAllocationTitle);
        this.dataTableAllocation.validate();
        this.dataTableAllocation.repaint();
    }
    //    private JPanel statusBar = new JPanel();
    public FrmAllocateExcel(){
        this.setTitle("汽车调拨表");
        setSize(600, 380);// 设置窗体大小
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.menu_cancel.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);
        this.getContentPane().add(new JScrollPane(this.dataTableAllocation), BorderLayout.CENTER);
        this.reloadScrapTable();
        menubar.add(menu_cancel);
        this.setJMenuBar(menubar);

        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == this.menuItem_Cancel) {
            setVisible(false);
            return;
        }
        this.reloadScrapTable();
    }
}