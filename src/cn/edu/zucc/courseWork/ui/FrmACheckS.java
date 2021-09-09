package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCAdmin;
import cn.edu.zucc.courseWork.model.CCStaff;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmACheckS extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar();
    private JMenu menu_Modify =new JMenu("员工管理");
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JMenuItem  menuItem_name=new JMenuItem("注销员工");
    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");

    private JPanel statusBar = new JPanel();

    private Object tblStaffTitle[]= CCStaff.tableTitles;
    private Object tblStaffData[][];
    DefaultTableModel tabStaffModel=new DefaultTableModel();
    private JTable dataTableStaff=new JTable(tabStaffModel);

    private CCStaff staff = null;
    List<CCStaff> staffdata = null;

    private void reloadStaffTable() {
        try {
            staffdata = CCcarUtil.staffManager.loadAll();
        }catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblStaffData = new Object[staffdata.size()][CCStaff.tableTitles.length];
        for(int i = 0;i < staffdata.size() ; i++)
            for(int j = 0;j<CCStaff.tableTitles.length;j++)
                tblStaffData[i][j] = staffdata.get(i).getCell(j);
        tabStaffModel.setDataVector(tblStaffData, tblStaffTitle);
        this.dataTableStaff.validate();
        this.dataTableStaff.repaint();
    }
    public FrmACheckS(){
//        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("个人信息管理-员工");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 默认关闭方式
        setSize(480, 380);// 设置窗体大小

        // 屏幕居中显示
//        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.menu_Modify.add(menuItem_name);this.menuItem_name.addActionListener(this);
        this.menu_cancel.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);
        menubar.add(menu_Modify);
        menubar.add(menu_cancel);
        this.setJMenuBar(menubar);

        this.getContentPane().add(new JScrollPane(this.dataTableStaff), BorderLayout.CENTER);
        this.reloadStaffTable();

        //状态栏
        CCAdmin admin = new CCAdmin();
        admin=CCAdmin.currentLoginAdmin;
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label=new JLabel("您好!"+admin.getStaff_name());//修改成   您好！+登陆用户名
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == this.menuItem_Cancel) {
            setVisible(false);
            return;
        }
        this.reloadStaffTable();
    }
}
