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
import android.palharini.myhealth.sessao.CaixaDialogo;
import android.palharini.myhealth.sessao.GerenciamentoSessao;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class TelaEdicaoPreferencias extends Activity {

	private CaixaDialogo caixa;
	private GerenciamentoSessao sessao;
	
	private PreferenciasDAO prefsDAO;
	private Preferencias prefs;
	
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
		setContentView(R.layout.activity_tela_configuracoes);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		chLembretePeso = (CheckBox) findViewById(R.id.chLembretePeso);
		etHoraLembretePeso = (EditText) findViewById(R.id.horaLembretePeso);
		
		chLembreteBPM = (CheckBox) findViewById(R.id.chLembreteBPM);
		etHoraLembreteBPM = (EditText) findViewById(R.id.horaLembreteBPM);
		
		btSalvar = (Button) findViewById(R.id.btSalvar);
		btLogout = (Button) findViewById(R.id.btLogout);
		
        caixa = new CaixaDialogo();
		
        sessao = new GerenciamentoSessao(getApplicationContext());
		
		prefsDAO = new PreferenciasDAO();
		prefs = prefsDAO.buscarPreferencias(sessao.getIdUsuario());
		
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
				new FragmentoTimePicker((EditText) v).show(getFragmentManager(), "timePicker");
			}
			
		});
		
		etHoraLembreteBPM.setOnClickListener(new EditText.OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new FragmentoTimePicker((EditText) v).show(getFragmentManager(), "timePicker");
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
				
				Preferencias prefsAtual = new Preferencias(
						prefs.getId(),
						sessao.getIdUsuario(),
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
					Intent voltarTelaPrincipal = new Intent(getApplicationContext(), TelaPrincipal.class);
					startActivity(voltarTelaPrincipal);
					finish();
				}
				else {
					Toast.makeText(getApplicationContext(), getString(R.string.toastPrefsFalha), Toast.LENGTH_LONG).show();
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
				Intent voltarTelaLogin = new Intent(getApplicationContext(), TelaLogin.class);
				startActivity(voltarTelaLogin);
			}
			
		});
		
	}
	
	public PendingIntent marcarNotificacaoPeso (long horaLembrete) {
		 
        Intent notificacaoIntent = new Intent(this, NotificacaoReceiver.class);
        notificacaoIntent.putExtra("ID_NOTIFICACAO", 1);
        notificacaoIntent.putExtra("ttNotificacao", getString(R.string.ttNotificacaoPeso));
        notificacaoIntent.putExtra("txNotificacao", getString(R.string.txNotificacaoPeso));
        PendingIntent pendingNotificacaoPesoIntent = PendingIntent.getBroadcast(this, 0, notificacaoIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, horaLembrete, TimeUnit.DAYS.toMillis(1), pendingNotificacaoPesoIntent);
        
        return pendingNotificacaoPesoIntent;

	}
    

	public PendingIntent marcarNotificacaoBPM (long horaLembrete) {
		 
        Intent notificacaoIntent = new Intent(this, NotificacaoReceiver.class);
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
