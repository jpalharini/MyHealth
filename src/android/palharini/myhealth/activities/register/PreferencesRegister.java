package android.palharini.myhealth.activities.register;

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
import android.palharini.myhealth.activities.Main;
import android.palharini.myhealth.date_time.pickers.TimePickerBox;
import android.palharini.myhealth.db.dao.PreferencesDAO;
import android.palharini.myhealth.db.entities.Preferences;
import android.palharini.myhealth.notifications.NotificationReceiver;
import android.palharini.myhealth.session.SessionManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class PreferencesRegister extends Activity {
	
	private SessionManager sessao;
	
	private CheckBox chLembretePeso, chLembreteBPM;
	private EditText etHoraLembretePeso, etHoraLembreteBPM;
	private Button btSalvar;
	
	private PreferencesDAO prefsDAO;
	
	private boolean blLembretePeso, blLembreteBPM;
	private String stHoraLembretePeso, stHoraLembreteBPM;
	private Calendar calHoraLembretePeso, calHoraLembreteBPM;
	private String[] arrHoraLembretePeso, arrHoraLembreteBPM;
	private long mlHoraLembretePeso, mlHoraLembreteBPM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences_register);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		sessao = new SessionManager(getApplicationContext());
		
		chLembretePeso = (CheckBox) findViewById(R.id.chLembretePeso);
		etHoraLembretePeso = (EditText) findViewById(R.id.horaLembretePeso);
		
		chLembreteBPM = (CheckBox) findViewById(R.id.chLembreteBPM);
		etHoraLembreteBPM = (EditText) findViewById(R.id.horaLembreteBPM);
		
		btSalvar = (Button) findViewById(R.id.btSave);
		
		prefsDAO = new PreferencesDAO();
				
		etHoraLembretePeso.setOnClickListener(new OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new TimePickerBox((EditText) v).show(getFragmentManager(), "timePicker");
			}
			
		});
		
		etHoraLembreteBPM.setOnClickListener(new OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new TimePickerBox((EditText) v).show(getFragmentManager(), "timePicker");
			}
			
		});
		
		btSalvar.setOnClickListener(new OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub {
				
				blLembretePeso = chLembretePeso.isChecked();
				blLembreteBPM = chLembreteBPM.isChecked();
								
				if (blLembretePeso) {
					stHoraLembretePeso = etHoraLembretePeso.getText().toString();
					arrHoraLembretePeso = stHoraLembretePeso.split(":");
					calHoraLembretePeso = Calendar.getInstance();
					calHoraLembretePeso.setTimeInMillis(System.currentTimeMillis());
					calHoraLembretePeso.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrHoraLembretePeso[0]));
					calHoraLembretePeso.set(Calendar.MINUTE, Integer.parseInt(arrHoraLembretePeso[1]));
					mlHoraLembretePeso = calHoraLembretePeso.getTimeInMillis();
				}
				else {
					stHoraLembretePeso = "00:00";
				}
				
				if (blLembreteBPM) {
					stHoraLembreteBPM = etHoraLembreteBPM.getText().toString();
					arrHoraLembreteBPM = stHoraLembreteBPM.split(":");
					calHoraLembreteBPM = Calendar.getInstance();
					calHoraLembreteBPM.setTimeInMillis(System.currentTimeMillis());
					calHoraLembreteBPM.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrHoraLembreteBPM[0]));
					calHoraLembreteBPM.set(Calendar.MINUTE, Integer.parseInt(arrHoraLembreteBPM[1]));
					mlHoraLembreteBPM = calHoraLembreteBPM.getTimeInMillis();
				}
				else {
					stHoraLembreteBPM = "00:00";
				}
				
				boolean prefs = prefsDAO.cadastrarPreferencias(new Preferences(
						0,
						sessao.getUserID(),
						blLembretePeso,
						stHoraLembretePeso,
						blLembreteBPM,
						stHoraLembreteBPM
						));
				
				if (prefs) {
					Toast.makeText(getApplicationContext(), getString(R.string.toastPrefsOK), Toast.LENGTH_LONG).show();
					if (blLembretePeso) {
						marcarNotificacaoPeso(mlHoraLembretePeso);
					}
					if (blLembreteBPM) {
						marcarNotificacaoBPM(mlHoraLembreteBPM);
					}
					Intent irTelaPrincipal = new Intent(getApplicationContext(), Main.class);
					startActivity(irTelaPrincipal);
					finish();
				}
				else {
					Toast.makeText(getApplicationContext(), getString(R.string.toastPrefsFail), Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}
	
	public void marcarNotificacaoPeso (long horaLembrete) {
		 
        Intent notificacaoIntentPeso = new Intent(this, NotificationReceiver.class);
        notificacaoIntentPeso.putExtra("ID_NOTIFICACAO", 1);
        notificacaoIntentPeso.putExtra("ttNotificacao", getString(R.string.ttNotificacaoPeso));
        notificacaoIntentPeso.putExtra("txNotificacao", getString(R.string.txNotificacaoPeso));
        PendingIntent pendingNotificacaoPesoIntent = PendingIntent.getBroadcast(
        		this, 1, notificacaoIntentPeso, PendingIntent.FLAG_UPDATE_CURRENT);
        
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating (
        		AlarmManager.RTC, horaLembrete, TimeUnit.DAYS.toMillis(1), pendingNotificacaoPesoIntent);
        
	}
    
	public void marcarNotificacaoBPM (long horaLembrete) {
		 
        Intent notificacaoIntentBPM = new Intent(this, NotificationReceiver.class);
        notificacaoIntentBPM.putExtra("ID_NOTIFICACAO", 2);
        notificacaoIntentBPM.putExtra("ttNotificacao", getString(R.string.ttNotificacaoBPM));
        notificacaoIntentBPM.putExtra("txNotificacao", getString(R.string.txNotificacaoBPM));
        PendingIntent pendingNotificacaoBPMIntent = PendingIntent.getBroadcast(
        		this, 2, notificacaoIntentBPM, PendingIntent.FLAG_UPDATE_CURRENT);
        
        AlarmManager alarmManager = (AlarmManager) getSystemService (Context.ALARM_SERVICE);
        alarmManager.setRepeating (
        		AlarmManager.RTC, horaLembrete, TimeUnit.DAYS.toMillis(1), pendingNotificacaoBPMIntent);
                
	}

}
