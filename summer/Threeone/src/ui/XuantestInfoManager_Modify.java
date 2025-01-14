package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.PeopleInfoManagerZ;
import control.RecommendInfoZ;
import control.SystemUserManager;
import control.XuantestInfoManagerZ;
import model.PeopleInfo;
import model.XuantestInfo;
import model.XuetestInfo;
import util.BaseException;
public class XuantestInfoManager_Modify extends JDialog implements ActionListener {
	private XuantestInfo xuantestinfo=null;
	String subbname;
	String testtid;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JComboBox cmbUsertype1= new JComboBox(new String[] { "物理", "化学","生物","政治","历史","地理"});
	JTextField edtx = new JTextField(20);
	public XuantestInfoManager_Modify(JDialog f, String s, boolean b,XuantestInfo rt) {
		super(f, s, b);
		this.xuantestinfo=rt;
		String subname=xuantestinfo.getTestname();
		this.subbname=subname;
		this.testtid=xuantestinfo.getTestid();
		int grade=xuantestinfo.getGrade();
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		this.cmbUsertype1.setSelectedItem(rt.getTestname());
		workPane.add(cmbUsertype1);
		this.edtx.setText(String.valueOf(this.xuantestinfo.getGrade()));
		workPane.add(edtx);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 300);
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
			this.xuantestinfo=null;
			return;
		}
		else if(e.getSource()==this.btnOk){
			int gradex= Integer.parseInt(this.edtx.getText().toString());
			System.out.print(gradex);
			String namex=this.cmbUsertype1.getSelectedItem().toString();
			System.out.print(namex);
			PeopleInfoManagerZ y=new PeopleInfoManagerZ();
//			int n=0;
//			try{
//				n=Integer.parseInt(this.edtLimited.getText());
//			}catch(Exception ex){
//				JOptionPane.showMessageDialog(null, this.edtLimited.getText()+"不是一个合法的整数","错误",JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			if(n<0 || n>100){
//				JOptionPane.showMessageDialog(null, "借阅限制必须在0-100之间","错误",JOptionPane.ERROR_MESSAGE);
//				return;
//			}
			XuantestInfo gg=new XuantestInfo();
			gg.setGrade(gradex);
			gg.setPeopleid(SystemUserManager.currentUser.getPeopleid());
			gg.setTestname(namex);
			
			try {
				gg.setTestid(y.LoadTestid(namex));
			} catch (BaseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				(new XuantestInfoManagerZ()).modifyXuantestInfo(this.xuantestinfo,gg);
				(new RecommendInfoZ()).removePRecommend(SystemUserManager.currentUser.getPeopleid());

				this.setVisible(false);
			} catch (BaseException e1) {
				this.xuantestinfo=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public XuantestInfo getXuantestInfo() {
		return xuantestinfo;
	}
	
}
