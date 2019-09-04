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
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import control.SystemUserManager;
import model.UsersInfo;
import util.BaseException;

public class UserMz_Add extends JDialog implements ActionListener{
private UsersInfo user=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnConfirm = new Button("ȷ��");
	private Button btnCancel = new Button("�˳�");
	private JLabel labelUser = new JLabel("�ʺţ�");
	private JLabel labelUserName = new JLabel("�û�����");
	private JLabel labelPwd1 = new JLabel("���룺");
	private JLabel labelPwd2= new JLabel("ȷ�����룺");
//	private ImageIcon img =new ImageIcon("C:\\Users\\24032\\Desktop\\th.jpg");
//	private JLabel imgLabel = new JLabel(img);
	//frame.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
	private JTextField edtUserId = new JTextField(20);
	private JTextField edtUserName = new JTextField(20);
	private JPasswordField edtPwd1 = new JPasswordField(20);
	private JPasswordField edtPwd2 = new JPasswordField(20);
	public UserMz_Add(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnConfirm);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelUser);
		workPane.add(edtUserId);
		workPane.add(labelUserName);
		workPane.add(edtUserName);
		workPane.add(labelPwd1);
		workPane.add(edtPwd1);
		workPane.add(labelPwd2);
		workPane.add(edtPwd2);
		//workPane.add(imgLabel);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 200);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		btnConfirm.addActionListener((ActionListener) this);
		btnCancel.addActionListener((ActionListener) this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnCancel) {
			UserM dlg=new UserM(null, "�û�����",true);
			this.setVisible(false);
			dlg.setVisible(true);
		}
		if(e.getSource()==this.btnConfirm) {
				if(this.edtPwd1.getPassword().toString().equals(this.edtPwd2.getPassword().toString()))
				{
					JOptionPane.showMessageDialog(null,  "��˶������Ƿ�һ��","������ʾ",JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					String PeopleName=this.edtUserName.getText();
					String Peopleid=this.edtUserId.getText();
				    String Peoplepwd=new String(this.edtPwd1.getPassword());
					String Peoplegrade="����Ա";
					UsersInfo user=new UsersInfo();
					user.setPeopleid(Peopleid);
					user.setPeoplepwd(Peoplepwd);
					user.setPeoplegrade(Peoplegrade);
					user.setPeoplename(PeopleName);
					try {
						(new SystemUserManager()).createUser(user);
						this.setVisible(false);
						JOptionPane.showMessageDialog(null, "���Ѿ��ɹ�ע��","��ϲ",JOptionPane.INFORMATION_MESSAGE);
					} catch (BaseException e1) {
						this.user=null;
						JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
					}
					UserM dlg=new UserM(null, "�û�����",true);
					this.setVisible(false);
					dlg.setVisible(true);
				}
		
		}
	}
}