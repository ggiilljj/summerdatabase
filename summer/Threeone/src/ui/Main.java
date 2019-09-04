package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;



import control.SystemUserManager;
public class Main extends JFrame implements ActionListener {
	private JMenuBar menubar=new JMenuBar(); 
	private JPanel workPane = new JPanel();
    private JMenu menu_Enroll=new JMenu("��������");
    private JMenu menu_People=new JMenu("������Ϣ����");
    private JMenu menu_Search=new JMenu("��ѯ�Ƽ�ԺУ");
    private JMenu menu_User=new JMenu("�û�����");
    
    private JMenuItem  menuItem_SchoolInfo=new JMenuItem("ѧУ��Ϣ����");
    private JMenuItem  menuItem_MajorInfo=new JMenuItem("רҵ����");
    private JMenuItem  menuItem_SignupPlan=new JMenuItem("������Ϣ������");
    private JMenuItem  menuItem_EnrollPlan=new JMenuItem("�����ƻ�������");
    private JMenuItem  menuItem_TestInfo=new JMenuItem("��Ŀ����");
    
    private JMenuItem  menuItem_PeopleInfo=new JMenuItem("������Ϣ����");
    private JMenuItem  menuItem_XuetestInfo=new JMenuItem("ѧ����Ϣ����");
    private JMenuItem  menuItem_XuantestInfo=new JMenuItem("ѡ����Ϣ����");
    private JMenuItem  menuItem_SpecInfo=new JMenuItem("�س���Ϣ����");
    
    private JMenuItem  menuItem_SearchRecommendInfo=new JMenuItem("�Ƽ������ѯ");

    private JMenuItem  menuItem_UserInfo=new JMenuItem("�û���Ϣ����");

	private ImageIcon img =new ImageIcon("C:\\Users\\24032\\Desktop\\th3.gif");
	private ImageIcon img2 =new ImageIcon("C:\\Users\\24032\\Desktop\\th4.gif");

	private JLabel imgLabel = new JLabel(img);
	private JLabel imgLabel2 = new JLabel(img2);
	private Login dlgLogin=null;
	private JPanel statusBar = new JPanel();
    public Main() {
    	this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("��λһ�����ϵͳ");
    	dlgLogin=new Login(this,"��½",true);
		dlgLogin.setVisible(true);
	    //�˵�
	    if("����Ա".equals(SystemUserManager.currentUser.getPeoplegrade())){
	    	menu_Enroll.add(menuItem_SchoolInfo);
	    	menuItem_SchoolInfo.addActionListener(this);
	    	menu_Enroll.add(menuItem_MajorInfo);
	    	menuItem_MajorInfo.addActionListener(this);
	    	menu_Enroll.add(menuItem_EnrollPlan);
	    	menuItem_EnrollPlan.addActionListener(this);
	    	menu_Enroll.add(menuItem_SignupPlan);
	    	menuItem_SignupPlan.addActionListener(this);
//	    	menu_Enroll.add(menuItem_TestInfo);
//	    	menuItem_TestInfo.addActionListener(this);
	    	menubar.add(this.menu_Enroll);
	    	menu_User.add(menuItem_UserInfo);
	    	menuItem_UserInfo.addActionListener(this);
	    	menubar.add(menu_User);
	    }
	    menu_People.add(this.menuItem_PeopleInfo);
	    menuItem_PeopleInfo.addActionListener(this);
	    menu_People.add(this.menuItem_XuetestInfo);
	    menuItem_XuetestInfo.addActionListener(this);
	    menu_People.add(this.menuItem_XuantestInfo);
	    menuItem_XuantestInfo.addActionListener(this);
	    menu_People.add(this.menuItem_SpecInfo);
	    menuItem_SpecInfo.addActionListener(this);
	    menubar.add(this.menu_People);
	    menu_Search.add(this.menuItem_SearchRecommendInfo);
	    menuItem_SearchRecommendInfo.addActionListener(this);
	    menubar.add(this.menu_Search);	    this.setJMenuBar(menubar);
	    //״̬��
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JLabel label=new JLabel("����!"+SystemUserManager.currentUser.getPeoplename()+SystemUserManager.currentUser.getPeoplegrade());
	    statusBar.add(label);
	    this.getContentPane().add(statusBar,BorderLayout.SOUTH);
		workPane.add(imgLabel);
		workPane.add(imgLabel2);
		workPane.setBackground(new Color(200,200,169));

		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(500,500);
	    this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		System.exit(0);
             }
        });
	    this.setVisible(true);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if((e.getSource()==this.menuItem_PeopleInfo) && ("�û�".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			PeopleInfoManager dlg=new PeopleInfoManager(this,"�û���Ϣ����",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_XuetestInfo)&& ("�û�".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			XuetestInfoManager dlg=new XuetestInfoManager(this,"ѧ���ɼ�����",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_XuantestInfo)&& ("�û�".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			XuantestInfoManager dlg=new XuantestInfoManager(this,"ѡ���ɼ�����",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_XuantestInfo)&& ("�û�".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			XuantestInfoManager dlg=new XuantestInfoManager(this,"ѡ���ɼ�����",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_SchoolInfo)&& ("����Ա".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			ZSchoolInfoManager dlg=new ZSchoolInfoManager(this,"ѧУ��Ϣ����",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_MajorInfo)&& ("����Ա".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			ZMajorInfoManager dlg=new ZMajorInfoManager(this,"רҵ��Ϣ����",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_EnrollPlan)&& ("����Ա".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			EnrollPlanManager dlg=new EnrollPlanManager(this,"�����ƻ�����",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_SignupPlan)&& ("����Ա".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			SignupPlanManager dlg=new SignupPlanManager(this,"������Ϣ����",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_PeopleInfo)&& ("����Ա".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			ZPeopleInfoManager dlg=new ZPeopleInfoManager(this,"������Ϣ����",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_SearchRecommendInfo)&& ("�û�".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			RecommendInfoManager dlg=new RecommendInfoManager(this,"�Ƽ�רҵ��ѯ",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_SpecInfo)&& ("�û�".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			SpecInfoManager dlg=new SpecInfoManager(this,"�س�����",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_UserInfo)&& ("����Ա".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			UserM dlg=new UserM(this,"�û�����",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_XuetestInfo)&& ("����Ա".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			ZXue dlg=new ZXue(this,"ѧ��",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_XuantestInfo)&& ("����Ա".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			Zxuan dlg=new Zxuan(this,"ѡ��",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_SpecInfo)&& ("����Ա".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			Zte dlg=new Zte(this,"�س���Ϣ",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_SearchRecommendInfo)&& ("����Ա".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			Ztui dlg=new Ztui(this,"�Ƽ������ѯ",true);
			dlg.setVisible(true);
		}
	}
}