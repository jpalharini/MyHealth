package android.palharini.myhealth.date_time.pickers;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.palharini.myhealth.date_time.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class TimePickerBox extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

	Calendar c, cal;
	DateFormat ts = new DateFormat();
	public EditText horaLembrete;
	
	@SuppressLint("ValidFragment")
	public TimePickerBox(EditText timePicker) {
		horaLembrete = timePicker;
	}
	
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		    // Use the current date as the default date in the picker
		    c = Calendar.getInstance();
		    int hora = c.get(Calendar.HOUR_OF_DAY);
		    int minuto = c.get(Calendar.MINUTE);
	
	    // Create a new instance of DatePickerDialog and return it
	    return new TimePickerDialog(getActivity(), this, hora, minuto, true);
	}
	
		@Override
		public void onTimeSet(TimePicker view, int hora, int minuto) {
			// TODO Auto-generated method stub
			String horario = (String.valueOf(hora) + ":" + String.valueOf(minuto));
			String horarioAndroid = ts.getAndroidTime(horario);
			horaLembrete.setText(horarioAndroid);
		}
	}