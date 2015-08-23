package android.palharini.myhealth.date_time.pickers;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.palharini.myhealth.date_time.DateFormat;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class DatePickerBox extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	DateFormat ts = new DateFormat();
	public EditText editDataNasc;

	@SuppressLint("ValidFragment")
    public DatePickerBox(EditText datePicker) {
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
       String dataNascAndroid = ts.getAndroidDate(dataNasc);
       editDataNasc.setText(dataNascAndroid);
	       
    }
	
}