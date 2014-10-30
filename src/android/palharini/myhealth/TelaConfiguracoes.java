package android.palharini.myhealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.palharini.myhealth.daos.PreferenciasDAO;
import android.palharini.myhealth.entidades.Preferencias;
import android.palharini.myhealth.fragmentos.FragmentoTimePicker;
import android.palharini.myhealth.sessao.CaixaDialogo;
import android.palharini.myhealth.sessao.GerenciamentoSessao;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class TelaConfiguracoes extends Activity {

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
							checkLembretePeso.isChecked(),
							horaLembretePeso.getText().toString(),
							checkLembreteBPM.isChecked(),
							horaLembreteBPM.getText().toString());
					
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
}
