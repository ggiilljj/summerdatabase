package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.EnrollPlanManagerZ;
import control.SignupPlanManagerZ;
import model.EnrollPlan;
import model.MajorInfo;
import model.SignupPlan;
import util.BaseException;

public class SignupPlanManager_Add extends JDialog implements ActionListener{
	private SignupPlan signupplan=null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");//招生计划表。。
	private JLabel labelSchoolid = new JLabel("学校id：");
	private JLabel labelSigncondclass= new JLabel("条件类型：");
	private JLabel labelSigncond1= new JLabel("条件1 :");
	private JLabel labelSigncond2= new JLabel("条件2：");
	private JLabel labelBegintime= new JLabel("开始报名时间：");
	private JLabel labelEndtime= new JLabel("结束报名时间：");
	private JLabel labelSignupway= new JLabel("报名方式：");
	private JComboBox cmbUsertype1= new JComboBox(new String[] { "1：A数", "2：总分计数"});
	private JTextField edtSchoolid = new JTextField(20);
	private JTextField edtSigncond1= new JTextField(20);
	private JTextField edtSigncond2= new JTextField(20);
	private JTextField edtBegintime= new JTextField(20);
	private JTextField edtEndtime= new JTextField(20);
	private JTextField edtSignupway= new JTextField(20);
	public SignupPlanManager_Add(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelSchoolid);
		workPane.add(edtSchoolid);
		workPane.add(labelSigncondclass);
		workPane.add(cmbUsertype1);
		workPane.add(labelSigncond1);
		workPane.add(edtSigncond1);
		workPane.add(labelSigncond2);
		workPane.add(edtSigncond2);
		workPane.add(labelBegintime);
		workPane.add(edtBegintime);
		workPane.add(labelEndtime);
		workPane.add(edtEndtime);
		workPane.add(labelSignupway);
		workPane.add(edtSignupway);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(220, 400);
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
			SignupPlan s=new SignupPlan();
			String schoolid=this.edtSchoolid.getText().toString();
			int signcondclass=this.cmbUsertype1.getSelectedIndex()+1;
			int signcond1=Integer.parseInt(this.edtSigncond1.getText().toString());
			int signcond2=Integer.parseInt(this.edtSigncond2.getText().toString());
			Date begintime=null;
			Date endtime=null;
			begintime = java.sql.Date.valueOf(this.edtBegintime.getText().toString());
			endtime = java.sql.Date.valueOf(this.edtEndtime.getText().toString());
			String signupway=this.edtSignupway.getText();
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
			this.signupplan=new SignupPlan();
			this.signupplan.setSchoolid(schoolid);
			this.signupplan.setSigncondclass(signcondclass);
			this.signupplan.setSigncond1(signcond1);
			this.signupplan.setSigncond2(signcond2);
			this.signupplan.setBegintime(begintime);
			this.signupplan.setEndtime(endtime);
			this.signupplan.setSignupway(signupway);
			try {
				(new SignupPlanManagerZ()).createSignupPlan(this.signupplan);
				this.setVisible(false);
			} catch (BaseException e1) {
				this.signupplan=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public SignupPlan getSignupplan() {
		return this.signupplan;
	}
	
}