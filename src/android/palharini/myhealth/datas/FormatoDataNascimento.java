package android.palharini.myhealth.datas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatoDataNascimento {

	private String formatoDataAndroid = "d/M/yyyy";
	private String formatoDataSQL = "yyyy-MM-dd";
	SimpleDateFormat sdfAnd = new SimpleDateFormat(formatoDataAndroid);
	SimpleDateFormat sdfSQL = new SimpleDateFormat(formatoDataSQL);
	Date dataNascDate = null;
	
	public String formatarDataSQL (String dataNasc) {
		try {
			dataNascDate = sdfAnd.parse(dataNasc);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return sdfSQL.format(dataNascDate);
	}
	
	public String formatarDataAndroid (String dataNasc) {
		try {
			dataNascDate = sdfSQL.parse(dataNasc);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return sdfAnd.format(dataNascDate);
	}
}
