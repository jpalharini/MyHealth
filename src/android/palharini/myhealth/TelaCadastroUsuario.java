package android.palharini.myhealth;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.palharini.myhealth.R;
import android.palharini.myhealth.daos.UsuarioDAO;
import android.palharini.myhealth.entidades.Usuario;
import android.palharini.myhealth.fragmentos.FragmentoDatePicker;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TelaCadastroUsuario extends Activity {

	Usuario usuario;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_cadastro);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		// Campos de entrada
		final EditText email = (EditText) findViewById(R.id.editEmail);
		final EditText senha = (EditText) findViewById(R.id.editSenha);
		final EditText confSenha = (EditText) findViewById(R.id.editConfSenha);
		final EditText nome = (EditText) findViewById(R.id.editNome);
		final EditText dataNasc = (EditText) findViewById(R.id.editNasc);
		final EditText altura = (EditText) findViewById(R.id.editAltura);
		final EditText peso = (EditText) findViewById(R.id.editPeso);
		final EditText maxBPM = (EditText) findViewById(R.id.editMaxBPM);
		final EditText minBPM = (EditText) findViewById(R.id.editMinBPM);
		
		Button okButton = (Button) findViewById(R.id.okButton);
		
		dataNasc.setOnClickListener(new EditText.OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new FragmentoDatePicker((EditText) v).show(getFragmentManager(), "datePicker");
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
					try {
						MessageDigest md = MessageDigest.getInstance("MD5");
						md.update(senhaString.getBytes("UTF-8"));
						BigInteger hash = new BigInteger(1, md.digest());
						criptSenha = hash.toString(16);
					} catch (NoSuchAlgorithmException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					String dataNascString = dataNasc.getText().toString();
					final String formatoData = "d/MM/yyyy";
					final String formatoDataSQL = "yyyy-MM-dd";
					SimpleDateFormat sdf = new SimpleDateFormat(formatoData);
					SimpleDateFormat sdfSQL = new SimpleDateFormat(formatoDataSQL);
					Date dataNascDate = null;
					try {
						dataNascDate = sdf.parse(dataNascString);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String dataNascSQL = sdfSQL.format(dataNascDate);
					
					UsuarioDAO dao = new UsuarioDAO();
					dao.cadastrarUsuario(new Usuario(
							0, 
							email.getText().toString(),
							criptSenha,
							nome.getText().toString(), 
							dataNascSQL,
							Double.parseDouble(altura.getText().toString()),
							Double.parseDouble(peso.getText().toString()),
							Integer.parseInt(maxBPM.getText().toString()),
							Integer.parseInt(minBPM.getText().toString())
					));
				}
			}
		});
	}
}