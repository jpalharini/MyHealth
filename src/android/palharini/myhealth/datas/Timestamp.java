package android.palharini.myhealth.datas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Timestamp {
	
	private String formatoDataSQL = "yyyy-MM-dd";
	private String formatoHoraSQL = "HH:mm:ss";
	private String formatoDataAndroid = "dd/MM/yyyy";
	private String formatoHoraAndroid = "HH:mm";
	
	private SimpleDateFormat sdfDataSQL = new SimpleDateFormat(formatoDataSQL);
	private SimpleDateFormat sdfHoraSQL = new SimpleDateFormat(formatoHoraSQL);
	private SimpleDateFormat sdfDataAndroid = new SimpleDateFormat(formatoDataAndroid);
	private SimpleDateFormat sdfHoraAndroid = new SimpleDateFormat(formatoHoraAndroid);
	
	Date dataDate = null;
	Date horaDate = null;
	String dataString;
	int dataInt;
	
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
	    	horaDate = sdfHoraAndroid.parse(horario);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sdfHoraAndroid.format(horaDate);
	}

	public int getCampoAtual(String periodo) {
		switch (periodo) {
		case "yyyy":
			return ano;
		case "MM":
			return mes;
		case "dd":
			return dia;
		case "HH":
			return hora;
		case "mm":
			return minuto;
		case "ss":
			return segundo;
		}
		
		return 0;
		
	}
	
	public String getDataAtual() {
		return String.valueOf(ano) + "-" + String.valueOf(mes + 1) + "-" + String.valueOf(dia);
	}
	
	public String getDataAtualBusca() {
		return dataBusca;
	}
}