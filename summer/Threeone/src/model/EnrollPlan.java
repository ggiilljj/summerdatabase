package model;

public class EnrollPlan {
	private String enrollid;//专业编号
	private int enrollyear;
	private String xuansubjects;//选考科目
	private int plannum;//计划数
	private double tuition;
	private String otherrequire;
	private String schoolid;
	private String schoolname;
	private String enrollname;
	public String getSchoolname() {
		return schoolname;
	}
	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}
	public String getEnrollname() {
		return enrollname;
	}
	public void setEnrollname(String enrollname) {
		this.enrollname = enrollname;
	}
	public String getEnrollid() {
		return enrollid;
	}
	public void setEnrollid(String enrollid) {
		this.enrollid=enrollid;
	}
	public int getEnrollyear() {
		return enrollyear;
	}
	public void setEnrollyear(int enrollyear) {
		this.enrollyear=enrollyear;
	}
	public String getXuansubjects() {
		return xuansubjects;
	}
	public void setXuansubjects(String xuansubjects) {
		this.xuansubjects=xuansubjects;
	}
	public int getPlannum() {
		return plannum;
	}
	public void setPlannum(int plannum) {
		this.plannum=plannum;
	}
	public double getTuition() {
		return tuition;
	}
	public void setTuition(double tuition) {
		this.tuition=tuition;
	}
	public String getOtherrequire() {
		return otherrequire;
	}
	public void setOtherrequire(String otherrequire) {
		this.otherrequire=otherrequire;
	}
	public String getSchoolid() {
		return schoolid;
	}
	public void setSchoolid(String schoolid) {
		this.schoolid=schoolid;
	}
}
