package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCStaff;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Frame;
import java.awt.event.*;
import java.util.List;

public class FrmStaffInforCheck extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar();
    private JMenu menu_Modify =new JMenu("信息修改");
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JMenuItem  menuItem_name=new JMenuItem("修改用户名");
    private JMenuItem menuItem_pwd = new JMenuItem("修改密码");
    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");

    private JPanel statusBar = new JPanel();

    private Object tblStaffTitle[]=CCStaff.tableTitles;
    private Object tblStaffData[][];
    DefaultTableModel tabStaffModel=new DefaultTableModel();
    private JTable dataTableStaff=new JTable(tabStaffModel);

    private CCStaff staff = null;
    List<CCStaff> staffdata = null;

    private void reloadStaffTable() {
        try {
            staffdata = CCcarUtil.staffManager.loadAllshop();
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
    public FrmStaffInforCheck(){
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
        this.menu_Modify.add(menuItem_pwd);this.menuItem_pwd.addActionListener(this);
        this.menu_cancel.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);
        menubar.add(menu_Modify);
        menubar.add(menu_cancel);
        this.setJMenuBar(menubar);

        this.getContentPane().add(new JScrollPane(this.dataTableStaff), BorderLayout.CENTER);
        this.reloadStaffTable();

        //状态栏
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        CCStaff staff = new CCStaff();
        staff = CCStaff.currentLoginStaff;
        JLabel label=new JLabel("您好!"+staff.getStaff_name());//修改成   您好！+登陆用户名
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);
//        this.addWindowListener(new WindowAdapter(){
//            public void windowClosing(WindowEvent e){
//               System.exit(0);
//            }
//        });
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==this.menuItem_pwd){
            FrmModifyPwdStaff dlg = new FrmModifyPwdStaff(null, "修改密码", true);
            dlg.setVisible(true);
        }else if (e.getSource() == this.menuItem_Cancel) {
            setVisible(false);
            return;
        }else if(e.getSource()==this.menuItem_name){
            FrmModifyStaffname dlg =new FrmModifyStaffname(null, "修改用户名", true);
            dlg.setVisible(true);
        }
        this.reloadStaffTable();
    }
}
