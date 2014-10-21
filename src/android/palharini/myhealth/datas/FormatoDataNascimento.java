package android.palharini.myhealth.datas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatoDataNascimento {

	private String formatoData = "d/MM/yyyy";
	private String formatoDataSQL = "yyyy-MM-dd";
	SimpleDateFormat sdf = new SimpleDateFormat(formatoData);
	SimpleDateFormat sdfSQL = new SimpleDateFormat(formatoDataSQL);
	Date dataNascDate = null;
	
	public String formatarData (String dataNasc) {
		try {
			dataNascDate = sdf.parse(dataNasc);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return sdfSQL.format(dataNascDate);
	}
}
