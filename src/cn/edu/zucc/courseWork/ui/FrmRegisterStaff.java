package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCStaff;
import cn.edu.zucc.courseWork.model.CCUser;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmRegisterStaff extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("注册");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelstaffId = new JLabel("工作编号：");
    private JLabel labelNet = new JLabel("所在网点：");
    private JLabel labelUser = new JLabel("用户名：");
    private JLabel labelPwd = new JLabel("密码：");
    private JLabel labelPwd2 = new JLabel("密码：");

    private JTextField edtUserId = new JTextField(15);
    private JPasswordField edtPwd = new JPasswordField(15);
    private JPasswordField edtPwd2 = new JPasswordField(15);
    private JTextField edtNet = new JTextField(15);
    private JTextField edtstaffId = new JTextField(15);
    public FrmRegisterStaff(Dialog f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelUser);
        workPane.add(edtUserId);
        workPane.add(labelstaffId);
        workPane.add(edtstaffId);
        workPane.add(labelNet);
        workPane.add(edtNet);
        workPane.add(labelPwd);
        workPane.add(edtPwd);
        workPane.add(labelPwd2);
        workPane.add(edtPwd2);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(230, 320);
        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnCancel)
            this.setVisible(false);
        else if (e.getSource() == this.btnOk) {
            String staff_id = this.edtstaffId.getText();
            String net_id = this.edtNet.getText();
            String staffname = this.edtUserId.getText();
            String pwd = new String(this.edtPwd.getPassword());
            String pwd2 = new String(this.edtPwd2.getPassword());
            try {
                CCStaff staff = CCcarUtil.staffManager.reg(staff_id,net_id,staffname,pwd,pwd2);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
