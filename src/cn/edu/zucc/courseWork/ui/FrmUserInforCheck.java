package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.model.CCStaff;
import cn.edu.zucc.courseWork.model.CCUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrmUserInforCheck extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar();
    private JMenu menu_Modify =new JMenu("信息修改");
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JMenuItem  menuItem_name=new JMenuItem("修改用户名");
    private JMenuItem menuItem_sex = new JMenuItem("修改性别");
    private JMenuItem menuItem_pwd = new JMenuItem("修改密码");
    private JMenuItem menuItem_tell = new JMenuItem("修改手机号");
    private JMenuItem menuItem_email = new JMenuItem("修改绑定邮箱");
    private JMenuItem menuItem_city = new JMenuItem("修改所在城市");
    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");


    private JPanel statusBar = new JPanel();

    public FrmUserInforCheck(){
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("个人信息管理-用户");

        this.menu_Modify.add(menuItem_name);this.menuItem_name.addActionListener(this);
        this.menu_Modify.add(menuItem_pwd);this.menuItem_pwd.addActionListener(this);
        this.menu_Modify.add(menuItem_sex);this.menuItem_sex.addActionListener(this);
        this.menu_Modify.add(menuItem_tell);this.menuItem_tell.addActionListener(this);
        this.menu_Modify.add(menuItem_email);this.menuItem_email.addActionListener(this);
        this.menu_Modify.add(menuItem_city);this.menuItem_city.addActionListener(this);
        this.menu_cancel.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);
        menubar.add(menu_Modify);
        menubar.add(menu_cancel);
        this.setJMenuBar(menubar);

        //状态栏
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        CCUser user = new CCUser();
        user = CCUser.currentLoginUser;
        JLabel label=new JLabel("您好!"+user.getUser_name());//修改成   您好！+登陆用户名
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);
//        this.addWindowListener(new WindowAdapter(){
//            public void windowClosing(WindowEvent e){
//                System.exit(0);
//            }
//        });
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==this.menuItem_pwd){
            FrmModifyPwdUser dlg = new FrmModifyPwdUser(null, "修改密码", true);
            dlg.setVisible(true);
        }else if (e.getSource() == this.menuItem_Cancel) {
            setVisible(false);
            return;
        }
    }

}