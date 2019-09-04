package control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;

import model.PeopleInfo;
import model.XuetestInfo;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

public class PeopleInfoManagerZ {
	public  void createPeopleInfo(PeopleInfo b) throws BaseException{
		
		
//		if(b.getBarcode()==null || "".equals(b.getBarcode()) || b.getBarcode().length()>20){
//			throw new BusinessException("条码必须是1-20个字");
//		}
//		if(b.getBookname()==null || "".equals(b.getBookname()) || b.getBookname().length()>50){
//			throw new BusinessException("图书名称必须是1-50个字");
//		}
		Connection conn=null;
		try {
			conn=(Connection) DBUtil.getConnection();
			String sql="select * from people_info where peopleid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, b.getPeopleid());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("该学生信息已存在");
			rs.close();
			pst.close();
			sql="insert into people_info(peopleid,testid,peoplename,sex,phonenum,mschoolname,province) values(?,?,?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, b.getPeopleid());
			pst.setString(2, b.getTestid());
			pst.setString(3, b.getPeoplename());
			pst.setString(4, b.getSex());
			pst.setString(5, b.getPhonenum());
			pst.setString(6, b.getMschoolname());
			pst.setString(7, b.getProvince());
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
	public void modifyPeopleInfo(PeopleInfo rt)throws BaseException{
//		if(rt.getReaderTypeId()<=0){
//			throw new BusinessException("读者类别ID必须是大于0的整数");
//		}
//		if(rt.getReaderTypeName()==null || "".equals(rt.getReaderTypeName()) || rt.getReaderTypeName().length()>20){
//				throw new BusinessException("读者类别名称必须是1-20个字");
//			}
//			if(rt.getLendBookLimitted()<0 || rt.getLendBookLimitted()>100){
//				throw new BusinessException("借阅图书数量必须在0-100之间");
//			}
			Connection conn=null;
			try {
				conn=(Connection) DBUtil.getConnection();
				String sql="select * from people_info where peopleid=?";
				java.sql.PreparedStatement pst=conn.prepareStatement(sql);
				pst.setString(1, rt.getPeopleid());
				java.sql.ResultSet rs=pst.executeQuery();
				if(!rs.next()) throw new BusinessException("请先建立个人信息");
				rs.close();
                pst.close();
				sql="update people_info set testid=?,peoplename=?,sex=?,phonenum=?,mschoolname=?,province=? where peopleid=?";
				pst=conn.prepareStatement(sql);
				pst.setString(1, rt.getTestid());
				pst.setString(2, rt.getPeoplename());
				pst.setString(3, rt.getSex());
				pst.setString(4, rt.getPhonenum());
				pst.setString(5, rt.getMschoolname());
				pst.setString(6, rt.getProvince());
				pst.setString(7, rt.getPeopleid());
				pst.execute();
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
	public List<XuetestInfo> loadXuetestInfo(String userid)throws BaseException{
		Connection conn=null;
		try {
			List<XuetestInfo> result=new ArrayList<XuetestInfo>();
			conn=(Connection) DBUtil.getConnection();
			String sql="select peopleid,testid,grade,testname from xuetest_info where peopleid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			//if(!rs.next()) throw new BusinessException("未录入学考成绩");
			while(rs.next())
			{
				XuetestInfo u=new XuetestInfo();
				u.setPeopleid(rs.getString(1));
				u.setTestid(rs.getString(2));
				u.setGrade(rs.getInt(3));
				u.setTestname(rs.getString(4));
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
	public List<XuetestInfo> loadallXuetestInfo(String keyword)throws BaseException{
		Connection conn=null;
		try {
			List<XuetestInfo> result=new ArrayList<XuetestInfo>();
			conn=(Connection) DBUtil.getConnection();
			String sql="select peopleid,testid,grade,testname from xuetest_info ";
			if(keyword!=null && !"".equals(keyword))
				sql+=" where (peopleid like ? or testid like ? or testname like ?)";
			sql+=" order by peopleid";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)){
				pst.setString(1, "%"+keyword+"%");
				pst.setString(2, "%"+keyword+"%");
				pst.setString(3, "%"+keyword+"%");
			}
			java.sql.ResultSet rs=pst.executeQuery();
			//if(!rs.next()) throw new BusinessException("未录入学考成绩");
			while(rs.next())
			{
				XuetestInfo u=new XuetestInfo();
				u.setPeopleid(rs.getString(1));
				u.setTestid(rs.getString(2));
				u.setGrade(rs.getInt(3));
				u.setTestname(rs.getString(4));
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
	public String LoadTestid(String testname)throws BaseException{
		String xx = null;
		Connection conn=null;
		try {
			conn=(Connection) DBUtil.getConnection();
			String sql="select subid from test_info where subname=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,testname);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("个人信息不 存在");
			xx=rs.getString(1);
			return xx;
			
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
 public  void createXuetestInfo(XuetestInfo b) throws BaseException{
		
		
//		if(b.getBarcode()==null || "".equals(b.getBarcode()) || b.getBarcode().length()>20){
//			throw new BusinessException("条码必须是1-20个字");
//		}
//		if(b.getBookname()==null || "".equals(b.getBookname()) || b.getBookname().length()>50){
//			throw new BusinessException("图书名称必须是1-50个字");
//		}
		Connection conn=null;
		try {
			conn=(Connection) DBUtil.getConnection();
			String sql="select count(testid) from xuetest_info where peopleid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, b.getPeopleid());
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				//if(rs.getInt(1)==9) throw new BusinessException("");
			}
			rs.close();
			pst.close();
			sql="insert into xuetest_info(peopleid,testid,grade,testname) values(?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, b.getPeopleid());
			pst.setString(2, b.getTestid());
			pst.setInt(3, b.getGrade());
			pst.setString(4, b.getTestname());
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
 public void modifyXuetestInfo(XuetestInfo rt)throws BaseException{
//		if(rt.getReaderTypeId()<=0){
//			throw new BusinessException("读者类别ID必须是大于0的整数");
//		}
//		if(rt.getReaderTypeName()==null || "".equals(rt.getReaderTypeName()) || rt.getReaderTypeName().length()>20){
//				throw new BusinessException("读者类别名称必须是1-20个字");
//			}
//			if(rt.getLendBookLimitted()<0 || rt.getLendBookLimitted()>100){
//				throw new BusinessException("借阅图书数量必须在0-100之间");
//			}
			Connection conn=null;
			try {
				conn=(Connection) DBUtil.getConnection();
				String sql="select * from people_info where peopleid=?";
				java.sql.PreparedStatement pst=conn.prepareStatement(sql);
				pst.setString(1, rt.getPeopleid());
				java.sql.ResultSet rs=pst.executeQuery();
				if(!rs.next()) throw new BusinessException("请先建立个人信息");
				rs.close();
                pst.close();
				sql="update xuetest_info set grade=?,testname=? where peopleid=? and testid=?";
				pst=conn.prepareStatement(sql);
				pst.setInt(1, rt.getGrade());
				pst.setString(2, rt.getTestname());
				pst.setString(3, rt.getPeopleid());
				pst.setString(4, rt.getTestid());
				pst.execute();
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
 public void removePeoples(String userid)throws BaseException{
//		if(rt.getReaderTypeId()<=0){
//			throw new BusinessException("读者类别ID必须是大于0的整数");
//		}
//		if(rt.getReaderTypeName()==null || "".equals(rt.getReaderTypeName()) || rt.getReaderTypeName().length()>20){
//				throw new BusinessException("读者类别名称必须是1-20个字");
//			}
//			if(rt.getLendBookLimitted()<0 || rt.getLendBookLimitted()>100){
//				throw new BusinessException("借阅图书数量必须在0-100之间");
//			}
			Connection conn=null;
			try {
				String tem=null;
				conn=(Connection) DBUtil.getConnection();
				conn.setAutoCommit(false); 
				String  sql="delete from recommend_info where peopleid=?";
	            java.sql.PreparedStatement  pst=conn.prepareStatement(sql);
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
 public void removeXue(String userid)throws BaseException{
//		if(rt.getReaderTypeId()<=0){
//			throw new BusinessException("读者类别ID必须是大于0的整数");
//		}
//		if(rt.getReaderTypeName()==null || "".equals(rt.getReaderTypeName()) || rt.getReaderTypeName().length()>20){
//				throw new BusinessException("读者类别名称必须是1-20个字");
//			}
//			if(rt.getLendBookLimitted()<0 || rt.getLendBookLimitted()>100){
//				throw new BusinessException("借阅图书数量必须在0-100之间");
//			}
			Connection conn=null;
			try {
				String tem=null;
				conn=(Connection) DBUtil.getConnection();
				conn.setAutoCommit(false); 
				String  sql="delete from recommend_info where peopleid=?";
	            java.sql.PreparedStatement  pst=conn.prepareStatement(sql);
	            pst.setString(1, userid);
	            pst.execute();
	            pst.close();
	            sql="delete from xuetest_info where peopleid=?";
	            pst=conn.prepareStatement(sql);
	            pst.setString(1, userid);
	            pst.execute();
	            pst.close();
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
 public void removeXuan(String userid)throws BaseException{
//		if(rt.getReaderTypeId()<=0){
//			throw new BusinessException("读者类别ID必须是大于0的整数");
//		}
//		if(rt.getReaderTypeName()==null || "".equals(rt.getReaderTypeName()) || rt.getReaderTypeName().length()>20){
//				throw new BusinessException("读者类别名称必须是1-20个字");
//			}
//			if(rt.getLendBookLimitted()<0 || rt.getLendBookLimitted()>100){
//				throw new BusinessException("借阅图书数量必须在0-100之间");
//			}
			Connection conn=null;
			try {
				String tem=null;
				conn=(Connection) DBUtil.getConnection();
				conn.setAutoCommit(false); 
				String  sql="delete from recommend_info where peopleid=?";
	            java.sql.PreparedStatement  pst=conn.prepareStatement(sql);
	            pst.setString(1, userid);
	            pst.execute();
	            pst.close();
	            sql="delete from xuantest_info where peopleid=?";
	            pst=conn.prepareStatement(sql);
	            pst.setString(1, userid);
	            pst.execute();
	            pst.close();
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
