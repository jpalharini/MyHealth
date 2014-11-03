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
import android.palharini.myhealth.sessao.GerenciamentoSessao;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class TelaCadastroPreferencias extends Activity {
	
	private boolean lembretePeso, lembreteBPM;
	private String horaLembretePesoString, horaLembreteBPMString;
	private long horaLembretePesoMillis, horaLembreteBPMMillis;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_cadastro_preferencias);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		final CheckBox checkLembretePeso = (CheckBox) findViewById(R.id.checkLembretePeso);
		final EditText horaLembretePeso = (EditText) findViewById(R.id.horaLembretePeso);
		
		final CheckBox checkLembreteBPM = (CheckBox) findViewById(R.id.checkLembreteBPM);
		final EditText horaLembreteBPM = (EditText) findViewById(R.id.horaLembreteBPM);
		
		final Button buttonSalvar = (Button) findViewById(R.id.buttonSalvar);
		
		final Timestamp ts = new Timestamp();
		final GerenciamentoSessao sessao = new GerenciamentoSessao(getApplicationContext());
		
		final PreferenciasDAO prefsdao = new PreferenciasDAO();
		
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
				// TODO Auto-generated method stub {
				
				boolean prefs = prefsdao.cadastrarPreferencias(new Preferencias(
						0,
						sessao.getIdUsuario(),
						lembretePeso,
						horaLembretePesoString,
						lembreteBPM,
						horaLembreteBPMString
						));
				
				if (prefs) {
					Toast.makeText(getApplicationContext(), getString(R.string.toastPrefsOK), Toast.LENGTH_LONG).show();
					if (lembretePeso) {
						marcarNotificacaoPeso(horaLembretePesoMillis);
					}
					if (lembreteBPM) {
						marcarNotificacaoBPM(horaLembreteBPMMillis);
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

}
