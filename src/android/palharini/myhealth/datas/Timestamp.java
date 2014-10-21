package android.palharini.myhealth.datas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Timestamp {
	
	private String formatoDataSQL = "yyyy-MM-dd HH:mm:ss";
	private SimpleDateFormat sdfSQL = new SimpleDateFormat(formatoDataSQL);
	Date timestampDate = null;
	
	public String getTimestamp() {
		final Calendar c = Calendar.getInstance();
		final int dia = c.get(Calendar.DAY_OF_MONTH);
	    final int mes = c.get(Calendar.MONTH);
	    final int ano = c.get(Calendar.YEAR);
	    final int hora = c.get(Calendar.HOUR_OF_DAY);
	    final int minuto = c.get(Calendar.MINUTE);
	    final int segundo = c.get(Calendar.SECOND);
	    
	    String timestamp = 
	    		String.valueOf(ano) + "-" + String.valueOf(mes + 1) + "-" + String.valueOf(dia) + " " + 
	    		String.valueOf(hora) + ":" + String.valueOf(minuto) + ":" + String.valueOf(segundo);
	    
	    try {
			timestampDate = sdfSQL.parse(timestamp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sdfSQL.format(timestampDate);
	}
}