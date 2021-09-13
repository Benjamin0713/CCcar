package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmModifyModel  extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelmodel=new JLabel("待修改车型编号");
    private JLabel labeltype = new JLabel("车类别：");
    private JLabel labelname = new JLabel("车型名称：");
    private JLabel labelbrand = new JLabel("品牌：");
    private JLabel labelcap = new JLabel("排量：");
    private JLabel labeltran = new JLabel("排档：");
    private JLabel labelsite = new JLabel("座位数：");
    private JLabel labelprice = new JLabel("租车价格：");

    private JTextField edtmodel=new JTextField(15);
    private JTextField edttype = new JTextField(15);
    private JTextField edtname = new JTextField(15);
    private JTextField edtbrand = new JTextField(15);
    private JTextField edtcap = new JTextField(15);
    private JTextField edttran = new JTextField(15);
    private JTextField edtsite = new JTextField(15);
    private JTextField edtprice = new JTextField(15);

    public FrmModifyModel (Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelmodel);
        workPane.add(edtmodel);
        workPane.add(labeltype);
        workPane.add(edttype);
        workPane.add(labelname);
        workPane.add(edtname);
        workPane.add(labelbrand);
        workPane.add(edtbrand);
        workPane.add(labelcap);
        workPane.add(edtcap);
        workPane.add(labeltran);
        workPane.add(edttran);
        workPane.add(labelsite);
        workPane.add(edtsite);
        workPane.add(labelprice);
        workPane.add(edtprice);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(220, 500);
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
                CCcarUtil.carModelManager.changemodel(new String(edtmodel.getText()),new String(edttype.getText()), new String(edtname.getText()), new String(edtbrand.getText()), new String(edtcap.getText()), new String(edttran.getText()), new String(edtsite.getText()), new String(edtprice.getText()));
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}