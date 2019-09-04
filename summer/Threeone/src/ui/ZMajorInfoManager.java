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

import control.MajorInfoManagerZ;
import control.SchoolInfoManagerZ;
import model.MajorInfo;
import model.SchoolInfo;
import util.BaseException;

public class ZMajorInfoManager extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("����רҵ");
	private Button btnModify = new Button("�޸�רҵ");
	private Button btnDelete = new Button("ɾ��רҵ");
//	private Map<String,SchoolInfo> SchoolInfo_name=new HashMap<String,SchoolInfo>();
//	private Map<String,SchoolInfo> SchoolInfo_id=new HashMap<String,SchoolInfo>();
	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("��ѯ");
	private Object tblTitle[]={"רҵId","רҵ��","רҵ����","ѧУId","ѧУ��"};
	private Object tblData[][];
	List<MajorInfo> majors=null;
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable readerTable=new JTable(tablmod);
	private void reloadTable(){
		try {
			majors=(new MajorInfoManagerZ()).searchMajor(this.edtKeyword.getText().toString());
			tblData =new Object[majors.size()][5];
			for(int i=0;i<majors.size();i++){
				tblData[i][0]=majors.get(i).getMajorid();
				tblData[i][1]=majors.get(i).getMajorname();
				tblData[i][2]=majors.get(i).getMajorclass();
				tblData[i][3]=majors.get(i).getSchoolid();
				tblData[i][4]=majors.get(i).getSchoolname();

			}
			tablmod.setDataVector(tblData,tblTitle);
			this.readerTable.validate();
			this.readerTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ZMajorInfoManager(Frame f, String s, boolean b) {
		super(f, s, b);
		//��ȡ���������Ϣ
		List<SchoolInfo> types=null;
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnModify);
		toolBar.add(this.btnDelete);
		toolBar.add(edtKeyword);
		toolBar.add(btnSearch);
		
		
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//��ȡ��������
		this.reloadTable();
		this.getContentPane().add(new JScrollPane(this.readerTable), BorderLayout.CENTER);
		
		// ��Ļ������ʾ
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
			ZMajorInfoManager_Add dlg=new ZMajorInfoManager_Add(this,"����רҵ",true);
			dlg.setVisible(true);
			this.reloadTable();
//			if(dlg.getReader()!=null){//ˢ�±���
//				this.reloadTable();
//			}
		}
		else if(e.getSource()==this.btnModify){
			int i=this.readerTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ��רҵ","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			MajorInfo major=this.majors.get(i);
			ZMajorInfoManager_Modify dlg=new ZMajorInfoManager_Modify(this,"�޸�רҵ",true,major);
			dlg.setVisible(true);
			if(dlg.getMajorInfo()!=null){//ˢ�±���
				this.reloadTable();
			}
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.readerTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ��רҵ","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			MajorInfo majorinfo=this.majors.get(i);
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ����רҵ��","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					(new MajorInfoManagerZ()).removemajor(majorinfo.getMajorid());
					this.reloadTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
		else if(e.getSource()==this.btnSearch){
			this.reloadTable();
		}
	}
}