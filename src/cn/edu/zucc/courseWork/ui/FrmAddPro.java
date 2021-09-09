package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmAddPro extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelNet = new JLabel("促销网点");
    private JLabel labelmodel = new JLabel("促销车型");
    private JLabel labeldis = new JLabel("促销折扣");
    private JLabel labelnum = new JLabel("促销数量");
    private JLabel labelstart = new JLabel("开始时间");
    private JLabel labelend = new JLabel("结束时间");

    private JTextField edtNet = new JTextField(15);
    private JTextField edtmodel = new JTextField(15);
    private JTextField edtdis = new JTextField(15);
    private JTextField edtnum = new JTextField(15);
    private JTextField edtstart = new JTextField(15);
    private JTextField edtend = new JTextField(15);
    public FrmAddPro(JFrame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelNet);
        workPane.add(edtNet);
        workPane.add(labelmodel);
        workPane.add(edtmodel);
        workPane.add(labeldis);
        workPane.add(edtdis);
        workPane.add(labelnum);
        workPane.add(edtnum);
        workPane.add(labelstart);
        workPane.add(edtstart);
        workPane.add(labelend);
        workPane.add(edtend);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(250, 300);
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
            String net=this.edtNet.getText();
            String model=this.edtmodel.getText();
            String dis=this.edtdis.getText();
            String num=this.edtnum.getText();
            String start=this.edtstart.getText();
            String end=this.edtend.getText();
            try {
                CCcarUtil.promManager.addPro(net,model,dis,num,start,end);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
