package control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;

import model.MajorInfo;
import model.SchoolInfo;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

public class MajorInfoManagerZ {
	public List<MajorInfo> searchMajor(String keyword)throws BaseException{
		List<MajorInfo> result=new ArrayList<MajorInfo>();
		Connection conn=null;
		try {
			conn=(Connection) DBUtil.getConnection();
			String sql="select * from major_info";
			if(keyword!=null && !"".equals(keyword))
				sql+=" where majorid like ? or majorname like ? or schoolname like ?";
			sql+=" order by majorname";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)){
				pst.setString(1, "%"+keyword+"%");
				pst.setString(2, "%"+keyword+"%");
				pst.setString(3, "%"+keyword+"%");
			}
				
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				MajorInfo r=new MajorInfo();
				r.setMajorid(rs.getString(1));
				r.setMajorname(rs.getString(2));
				r.setMajorclass(rs.getString(3));
				r.setSchoolid(rs.getString(4));
				r.setSchoolname(rs.getString(5));
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
public  void createMajorInfo(MajorInfo b) throws BaseException{
		
		
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
			if(!rs.next()) throw new BusinessException("请先创建学校信息");
			rs.close();
			pst.close();
		    sql="select * from school_info where schoolname=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, b.getSchoolname());
			rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("请先创建学校信息");
			rs.close();
			pst.close();
			sql="select * from major_info where majorid=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, b.getMajorid());
			rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("该专业信息已经存在");
			rs.close();
			pst.close();
     		sql="insert into major_info(majorid,majorname,majorclass,schoolid,schoolname) values(?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, b.getMajorid());
			pst.setString(2, b.getMajorname());
			pst.setString(3, b.getMajorclass());
			pst.setString(4, b.getSchoolid());
			pst.setString(5, b.getSchoolname());
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
public void modifyMajorInfo(MajorInfo rt,MajorInfo tem)throws BaseException{
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
			String sql="select * from school_info where schoolid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, tem.getSchoolid());
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("请先建立学校信息");
			rs.close();
            pst.close();
            sql="select * from major_info where majorid=?";
			 pst=conn.prepareStatement(sql);
			pst.setString(1, tem.getMajorid());
			 rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("该专业已经存在");
			rs.close();
            pst.close();
			sql="update major_info set majorid=?,majorname=?,majorclass=?,schoolid=?,schoolname=? where majorid=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, rt.getMajorid());
			pst.setString(2, rt.getMajorname());
			pst.setString(3, rt.getMajorclass());
			pst.setString(4, rt.getSchoolid());
			pst.setString(5, rt.getSchoolname());
			pst.setString(6, tem.getMajorid());
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
public void removemajor(String majorid)throws BaseException{
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
			String sql="delete from enroll_plan where enrollid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, majorid);
			pst.execute();
            pst.close();
            sql="delete from recommend_info where majorid=?";
            pst=conn.prepareStatement(sql);
            pst.setString(1, majorid);
            pst.execute();
            pst.close();
		    sql="delete from major_info where majorid=? ";		
		    pst=conn.prepareStatement(sql);
			pst.setString(1, majorid);
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
