package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import control.EnrollPlanManagerZ;
import control.SignupPlanManagerZ;
import model.EnrollPlan;
import model.SignupPlan;
import util.BaseException;

public class SignupPlanManager extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加报名信息表");
	private Button btnModify = new Button("修改报名信息表");
	private Button btnDelete = new Button("删除报名信息表");
//	private Map<String,SchoolInfo> SchoolInfo_name=new HashMap<String,SchoolInfo>();
//	private Map<String,SchoolInfo> SchoolInfo_id=new HashMap<String,SchoolInfo>();
	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("查询");
	private Object tblTitle[]={"学校id","学校","条件类型","条件1","条件2","开始时间","结束时间","报名方式"};
	private Object tblData[][];
	List<SignupPlan> signups=null;
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable readerTable=new JTable(tablmod);
	private void reloadTable(){
		try {
			signups=(new SignupPlanManagerZ()).searchSignups(this.edtKeyword.getText().toString());
			tblData =new Object[signups.size()][8];
			for(int i=0;i<signups.size();i++){
				tblData[i][0]=signups.get(i).getSchoolid();
				tblData[i][1]=signups.get(i).getSchoolname();
				if(signups.get(i).getSigncondclass()==1)
				{
					tblData[i][2]="A数计";
					tblData[i][3]=signups.get(i).getSigncond1()+"A";
					tblData[i][4]=signups.get(i).getSigncond2()+"A"+"+特长";
				}
				else if(signups.get(i).getSigncondclass()==2)
				{
					tblData[i][2]="分数计";
					tblData[i][3]=signups.get(i).getSigncond1()+"A";
					tblData[i][4]=signups.get(i).getSigncond2()+"A"+"+特长";
				}
				
				tblData[i][5]=signups.get(i).getBegintime();
				tblData[i][6]=signups.get(i).getEndtime();
				tblData[i][7]=signups.get(i).getSignupway();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.readerTable.validate();
			this.readerTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public SignupPlanManager(Frame f, String s, boolean b) {
		super(f, s, b);
		//提取读者类别信息
		List<SignupPlan> types=null;
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnModify);
		toolBar.add(this.btnDelete);
		toolBar.add(edtKeyword);
		toolBar.add(btnSearch);
		
		
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadTable();
		this.getContentPane().add(new JScrollPane(this.readerTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800,600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnAdd.addActionListener(this);
		this.btnModify.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.btnSearch.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnAdd){
			SignupPlanManager_Add dlg=new SignupPlanManager_Add(this,"添加学校报名表",true);
			dlg.setVisible(true);
			this.reloadTable();
//			if(dlg.getReader()!=null){//刷新表格
//				this.reloadTable();
//			}
		}
		else if(e.getSource()==this.btnModify){
			int i=this.readerTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择招生计划表","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			SignupPlan signups=this.signups.get(i);
//			BeanReader reader=this.readers.get(i);
//		
			SignupPlanManager_Modify dlg=new SignupPlanManager_Modify(this,"修改招生计划",true,signups);
			dlg.setVisible(true);
			if(dlg.getSignupPlan()!=null){//刷新表格
				this.reloadTable();
			}
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.readerTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择报名信息表","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			SignupPlan signs=this.signups.get(i);
			if(JOptionPane.showConfirmDialog(this,"确定删除该报名信息吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					(new SignupPlanManagerZ()).removeSign(signs);
					this.reloadTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
		else if(e.getSource()==this.btnSearch){
			this.reloadTable();
		}
	}
}