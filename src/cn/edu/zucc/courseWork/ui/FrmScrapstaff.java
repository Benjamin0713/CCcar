package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.model.CCStaff;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmScrapstaff extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_scrap=new JMenu("报废管理");
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JMenuItem menuItem_scrap = new JMenuItem("报废");
    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");

    private JPanel statusBar = new JPanel();
    public FrmScrapstaff(){
        this.setTitle("汽车报废-员工");
        setSize(480, 380);// 设置窗体大小
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.menu_scrap.add(menuItem_scrap);this.menuItem_scrap.addActionListener(this);
        this.menu_cancel.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);

        menubar.add(menu_scrap);
        menubar.add(menu_cancel);
        this.setJMenuBar(menubar);

        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        CCStaff staff = new CCStaff();
        staff = CCStaff.currentLoginStaff;
        JLabel label=new JLabel("您好!"+staff.getStaff_name());//修改成   您好！+登陆用户名
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == this.menuItem_Cancel) {
            setVisible(false);
            return;
        }
    }
}
