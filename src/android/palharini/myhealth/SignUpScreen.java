package android.palharini.myhealth;

import android.palharini.myhealth.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.palharini.myhealth.entity.Usuario;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpScreen extends Activity {

	Usuario usuario;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up_screen);
		
		final TextView bemVindo = (TextView) findViewById(R.id.welcomeText);
		final EditText nome = (EditText) findViewById(R.id.editNome);
		final EditText nasc = (EditText) findViewById(R.id.editNasc);
		final EditText altura = (EditText) findViewById(R.id.editAltura);
		final EditText peso = (EditText) findViewById(R.id.editPeso);
		
		Button okButton = (Button) findViewById(R.id.okButton);
		
		okButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick (View v){
				
			}
		});
	}
}