package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCAdmin;
import cn.edu.zucc.courseWork.model.CCStaff;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmModifyAdminname extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");

    private JLabel labelNameOld = new JLabel("原用户名：");
    private JLabel labelName = new JLabel("新用户名：");
    private JLabel labeltip = new JLabel("修改后请重启系统");
    private JPasswordField edtNameOld = new JPasswordField(20);
    private JPasswordField edtName = new JPasswordField(20);
    public FrmModifyAdminname(Frame f, String s, boolean b){
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelNameOld);
        workPane.add(edtNameOld);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labeltip);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(300, 215);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.btnCancel.addActionListener(this);
        this.btnOk.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==this.btnCancel)
            this.setVisible(false);
        else if(e.getSource()==this.btnOk){
            try {
                CCcarUtil.adminManager.changeName(CCAdmin.currentLoginAdmin,new String(edtNameOld.getText()),new String(edtName.getText()));
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
