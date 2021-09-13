package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCAdmin;
import cn.edu.zucc.courseWork.model.CCType;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmUsercheckType extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_cancel = new JMenu("退出该界面");

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

    public FrmUsercheckType(){
        this.setTitle("汽车类别");
        setSize(700, 480);// 设置窗体大小
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.menu_cancel.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);

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
        }
        this.reloadCouponTable();
    }
}
