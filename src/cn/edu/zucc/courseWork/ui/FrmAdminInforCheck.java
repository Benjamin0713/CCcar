package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.model.CCAdmin;
import cn.edu.zucc.courseWork.model.CCStaff;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmAdminInforCheck extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar();
    private JMenu menu_Modify =new JMenu("信息修改");
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JMenuItem  menuItem_name=new JMenuItem("修改用户名");
    private JMenuItem menuItem_pwd = new JMenuItem("修改密码");
    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");

    private JPanel statusBar = new JPanel();

    public FrmAdminInforCheck(){
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("个人信息管理-管理员");

        this.menu_Modify.add(menuItem_name);this.menuItem_name.addActionListener(this);
        this.menu_Modify.add(menuItem_pwd);this.menuItem_pwd.addActionListener(this);
        this.menu_cancel.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);
        menubar.add(menu_Modify);
        menubar.add(menu_cancel);
        this.setJMenuBar(menubar);

        //状态栏
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        CCAdmin admin = new CCAdmin();
        admin=CCAdmin.currentLoginAdmin;
        JLabel label=new JLabel("您好!"+admin.getStaff_name());//修改成   您好！+登陆用户名
        statusBar.add(label);
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);
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
            FrmModifyPwdAdmin dlg = new FrmModifyPwdAdmin(null, "修改密码", true);
            dlg.setVisible(true);
        }else if (e.getSource() == this.menuItem_Cancel) {
            setVisible(false);
            return;
        }
    }
}