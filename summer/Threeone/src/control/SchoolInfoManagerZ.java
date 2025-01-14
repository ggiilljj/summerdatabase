package control;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;

import model.PeopleInfo;
import model.SchoolInfo;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;
public class SchoolInfoManagerZ {
	public List<SchoolInfo> searchSchool(String keyword)throws BaseException{
		List<SchoolInfo> result=new ArrayList<SchoolInfo>();
		Connection conn=null;
		try {
			conn=(Connection) DBUtil.getConnection();
		    String sql="SELECT * from school_info";
		    //String sql="Call schoolsinfo"
			if(keyword!=null && !"".equals(keyword))
				sql+=" where (schoolid like ? or schoolname like ?)";
			sql+=" order by schoolid";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)){
				pst.setString(1, "%"+keyword+"%");
				pst.setString(2, "%"+keyword+"%");
				
			}
				
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				SchoolInfo r=new SchoolInfo();
				r.setSchoolid(rs.getString(1));
				r.setSchoolname(rs.getString(2));
				r.setProvince(rs.getString(3));
				r.setCity(rs.getString(4));
				r.setSchooldesc(rs.getString(5));
				result.add(r);
			}
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
		return result;
		
	}
public  void createSchoolInfo(SchoolInfo b) throws BaseException{
		
		
//		if(b.getBarcode()==null || "".equals(b.getBarcode()) || b.getBarcode().length()>20){
//			throw new BusinessException("条码必须是1-20个字");
//		}
//		if(b.getBookname()==null || "".equals(b.getBookname()) || b.getBookname().length()>50){
//			throw new BusinessException("图书名称必须是1-50个字");
//		}
		Connection conn=null;
		try {
			conn=(Connection) DBUtil.getConnection();
			String sql="select * from school_info where schoolid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, b.getSchoolid());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("该学校信息已存在");
			rs.close();
			pst.close();
		    sql="select * from school_info where schoolname=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, b.getSchoolname());
			rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("该学校信息已存在");
			rs.close();
			pst.close();
			sql="insert into school_info(schoolid,schoolname,province,city,schooldesc) values(?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, b.getSchoolid());
			pst.setString(2, b.getSchoolname());
			pst.setString(3, b.getProvince());
			pst.setString(4, b.getCity());
			pst.setString(5, b.getSchooldesc());
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
public void modifySchoolInfo(SchoolInfo rt,SchoolInfo tem)throws BaseException, SQLException{
//	if(rt.getReaderTypeId()<=0){
//		throw new BusinessException("读者类别ID必须是大于0的整数");
//	}
//	if(rt.getReaderTypeName()==null || "".equals(rt.getReaderTypeName()) || rt.getReaderTypeName().length()>20){
//			throw new BusinessException("读者类别名称必须是1-20个字");
//		}
//		if(rt.getLendBookLimitted()<0 || rt.getLendBookLimitted()>100){
//			throw new BusinessException("借阅图书数量必须在0-100之间");
//		}
		Connection conn=null;
		try {
			conn=(Connection) DBUtil.getConnection();
			conn.setAutoCommit(false); 
			
			String sql="select * from school_info where schoolid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, tem.getSchoolid());
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("请先建立学校信息");
			rs.close();
            pst.close();
			sql="update school_info set schoolid=?,schoolname=?,province=?,city=?,schooldesc=? where schoolid=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, rt.getSchoolid());
			pst.setString(2, rt.getSchoolname());
			pst.setString(3, rt.getProvince());
			pst.setString(4, rt.getCity());
			pst.setString(5, rt.getSchooldesc());
			pst.setString(6, tem.getSchoolid());
			pst.execute();
			sql="update major_info set schoolname=? where schoolid=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, rt.getSchoolname());
			pst.setString(2, rt.getSchoolid());
			pst.execute();
			conn.setAutoCommit(true); 
		
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
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
public void removeSchools(String schoolid)throws BaseException{
//	if(rt.getReaderTypeId()<=0){
//		throw new BusinessException("读者类别ID必须是大于0的整数");
//	}
//	if(rt.getReaderTypeName()==null || "".equals(rt.getReaderTypeName()) || rt.getReaderTypeName().length()>20){
//			throw new BusinessException("读者类别名称必须是1-20个字");
//		}
//		if(rt.getLendBookLimitted()<0 || rt.getLendBookLimitted()>100){
//			throw new BusinessException("借阅图书数量必须在0-100之间");
//		}
		Connection conn=null;
		try {
			conn=(Connection) DBUtil.getConnection();
			conn.setAutoCommit(false); 
			System.out.print(schoolid);
			String sql="select * from major_info where schoolid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, schoolid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) {System.out.print(rs.getString(1)); throw new BusinessException("存在相关专业，不能删除");}
			rs.close();
            pst.close();
            sql="delete from signup_plan where schoolid=?";
            pst=conn.prepareStatement(sql);
            pst.setString(1, schoolid);
            pst.execute();
            pst.close();
		    sql="delete from school_info where schoolid=? ";		
		    pst=conn.prepareStatement(sql);
			pst.setString(1, schoolid);
			pst.execute();
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
