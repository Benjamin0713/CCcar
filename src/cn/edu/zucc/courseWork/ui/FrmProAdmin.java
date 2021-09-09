package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.*;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmProAdmin extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_static=new JMenu("限时促销管理");
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JMenuItem menuItem_proadd = new JMenuItem("添加促销");
    private JMenuItem menuItem_promodify = new JMenuItem("修改促销信息");
    private JMenuItem menuItem_prodele = new JMenuItem("删除促销");
    private JMenuItem  menuItem_Cancel = new JMenuItem ("退出");

    private JPanel statusBar = new JPanel();
    private Object tblPromotionTitle[]= CCPromotion.tableTitles;
    private Object tblPromotionData[][];
    DefaultTableModel tabPromotionModel=new DefaultTableModel();
    private JTable dataTablePromotion=new JTable(tabPromotionModel);

    private CCPromotion Promotion = null;
    List<CCPromotion> Promotiondata = null;

    private void reloadPromotionTable() {
        try {
            Promotiondata = CCcarUtil.promManager.loadAllshop();
        }catch (BaseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            return;
        }
        tblPromotionData = new Object[Promotiondata.size()][CCPromotion.tableTitles.length];
        for(int i = 0;i < Promotiondata.size() ; i++)
            for(int j = 0; j< CCPromotion.tableTitles.length; j++)
                tblPromotionData[i][j] = Promotiondata.get(i).getCell(j);
        tabPromotionModel.setDataVector(tblPromotionData, tblPromotionTitle);
        this.dataTablePromotion.validate();
        this.dataTablePromotion.repaint();
    }
    public FrmProAdmin(){
        this.setTitle("限时促销-管理员");
        setSize(680, 380);// 设置窗体大小
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.menu_static.add(menuItem_proadd);this.menuItem_proadd.addActionListener(this);
        this.menu_static.add(menuItem_promodify);this.menuItem_promodify.addActionListener(this);
        this.menu_static.add(menuItem_prodele);this.menuItem_prodele.addActionListener(this);
        this.menu_cancel.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);

        menubar.add(menu_static);
        menubar.add(menu_cancel);
        this.setJMenuBar(menubar);
        this.getContentPane().add(new JScrollPane(this.dataTablePromotion), BorderLayout.CENTER);
        this.reloadPromotionTable();
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        CCAdmin user = new CCAdmin();
        user = CCAdmin.currentLoginAdmin;
        JLabel label=new JLabel("您好!"+user.getStaff_name());//修改成   您好！+登陆用户名
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == this.menuItem_Cancel) {
            setVisible(false);
            return;
        }else if(e.getSource()==this.menuItem_proadd){
            FrmAddPro dlg =new FrmAddPro(null, "添加促销", true);
            dlg.setVisible(true);
        }
        this.reloadPromotionTable();
    }
}