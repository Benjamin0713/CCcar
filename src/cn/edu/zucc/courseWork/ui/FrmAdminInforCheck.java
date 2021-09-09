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

public class FrmAdminInforCheck extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar();
    private JMenu menu_Modify =new JMenu("信息修改");
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JMenuItem  menuItem_name=new JMenuItem("修改用户名");
    private JMenuItem menuItem_pwd = new JMenuItem("修改密码");
    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");

    private JPanel statusBar = new JPanel();

    private Object tblAdminTitle[]=CCAdmin.tableTitles;
    private Object tblAdminData[][];
    DefaultTableModel tabAdminModel=new DefaultTableModel();
    private JTable dataTableAdmin=new JTable(tabAdminModel);

    private CCAdmin Admin = null;
    List<CCAdmin> Admindata = null;

    private void reloadStaffTable() {
        try {
            Admindata = CCcarUtil.adminManager.loadAllshop();
        }catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblAdminData = new Object[Admindata.size()][CCAdmin.tableTitles.length];
        for(int i = 0;i < Admindata.size() ; i++)
            for(int j = 0;j<CCAdmin.tableTitles.length;j++)
                tblAdminData[i][j] = Admindata.get(i).getCell(j);
        tabAdminModel.setDataVector(tblAdminData, tblAdminTitle);
        this.dataTableAdmin.validate();
        this.dataTableAdmin.repaint();
    }
    public FrmAdminInforCheck(){
//        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("个人信息管理-管理员");
        setSize(480, 380);// 设置窗体大小
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

        this.getContentPane().add(new JScrollPane(this.dataTableAdmin), BorderLayout.CENTER);
        this.reloadStaffTable();
        //状态栏
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        CCAdmin admin = new CCAdmin();
        admin=CCAdmin.currentLoginAdmin;
        JLabel label=new JLabel("您好!"+admin.getStaff_name());//修改成   您好！+登陆用户名
        statusBar.add(label);
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==this.menuItem_pwd){
            FrmModifyPwdAdmin dlg = new FrmModifyPwdAdmin(null, "修改密码", true);
            dlg.setVisible(true);
        }else if (e.getSource() == this.menuItem_Cancel) {
            setVisible(false);
            return;
        }else if(e.getSource()==this.menuItem_name){
            FrmModifyAdminname dlg =new FrmModifyAdminname(null, "修改用户名", true);
            dlg.setVisible(true);
        }
        this.reloadStaffTable();
    }
}