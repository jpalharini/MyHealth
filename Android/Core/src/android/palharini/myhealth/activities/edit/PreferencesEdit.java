package android.palharini.myhealth.activities.edit;

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

	private DialogBox caixa;
	private SessionManager sessao;
	
	private PreferencesDAO prefsDAO;
	private Preferences prefs;
	
	private CheckBox chLembretePeso, chLembreteBPM;
	private EditText etHoraLembretePeso, etHoraLembreteBPM;
	private Button btSalvar, btLogout;
	
	private Calendar calHoraLembretePeso, calHoraLembreteBPM;
	private Boolean lembretePeso, lembreteBPM;
	private Boolean blPrefs;
	private String stHoraLembretePeso, stHoraLembreteBPM;
	private String[] arrHoraLembretePeso, arrHoraLembreteBPM;
	private PendingIntent pendingNotificacaoPesoIntent = null, pendingNotificacaoBPMIntent = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences_edit);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		chLembretePeso = (CheckBox) findViewById(R.id.chLembretePeso);
		etHoraLembretePeso = (EditText) findViewById(R.id.horaLembretePeso);
		
		chLembreteBPM = (CheckBox) findViewById(R.id.chLembreteBPM);
		etHoraLembreteBPM = (EditText) findViewById(R.id.horaLembreteBPM);
		
		btSalvar = (Button) findViewById(R.id.btSave);
		btLogout = (Button) findViewById(R.id.btLogout);
		
        caixa = new DialogBox();
		
        sessao = new SessionManager(getApplicationContext());
		
		prefsDAO = new PreferencesDAO();
		prefs = prefsDAO.buscarPreferencias(sessao.getUserID());
		
		if (prefs != null) {
			if (prefs.isLembretePeso()) {
				chLembretePeso.setChecked(true);
				etHoraLembretePeso.setText(prefs.getHoraLembretePeso().toString());
			}
			
			if (prefs.isLembreteBPM()) {
				chLembreteBPM.setChecked(true);
				etHoraLembreteBPM.setText(prefs.getHoraLembreteBPM().toString());
			}
		}
		else {
			caixa.showAlertDialog(
        			this, 
        			getString(R.string.tvFalhaConexao), 
        			getString(R.string.tvServidorNaoResponde), 
        			false);
		}
		
		etHoraLembretePeso.setOnClickListener(new EditText.OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new TimePickerBox((EditText) v).show(getFragmentManager(), "timePicker");
			}
			
		});
		
		etHoraLembreteBPM.setOnClickListener(new EditText.OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new TimePickerBox((EditText) v).show(getFragmentManager(), "timePicker");
			}
			
		});
		
		btSalvar.setOnClickListener(new Button.OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lembretePeso = chLembretePeso.isChecked();
				lembreteBPM = chLembreteBPM.isChecked();
				
				stHoraLembretePeso = etHoraLembretePeso.getText().toString();
				stHoraLembreteBPM = etHoraLembreteBPM.getText().toString();
				
				if (lembretePeso) {
					stHoraLembretePeso = etHoraLembretePeso.getText().toString();
					arrHoraLembretePeso = stHoraLembretePeso.split(":");
					calHoraLembretePeso = Calendar.getInstance();
					calHoraLembretePeso.setTimeInMillis(System.currentTimeMillis());
					calHoraLembretePeso.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrHoraLembretePeso[0]));
					calHoraLembretePeso.set(Calendar.MINUTE, Integer.parseInt(arrHoraLembretePeso[1]));
				}
				else {
					stHoraLembretePeso = "00:00";
				}
				
				if (lembreteBPM) {
					stHoraLembreteBPM = etHoraLembreteBPM.getText().toString();
					arrHoraLembreteBPM = stHoraLembreteBPM.split(":");
					calHoraLembreteBPM = Calendar.getInstance();
					calHoraLembreteBPM.setTimeInMillis(System.currentTimeMillis());
					calHoraLembreteBPM.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrHoraLembreteBPM[0]));
					calHoraLembreteBPM.set(Calendar.MINUTE, Integer.parseInt(arrHoraLembreteBPM[1]));
				}
				else {
					stHoraLembreteBPM = "00:00";
				}
				
				Preferences prefsAtual = new Preferences(
						prefs.getId(),
						sessao.getUserID(),
						lembretePeso,
						stHoraLembretePeso,
						lembreteBPM,
						stHoraLembreteBPM
						);
				
				blPrefs = prefsDAO.atualizarPreferencias(prefsAtual);
				
				if (chLembretePeso.isChecked() != prefs.isLembretePeso()) {
					if (lembretePeso) {
						pendingNotificacaoPesoIntent = marcarNotificacaoPeso(calHoraLembretePeso.getTimeInMillis());
					}
					else {
						cancelarNotificacao(pendingNotificacaoPesoIntent);
					}
					if (lembreteBPM) {
						pendingNotificacaoBPMIntent = marcarNotificacaoBPM(calHoraLembreteBPM.getTimeInMillis());
					}
					else {
						cancelarNotificacao(pendingNotificacaoBPMIntent);
					}
				}
				
				if (blPrefs) {
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
				cancelarNotificacao(pendingNotificacaoPesoIntent);
				cancelarNotificacao(pendingNotificacaoBPMIntent);
				sessao.logoutUsuario();
				Intent voltarTelaLogin = new Intent(getApplicationContext(), Login.class);
				startActivity(voltarTelaLogin);
			}
			
		});
		
	}
	
	public PendingIntent marcarNotificacaoPeso (long horaLembrete) {
		 
        Intent notificacaoIntent = new Intent(this, NotificationReceiver.class);
        notificacaoIntent.putExtra("ID_NOTIFICACAO", 1);
        notificacaoIntent.putExtra("ttNotificacao", getString(R.string.ttNotificacaoPeso));
        notificacaoIntent.putExtra("txNotificacao", getString(R.string.txNotificacaoPeso));
        PendingIntent pendingNotificacaoPesoIntent = PendingIntent.getBroadcast(this, 0, notificacaoIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, horaLembrete, TimeUnit.DAYS.toMillis(1), pendingNotificacaoPesoIntent);
        
        return pendingNotificacaoPesoIntent;

	}
    

	public PendingIntent marcarNotificacaoBPM (long horaLembrete) {
		 
        Intent notificacaoIntent = new Intent(this, NotificationReceiver.class);
        notificacaoIntent.putExtra("ID_NOTIFICACAO", 2);
        notificacaoIntent.putExtra("ttNotificacao", getString(R.string.ttNotificacaoBPM));
        notificacaoIntent.putExtra("txNotificacao", getString(R.string.txNotificacaoBPM));
        PendingIntent pendingNotificacaoBPMIntent = PendingIntent.getBroadcast(this, 0, notificacaoIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, horaLembrete, TimeUnit.DAYS.toMillis(1), pendingNotificacaoBPMIntent);
        
        return pendingNotificacaoBPMIntent;
        
	} 
	
    public void cancelarNotificacao (PendingIntent pendingNotificacaoIntent) {
    	AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    	alarmManager.cancel(pendingNotificacaoIntent);
    }
    
}
