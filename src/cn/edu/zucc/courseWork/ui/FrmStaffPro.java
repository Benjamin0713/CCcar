package cn.edu.zucc.courseWork.ui;

import cn.edu.zucc.courseWork.CCcarUtil;
import cn.edu.zucc.courseWork.model.CCAdmin;
import cn.edu.zucc.courseWork.model.CCPromotion;
import cn.edu.zucc.courseWork.model.CCStaff;
import cn.edu.zucc.courseWork.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmStaffPro extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar=new JMenuBar(); ;
    private JMenu menu_static=new JMenu("促销管理");
    private JMenu menu_cancel = new JMenu("退出该界面");
    private JMenu menu_scrap=new JMenu("信息查询");

    private JMenuItem menuItem_checkmodel=new JMenuItem("查看车型信息");
    private JMenuItem menuItem_checknet=new JMenuItem("查看网点信息");
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
            Promotiondata = CCcarUtil.promManager.loadAll();
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
    public FrmStaffPro(){
        this.setTitle("限时促销-工作人员");
        setSize(680, 380);// 设置窗体大小
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.menu_scrap.add(menuItem_checknet);this.menuItem_checknet.addActionListener(this);
        this.menu_scrap.add(menuItem_checkmodel);this.menuItem_checkmodel.addActionListener(this);
        this.menu_static.add(menuItem_proadd);this.menuItem_proadd.addActionListener(this);
        this.menu_static.add(menuItem_promodify);this.menuItem_promodify.addActionListener(this);
        this.menu_static.add(menuItem_prodele);this.menuItem_prodele.addActionListener(this);
        this.menu_cancel.add(menuItem_Cancel);this.menuItem_Cancel.addActionListener(this);

        menubar.add(menu_static);
        menubar.add(menu_scrap);
        menubar.add(menu_cancel);
        this.setJMenuBar(menubar);
        this.getContentPane().add(new JScrollPane(this.dataTablePromotion), BorderLayout.CENTER);
        this.reloadPromotionTable();
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        CCStaff staff=new CCStaff();
        staff=CCStaff.currentLoginStaff;
        JLabel label=new JLabel("您好!"+staff.getStaff_name());//修改成   您好！+登陆用户名
        statusBar.add(label);
        this.getContentPane().add(statusBar,BorderLayout.SOUTH);

        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == this.menuItem_Cancel) {
            setVisible(false);
            return;
        }else if(e.getSource()==this.menuItem_proadd){
            FrmAddprostaff dlg =new FrmAddprostaff(null, "添加促销", true);
            dlg.setVisible(true);
        }else if(e.getSource()==this.menuItem_prodele){
            int i = FrmStaffPro.this.dataTablePromotion.getSelectedRow();
            if (i < 0) {
                JOptionPane.showMessageDialog(null, "请选择折扣", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                CCcarUtil.promManager.delete(this.Promotiondata.get(i));
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }else if(e.getSource()==this.menuItem_promodify){
            FrmModifyPro dlg=new FrmModifyPro(null,"修改促销信息",true);
            dlg.setVisible(true);
        }else if(e.getSource()==this.menuItem_checknet){
            new FrmUsercheckNet().setVisible(true);
        }else if(e.getSource()==this.menuItem_checkmodel){
            new FrmUsercheckModel().setVisible(true);
        }
        this.reloadPromotionTable();
    }
}