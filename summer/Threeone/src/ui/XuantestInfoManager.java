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
import java.sql.SQLException;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import control.PeopleInfoManagerZ;
import control.SystemUserManager;
import control.XuantestInfoManagerZ;
import model.PeopleInfo;
import model.XuantestInfo;
import model.XuetestInfo;
import util.BaseException;
import util.BusinessException;

public class XuantestInfoManager extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("填写选考信息");
	private Button btnModify = new Button("修改选考信息");
	private Object tblTitle[]={"科目","成绩","科目号","id"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable readerTypeTable=new JTable(tablmod);
	private void reloadTable(){
		try {
			XuantestInfoManagerZ s=new XuantestInfoManagerZ();
			List<XuantestInfo> types= s.loadXuantestInfo(SystemUserManager.currentUser.getPeopleid());
			tblData =new Object[types.size()][4];
			for(int i=0;i<types.size();i++)
			{
				tblData[i][0]=types.get(i).getTestname()+"";
				tblData[i][1]=types.get(i).getGrade();
				tblData[i][2]=types.get(i).getTestid()+"";
				tblData[i][3]=types.get(i).getPeopleid()+"";
				System.out.print(tblData[i][0]);
			}
			
			tablmod.setDataVector(tblData,tblTitle);
			this.readerTypeTable.validate();
			this.readerTypeTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public XuantestInfoManager(Frame f, String s, boolean b) {
		//提取读者类别信息
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnModify);
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

		this.btnAdd.addActionListener(this);
		this.btnModify.addActionListener(this);
	
		this.addWindowListener((WindowListener) new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnAdd){
			XuantestInfoManagerZ s=new XuantestInfoManagerZ();
			try {
				List<XuantestInfo> t= s.loadXuantestInfo(SystemUserManager.currentUser.getPeopleid());
				if(t.size()>0)
				{
					throw new BusinessException("选考成绩已经录入");
				}
				else
				{
					XuantestInfoManager_Add dlg=new XuantestInfoManager_Add(this,"选考信息增加",true);
					dlg.setVisible(true);
					this.reloadTable();
				}
			} catch (BaseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
			
		}
		else if(e.getSource()==this.btnModify){
			int i=this.readerTypeTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			XuantestInfo xuantestinfo=new XuantestInfo();
			xuantestinfo.setTestname(this.tblData[i][0].toString());
			xuantestinfo.setGrade(Integer.parseInt(this.tblData[i][1].toString()));
			xuantestinfo.setTestid(this.tblData[i][2].toString());
			xuantestinfo.setPeopleid(this.tblData[i][3].toString());
			XuantestInfoManager_Modify dlg=new XuantestInfoManager_Modify(this,"修改选考信息",true,xuantestinfo);
			dlg.setVisible(true);
			if(dlg.getXuantestInfo()!=null){//刷新表格
				this.reloadTable();
			}
		}
	}
}
