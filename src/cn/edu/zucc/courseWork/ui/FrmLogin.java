package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCAdmin;
import cn.edu.zucc.courseWork.model.CCStaff;
import cn.edu.zucc.courseWork.model.CCUser;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrmLogin extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JPanel LoginPane = new JPanel();
    private JButton btnLogin = new JButton("登陆");
    private JButton btnCancel = new JButton("退出");
    private JButton btnRegister = new JButton("注册");
    private ButtonGroup buttonGroup = new ButtonGroup();
    private JRadioButton btnLoginP = new JRadioButton("用户");
    private JRadioButton btnLoginA = new JRadioButton("管理员");
    private JRadioButton btnLoginS = new JRadioButton("工作员");
    private JLabel labelUser = new JLabel("用户：");
    private JLabel labelPwd = new JLabel("密码：");
    private JTextField edtUserId = new JTextField(20);
    private JPasswordField edtPwd = new JPasswordField(20);

    public FrmLogin(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        toolBar.add(this.btnRegister);
        toolBar.add(btnLogin);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        btnLoginP.setSelected(true);
        buttonGroup.add(btnLoginP);
        buttonGroup.add(btnLoginA);
        buttonGroup.add(btnLoginS);
        LoginPane.add(btnLoginP);
        LoginPane.add(btnLoginS);
        LoginPane.add(btnLoginA);
        this.getContentPane().add(LoginPane,BorderLayout.NORTH);
        workPane.add(labelUser);
        workPane.add(edtUserId);
        workPane.add(labelPwd);
        workPane.add(edtPwd);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(320, 180);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        btnLogin.addActionListener(this);
        btnCancel.addActionListener(this);
        this.btnRegister.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnLogin) {
            String name=this.edtUserId.getText();
            String pwd=new String(this.edtPwd.getPassword());
            if(((JRadioButton)LoginPane.getComponent(0)).isSelected()){
                try {
                    CCUser.currentLoginUser= CCcarUtil.userManager.login(name, pwd);
                    new FrmMainUser().setVisible(true);
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }else if(((JRadioButton)LoginPane.getComponent(1)).isSelected()){
                try {
                    CCStaff.currentLoginStaff= CCcarUtil.staffManager.login(name, pwd);
                    new FrmMainStaff().setVisible(true);
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }else {
                try {
                    CCAdmin.currentLoginAdmin= CCcarUtil.adminManager.login(name, pwd);
                    new FrmMainAdmin().setVisible(true);
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            this.setVisible(false);
        } else if (e.getSource() == this.btnCancel) {
            System.exit(0);
        } else if(e.getSource()==this.btnRegister){
            if(((JRadioButton)LoginPane.getComponent(0)).isSelected()){
                FrmRegisterUser dlg=new FrmRegisterUser(this,"注册",true);
                dlg.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null, "无法注册", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
//            else if(((JRadioButton)LoginPane.getComponent(1)).isSelected()){
//                FrmRegisterStaff dlg=new FrmRegisterStaff(this,"注册",true);
//                dlg.setVisible(true);
//            }
//            else {
//                FrmRegisterAdmin dlg=new FrmRegisterAdmin(this,"注册",true);
//                dlg.setVisible(true);
//            }
        }
    }
}
