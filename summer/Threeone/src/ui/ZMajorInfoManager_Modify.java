package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.MajorInfoManagerZ;
import control.SchoolInfoManagerZ;
import model.MajorInfo;
import model.SchoolInfo;
import util.BaseException;

public class ZMajorInfoManager_Modify extends JDialog implements ActionListener {
	private MajorInfo majorinfo=null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	//private JLabel labelMajorid = new JLabel("专业id：");
	private JLabel labelMajorname= new JLabel("专业名：");
	private JLabel labelMajorclass= new JLabel("专业类别：");
	//private JLabel labelSchoolid= new JLabel("学校id：");
	//private JLabel labelSchoolname= new JLabel("学校名：");
	//private JTextField edtMajorid = new JTextField(20);
	private JTextField edtMajorname= new JTextField(20);
	private JTextField edtMajorclass= new JTextField(20);
	//private JTextField edtSchoolid= new JTextField(20);
	//private JTextField edtSchoolname= new JTextField(20);
	public ZMajorInfoManager_Modify(JDialog f, String s, boolean b,MajorInfo rt) {
		super(f, s, b);
		this.majorinfo=rt;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
//		workPane.add(labelMajorid);
//		this.edtMajorid.setText(rt.getMajorid());
//		workPane.add(edtMajorid);
		workPane.add(labelMajorname);
		this.edtMajorname.setText(rt.getMajorname());
		workPane.add(edtMajorname);
		workPane.add(labelMajorclass);
		this.edtMajorclass.setText(rt.getMajorclass());
//		workPane.add(edtMajorclass);
//		workPane.add(labelSchoolid);
//		this.edtSchoolid.setText(rt.getSchoolid());
//		workPane.add(edtSchoolid);
//		workPane.add(labelSchoolname);
//		this.edtSchoolname.setText(rt.getSchoolname());
//		workPane.add(edtSchoolname);
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
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ZMajorInfoManager_Modify.this.majorinfo=null;
			}
		});
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			this.majorinfo=null;
			return;
		}
		else if(e.getSource()==this.btnOk){
			MajorInfo s=new MajorInfo();
			//String Majorid=this.edtMajorid.getText();
			String Majorname=this.edtMajorname.getText();
			String Majorclass=this.edtMajorclass.getText();
			//String Schoolid=this.edtSchoolid.getText();
			//String Schoolname=this.edtSchoolname.getText();
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
			
			
			s.setSchoolname(this.majorinfo.getSchoolname());
			s.setSchoolid(this.majorinfo.getSchoolid());
			s.setMajorid(this.majorinfo.getMajorid());
			s.setMajorname(Majorname);
			s.setMajorclass(Majorclass);
			try {
				(new MajorInfoManagerZ()).modifyMajorInfo(s,this.majorinfo);
				
				this.setVisible(false);
			} catch (BaseException e1) {
				this.majorinfo=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public MajorInfo getMajorInfo() {
		return majorinfo;
	}
	
}
