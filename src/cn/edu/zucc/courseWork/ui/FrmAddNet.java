package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmAddNet extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelName = new JLabel("网点名称");
    private JLabel labelprice = new JLabel("所在城市");
    private JLabel labelstart = new JLabel("网点地址");
    private JLabel labelend = new JLabel("联系方式");

    private JTextField edtName = new JTextField(15);
    private JTextField edtprice = new JTextField(15);
    private JTextField edtstart = new JTextField(15);
    private JTextField edtend = new JTextField(15);
    public FrmAddNet(JFrame f, String s, boolean b) {
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
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(250, 200);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.btnOk.addActionListener(this);
        this.btnCancel.addActionListener(this);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel) {
            this.setVisible(false);
            return;
        }
        else if(e.getSource()==this.btnOk){
            String name=this.edtName.getText();
            String city=this.edtprice.getText();
            String add=this.edtstart.getText();
            String tell=this.edtend.getText();
            try {
                CCcarUtil.netManager.addNet(name,city,add,tell);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
