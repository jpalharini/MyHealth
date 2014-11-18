package android.palharini.myhealth.datas;

import java.util.Calendar;

import android.app.TimePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TimePicker;

public class FragmentoTimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

	Calendar c, cal;
	Timestamp ts = new Timestamp();
	public EditText horaLembrete;
	
	public FragmentoTimePicker(EditText timePicker) {
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
			String horarioAndroid = ts.getHorarioAndroid(horario);
			horaLembrete.setText(horarioAndroid);
		}
	}