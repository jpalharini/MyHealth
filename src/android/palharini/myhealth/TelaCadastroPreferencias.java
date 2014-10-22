package android.palharini.myhealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.palharini.myhealth.daos.PreferenciasDAO;
import android.palharini.myhealth.entidades.Preferencias;
import android.palharini.myhealth.fragmentos.FragmentoTimePicker;
import android.palharini.myhealth.sessao.GerenciamentoSessao;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class TelaCadastroPreferencias extends Activity {

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
		
		buttonSalvar.setOnClickListener(new Button.OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub {
					
				boolean prefs = prefsdao.cadastrarPreferencias(new Preferencias(
						sessao.getIdUsuario(),
						checkLembretePeso.isChecked(),
						horaLembretePeso.getText().toString(),
						checkLembreteBPM.isChecked(),
						horaLembreteBPM.getText().toString()
						));
				
				if (prefs) {
					Toast.makeText(getApplicationContext(), getString(R.string.toastUsrOK), Toast.LENGTH_LONG).show();
					Intent irTelaPrincipal = new Intent(getApplicationContext(), TelaPrincipal.class);
					startActivity(irTelaPrincipal);
				}
				else {
					
				}
			}
		});
		
	}
}
