package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.ImageIcon;

import control.SystemUserManager;
import model.UsersInfo;
import util.BaseException;

public class Login extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnLogin = new Button("��½");
	private Button btnCancel = new Button("�˳�");
	private Button btnURegister=new Button("�û�ע��");
	//private Button btnZRegister=new Button("����Աע��");
	private JLabel labelUser = new JLabel("�û���");
	private JLabel labelPwd = new JLabel("���룺");
	private ImageIcon img =new ImageIcon("C:\\Users\\24032\\Desktop\\th3.gif");
	private JLabel imgLabel = new JLabel(img);
	//frame.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
	private JTextField edtUserId = new JTextField(20);
	private JPasswordField edtPwd = new JPasswordField(20);
	public Login(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnLogin);
		toolBar.add(btnCancel);
		toolBar.add(btnURegister);
		//toolBar.add(btnZRegister);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelUser);
		workPane.add(edtUserId);
		workPane.add(labelPwd);
		workPane.add(edtPwd);
		workPane.add(imgLabel);
		workPane.setBackground(new Color(200,200,169));
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 380);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		btnLogin.addActionListener((ActionListener) this);
		btnCancel.addActionListener(this);
		btnURegister.addActionListener(this);
	//	btnZRegister.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnURegister) {
			Uregister dlg=new Uregister(null, "�û�ע��",true);
			this.setVisible(false);
			dlg.setVisible(true);
		}
//		if(e.getSource()==this.btnZRegister) {
//			Zregister dlg=new Zregister(null, "����Աע��",true);
//			this.setVisible(false);
//			dlg.setVisible(true);
//		}
		if (e.getSource() == this.btnLogin) {
			SystemUserManager sum=new SystemUserManager ();
			String userid=this.edtUserId.getText();
			String pwd=new String(this.edtPwd.getPassword());
			try {
				UsersInfo user=sum.loadUser(userid);
				if(pwd.equals(user.getPeoplepwd())){
					SystemUserManager.currentUser=user;
					setVisible(false);
				}
				else{
					JOptionPane.showMessageDialog(null,  "�������","������ʾ",JOptionPane.ERROR_MESSAGE);
				}
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "������ʾ",JOptionPane.ERROR_MESSAGE);
			}
			
		}
		else if (e.getSource() == this.btnCancel) {
			System.exit(0);
		}
	}
}
