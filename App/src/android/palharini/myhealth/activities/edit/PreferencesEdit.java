package android.palharini.myhealth.activities.edit;

import java.lang.String;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.palharini.myhealth.R;
import android.palharini.myhealth.activities.Login;
import android.palharini.myhealth.date_time.pickers.TimePickerBox;
import android.palharini.myhealth.db.dao.PreferencesDAO;
import android.palharini.myhealth.db.entities.Preferences;
import android.palharini.myhealth.notifications.NotificationReceiver;
import android.palharini.myhealth.session.DialogBox;
import android.palharini.myhealth.session.SessionManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class PreferencesEdit extends Activity {

	private DialogBox dialogBox;
	private SessionManager sessionManager;
	
	private PreferencesDAO preferencesDAO;
	private Preferences preferences;
	
	private CheckBox chWeighReminder, chHrReminder;
	private EditText etWeighReminderTime, chHrReminderTime;
	private Button btSave, btLogout;
	
	private Calendar calendar;
	private Boolean blWeighReminder, blHrReminder;
	private Boolean blPreferences;
	private String stWeighReminderTime, stHrReminderTime;
	private String[] stArrReminderTime;
	private PendingIntent pendIntWeigh = null, pendIntHr = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences_edit);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		chWeighReminder = (CheckBox) findViewById(R.id.chLembretePeso);
		etWeighReminderTime = (EditText) findViewById(R.id.horaLembretePeso);
		
		chHrReminder = (CheckBox) findViewById(R.id.chLembreteBPM);
		chHrReminderTime = (EditText) findViewById(R.id.horaLembreteBPM);
		
		btSave = (Button) findViewById(R.id.btSave);
		btLogout = (Button) findViewById(R.id.btLogout);
		
        dialogBox = new DialogBox();
		
        sessionManager = new SessionManager(getApplicationContext());
		
		preferencesDAO = new PreferencesDAO();
		preferences = preferencesDAO.buscarPreferencias(sessionManager.getUserID());
		
		if (preferences != null) {
			if (preferences.isLembretePeso()) {
				chWeighReminder.setChecked(true);
				etWeighReminderTime.setText(preferences.getHoraLembretePeso().toString());
			}
			
			if (preferences.isLembreteBPM()) {
				chHrReminder.setChecked(true);
				chHrReminderTime.setText(preferences.getHoraLembreteBPM().toString());
			}
		}
		else {
			dialogBox.showAlertDialog(
        			this, 
        			getString(R.string.tvFalhaConexao), 
        			getString(R.string.tvServidorNaoResponde), 
        			false);
		}
		
		etWeighReminderTime.setOnClickListener(new EditText.OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new TimePickerBox((EditText) v).show(getFragmentManager(), "timePicker");
			}
			
		});
		
		chHrReminderTime.setOnClickListener(new EditText.OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new TimePickerBox((EditText) v).show(getFragmentManager(), "timePicker");
			}
			
		});
		
		btSave.setOnClickListener(new Button.OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				blWeighReminder = chWeighReminder.isChecked();
				blHrReminder = chHrReminder.isChecked();
				
				stWeighReminderTime = etWeighReminderTime.getText().toString();
				stHrReminderTime = chHrReminderTime.getText().toString();
				
				if (blWeighReminder) {
					stWeighReminderTime = etWeighReminderTime.getText().toString();
                    stArrReminderTime = new String[]{stHrReminderTime.split(":")};
					calendar = Calendar.getInstance();
					calendar.setTimeInMillis(System.currentTimeMillis());
					calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(stArrReminderTime[0]));
					calendar.set(Calendar.MINUTE, Integer.parseInt(stArrReminderTime[1]));
				}
				else {
					stWeighReminderTime = "00:00";
				}
				
				if (blHrReminder) {
					stHrReminderTime = chHrReminderTime.getText().toString();
					stArrReminderTime = new String[]{stHrReminderTime.split(":")};
					calendar = Calendar.getInstance();
					calendar.setTimeInMillis(System.currentTimeMillis());
					calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(stArrReminderTime[0]));
					calendar.set(Calendar.MINUTE, Integer.parseInt(stArrReminderTime[1]));
				}
				else {
					stHrReminderTime = "00:00";
				}
				
				Preferences prefsAtual = new Preferences(
						preferences.getId(),
						sessionManager.getUserID(),
						blWeighReminder,
						stWeighReminderTime,
						blHrReminder,
						stHrReminderTime
						);
				
				blPreferences = preferencesDAO.atualizarPreferencias(prefsAtual);
				
				if (chWeighReminder.isChecked() != preferences.isLembretePeso()) {
					if (blWeighReminder) {
						pendIntWeigh = scheduleWeighNotification(calendar.getTimeInMillis());
					}
					else {
						cancelNotification(pendIntWeigh);
					}
					if (blHrReminder) {
						pendIntHr = scheduleHrNotification(calHrReminderTime.getTimeInMillis());
					}
					else {
						cancelNotification(pendIntHr);
					}
				}
				
				if (blPreferences) {
					Toast.makeText(getApplicationContext(), getString(R.string.toastPrefsOK), Toast.LENGTH_LONG).show();
					finish();
				}
				else {
					Toast.makeText(getApplicationContext(), getString(R.string.toastPrefsFail), Toast.LENGTH_LONG).show();
				}
					
			}
			
		});
		
		btLogout.setOnClickListener(new Button.OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cancelNotification(pendIntWeigh);
				cancelNotification(pendIntHr);
				sessionManager.logoutUsuario();
				Intent voltarTelaLogin = new Intent(getApplicationContext(), Login.class);
				startActivity(voltarTelaLogin);
			}
			
		});
		
	}
	
	public PendingIntent scheduleWeighNotification(long reminderTime) {
		 
        Intent notificacaoIntent = new Intent(this, NotificationReceiver.class);
        notificacaoIntent.putExtra("ID_NOTIFICACAO", 1);
        notificacaoIntent.putExtra("ttNotificacao", getString(R.string.ttNotificacaoPeso));
        notificacaoIntent.putExtra("txNotificacao", getString(R.string.txNotificacaoPeso));
        PendingIntent pendingNotificacaoPesoIntent = PendingIntent.getBroadcast(this, 0, notificacaoIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, reminderTime, TimeUnit.DAYS.toMillis(1), pendingNotificacaoPesoIntent);
        
        return pendingNotificacaoPesoIntent;

	}
    

	public PendingIntent scheduleHrNotification(long reminderTime) {
		 
        Intent notificacaoIntent = new Intent(this, NotificationReceiver.class);
        notificacaoIntent.putExtra("ID_NOTIFICACAO", 2);
        notificacaoIntent.putExtra("ttNotificacao", getString(R.string.ttNotificacaoBPM));
        notificacaoIntent.putExtra("txNotificacao", getString(R.string.txNotificacaoBPM));
        PendingIntent pendingNotificacaoBPMIntent = PendingIntent.getBroadcast(this, 0, notificacaoIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, reminderTime, TimeUnit.DAYS.toMillis(1), pendingNotificacaoBPMIntent);
        
        return pendingNotificacaoBPMIntent;
        
	} 
	
    public void cancelNotification(PendingIntent pendIntNotification) {
    	AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    	alarmManager.cancel(pendIntNotification);
    }
    
}
