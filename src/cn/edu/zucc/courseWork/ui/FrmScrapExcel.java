package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCScrap;
import cn.edu.zucc.courseWork.model.CCStaff;
import cn.edu.zucc.courseWork.model.CCUser;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmScrapExcel extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");

    private Object tblScrapTitle[]= CCScrap.tableTitles;
    private Object tblScrapData[][];
    DefaultTableModel tabScrapModel=new DefaultTableModel();
    private JTable dataTableScrap=new JTable(tabScrapModel);

    private CCScrap Scrap = null;
    List<CCScrap> Scrapdata = null;

    private void reloadScrapTable() {
        try {
            Scrapdata = CCcarUtil.scrapManager.loadAllshop();
        }catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblScrapData = new Object[Scrapdata.size()][CCScrap.tableTitles.length];
        for(int i = 0;i < Scrapdata.size() ; i++)
            for(int j = 0;j<CCScrap.tableTitles.length;j++)
                tblScrapData[i][j] = Scrapdata.get(i).getCell(j);
        tabScrapModel.setDataVector(tblScrapData, tblScrapTitle);
        this.dataTableScrap.validate();
        this.dataTableScrap.repaint();
    }
//    private JPanel statusBar = new JPanel();
    public FrmScrapExcel(){
        this.setTitle("汽车报废表");
        setSize(600, 380);// 设置窗体大小
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.menu_cancel.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);
        this.getContentPane().add(new JScrollPane(this.dataTableScrap), BorderLayout.CENTER);
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
