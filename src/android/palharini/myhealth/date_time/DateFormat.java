package android.palharini.myhealth.date_time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormat {
	
	private String formatoDataSQL = "yyyy-MM-dd";
	private String formatoHoraSQL = "HH:mm:ss";
	
	private String formatoDataSQLBusca = "yyyyMMdd";
	
	private String formatoDataAndroid = "dd/MM/yyyy";
	private String formatoHorarioAndroid = "HH:mm";
		
	private SimpleDateFormat sdfDataSQL = new SimpleDateFormat(formatoDataSQL);
	private SimpleDateFormat sdfHoraSQL = new SimpleDateFormat(formatoHoraSQL);
	
	private SimpleDateFormat sdfDataSQLBusca = new SimpleDateFormat(formatoDataSQLBusca);
	
	private SimpleDateFormat sdfDataAndroid = new SimpleDateFormat(formatoDataAndroid);
	private SimpleDateFormat sdfHorarioAndroid = new SimpleDateFormat(formatoHorarioAndroid);
	
	private Date dataDate = null, horaDate = null;
	
	private Calendar c = Calendar.getInstance();
	private int dia = c.get(Calendar.DAY_OF_MONTH);
	private int mes = c.get(Calendar.MONTH);
	private int ano = c.get(Calendar.YEAR);
	private int hora = c.get(Calendar.HOUR_OF_DAY);
	private int minuto = c.get(Calendar.MINUTE);
	private int segundo = c.get(Calendar.SECOND);
	
    private String data = 
    		String.valueOf(ano) + "-" + String.valueOf(mes + 1) + "-" + String.valueOf(dia);
    
    private String dataBusca =
    		String.valueOf(ano) + String.valueOf(mes + 1) + String.valueOf(dia);
    
    private String horario = 
    		String.valueOf(hora) + ":" + String.valueOf(minuto) + ":" + String.valueOf(segundo);
        
    public String getDateSQL(String data) {
	    
	    try {
	    	dataDate = sdfDataSQL.parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sdfDataSQL.format(dataDate);
	}
    
    public String getDataAtualSQL() {
	    
	    try {
	    	dataDate = sdfDataSQL.parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sdfDataSQL.format(dataDate);
	}
	
	public String getHorarioAtualSQL() {
	    
	    try {
	    	horaDate = sdfHoraSQL.parse(horario);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sdfHoraSQL.format(horaDate);
	}
	
	public String getDateAndroid(String newDate) {
	    
	    try {
	    	dataDate = sdfDataAndroid.parse(newDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sdfDataAndroid.format(dataDate);
	}
	
	public String getHorarioAndroid(String newTime) {
	    
	    try {
	    	horaDate = sdfHorarioAndroid.parse(newTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sdfHorarioAndroid.format(horaDate);
	}

	public String getDataAtual() {
		return String.valueOf(ano) + "-" + String.valueOf(mes + 1) + "-" + String.valueOf(dia);
	}
	
	public String getDataAtualBusca() {
		
		try {
	    	dataDate = sdfDataSQLBusca.parse(dataBusca);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sdfDataSQLBusca.format(dataDate);
	}
	
}