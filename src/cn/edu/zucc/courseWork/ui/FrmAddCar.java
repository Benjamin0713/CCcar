package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.util.BaseException;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrmAddCar extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnOk = new JButton("确定");
    private JButton btnCancel = new JButton("取消");
    private JLabel labelName = new JLabel("汽车车型");
    private JLabel labelprice = new JLabel("汽车牌照");
    private JLabel labelstart = new JLabel("所属网点");
    private JLabel labelend = new JLabel("汽车状态");

    private JTextField edtName = new JTextField(15);
    private JTextField edtprice = new JTextField(15);
    private JTextField edtstart = new JTextField(15);
    private JTextField edtend = new JTextField(15);
    public FrmAddCar(JFrame f, String s, boolean b) {
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
            String model=this.edtName.getText();
            String licen=this.edtprice.getText();
            String net=this.edtstart.getText();
            String state=this.edtend.getText();
            //车牌号格式验证
            String vehicleNoStyle = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{1}(([A-HJ-Z]{1}[A-HJ-NP-Z0-9]{5})|([A-HJ-Z]{1}(([DF]{1}[A-HJ-NP-Z0-9]{1}[0-9]{4})|([0-9]{5}[DF]{1})))|([A-HJ-Z]{1}[A-D0-9]{1}[0-9]{3}警)))|([0-9]{6}使)|((([沪粤川云桂鄂陕蒙藏黑辽渝]{1}A)|鲁B|闽D|蒙E|蒙H)[0-9]{4}领)|(WJ[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼·•]{1}[0-9]{4}[TDSHBXJ0-9]{1})|([VKHBSLJNGCE]{1}[A-DJ-PR-TVY]{1}[0-9]{5})";
//            "^[\u4e00-\u9fa5]{1}[A-Z0-9]{6}$";
            Pattern pattern = Pattern.compile(vehicleNoStyle);
            Matcher matcher = pattern.matcher(this.edtprice.getText().trim());
            if (!matcher.matches()) {
                JOptionPane.showMessageDialog(null, "车牌号格式不对！请认真填写！(字母必须大写)", "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                CCcarUtil.carManager.addCar(model,licen,net,state);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
