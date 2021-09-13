package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCAdmin;
import cn.edu.zucc.courseWork.model.CCUser;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmACheckU extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar();
    private JMenu menu_Modify =new JMenu("用户管理");
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JMenuItem  menuItem_name=new JMenuItem("注销用户");
    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");


    private JPanel statusBar = new JPanel();

    private Object tblUserTitle[]=CCUser.tableTitles;
    private Object tblUserData[][];
    DefaultTableModel tabUserModel=new DefaultTableModel();
    private JTable dataTableUser=new JTable(tabUserModel);

    private CCUser User = null;
    List<CCUser> Userdata = null;

    private void reloadStaffTable() {
        try {
            Userdata = CCcarUtil.userManager.loadAll();
        }catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblUserData = new Object[Userdata.size()][CCUser.tableTitles.length];
        for(int i = 0;i < Userdata.size() ; i++)
            for(int j = 0;j<CCUser.tableTitles.length;j++)
                tblUserData[i][j] = Userdata.get(i).getCell(j);
        tabUserModel.setDataVector(tblUserData, tblUserTitle);
        this.dataTableUser.validate();
        this.dataTableUser.repaint();
    }
    public FrmACheckU(){
//        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("个人信息管理-用户");
        setSize(950, 500);// 设置窗体大小
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.menu_Modify.add(menuItem_name);this.menuItem_name.addActionListener(this);
        this.menu_cancel.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);
        menubar.add(menu_Modify);
        menubar.add(menu_cancel);
        this.setJMenuBar(menubar);

        this.getContentPane().add(new JScrollPane(this.dataTableUser), BorderLayout.CENTER);
        this.reloadStaffTable();
        //状态栏
        CCAdmin admin = new CCAdmin();
        admin=CCAdmin.currentLoginAdmin;
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label=new JLabel("您好!"+admin.getStaff_name());//修改成   您好！+登陆用户名
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e){
         if (e.getSource() == this.menuItem_Cancel) {
            setVisible(false);
            return;
        }else if(e.getSource()==this.menuItem_name){
             int i = FrmACheckU.this.dataTableUser.getSelectedRow();
             if (i < 0) {
                 JOptionPane.showMessageDialog(null, "请选择用户", "错误", JOptionPane.ERROR_MESSAGE);
                 return;
             }
             try {
                 CCcarUtil.userManager.delete(this.Userdata.get(i));
             } catch (BaseException e1) {
                 JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                 return;
             }
         }
        this.reloadStaffTable();
    }
}