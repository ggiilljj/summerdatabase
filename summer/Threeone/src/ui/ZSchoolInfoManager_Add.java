package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.PeopleInfoManagerZ;
import control.SchoolInfoManagerZ;
import control.SystemUserManager;
import model.PeopleInfo;
import model.SchoolInfo;
import util.BaseException;

public class ZSchoolInfoManager_Add extends JDialog implements ActionListener{
	private SchoolInfo schoolinfo=null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelSchoolid = new JLabel("学校id：");
	private JLabel labelSchoolname= new JLabel("学校名：");
	private JLabel labelProvince= new JLabel("所属省份：");
	private JLabel labelCity= new JLabel("所属城市：");
	private JLabel labelSchooldesc= new JLabel("备注描述：");
	private JTextField edtSchoolid = new JTextField(20);
	private JTextField edtSchoolname= new JTextField(20);
	private JTextField edtProvince= new JTextField(20);
	private JTextField edtCity= new JTextField(20);
	private JTextField edtSchooldesc= new JTextField(20);
	public ZSchoolInfoManager_Add(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelSchoolid);
		workPane.add(edtSchoolid);
		workPane.add(labelSchoolname);
		workPane.add(edtSchoolname);
		workPane.add(labelProvince);
		workPane.add(edtProvince);
		workPane.add(labelCity);
		workPane.add(edtCity);
		workPane.add(labelSchooldesc);
		workPane.add(edtSchooldesc);
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
			return;
		}
		else if(e.getSource()==this.btnOk){
			SchoolInfo s=new SchoolInfo();
			String Schoolid=this.edtSchoolid.getText();
			String Schoolname=this.edtSchoolname.getText();
			String Province=this.edtProvince.getText();
			String City=this.edtCity.getText();
			String Schooldesc=this.edtSchooldesc.getText();
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
			this.schoolinfo=new SchoolInfo();
			this.schoolinfo.setSchoolname(Schoolname);
			this.schoolinfo.setSchoolid(Schoolid);
			this.schoolinfo.setProvince(Province);
			this.schoolinfo.setCity(City);
			this.schoolinfo.setSchooldesc(Schooldesc);
			try {
				(new SchoolInfoManagerZ()).createSchoolInfo(this.schoolinfo);
				this.setVisible(false);
			} catch (BaseException e1) {
				this.schoolinfo=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public SchoolInfo getPeopleInfo() {
		return schoolinfo;
	}
	
}