package control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;

import model.EnrollPlan;
import model.SignupPlan;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

public class SignupPlanManagerZ {
	public List<SignupPlan> searchSignups(String keyword)throws BaseException{
		List<SignupPlan> result=new ArrayList<SignupPlan>();
		Connection conn=null;
		try {
			conn=(Connection) DBUtil.getConnection();
			String sql="select signup_plan.*,school_info.schoolname from signup_plan,school_info where signup_plan.schoolid=school_info.schoolid";
			if(keyword!=null && !"".equals(keyword))
				sql+=" and (school_info.schoolname like ? or signup_plan.schoolid like ? )";
			sql+=" order by signup_plan.schoolid";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)){
				pst.setString(1, "%"+keyword+"%");
				pst.setString(2, "%"+keyword+"%");
			}
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				SignupPlan r=new SignupPlan();
				r.setSchoolid(rs.getString(1));
				r.setSigncond1(rs.getInt(2));
				r.setBegintime(rs.getDate(3));
				r.setEndtime(rs.getDate(4));
				r.setSignupway(rs.getString(5));
				r.setSigncond2(rs.getInt(6));
				r.setSigncondclass(rs.getInt(7));
				r.setSchoolname(rs.getString(8));
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
public  void createSignupPlan(SignupPlan b) throws BaseException{
		
		
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
			sql="select * from signup_plan where schoolid=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, b.getSchoolid());
			rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("该学校的报名表已经存在");
			rs.close();
			pst.close();
     		sql="insert into signup_plan(schoolid,signcond1,begintime,endtime,signupway,signcond2,signcondclass) values(?,?,?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, b.getSchoolid());
			pst.setInt(2, b.getSigncond1());
			pst.setDate(3, b.getBegintime());
			pst.setDate(4, b.getEndtime());
			pst.setString(5, b.getSignupway());
			pst.setInt(6, b.getSigncond2());
			pst.setInt(7, b.getSigncondclass());
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
public void modifySignupPlan(SignupPlan rt,SignupPlan tem)throws BaseException{
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
			pst.setString(1, rt.getSchoolid());
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("请先创建学校信息");
			rs.close();
			pst.close();
			sql="update signup_plan set signcond1=?,begintime=?,endtime=?,signupway=?,signcond2=?,signcondclass=? where schoolid=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, rt.getSigncond1());
			pst.setDate(2, rt.getBegintime());
			pst.setDate(3, rt.getEndtime());
			pst.setString(4, rt.getSignupway());
			pst.setInt(5, rt.getSigncond2());
			pst.setInt(6, rt.getSigncondclass());
			pst.setString(7, tem.getSchoolid());
			
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
public void removeSign(SignupPlan s)throws BaseException{
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
//			System.out.print(schoolid);
			String sql="delete from signup_plan where schoolid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, s.getSchoolid());
			pst.execute();
            pst.close();
            sql="delete from recommend_info where schoolid=?";
            pst=conn.prepareStatement(sql);
            pst.setString(1, s.getSchoolid());
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
