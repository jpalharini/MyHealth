package android.palharini.myhealth.db.entities;

public class Indicator {

	private Integer id;
	private Integer typeID;
	private Integer userID;
	private Double measure1;
	private Double measure2;
	private String measUnit;
	private String strDate;
	private String strTime;
	
	public Indicator(){}

	public Indicator(Integer id, Integer typeID, Integer userID, Double measure1, Double measure2,
					 String measUnit, String strDate, String strTime) {
		this.id = id;
		this.typeID = typeID;
		this.userID = userID;
		this.measure1 = measure1;
		this.measure2 = measure2;
		this.measUnit = measUnit;
		this.strDate = strDate;
		this.strTime = strTime;
	}
	
	public Indicator(Integer id, Integer typeID, Double measure1, Double measure2,
					 String measUnit) {
		this.id = id;
		this.typeID = typeID;
		this.measure1 = measure1;
		this.measure2 = measure2;
		this.measUnit = measUnit;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTypeID() {
		return typeID;
	}

	public void setTypeId(Integer typeID) {
		this.typeID = typeID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserId(Integer userID) {
		this.userID = userID;
	}

	public Double getMeasure1() {
		return measure1;
	}

	public void setMeasure1(Double measure1) {
		this.measure1 = measure1;
	}
	
	public Double getMeasure2() {
		return measure2;
	}

	public void setMeasure2(Double measure2) {
		this.measure2 = measure2;
	}

	public String getMeasUnit() {
		return measUnit;
	}

	public void setMeasUnit(String measUnit) {
		this.measUnit = measUnit;
	}

	public String getStrDate() {
		return strDate;
	}

	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}

	public String getStrTime() {
		return strTime;
	}

	public void setStrTime(String strTime) {
		this.strTime = strTime;
	}

	@Override
	public String toString() {		
		return this.strDate + " " + this.strTime;
	}
	
}