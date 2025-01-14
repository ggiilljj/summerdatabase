package control;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.PeopleInfo;
import model.UsersInfo;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

public class SystemUserManager {
	public static UsersInfo currentUser=null;
	public UsersInfo loadUser(String userid)throws BaseException{
		Connection conn=null;
		try {
			conn=(Connection) DBUtil.getConnection();
			String sql="select peopleid,peoplename,peoplepwd,peoplegrade from users_info where peopleid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("登陆账号不存在");
			UsersInfo u=new UsersInfo();
			u.setPeopleid(rs.getString(1));
			u.setPeoplename(rs.getString(2));
			u.setPeoplepwd(rs.getString(3));
			u.setPeoplegrade(rs.getString(4));
			//if(u.getRemoveDate()!=null) throw new BusinessException("该账号已经被删除");
			rs.close();
			pst.close();
			return u;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	public void createUser(UsersInfo user)throws BaseException{
//		if(user.getUserid()==null || "".equals(user.getUserid()) || user.getUserid().length()>20){
//			throw new BusinessException("登陆账号必须是1-20个字");
//		}
//		if(user.getUsername()==null || "".equals(user.getUsername()) || user.getUsername().length()>50){
//			throw new BusinessException("账号名称必须是1-50个字");
//		}
//		if(!"管理员".equals(user.getUsertype()) && "借阅员".equals(user.getUsertype())){
//			throw new BusinessException("用户类别 必须是借阅员或管理员");
//		}
//		
//		
		Connection conn=null;
		try {
			conn=(Connection) DBUtil.getConnection();
			String sql="select * from users_info where peopleid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,user.getPeopleid());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("登陆账号已经存在");
			rs.close();
			pst.close();
			sql="insert into users_info(peopleid,peoplename,peoplegrade,peoplepwd) values(?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, user.getPeopleid());
			pst.setString(2, user.getPeoplename());
			//user.setPwd(user.getUserid());//默认密码为账号
			pst.setString(3,user.getPeoplegrade());
			pst.setString(4, user.getPeoplepwd());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public List<PeopleInfo> loadZPeople(String keyword)throws BaseException{
		Connection conn=null;
		try {
			List<PeopleInfo> result=new ArrayList<PeopleInfo>();
			conn=(Connection) DBUtil.getConnection();
			String sql="select peopleid,peoplename,sex,phonenum,mschoolname,province,testid from people_info ";
			if(keyword!=null && !"".equals(keyword))
				sql+=" where (peopleid like ? or testid like ? or peoplename like ?)";
			sql+=" order by peopleid";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)){
				pst.setString(1, "%"+keyword+"%");
				pst.setString(2, "%"+keyword+"%");
				pst.setString(3, "%"+keyword+"%");
			}
			java.sql.ResultSet rs=pst.executeQuery();
			//if(!rs.next()) throw new BusinessException("个人信息不 存在");
			while(rs.next())
			{
				PeopleInfo u=new PeopleInfo();
				u.setPeopleid(rs.getString(1));
				System.out.print(rs.getString(1));
				u.setPeoplename(rs.getString(2));
				u.setSex(rs.getString(3));
				u.setPhonenum(rs.getString(4));
				u.setMschoolname(rs.getString(5));
				u.setProvince(rs.getString(6));
				u.setTestid(rs.getString(7));
				result.add(u);
			}
			rs.close();
			pst.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	public List<PeopleInfo> loadPeople(String userid)throws BaseException{
		Connection conn=null;
		try {
			List<PeopleInfo> result=new ArrayList<PeopleInfo>();
			conn=(Connection) DBUtil.getConnection();
			String sql="select peopleid,peoplename,sex,phonenum,mschoolname,province,testid from people_info where peopleid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			//if(!rs.next()) throw new BusinessException("个人信息不 存在");
			if(rs.next())
			{
				PeopleInfo u=new PeopleInfo();
				u.setPeopleid(rs.getString(1));
				u.setPeoplename(rs.getString(2));
				u.setSex(rs.getString(3));
				u.setPhonenum(rs.getString(4));
				u.setMschoolname(rs.getString(5));
				u.setProvince(rs.getString(6));
				u.setTestid(rs.getString(7));
				rs.close();
				pst.close();
				result.add(u);
			}
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	public List<UsersInfo> loadaUser()throws BaseException{
		Connection conn=null;
		try {
			conn=(Connection) DBUtil.getConnection();
			String sql="select peopleid,peoplename,peoplepwd,peoplegrade from users_info ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs=pst.executeQuery();
			List<UsersInfo>x=new ArrayList<>();
			while(rs.next())
			{
				UsersInfo u=new UsersInfo();
				u.setPeopleid(rs.getString(1));
				u.setPeoplename(rs.getString(2));
				u.setPeoplepwd(rs.getString(3));
				u.setPeoplegrade(rs.getString(4));
				x.add(u);
			}
			
			//if(u.getRemoveDate()!=null) throw new BusinessException("该账号已经被删除");
			rs.close();
			pst.close();
			return x;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}}
	public void removeUser(String userid)throws BaseException{
//			if(rt.getReaderTypeId()<=0){
//				throw new BusinessException("读者类别ID必须是大于0的整数");
//			}
//			if(rt.getReaderTypeName()==null || "".equals(rt.getReaderTypeName()) || rt.getReaderTypeName().length()>20){
//					throw new BusinessException("读者类别名称必须是1-20个字");
//				}
//				if(rt.getLendBookLimitted()<0 || rt.getLendBookLimitted()>100){
//					throw new BusinessException("借阅图书数量必须在0-100之间");
//				}
				Connection conn=null;
				try {
					String tem=null;
					conn=(Connection) DBUtil.getConnection();
					conn.setAutoCommit(false); 
					String sql="select peoplegrade from users_info where peopleid=?";
					java.sql.PreparedStatement pst=conn.prepareStatement(sql);
					pst.setString(1, userid);
					java.sql.ResultSet rs=pst.executeQuery();
					if(rs.next()) {tem=rs.getString(1);}; 
					rs.close();
		            pst.close();
		            if(tem.equals("管理员"))
		            {
		            	sql="delete from users_info where peopleid=?";
		            	pst.setString(1, userid);
		            
		            }
		            else
		            {
		            	    sql="delete from recommend_info where peopleid=?";
				            pst=conn.prepareStatement(sql);
				            pst.setString(1, userid);
				            pst.execute();
				            pst.close();
				            sql="delete from spec_info where peopleid=?";
				            pst=conn.prepareStatement(sql);
				            pst.setString(1, userid);
				            pst.execute();
				            pst.close();
				            sql="delete from xuantest_info where peopleid=?";
				            pst=conn.prepareStatement(sql);
				            pst.setString(1, userid);
				            pst.execute();
				            pst.close();
				            sql="delete from xuetest_info where peopleid=?";
				            pst=conn.prepareStatement(sql);
				            pst.setString(1, userid);
				            pst.execute();
				            pst.close();
				            sql="delete from people_info where peopleid=?";
				            pst=conn.prepareStatement(sql);
				            pst.setString(1, userid);
				            pst.execute();
				            pst.close();
						    sql="delete from users_info where peopleid=? ";		
						    pst=conn.prepareStatement(sql);
							pst.setString(1, userid);
							pst.execute();
		            }
		           
					conn.setAutoCommit(true); 
				} catch (SQLException e) {
					e.printStackTrace();
					try {
						conn.rollback();
					}
					catch(SQLException e1)
					{
						e1.printStackTrace();
					}
					throw new DbException(e);
				}
				finally{
					if(conn!=null)
						try {
							conn.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
				
		}
		
	}
	

