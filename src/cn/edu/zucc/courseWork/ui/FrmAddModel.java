package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmAddModel extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labeltype = new JLabel("汽车类别");
    private JLabel labelName = new JLabel("车型名称");
    private JLabel labelbrand = new JLabel("汽车品牌");
    private JLabel labelcap = new JLabel("汽车排量");
    private JLabel labeltran = new JLabel("汽车排档");
    private JLabel labelsite = new JLabel("总座位数");
    private JLabel labelprice = new JLabel("租车价格");

    private JTextField edttype = new JTextField(15);
    private JTextField edtName = new JTextField(15);
    private JTextField edtbrand = new JTextField(15);
    private JTextField edtcap = new JTextField(15);
    private JTextField edttran = new JTextField(15);
    private JTextField edtsite = new JTextField(15);
    private JTextField edtprice = new JTextField(15);
    public FrmAddModel(JFrame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labeltype);
        workPane.add(edttype);
        workPane.add(labelName);
        workPane.add(edtName);
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
        this.setSize(250, 350);
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
            String type = this.edttype.getText();
            String name = this.edtName.getText();
            String brand = this.edtbrand.getText();
            String cap = this.edtcap.getText();
            String tran = this.edttran.getText();
            String site = this.edtsite.getText();
            String price = this.edtprice.getText();
            try {
                CCcarUtil.carModelManager.addModel(type,name,brand,cap,tran,site,price);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}