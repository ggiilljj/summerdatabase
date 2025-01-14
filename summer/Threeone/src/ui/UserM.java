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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import control.SchoolInfoManagerZ;
import control.SystemUserManager;
import model.SchoolInfo;
import model.UsersInfo;
import util.BaseException;

public class UserM extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAddz = new Button("添加管理员用户");
	private Button btnAddu = new Button("添加普通用户");
	//private Button btnModify = new Button("修改用户信息");
	private Button btnDelete = new Button("删除用户");
//	private Map<String,SchoolInfo> SchoolInfo_name=new HashMap<String,SchoolInfo>();
//	private Map<String,SchoolInfo> SchoolInfo_id=new HashMap<String,SchoolInfo>();
	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("查询");
	private Object tblTitle[]={"用户Id","用户名","用户级别","用户密码"};
	private Object tblData[][];
	List<UsersInfo> users=null;
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable readerTable=new JTable(tablmod);
	private void reloadTable(){
		try {
			users=(new SystemUserManager()).loadaUser();
			tblData =new Object[users.size()][4];
			for(int i=0;i<users.size();i++){
				tblData[i][0]=users.get(i).getPeopleid();
				tblData[i][1]=users.get(i).getPeoplename();
				tblData[i][2]=users.get(i).getPeoplegrade();
				tblData[i][3]=users.get(i).getPeoplepwd();

			}
			tablmod.setDataVector(tblData,tblTitle);
			this.readerTable.validate();
			this.readerTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public UserM(Frame f, String s, boolean b) {
		super(f, s, b);
		//提取读者类别信息
		List<SchoolInfo> types=null;
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAddz);
		toolBar.add(btnAddu);
		//toolBar.add(btnModify);
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

		this.btnAddz.addActionListener(this);
		this.btnAddu.addActionListener(this);
		//this.btnModify.addActionListener(this);
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
		if(e.getSource()==this.btnAddu){
			UserMu_Add dlg=new UserMu_Add(null,"添加用户",true);
			dlg.setVisible(true);
			this.reloadTable();
		
//			if(dlg.getReader()!=null){//刷新表格
//				this.reloadTable();
//			}
		}
		else if (e.getSource()==this.btnAddz)
		{
			UserMz_Add dlg=new UserMz_Add(null,"添加用户",true);
			dlg.setVisible(true);
			this.reloadTable();
			
		}
//		else if(e.getSource()==this.btnModify){
//			int i=this.readerTable.getSelectedRow();
//			if(i<0) {
//				JOptionPane.showMessageDialog(null,  "请选择学校","提示",JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			UsersInfo users=this.users.get(i);
//			BeanReader reader=this.readers.get(i);
//			
//			ZSchoolInfoManager_Modify dlg=new ZSchoolInfoManager_Modify(this,"修改读者",true,school);
//			dlg.setVisible(true);
//			if(dlg.getSchoolInfo()!=null){//刷新表格
//				this.reloadTable();
//			}
//		}
		else if(e.getSource()==this.btnDelete){
			int i=this.readerTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择相关帐号","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			UsersInfo user=this.users.get(i);
			if(JOptionPane.showConfirmDialog(this,"确定删除该帐号吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					(new SystemUserManager()).removeUser(user.getPeopleid());
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
