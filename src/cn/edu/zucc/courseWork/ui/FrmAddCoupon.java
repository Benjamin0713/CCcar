package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCStaff;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmAddCoupon extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelName = new JLabel("优惠内容");
    private JLabel labelprice = new JLabel("减免金额");
    private JLabel labelstart = new JLabel("起始时间");
    private JLabel labelend = new JLabel("结束时间");
    private JLabel labelnet = new JLabel("优惠网点");

    private JTextField edtName = new JTextField(15);
    private JTextField edtprice = new JTextField(15);
    private JTextField edtstart = new JTextField(15);
    private JTextField edtend = new JTextField(15);
    private JTextField edtnet = new JTextField(15);
    public FrmAddCoupon(JFrame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelName);
        workPane.add(edtName);
        workPane.add(labelprice);
        workPane.add(edtprice);
        workPane.add(labelstart);
        workPane.add(edtstart);
        workPane.add(labelend);
        workPane.add(edtend);
        workPane.add(labelnet);
        workPane.add(edtnet);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(250, 250);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.btnOk.addActionListener(this);
        this.btnCancel.addActionListener(this);

        CCStaff staff = new CCStaff();
        staff = CCStaff.currentLoginStaff;

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel) {
            this.setVisible(false);
            return;
        }
        else if(e.getSource()==this.btnOk){
            String name=this.edtName.getText();
            String price=this.edtprice.getText();
            String start=this.edtstart.getText();
            String end=this.edtend.getText();
            String net=this.edtnet.getText();
            try {
                CCcarUtil.couponManager.addCoupon(name,price,start,end,net);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
