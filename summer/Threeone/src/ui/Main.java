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
    private JMenu menu_Enroll=new JMenu("招生管理");
    private JMenu menu_People=new JMenu("个人信息管理");
    private JMenu menu_Search=new JMenu("查询推荐院校");
    private JMenu menu_User=new JMenu("用户管理");
    
    private JMenuItem  menuItem_SchoolInfo=new JMenuItem("学校信息管理");
    private JMenuItem  menuItem_MajorInfo=new JMenuItem("专业管理");
    private JMenuItem  menuItem_SignupPlan=new JMenuItem("报名信息表管理");
    private JMenuItem  menuItem_EnrollPlan=new JMenuItem("招生计划表管理");
    private JMenuItem  menuItem_TestInfo=new JMenuItem("科目管理");
    
    private JMenuItem  menuItem_PeopleInfo=new JMenuItem("个人信息管理");
    private JMenuItem  menuItem_XuetestInfo=new JMenuItem("学考信息管理");
    private JMenuItem  menuItem_XuantestInfo=new JMenuItem("选考信息管理");
    private JMenuItem  menuItem_SpecInfo=new JMenuItem("特长信息管理");
    
    private JMenuItem  menuItem_SearchRecommendInfo=new JMenuItem("推荐情况查询");

    private JMenuItem  menuItem_UserInfo=new JMenuItem("用户信息管理");

	private ImageIcon img =new ImageIcon("C:\\Users\\24032\\Desktop\\th3.gif");
	private ImageIcon img2 =new ImageIcon("C:\\Users\\24032\\Desktop\\th4.gif");

	private JLabel imgLabel = new JLabel(img);
	private JLabel imgLabel2 = new JLabel(img2);
	private Login dlgLogin=null;
	private JPanel statusBar = new JPanel();
    public Main() {
    	this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("三位一体操作系统");
    	dlgLogin=new Login(this,"登陆",true);
		dlgLogin.setVisible(true);
	    //菜单
	    if("管理员".equals(SystemUserManager.currentUser.getPeoplegrade())){
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
	    //状态栏
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JLabel label=new JLabel("您好!"+SystemUserManager.currentUser.getPeoplename()+SystemUserManager.currentUser.getPeoplegrade());
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
		if((e.getSource()==this.menuItem_PeopleInfo) && ("用户".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			PeopleInfoManager dlg=new PeopleInfoManager(this,"用户信息管理",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_XuetestInfo)&& ("用户".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			XuetestInfoManager dlg=new XuetestInfoManager(this,"学考成绩管理",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_XuantestInfo)&& ("用户".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			XuantestInfoManager dlg=new XuantestInfoManager(this,"选考成绩管理",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_XuantestInfo)&& ("用户".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			XuantestInfoManager dlg=new XuantestInfoManager(this,"选考成绩管理",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_SchoolInfo)&& ("管理员".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			ZSchoolInfoManager dlg=new ZSchoolInfoManager(this,"学校信息管理",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_MajorInfo)&& ("管理员".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			ZMajorInfoManager dlg=new ZMajorInfoManager(this,"专业信息管理",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_EnrollPlan)&& ("管理员".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			EnrollPlanManager dlg=new EnrollPlanManager(this,"招生计划管理",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_SignupPlan)&& ("管理员".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			SignupPlanManager dlg=new SignupPlanManager(this,"报名信息管理",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_PeopleInfo)&& ("管理员".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			ZPeopleInfoManager dlg=new ZPeopleInfoManager(this,"个人信息管理",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_SearchRecommendInfo)&& ("用户".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			RecommendInfoManager dlg=new RecommendInfoManager(this,"推荐专业查询",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_SpecInfo)&& ("用户".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			SpecInfoManager dlg=new SpecInfoManager(this,"特长管理",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_UserInfo)&& ("管理员".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			UserM dlg=new UserM(this,"用户管理",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_XuetestInfo)&& ("管理员".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			ZXue dlg=new ZXue(this,"学考",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_XuantestInfo)&& ("管理员".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			Zxuan dlg=new Zxuan(this,"选考",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_SpecInfo)&& ("管理员".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			Zte dlg=new Zte(this,"特长信息",true);
			dlg.setVisible(true);
		}
		if((e.getSource()==this.menuItem_SearchRecommendInfo)&& ("管理员".equals(SystemUserManager.currentUser.getPeoplegrade()))){
			Ztui dlg=new Ztui(this,"推荐情况查询",true);
			dlg.setVisible(true);
		}
	}
}
