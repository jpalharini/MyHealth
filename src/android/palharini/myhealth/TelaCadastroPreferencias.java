package android.palharini.myhealth;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.palharini.myhealth.dao.PreferenciasDAO;
import android.palharini.myhealth.datas.FragmentoTimePicker;
import android.palharini.myhealth.entidades.Preferencias;
import android.palharini.myhealth.notificacoes.NotificacaoReceiver;
import android.palharini.myhealth.sessao.GerenciamentoSessao;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class TelaCadastroPreferencias extends Activity {
	
	private GerenciamentoSessao sessao;
	
	private CheckBox chLembretePeso, chLembreteBPM;
	private EditText etHoraLembretePeso, etHoraLembreteBPM;
	private Button btSalvar;
	
	private PreferenciasDAO prefsDAO;
	
	private boolean blLembretePeso, blLembreteBPM;
	private String stHoraLembretePeso, stHoraLembreteBPM;
	private Calendar calHoraLembretePeso, calHoraLembreteBPM;
	private String[] arrHoraLembretePeso, arrHoraLembreteBPM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_cadastro_preferencias);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		sessao = new GerenciamentoSessao(getApplicationContext());
		
		chLembretePeso = (CheckBox) findViewById(R.id.chLembretePeso);
		etHoraLembretePeso = (EditText) findViewById(R.id.horaLembretePeso);
		
		chLembreteBPM = (CheckBox) findViewById(R.id.chLembreteBPM);
		etHoraLembreteBPM = (EditText) findViewById(R.id.horaLembreteBPM);
		
		btSalvar = (Button) findViewById(R.id.btSalvar);
		
		prefsDAO = new PreferenciasDAO();
				
		etHoraLembretePeso.setOnClickListener(new OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new FragmentoTimePicker((EditText) v).show(getFragmentManager(), "timePicker");
			}
			
		});
		
		etHoraLembreteBPM.setOnClickListener(new OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new FragmentoTimePicker((EditText) v).show(getFragmentManager(), "timePicker");
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
					marcarNotificacaoPeso(calHoraLembretePeso.getTimeInMillis());
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
					marcarNotificacaoBPM(calHoraLembreteBPM.getTimeInMillis());
				}
				else {
					stHoraLembreteBPM = "00:00";
				}
				
				boolean prefs = prefsDAO.cadastrarPreferencias(new Preferencias(
						0,
						sessao.getIdUsuario(),
						blLembretePeso,
						stHoraLembretePeso,
						blLembreteBPM,
						stHoraLembreteBPM
						));
				
				if (prefs) {
					Toast.makeText(getApplicationContext(), getString(R.string.toastPrefsOK), Toast.LENGTH_LONG).show();
					if (blLembretePeso) {
						marcarNotificacaoPeso(calHoraLembretePeso.getTimeInMillis());
					}
					if (blLembreteBPM) {
						marcarNotificacaoBPM(calHoraLembreteBPM.getTimeInMillis());
					}
					Intent irTelaPrincipal = new Intent(getApplicationContext(), TelaPrincipal.class);
					startActivity(irTelaPrincipal);
				}
				else {
					Toast.makeText(getApplicationContext(), getString(R.string.toastPrefsFalha), Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}
	
	public void marcarNotificacaoPeso (long horaLembrete) {
		 
        Intent notificacaoIntent = new Intent(this, NotificacaoReceiver.class);
        notificacaoIntent.putExtra("ID_NOTIFICACAO", 1);
        notificacaoIntent.putExtra("ttNotificacao", getString(R.string.ttNotificacaoPeso));
        notificacaoIntent.putExtra("txNotificacao", getString(R.string.txNotificacaoPeso));
        PendingIntent pendingNotificacaoPesoIntent = PendingIntent.getBroadcast(this, 0, notificacaoIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, horaLembrete, TimeUnit.DAYS.toMillis(1), pendingNotificacaoPesoIntent);
        
	}
    
	public void marcarNotificacaoBPM (long horaLembrete) {
		 
        Intent notificacaoIntent = new Intent(this, NotificacaoReceiver.class);
        notificacaoIntent.putExtra("ID_NOTIFICACAO", 2);
        notificacaoIntent.putExtra("ttNotificacao", getString(R.string.ttNotificacaoBPM));
        notificacaoIntent.putExtra("txNotificacao", getString(R.string.txNotificacaoBPM));
        PendingIntent pendingNotificacaoBPMIntent = PendingIntent.getBroadcast(this, 0, notificacaoIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        AlarmManager alarmManager = (AlarmManager) getSystemService (Context.ALARM_SERVICE);
        alarmManager.setRepeating (AlarmManager.RTC_WAKEUP, horaLembrete, TimeUnit.DAYS.toMillis(1), pendingNotificacaoBPMIntent);
                
	}

}
