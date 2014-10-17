package android.palharini.myhealth;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.palharini.myhealth.daos.UsuarioDAO;
import android.palharini.myhealth.entidades.Usuario;
import android.palharini.myhealth.fragmentos.FragmentoDatePicker;
import android.palharini.myhealth.sessao.GerenciamentoSessao;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TelaEdicaoUsuario extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_edicao_usuario);
		
		GerenciamentoSessao sessao = new GerenciamentoSessao(getApplicationContext());
		
		final EditText email = (EditText) findViewById(R.id.editEmail);
		final EditText senha = (EditText) findViewById(R.id.editSenha);
		final EditText confSenha = (EditText) findViewById(R.id.editConfSenha);
		final EditText nome = (EditText) findViewById(R.id.editNome);
		final EditText dataNasc = (EditText) findViewById(R.id.editNasc);
		final EditText altura = (EditText) findViewById(R.id.editAltura);
		final EditText alvoBPM = (EditText) findViewById(R.id.editAlvoBPM);
		
		Button okButton = (Button) findViewById(R.id.okButton);
		
		UsuarioDAO dao = new UsuarioDAO();
		final Usuario dados = dao.buscarUsuario(sessao.getIdUsuario());
		
		email.setText(dados.getEmail());
		nome.setText(dados.getNome());
		dataNasc.setText(dados.getDataNascimento());
		altura.setText(String.valueOf(dados.getAltura()));
		alvoBPM.setText(String.valueOf(dados.getAlvoBPM()));
				
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
				
				if (
						!email.equals(dados.getEmail()) || 
						!nome.equals(dados.getNome()) || 
						!dataNasc.equals(dados.getDataNascimento()) ||
						!altura.equals(String.valueOf(dados.getAltura())) ||
						!alvoBPM.equals(String.valueOf(dados.getAlvoBPM()))
						) {
			
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
						dao.atualizarUsuario(new Usuario(
								dados.getId(), 
								email.getText().toString(),
								criptSenha,
								nome.getText().toString(), 
								dataNascSQL,
								Double.parseDouble(altura.getText().toString()),
								Integer.parseInt(alvoBPM.getText().toString())
						));
					}
				}
				else {
					Intent voltarTelaPrincipal = new Intent(getApplicationContext(), TelaAcompanhamento.class);
                    startActivity(voltarTelaPrincipal);
                    finish();
				}
			}
		});
		
	}
}
