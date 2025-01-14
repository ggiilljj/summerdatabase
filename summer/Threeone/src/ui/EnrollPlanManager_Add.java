package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.EnrollPlanManagerZ;
import control.MajorInfoManagerZ;
import model.EnrollPlan;
import model.MajorInfo;
import util.BaseException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
public class EnrollPlanManager_Add extends JDialog implements ActionListener{
	private EnrollPlan enrollplan=null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");//招生计划表。。
	private JLabel labelEnrollid = new JLabel("专业id：");
	private JLabel labelSchoolid= new JLabel("学校id：");
	private JLabel labelEnrollyear= new JLabel("录取年份：");
	private JLabel labelPlannum= new JLabel("计划数：");
	private JLabel labelTuition= new JLabel("学费：");
	private JLabel labelOtherrequire= new JLabel("其他要求：");
	private JLabel labelxuan= new JLabel("选考科目填写：");
	private JComboBox cmbUsertype1= new JComboBox(new String[] { "物理", "化学","生物","政治","历史","地理","无"});
	private JComboBox cmbUsertype2= new JComboBox(new String[] { "物理", "化学","生物","政治","历史","地理","无"});
	private JComboBox cmbUsertype3= new JComboBox(new String[] { "物理", "化学","生物","政治","历史","地理","无"});
	private JTextField edtEnrollid = new JTextField(20);
	private JTextField edtSchoolid= new JTextField(20);
	private JTextField edtEnrollyear= new JTextField(20);
	private JTextField edtPlannum= new JTextField(20);
	private JTextField edtTuition= new JTextField(20);
	private JTextField edtOtherrequire= new JTextField(20);
	public EnrollPlanManager_Add(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelEnrollid);
		workPane.add(edtEnrollid);
		workPane.add(labelSchoolid);
		workPane.add(edtSchoolid);
		workPane.add(labelEnrollyear);
		workPane.add(edtEnrollyear);
		workPane.add(labelPlannum);
		workPane.add(edtPlannum);
		workPane.add(labelTuition);
		workPane.add(edtTuition);
		workPane.add(labelOtherrequire);
		workPane.add(edtOtherrequire);
		workPane.add(labelxuan);
		workPane.add(cmbUsertype1);
		workPane.add(cmbUsertype2);
		workPane.add(cmbUsertype3);
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
			MajorInfo s=new MajorInfo();
			String enrollid=this.edtEnrollid.getText().toString();
			String schoolid=this.edtSchoolid.getText().toString();
			int enrollyear=Integer.parseInt(this.edtEnrollyear.getText().toString());
			int plannum=Integer.parseInt(this.edtPlannum.getText().toString());
			double tuition=Double.parseDouble(this.edtTuition.getText().toString());
			String otherrequire=this.edtOtherrequire.getText();
			String xuan1=this.cmbUsertype1.getSelectedItem().toString();
			String xuan2=this.cmbUsertype2.getSelectedItem().toString();
			String xuan3=this.cmbUsertype3.getSelectedItem().toString();
			Set<String>set=new HashSet<String>();
			String xuanke="";
			if(!xuan1.equals("无")){set.add(xuan1);}
			System.out.print(!xuan1.equals("无"));
			if(!xuan2.equals("无")) {set.add(xuan2);}
			if(!xuan3.equals("无")) {set.add(xuan3);}
			Iterator<String> it = set.iterator();  
			System.out.print(it.hasNext());
			if(set.isEmpty()) xuanke="无";
			else
			{
				while(it.hasNext()) {
					String str=it.next();
					System.out.print(str);
					System.out.print("111");
					xuanke=xuanke+str;
				}
					
			}
		
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
			this.enrollplan=new EnrollPlan();
			this.enrollplan.setEnrollid(enrollid);
			this.enrollplan.setSchoolid(schoolid);
			this.enrollplan.setEnrollyear(enrollyear);
			this.enrollplan.setPlannum(plannum);
			this.enrollplan.setTuition(tuition);
			this.enrollplan.setOtherrequire(otherrequire);
			this.enrollplan.setXuansubjects(xuanke);
			try {
				(new EnrollPlanManagerZ()).createEnrollPlan(this.enrollplan);
				this.setVisible(false);
			} catch (BaseException e1) {
				this.enrollplan=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public EnrollPlan getEnrollPlan() {
		return this.enrollplan;
	}
	
}