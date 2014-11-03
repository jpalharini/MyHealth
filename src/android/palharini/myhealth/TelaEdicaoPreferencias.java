package android.palharini.myhealth;

import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.palharini.myhealth.daos.PreferenciasDAO;
import android.palharini.myhealth.datas.Timestamp;
import android.palharini.myhealth.entidades.Preferencias;
import android.palharini.myhealth.fragmentos.FragmentoTimePicker;
import android.palharini.myhealth.notificacoes.ServicoNotificacao;
import android.palharini.myhealth.sessao.CaixaDialogo;
import android.palharini.myhealth.sessao.GerenciamentoSessao;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class TelaEdicaoPreferencias extends Activity {

	private boolean lembretePeso, lembreteBPM;
	private String horaLembretePesoString, horaLembreteBPMString;
	private long horaLembretePesoMillis, horaLembreteBPMMillis;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_configuracoes);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		final CheckBox checkLembretePeso = (CheckBox) findViewById(R.id.checkLembretePeso);
		final EditText horaLembretePeso = (EditText) findViewById(R.id.horaLembretePeso);
		
		final CheckBox checkLembreteBPM = (CheckBox) findViewById(R.id.checkLembreteBPM);
		final EditText horaLembreteBPM = (EditText) findViewById(R.id.horaLembreteBPM);
		
		final Button buttonSalvar = (Button) findViewById(R.id.buttonSalvar);
		final Button buttonLogout = (Button) findViewById(R.id.buttonLogout);
		
        final CaixaDialogo caixa = new CaixaDialogo();
		
		final Timestamp ts = new Timestamp();
		final GerenciamentoSessao sessao = new GerenciamentoSessao(getApplicationContext());
		
		final PreferenciasDAO prefsdao = new PreferenciasDAO();
		final Preferencias prefs = prefsdao.buscarPreferencias(sessao.getIdUsuario());
		
		if (prefs != null) {
			if (prefs.isLembretePeso()) {
				checkLembretePeso.setChecked(true);
				horaLembretePeso.setText(prefs.getHoraLembretePeso().toString());
			}
			
			if (prefs.isLembreteBPM()) {
				checkLembreteBPM.setChecked(true);
				horaLembreteBPM.setText(prefs.getHoraLembreteBPM().toString());
			}
		}
		else {
			caixa.showAlertDialog(
        			this, 
        			getString(R.string.textFalhaConexao), 
        			getString(R.string.textServidorNaoResponde), 
        			false);
		}
		
		horaLembretePeso.setOnClickListener(new EditText.OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new FragmentoTimePicker((EditText) v).show(getFragmentManager(), "timePicker");
			}
			
		});
		
		horaLembreteBPM.setOnClickListener(new EditText.OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new FragmentoTimePicker((EditText) v).show(getFragmentManager(), "timePicker");
			}
			
		});
		
		
		lembretePeso = checkLembretePeso.isChecked();
		lembreteBPM = checkLembreteBPM.isChecked();
		
		horaLembretePesoString = horaLembretePeso.getText().toString();
		horaLembreteBPMString = horaLembreteBPM.getText().toString();
		
		horaLembretePesoMillis = ts.getHorarioMillis(horaLembretePeso.getText().toString());
		horaLembreteBPMMillis = ts.getHorarioMillis(horaLembreteBPM.getText().toString());
		
		buttonSalvar.setOnClickListener(new Button.OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (
						checkLembretePeso.isChecked() != prefs.isLembretePeso() ||
						!horaLembretePeso.getText().toString().equals(prefs.getHoraLembretePeso()) ||
						checkLembreteBPM.isChecked() != prefs.isLembreteBPM() || 
						!horaLembreteBPM.getText().toString().equals(prefs.getHoraLembreteBPM())) {
					
					Preferencias prefsAtual = new Preferencias(
							prefs.getId(),
							sessao.getIdUsuario(),
							lembretePeso,
							horaLembretePesoString,
							lembreteBPM,
							horaLembreteBPMString
							);
					
					if (checkLembretePeso.isChecked() != prefs.isLembretePeso()) {
						PendingIntent notificacaoIntent = null;
						if (lembretePeso) {
							notificacaoIntent = marcarNotificacaoPeso(horaLembretePesoMillis);
						}
						else {
							cancelarNotificacao(notificacaoIntent);
						}
						if (lembreteBPM) {
							notificacaoIntent = marcarNotificacaoBPM(horaLembreteBPMMillis);
						}
						else {
							cancelarNotificacao(notificacaoIntent);
						}
					}
					prefsdao.atualizarPreferencias(prefsAtual);
					
					Intent voltarTelaPrincipal = new Intent(getApplicationContext(), TelaPrincipal.class);
					startActivity(voltarTelaPrincipal);
				}
				else {
					Intent voltarTelaPrincipal = new Intent(getApplicationContext(), TelaPrincipal.class);
					startActivity(voltarTelaPrincipal);
				}
					
			}
			
		});
		
		buttonLogout.setOnClickListener(new Button.OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sessao.logoutUsuario();
				Intent voltarTelaLogin = new Intent(getApplicationContext(), TelaLogin.class);
				startActivity(voltarTelaLogin);
			}
			
		});
		
	}
	
	private PendingIntent marcarNotificacaoPeso (long horaLembrete) {
		 
        Intent notificacaoIntent = new Intent(this, ServicoNotificacao.class);
        notificacaoIntent.putExtra("ID_NOTIFICACAO", 1);
        notificacaoIntent.putExtra("tituloNotificacao", getString(R.string.tituloNotificacaoPeso));
        notificacaoIntent.putExtra("textoNotificacao", getString(R.string.textoNotificacaoPeso));
        PendingIntent pendingNotificacaoPesoIntent = PendingIntent.getBroadcast(this, 0, notificacaoIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, horaLembrete, TimeUnit.DAYS.toMillis(1), pendingNotificacaoPesoIntent);
        
        return pendingNotificacaoPesoIntent;

	}
    

	private PendingIntent marcarNotificacaoBPM (long horaLembrete) {
		 
        Intent notificacaoIntent = new Intent(this, ServicoNotificacao.class);
        notificacaoIntent.putExtra("ID_NOTIFICACAO", 1);
        notificacaoIntent.putExtra("tituloNotificacao", getString(R.string.tituloNotificacaoBPM));
        notificacaoIntent.putExtra("textoNotificacao", getString(R.string.textoNotificacaoBPM));
        PendingIntent pendingNotificacaoBPMIntent = PendingIntent.getBroadcast(this, 0, notificacaoIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, horaLembrete, TimeUnit.DAYS.toMillis(1), pendingNotificacaoBPMIntent);
        
        return pendingNotificacaoBPMIntent;
        
	} 
	
    private void cancelarNotificacao (PendingIntent pendingNotificacaoIntent) {
    	AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    	alarmManager.cancel(pendingNotificacaoIntent);
    }
    
}
