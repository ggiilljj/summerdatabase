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
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import control.PeopleInfoManagerZ;
import control.SchoolInfoManagerZ;
import control.SystemUserManager;
import model.PeopleInfo;
import model.UsersInfo;
import model.XuetestInfo;
import util.BaseException;
import util.BusinessException;
import model.SchoolInfo;

public class ZSchoolInfoManager extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加学校");
	private Button btnModify = new Button("修改学校信息");
	private Button btnDelete = new Button("删除学校");
	private Map<String,SchoolInfo> SchoolInfo_name=new HashMap<String,SchoolInfo>();
	private Map<String,SchoolInfo> SchoolInfo_id=new HashMap<String,SchoolInfo>();
	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("查询");
	private Object tblTitle[]={"学校编号","学校名","所属省份","所属城市","其他介绍"};
	private Object tblData[][];
	List<SchoolInfo> schools=null;
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable readerTable=new JTable(tablmod);
	private void reloadTable(){
		try {
			schools=(new SchoolInfoManagerZ()).searchSchool(this.edtKeyword.getText().toString());
			tblData =new Object[schools.size()][5];
			for(int i=0;i<schools.size();i++){
				tblData[i][0]=schools.get(i).getSchoolid();
				tblData[i][1]=schools.get(i).getSchoolname();
				tblData[i][2]=schools.get(i).getProvince();
				tblData[i][3]=schools.get(i).getCity();
				tblData[i][4]=schools.get(i).getSchooldesc();

			}
			tablmod.setDataVector(tblData,tblTitle);
			this.readerTable.validate();
			this.readerTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ZSchoolInfoManager(Frame f, String s, boolean b) {
		super(f, s, b);
		//提取读者类别信息
		List<SchoolInfo> types=null;
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
		this.setSize(800, 600);
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
			ZSchoolInfoManager_Add dlg=new ZSchoolInfoManager_Add(this,"添加学校",true);
			dlg.setVisible(true);
			this.reloadTable();
//			if(dlg.getReader()!=null){//刷新表格
//				this.reloadTable();
//			}
		}
		else if(e.getSource()==this.btnModify){
			int i=this.readerTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择学校","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			SchoolInfo school=this.schools.get(i);
//			BeanReader reader=this.readers.get(i);
//			
			ZSchoolInfoManager_Modify dlg=new ZSchoolInfoManager_Modify(this,"修改读者",true,school);
			dlg.setVisible(true);
			if(dlg.getSchoolInfo()!=null){//刷新表格
				this.reloadTable();
			}
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.readerTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择学校信息","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			SchoolInfo school=this.schools.get(i);
			if(JOptionPane.showConfirmDialog(this,"确定删除该学校吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					SchoolInfoManagerZ s=new SchoolInfoManagerZ();
					System.out.print(school.getSchoolid());
					s.removeSchools(school.getSchoolid().toString());
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
