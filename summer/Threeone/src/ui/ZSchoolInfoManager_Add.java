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
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	private JLabel labelSchoolid = new JLabel("ѧУid��");
	private JLabel labelSchoolname= new JLabel("ѧУ����");
	private JLabel labelProvince= new JLabel("����ʡ�ݣ�");
	private JLabel labelCity= new JLabel("�������У�");
	private JLabel labelSchooldesc= new JLabel("��ע������");
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
		// ��Ļ������ʾ
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
//				JOptionPane.showMessageDialog(null, this.edtLimited.getText()+"����һ���Ϸ�������","����",JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			if(n<0 || n>100){
//				JOptionPane.showMessageDialog(null, "�������Ʊ�����0-100֮��","����",JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public SchoolInfo getPeopleInfo() {
		return schoolinfo;
	}
	
}