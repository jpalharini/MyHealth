package android.palharini.myhealth.datas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Timestamp {
	
	private String formatoDataSQL = "yyyy-MM-dd";
	private String formatoHoraSQL = "HH:mm:ss";
	
	private String formatoDataSQLBusca = "yyyyMMdd";
	
	private String formatoDataAndroid = "dd/MM/yyyy";
	private String formatoHorarioAndroid = "HH:mm";
	
	private String formatoHoraAndroid = "HH";
	private String formatoMinutoAndroid = "mm";
	
	private SimpleDateFormat sdfDataSQL = new SimpleDateFormat(formatoDataSQL);
	private SimpleDateFormat sdfHoraSQL = new SimpleDateFormat(formatoHoraSQL);
	
	private SimpleDateFormat sdfDataSQLBusca = new SimpleDateFormat(formatoDataSQLBusca);
	
	private SimpleDateFormat sdfDataAndroid = new SimpleDateFormat(formatoDataAndroid);
	private SimpleDateFormat sdfHorarioAndroid = new SimpleDateFormat(formatoHorarioAndroid);
	
	private SimpleDateFormat sdfHoraAndroid = new SimpleDateFormat(formatoHoraAndroid);
	private SimpleDateFormat sdfMinutoAndroid = new SimpleDateFormat(formatoMinutoAndroid);
	
	private Date dataDate = null, horaDate = null;
	private String horaString, minutoString;
	private Long horaMillis, minutoMillis, horarioMillis;
	
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
        
	public String getDataSQL() {
	    
	    try {
	    	dataDate = sdfDataSQL.parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sdfDataSQL.format(dataDate);
	}
	
	public String getHorarioSQL() {
	    
	    try {
	    	horaDate = sdfHoraSQL.parse(horario);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sdfHoraSQL.format(horaDate);
	}
	
	public String getDataAndroid(String data) {
	    
	    try {
	    	dataDate = sdfDataAndroid.parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sdfDataAndroid.format(dataDate);
	}
	
	public String getHorarioAndroid(String horario) {
	    
	    try {
	    	horaDate = sdfHorarioAndroid.parse(horario);
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
	
	public long getHorarioMillis (String horario) {
		
		try {
	    	horaDate = sdfHoraAndroid.parse(horario);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		horaString = sdfHoraAndroid.format(horaDate);
		
		try {
	    	horaDate = sdfMinutoAndroid.parse(horario);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		minutoString = sdfMinutoAndroid.format(horaDate);
		
		horaMillis = TimeUnit.HOURS.toMillis(Integer.parseInt(horaString));
		minutoMillis = TimeUnit.MINUTES.toMillis(Integer.parseInt(minutoString));
		
		horarioMillis = horaMillis + minutoMillis;
		
		return horarioMillis;
	}
}