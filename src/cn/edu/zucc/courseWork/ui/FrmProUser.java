package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCPromotion;
import cn.edu.zucc.courseWork.model.CCUser;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmProUser extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_coupon=new JMenu("限时促销管理");
    private JMenu menu_cancel = new JMenu("退出该界面");

    private JMenuItem menuItem_couponget = new JMenuItem("领取");
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
    public FrmProUser(){
        this.setTitle("限时促销-用户");
        setSize(680, 380);// 设置窗体大小
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.menu_coupon.add(menuItem_couponget);this.menuItem_couponget.addActionListener(this);
        this.menu_cancel.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);

        menubar.add(menu_coupon);
        menubar.add(menu_cancel);
        this.setJMenuBar(menubar);
        this.getContentPane().add(new JScrollPane(this.dataTablePromotion), BorderLayout.CENTER);
        this.reloadPromotionTable();
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
        }else if (e.getSource() == this.menuItem_couponget) {
            int i = FrmProUser.this.dataTablePromotion.getSelectedRow();
            if (i < 0) {
                JOptionPane.showMessageDialog(null, "请选择促销", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                CCcarUtil.promManager.holdpro(this.Promotiondata.get(i));
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        this.reloadPromotionTable();
    }
}