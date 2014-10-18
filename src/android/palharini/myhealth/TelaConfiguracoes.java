package android.palharini.myhealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.palharini.myhealth.fragmentos.FragmentoTimePicker;
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
		
		final CheckBox checkLembretePeso = (CheckBox) findViewById(R.id.checkLembretePeso);
		final EditText horaLembretePeso = (EditText) findViewById(R.id.horaLembretePeso);
		
		final CheckBox checkLembreteBPM = (CheckBox) findViewById(R.id.checkLembreteBPM);
		final EditText horaLembreteBPM = (EditText) findViewById(R.id.horaLembreteBPM);
		
		final Button buttonLogout = (Button) findViewById(R.id.buttonLogout);
		
		final GerenciamentoSessao sessao = new GerenciamentoSessao(getApplicationContext());
		
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
