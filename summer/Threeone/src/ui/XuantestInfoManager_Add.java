package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.PeopleInfoManagerZ;
import control.SystemUserManager;
import control.XuantestInfoManagerZ;
import model.PeopleInfo;
import model.XuantestInfo;
import util.BaseException;

public class XuantestInfoManager_Add extends JDialog implements ActionListener{
	private XuantestInfo xuantestInfo=null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	private JTextField edtGrade1= new JTextField(20);
	private JTextField edtGrade2 = new JTextField(20);
	private JTextField edtGrade3 = new JTextField(20);
	private JComboBox cmbUsertype1= new JComboBox(new String[] { "����", "��ѧ","����","����","��ʷ","����"});
	private JComboBox cmbUsertype2= new JComboBox(new String[] { "����", "��ѧ","����","����","��ʷ","����"});
	private JComboBox cmbUsertype3= new JComboBox(new String[] { "����", "��ѧ","����","����","��ʷ","����"});
	public XuantestInfoManager_Add(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(cmbUsertype1);
		workPane.add(edtGrade1);
		workPane.add(cmbUsertype2);
		workPane.add(edtGrade2);
		workPane.add(cmbUsertype3);
		workPane.add(edtGrade3);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 180);
		// ��Ļ������ʾ
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
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			if(this.cmbUsertype1.getSelectedIndex()==this.cmbUsertype2.getSelectedIndex()||this.cmbUsertype3.getSelectedIndex()==this.cmbUsertype2.getSelectedIndex()||this.cmbUsertype3.getSelectedIndex()==this.cmbUsertype1.getSelectedIndex()){
				JOptionPane.showMessageDialog(null,  "������ѡ��ѡ����Ŀ","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			String gradex[]=new String[3];
			 gradex[0]=this.edtGrade1.getText();
			 gradex[1]=this.edtGrade2.getText();
			 gradex[2]=this.edtGrade2.getText();
			if(gradex[0]==""||gradex[1]==""||gradex[2]==""){
				JOptionPane.showMessageDialog(null,  "��Ŀ�ɼ�����Ϊ��","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
//			if(gradex[1]==""||gradex[2]==""||gradex[3]==""){
//				JOptionPane.showMessageDialog(null,  "��Ŀ�ɼ�����Ϊ��","��ʾ",JOptionPane.ERROR_MESSAGE);
//				return;
//			}
			String tx[]=new String[3];
			 tx[0]=this.cmbUsertype1.getSelectedItem().toString();
			 tx[1]=this.cmbUsertype2.getSelectedItem().toString();
			 tx[2]=this.cmbUsertype3.getSelectedItem().toString();
			for(int i=0;i<3;i++)
			{
				XuantestInfo user=new XuantestInfo();
				PeopleInfoManagerZ y=new PeopleInfoManagerZ();
				user.setGrade(Integer.parseInt(gradex[i]));
				try {
					user.setTestid(y.LoadTestid(tx[i]));
				} catch (BaseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				user.setPeopleid(SystemUserManager.currentUser.getPeopleid());
				user.setTestname(tx[i]);
				try {
					(new XuantestInfoManagerZ()).createXuantestInfo(user);
					this.setVisible(false);
				} catch (BaseException e1) {
					this.xuantestInfo=null;
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
			}
			
		}
	}
	public XuantestInfo getXuantestInfo() {
		return xuantestInfo;
	}
	
}