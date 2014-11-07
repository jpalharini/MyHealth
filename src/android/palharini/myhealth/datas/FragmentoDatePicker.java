package android.palharini.myhealth.datas;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

public class FragmentoDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	Timestamp ts = new Timestamp();
	public EditText editDataNasc;
	
	public FragmentoDatePicker(EditText datePicker) {
		editDataNasc = datePicker;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    // Use the current date as the default date in the picker
	    final Calendar c = Calendar.getInstance();
	    int ano = c.get(Calendar.YEAR);
	    int mes = c.get(Calendar.MONTH);
	    int dia = c.get(Calendar.DAY_OF_MONTH);

    // Create a new instance of DatePickerDialog and return it
    return new DatePickerDialog(getActivity(), this, ano, mes, dia);
}

    @Override
    public void onDateSet(DatePicker view, int ano, int mes, int dia) {
       String dataNasc = String.valueOf(dia) + "/" + String.valueOf(mes + 1 ) + "/" + String.valueOf(ano);
       String dataNascAndroid = ts.getDataAndroid(dataNasc);
       editDataNasc.setText(dataNascAndroid);
	       
    }
	
}