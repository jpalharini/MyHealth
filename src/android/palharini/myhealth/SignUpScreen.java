package android.palharini.myhealth;

import android.palharini.myhealth.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.palharini.myhealth.dao.UsuarioDAO;
import android.palharini.myhealth.entity.Usuario;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpScreen extends Activity {

	Usuario usuario;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up_screen);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		final EditText nome = (EditText) findViewById(R.id.editNome);
		final EditText nasc = (EditText) findViewById(R.id.editNasc);
		final EditText altura = (EditText) findViewById(R.id.editAltura);
		final EditText peso = (EditText) findViewById(R.id.editPeso);
		final EditText maxBPM = (EditText) findViewById(R.id.editMaxBPM);
		final EditText minBPM = (EditText) findViewById(R.id.editMinBPM);
		
		Button okButton = (Button) findViewById(R.id.okButton);
		
		okButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick (View v){
				String nomeString = nome.getText().toString();
				String nascString = nasc.getText().toString();
				String alturaString = altura.getText().toString();
				final double alturaDouble = Double.parseDouble(alturaString);
				String pesoString = peso.getText().toString();
				final double pesoDouble = Double.parseDouble(pesoString);
				String maxBPMString = maxBPM.getText().toString();
				String minBPMString = minBPM.getText().toString();
				
				if (maxBPMString != null || minBPMString != null) {
					final int maxBPMInt = Integer.parseInt(maxBPMString);
					final int minBPMInt = Integer.parseInt(minBPMString);
					
					UsuarioDAO dao = new UsuarioDAO();
					boolean resultado = dao.cadastrarUsuario(new Usuario(
							0, nomeString, nascString, alturaDouble, pesoDouble, maxBPMInt, minBPMInt));
				}
				
				else {
					UsuarioDAO dao = new UsuarioDAO();
					boolean resultado = dao.cadastrarUsuario(new Usuario(
							0, nomeString, nascString, alturaDouble, pesoDouble));
				}
			}
		});
	}
}