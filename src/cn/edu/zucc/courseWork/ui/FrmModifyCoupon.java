package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmModifyCoupon extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelcar=new JLabel("待修改优惠券编号：");
    private JLabel labelsex = new JLabel("内容：");
    private JLabel labeltell = new JLabel("优惠金额：");
    private JLabel labelemail = new JLabel("开始时间：");
    private JLabel labelcity = new JLabel("结束时间：");

    private JTextField edtcar=new JTextField(15);
    private JTextField edtsex = new JTextField(15);
    private JTextField edttell = new JTextField(15);
    private JTextField edtemail = new JTextField(15);
    private JTextField edtcity = new JTextField(15);
    public FrmModifyCoupon(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelcar);
        workPane.add(edtcar);
        workPane.add(labelsex);
        workPane.add(edtsex);
        workPane.add(labeltell);
        workPane.add(edttell);
        workPane.add(labelemail);
        workPane.add(edtemail);
        workPane.add(labelcity);
        workPane.add(edtcity);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(220, 350);
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
        if (e.getSource() == this.btnCancel)
            this.setVisible(false);
        else if (e.getSource() == this.btnOk) {
            try {
                CCcarUtil.couponManager.changecou(new String(edtcar.getText()),new String(edtsex.getText()), new String(edttell.getText()), new String(edtemail.getText()), new String(edtcity.getText()));
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}