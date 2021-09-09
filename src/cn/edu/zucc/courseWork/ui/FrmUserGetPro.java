package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCHoldCoupon;
import cn.edu.zucc.courseWork.model.CCProGet;
import cn.edu.zucc.courseWork.model.CCUser;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmUserGetPro extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");

    private JPanel statusBar = new JPanel();
    private Object tblProGetTitle[]= CCProGet.tableTitles;
    private Object tblProGetData[][];
    DefaultTableModel tabProGetModel=new DefaultTableModel();
    private JTable dataTableProGet=new JTable(tabProGetModel);

    private CCProGet ProGet = null;
    List<CCProGet> ProGetdata = null;

    private void reloadCouponTable() {
        try {
            ProGetdata = CCcarUtil.promManager.loadAllHold();
        }catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblProGetData = new Object[ProGetdata.size()][CCProGet.tableTitles.length];
        for(int i = 0;i < ProGetdata.size() ; i++)
            for(int j = 0; j< CCHoldCoupon.tableTitles.length; j++)
                tblProGetData[i][j] = ProGetdata.get(i).getCell(j);
        tabProGetModel.setDataVector(tblProGetData, tblProGetTitle);
        this.dataTableProGet.validate();
        this.dataTableProGet.repaint();
    }
    public FrmUserGetPro(){
        this.setTitle("参加的限时促销");
        setSize(1000, 380);// 设置窗体大小
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.menu_cancel.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);

        menubar.add(menu_cancel);
        this.setJMenuBar(menubar);
        this.getContentPane().add(new JScrollPane(this.dataTableProGet), BorderLayout.CENTER);
        this.reloadCouponTable();
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        CCUser user = new CCUser();
        user = CCUser.currentLoginUser;
        JLabel label=new JLabel("您好!"+user.getUser_name());//修改成   您好！+登陆用户名
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