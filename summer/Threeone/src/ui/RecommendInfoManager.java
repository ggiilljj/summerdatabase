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
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import control.RecommendInfoZ;
import control.SystemUserManager;
import model.PeopleInfo;
import model.RecommendInfo;
import util.BaseException;

public class RecommendInfoManager extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnRefresh = new Button("刷新用户信息");
	private Object tblTitle[]={"用户id","学校Id","专业id","原因"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable readerTypeTable=new JTable(tablmod);
	private void reloadTable(){
		try {
			RecommendInfoZ s=new RecommendInfoZ();
			List<RecommendInfo> types= s.loadRecommend(SystemUserManager.currentUser.getPeopleid());
			tblData =new Object[types.size()][4];
			for(int i=0;i<types.size();i++)
			{
				tblData[i][0]=types.get(i).getPeopleid()+"";
				tblData[i][1]=types.get(i).getSchoolid();
				tblData[i][2]=types.get(i).getMajorid()+"";
				tblData[i][3]=types.get(i).getReason()+"";
				s.AddRecommend(types.get(i));
			}
			
			tablmod.setDataVector(tblData,tblTitle);
			this.readerTypeTable.validate();
			this.readerTypeTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public RecommendInfoManager(Frame f, String s, boolean b) {
		//提取读者类别信息
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
//		toolBar.add(btnAdd);
//		toolBar.add(btnModify);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadTable();
		this.getContentPane().add(new JScrollPane(this.readerTypeTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

//		this.btnAdd.addActionListener(this);
    	this.btnRefresh.addActionListener(this);
	
		this.addWindowListener((WindowListener) new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//		if(e.getSource()==this.btnAdd){
//			
//			SystemUserManager s=new SystemUserManager();
//			try {
//				List<PeopleInfo> t= s.loadPeople(SystemUserManager.currentUser.getPeopleid());
//				if(t.size()>0)
//				{
//					throw new BusinessException("该学生信息已经录入");
//				}
//				else
//				{
//					PeopleInfoManager_Add dlg=new PeopleInfoManager_Add(this,"用户信息增加",true);
//					dlg.setVisible(true);
//				}
//			} catch (BaseException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
//			}
////			this.reloadTable();
//		}
//		else if(e.getSource()==this.btnModify){
//			int i=this.readerTypeTable.getSelectedRow();
//			if(i<0) {
//				JOptionPane.showMessageDialog(null,  "请选择","提示",JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			PeopleInfo peopleinfo=new PeopleInfo();
//			peopleinfo.setPeopleid(this.tblData[i][0].toString());
//			peopleinfo.setTestid(this.tblData[i][1].toString());
//			peopleinfo.setPeoplename(this.tblData[i][2].toString());
//			peopleinfo.setSex(this.tblData[i][3].toString());
//			peopleinfo.setPhonenum(this.tblData[i][4].toString());
//			peopleinfo.setMschoolname(this.tblData[i][5].toString());
//			peopleinfo.setProvince(this.tblData[i][6].toString());
//			PeopleInfoManager_Modify dlg=new PeopleInfoManager_Modify(this,"修改读者类别",true,peopleinfo);
//			dlg.setVisible(true);
//			if(dlg.getReadertype()!=null){//刷新表格
//				this.reloadTable();
//			}
//		}
	}
	
}