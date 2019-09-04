package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import control.RecommendInfoZ;
import control.SchoolInfoManagerZ;
import model.EnrollPlan;
import model.MajorInfo;
import model.SchoolInfo;
import util.BaseException;

public class EnrollPlanManager_Modify extends JDialog implements ActionListener {
	private EnrollPlan enrollplan=null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");//�����ƻ�������
//	private JLabel labelEnrollid = new JLabel("רҵid��");
//	private JLabel labelSchoolid= new JLabel("ѧУid��");
	private JLabel labelEnrollyear= new JLabel("¼ȡ��ݣ�");
	private JLabel labelPlannum= new JLabel("�ƻ�����");
	private JLabel labelTuition= new JLabel("ѧ�ѣ�");
	private JLabel labelOtherrequire= new JLabel("����Ҫ��");
	private JLabel labelxuan= new JLabel("ѡ����Ŀ��д��");
	private JComboBox cmbUsertype1= new JComboBox(new String[] { "����", "��ѧ","����","����","��ʷ","����","��"});
	private JComboBox cmbUsertype2= new JComboBox(new String[] { "����", "��ѧ","����","����","��ʷ","����","��"});
	private JComboBox cmbUsertype3= new JComboBox(new String[] { "����", "��ѧ","����","����","��ʷ","����","��"});
//	private JTextField edtEnrollid = new JTextField(20);
//	private JTextField edtSchoolid= new JTextField(20);
	private JTextField edtEnrollyear= new JTextField(20);
	private JTextField edtPlannum= new JTextField(20);
	private JTextField edtTuition= new JTextField(20);
	private JTextField edtOtherrequire= new JTextField(20);
	public EnrollPlanManager_Modify(JDialog f, String s, boolean b,EnrollPlan rt) {
		super(f, s, b);
		this.enrollplan=rt;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
//		workPane.add(labelEnrollid);
//		this.edtEnrollid.setText(rt.getEnrollid());
//		workPane.add(edtEnrollid);
//		workPane.add(labelSchoolid);
//		this.edtSchoolid.setText(rt.getSchoolid());
//		workPane.add(edtSchoolid);
		workPane.add(labelEnrollyear);
		this.edtEnrollyear.setText(String.valueOf(rt.getEnrollyear()));
		workPane.add(edtEnrollyear);
		workPane.add(labelPlannum);
		this.edtPlannum.setText(String.valueOf(rt.getPlannum()));
		workPane.add(edtPlannum);
		workPane.add(labelTuition);
		this.edtTuition.setText(String.valueOf(rt.getTuition()));
		workPane.add(edtTuition);
		workPane.add(labelOtherrequire);
		this.edtOtherrequire.setText(rt.getOtherrequire());
		workPane.add(edtOtherrequire);
		workPane.add(labelxuan);
		workPane.add(cmbUsertype1);
		workPane.add(cmbUsertype2);
		workPane.add(cmbUsertype3);
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
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				EnrollPlanManager_Modify.this.enrollplan=null;
			}
		});
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			this.enrollplan=null;
			return;
		}
		else if(e.getSource()==this.btnOk){
			EnrollPlan s=new EnrollPlan();
//			String enrollid=this.edtEnrollid.getText().toString();
//			String schoolid=this.edtSchoolid.getText().toString();
			int enrollyear=Integer.parseInt(this.edtEnrollyear.getText().toString());
			int plannum=Integer.parseInt(this.edtPlannum.getText().toString());
			double tuition=Double.parseDouble(this.edtTuition.getText().toString());
			String otherrequire=this.edtOtherrequire.getText();
			String xuan1=this.cmbUsertype1.getSelectedItem().toString();
			String xuan2=this.cmbUsertype2.getSelectedItem().toString();
			String xuan3=this.cmbUsertype3.getSelectedItem().toString();
			Set<String>set=new HashSet<String>();
			String xuanke="";
			if(!xuan1.equals("��")){set.add(xuan1);}
			System.out.print(!xuan1.equals("��"));
			if(!xuan2.equals("��")) {set.add(xuan2);}
			if(!xuan3.equals("��")) {set.add(xuan3);}
			Iterator<String> it = set.iterator();  
			System.out.print(it.hasNext());
			if(set.isEmpty()) xuanke="��";
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
//				JOptionPane.showMessageDialog(null, this.edtLimited.getText()+"����һ���Ϸ�������","����",JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			if(n<0 || n>100){
//				JOptionPane.showMessageDialog(null, "�������Ʊ�����0-100֮��","����",JOptionPane.ERROR_MESSAGE);
//				return;
//			}
			
			
			s.setEnrollid(this.enrollplan.getEnrollid());
			s.setSchoolid(this.enrollplan.getSchoolid());
			s.setEnrollyear(enrollyear);
			s.setPlannum(plannum);
			s.setTuition(tuition);
			s.setOtherrequire(otherrequire);
			s.setXuansubjects(xuanke);
			try {
				(new EnrollPlanManagerZ()).modifyEnrollPlan(s,this.enrollplan);
				(new RecommendInfoZ()).removeSRecommend(this.enrollplan.getSchoolid());
				this.setVisible(false);
			} catch (BaseException e1) {
				this.enrollplan=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public EnrollPlan getEnrollPlan() {
		return enrollplan;
	}
	
}