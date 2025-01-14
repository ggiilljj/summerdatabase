package control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.EnrollPlan;
import model.RecommendInfo;
import model.SignupPlan;
import model.SpecInfo;
import model.XuantestInfo;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import util.DbException;

public class RecommendInfoZ {
	public List<RecommendInfo> loadRecommend(String userid)throws BaseException{
		Connection conn=null;
		try {
			int Anum=0;
			int Bnum=0;
			int Cnum=0;
			int Azhunum=0;
			int Afen=0;
			conn=(Connection) DBUtil.getConnection();
			String sql="select testname,grade from xuetest_info where peopleid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			//if(!rs.next()) throw new BusinessException("个人信息不 存在");
			while(rs.next())
			{ 
				String tname=rs.getString(1);
				int tgrade=rs.getInt(2);
				if(tname.equals("数学")||tname.equals("语文")||tname.equals("英语"))
				{
					if(tgrade>=90) {Anum++;Azhunum++;}
					else if(tgrade<90&&tgrade>=80) {
						Bnum++;
					}
					else if(tgrade<80&&tgrade>=70)
					{
						Cnum++;
					}
				}
				else
				{
					Anum++;
				}
				
			}
			Afen=Azhunum*15+(Anum-Azhunum)*10+Bnum*8+Cnum*4;
			Anum=Anum;
			String zongtest=null;
			rs.close();
			pst.close();
			sql="select testname from  xuantest_info where peopleid=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
		    rs=pst.executeQuery();
		    List<String>ke=new ArrayList<>();
		    System.out.print(rs.getFetchSize());
		    while(rs.next())
		    {
		    	ke.add(rs.getString(1));
		    	System.out.print(rs.getString(1));
		    }
		    rs.close();
		    pst.close();
		    sql="select * from grade_info";
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
		    List <RecommendInfo> ss=new ArrayList<>();
		    while(rs.next())
		    {
		    	String xuansubjects=null;
		    	String schoolid=rs.getString(1);
		    	String majorid=rs.getString(2);
		    	int signcondclass=rs.getInt(3);
		    	int signcond1=rs.getInt(4);
		    	int signcond2=rs.getInt(5);
		    	xuansubjects=rs.getString(6);
		    	int judge=0;
		    	for(int j=0;j<3;j++)
		    	{
			    		if(xuansubjects.contains(ke.get(j).toString()))
			    		{
			    			judge+=2;
			    		}
		    	}
		    	if(judge==xuansubjects.length()||xuansubjects.equals("无"))
		    	{
		    		if(signcondclass==1)
		    		{
		    			if(Anum>signcond1)
		    			{
		    				RecommendInfo u=new RecommendInfo();
				    		u.setMajorid(majorid);
				    		u.setSchoolid(schoolid);
				    		u.setPeopleid(userid);
				    		u.setReason("符合");
				    		ss.add(u);
		    			}
		    		}
		    		else if(signcondclass==2)
		    		{
		    			if(Afen>signcond1)
		    			{
		    				RecommendInfo u=new RecommendInfo();
				    		u.setMajorid(majorid);
				    		u.setSchoolid(schoolid);
				    		u.setPeopleid(userid);
				    		u.setReason("符合");
				    		ss.add(u);
		    			}
		    		}
		    		
		    	}
		    	
		    }
		    return ss;
		    		
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
public  void AddRecommend(RecommendInfo b) throws BaseException{
		
		
//		if(b.getBarcode()==null || "".equals(b.getBarcode()) || b.getBarcode().length()>20){
//			throw new BusinessException("条码必须是1-20个字");
//		}
//		if(b.getBookname()==null || "".equals(b.getBookname()) || b.getBookname().length()>50){
//			throw new BusinessException("图书名称必须是1-50个字");
//		}
		Connection conn=null;
		try {
			conn=(Connection) DBUtil.getConnection();
			String sql="select majorid from recommend_info where peopleid=? and majorid=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, b.getPeopleid());
			pst.setString(2, b.getMajorid());
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next())
			{
	     		sql="insert into recommend_info(peopleid,majorid,reason,schoolid) values(?,?,?,?)";
				pst=conn.prepareStatement(sql);
				pst.setString(1, b.getPeopleid());
				System.out.print(b.getMajorid());
				pst.setString(2, b.getMajorid());
				pst.setString(3, b.getReason());
				pst.setString(4, b.getSchoolid());
				pst.execute();
				pst.close();
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
		
	}
public List<RecommendInfo> searchRecommend(String keyword)throws BaseException{
	List<RecommendInfo> result=new ArrayList<RecommendInfo>();
	Connection conn=null;
	try {
		conn=(Connection) DBUtil.getConnection();
		String sql="select * from recommend_info ";
		if(keyword!=null && !"".equals(keyword))
			sql+="where  (peopleid like ? or majorid like ? or reason like ?)";
		sql+=" order by peopleid";
		java.sql.PreparedStatement pst=conn.prepareStatement(sql);
		if(keyword!=null && !"".equals(keyword)){
			pst.setString(1, "%"+keyword+"%");
			pst.setString(2, "%"+keyword+"%");
			pst.setString(3, "%"+keyword+"%");
		}
			
		java.sql.ResultSet rs=pst.executeQuery();
		while(rs.next()){
			RecommendInfo r= new RecommendInfo();
			r.setPeopleid(rs.getString(1));
			r.setMajorid(rs.getString(2));
			r.setReason(rs.getString(3));
			r.setSchoolid(rs.getString(4));
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
public  String getSchoolid(String majorid) throws BaseException{
	
	
//	if(b.getBarcode()==null || "".equals(b.getBarcode()) || b.getBarcode().length()>20){
//		throw new BusinessException("条码必须是1-20个字");
//	}
//	if(b.getBookname()==null || "".equals(b.getBookname()) || b.getBookname().length()>50){
//		throw new BusinessException("图书名称必须是1-50个字");
//	}
	Connection conn=null;
	try {
		conn=(Connection) DBUtil.getConnection();
		String sql="select schoolid from major_info where  majorid=?";
		java.sql.PreparedStatement pst=conn.prepareStatement(sql);
		pst.setString(1, majorid);
		java.sql.ResultSet rs=pst.executeQuery();
		if(rs.next())
		{
			return rs.getString(1);
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
	return majorid;
	
}
public void removeSRecommend(String schoolid)throws BaseException{
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
        	String  sql="delete from recommend_info where schoolid=?";
            java.sql.PreparedStatement   pst=conn.prepareStatement(sql);
            pst.setString(1, schoolid);
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
public void removePRecommend(String peopleid)throws BaseException{
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
        	String  sql="delete from recommend_info where peopleid=?";
            java.sql.PreparedStatement   pst=conn.prepareStatement(sql);
            pst.setString(1, peopleid);
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
