package android.palharini.myhealth;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.jasypt.util.password.StrongPasswordEncryptor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.palharini.myhealth.dao.UsuarioDAO;
import android.palharini.myhealth.entity.Usuario;
import android.palharini.myjealth.fragment.DatePickerFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TelaCadastro extends Activity {

	Usuario usuario;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up_screen);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		// Campos de entrada
		final EditText login = (EditText) findViewById(R.id.editLogin);
		final EditText senha = (EditText) findViewById(R.id.editSenha);
		final EditText confSenha = (EditText) findViewById(R.id.editConfSenha);
		final EditText nome = (EditText) findViewById(R.id.editNome);
		final EditText nasc = (EditText) findViewById(R.id.editNasc);
		final EditText altura = (EditText) findViewById(R.id.editAltura);
		final EditText peso = (EditText) findViewById(R.id.editPeso);
		final EditText maxBPM = (EditText) findViewById(R.id.editMaxBPM);
		final EditText minBPM = (EditText) findViewById(R.id.editMinBPM);
		
		Button okButton = (Button) findViewById(R.id.okButton);
		
		nasc.setOnClickListener(new EditText.OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerFragment((EditText) v).show(getFragmentManager(), "datePicker");
			}
			
		});
		
		okButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick (View v){
				
				// Cadastro no banco
				String senhaString = senha.getText().toString();
				String confSenhaString = confSenha.getText().toString();
				String criptSenha = null;
				
				if (senhaString.equals(confSenhaString)){
					StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
					criptSenha = passwordEncryptor.encryptPassword(senhaString);
					
					String dataNascString = nasc.getText().toString();
					SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date dataNascDate = null;
					try {
						dataNascDate = formatoData.parse(dataNascString);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					java.sql.Date dataNascDateSQL = new java.sql.Date(dataNascDate.getTime());
					
					UsuarioDAO dao = new UsuarioDAO();
					boolean resultado = dao.cadastrarUsuario(new Usuario(
							0, 
							login.getText().toString(),
							criptSenha,
							nome.getText().toString(), 
							dataNascDateSQL,
							Double.parseDouble(altura.getText().toString()),
							Double.parseDouble(peso.getText().toString()),
							Integer.parseInt(maxBPM.getText().toString()),
							Integer.parseInt(minBPM.getText().toString())
					));
					if (resultado=true){
						Intent intent = new Intent(TelaCadastro.this, TelaPrincipal.class);
					}
						
					else {
						Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}
}