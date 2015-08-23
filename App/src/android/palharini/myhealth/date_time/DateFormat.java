package android.palharini.myhealth.date_time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormat {
	
	private String strSqlDateFormat = "yyyy-MM-dd";
	private String strSqlTimeFormat = "HH:mm:ss";
	
	private String strSqlSearchDateFormat = "yyyyMMdd";
	
	private String strAndroidDateFormat = "dd/MM/yyyy";
	private String strAndroidTimeFormat = "HH:mm";
		
	private SimpleDateFormat sdfSqlDateFormat = new SimpleDateFormat(strSqlDateFormat);
	private SimpleDateFormat sdfSqlTimeFormat = new SimpleDateFormat(strSqlTimeFormat);
	
	private SimpleDateFormat sdfSqlSearchDateFormat = new SimpleDateFormat(strSqlSearchDateFormat);
	
	private SimpleDateFormat sdfAndroidDateFormat = new SimpleDateFormat(strAndroidDateFormat);
	private SimpleDateFormat sdfAndroidTimeFormat = new SimpleDateFormat(strAndroidTimeFormat);
	
	private Date dtDate = null, dtTime = null;
	
	private Calendar c = Calendar.getInstance();
	private int day = c.get(Calendar.DAY_OF_MONTH);
	private int month = c.get(Calendar.MONTH);
	private int year = c.get(Calendar.YEAR);
	private int hour = c.get(Calendar.HOUR_OF_DAY);
	private int minute = c.get(Calendar.MINUTE);
	private int second = c.get(Calendar.SECOND);
	
    private String strSqlDate =
    		String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(day);
    
    private String strSqlSearchDate =
    		String.valueOf(year) + String.valueOf(month + 1) + String.valueOf(day);
    
    private String strSqlTime =
    		String.valueOf(hour) + ":" + String.valueOf(minute) + ":" + String.valueOf(second);
        
    public String getSqlDate(String data) {
	    
	    try {
	    	dtDate = sdfSqlDateFormat.parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sdfSqlDateFormat.format(dtDate);
	}
    
    public String getCurrentSqlDate() {
	    
	    try {
	    	dtDate = sdfSqlDateFormat.parse(strSqlDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sdfSqlDateFormat.format(dtDate);
	}
	
	public String getCurrentSqlTime() {
	    
	    try {
	    	dtTime = sdfSqlTimeFormat.parse(strSqlTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sdfSqlTimeFormat.format(dtTime);
	}
	
	public String getAndroidDate(String newDate) {
	    
	    try {
	    	dtDate = sdfAndroidDateFormat.parse(newDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sdfAndroidDateFormat.format(dtDate);
	}
	
	public String getAndroidTime(String newTime) {
	    
	    try {
	    	dtTime = sdfAndroidTimeFormat.parse(newTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sdfAndroidTimeFormat.format(dtTime);
	}

	public String getCurrentSqlSearchDate() {
		
		try {
	    	dtDate = sdfSqlSearchDateFormat.parse(strSqlSearchDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sdfSqlSearchDateFormat.format(dtDate);
	}
	
}