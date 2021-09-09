package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCUser;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmRegisterUser extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("注册");
    private JButton btnCancel = new JButton("取消");

    private JLabel labelUser = new JLabel("用户名：");
    private JLabel labelname = new JLabel("姓名：");
    private JLabel labelSex = new JLabel("性别：");
    private JLabel labelPwd = new JLabel("密码：");
    private JLabel labelPwd2 = new JLabel("密码：");
    private JLabel labelTell = new JLabel("手机号：");
    private JLabel labelEmai = new JLabel("邮箱：");
    private JLabel labelCity = new JLabel("城市：");
    private JTextField edtUserId = new JTextField(15);
    private JPasswordField edtPwd = new JPasswordField(15);
    private JPasswordField edtPwd2 = new JPasswordField(15);
    private JTextField edtSex = new JTextField(15);
    private JTextField edtTell = new JTextField(15);
    private JTextField edtEmai = new JTextField(15);
    private JTextField edtCity = new JTextField(15);
    private JTextField edtname = new JTextField(15);

    public FrmRegisterUser(Dialog f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelUser);
        workPane.add(edtUserId);
        workPane.add(labelname);
        workPane.add(edtname);
        workPane.add(labelSex);
        workPane.add(edtSex);
        workPane.add(labelPwd);
        workPane.add(edtPwd);
        workPane.add(labelPwd2);
        workPane.add(edtPwd2);
        workPane.add(labelTell);
        workPane.add(edtTell);
        workPane.add(labelEmai);
        workPane.add(edtEmai);
        workPane.add(labelCity);
        workPane.add(edtCity);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(230, 450);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
        else if(e.getSource()==this.btnOk){
            String userid=this.edtUserId.getText();
            String name=this.edtname.getText();
            String sex=this.edtSex.getText();
            String pwd=new String(this.edtPwd.getPassword());
            String pwd2=new String(this.edtPwd2.getPassword());
            String tell=this.edtTell.getText();
            String email=this.edtEmai.getText();
            String city=this.edtCity.getText();
            try {
                CCUser user= CCcarUtil.userManager.reg(userid,name,sex,pwd,pwd2,tell,email,city);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }

        }


    }
}
