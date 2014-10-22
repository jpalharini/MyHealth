package android.palharini.myhealth;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.palharini.myhealth.daos.UsuarioDAO;
import android.palharini.myhealth.datas.FormatoDataNascimento;
import android.palharini.myhealth.entidades.Usuario;
import android.palharini.myhealth.fragmentos.FragmentoDatePicker;
import android.palharini.myhealth.sessao.GerenciamentoSessao;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TelaEdicaoUsuario extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_edicao_usuario);
		
		final GerenciamentoSessao sessao = new GerenciamentoSessao(getApplicationContext());
		
		final EditText email = (EditText) findViewById(R.id.editEmail);
		final EditText senha = (EditText) findViewById(R.id.editSenha);
		final EditText confSenha = (EditText) findViewById(R.id.editConfSenha);
		final EditText nome = (EditText) findViewById(R.id.editNome);
		final EditText dataNasc = (EditText) findViewById(R.id.editNasc);
		final EditText alvoBPM = (EditText) findViewById(R.id.editAlvoBPM);
		
		Button buttonSalvar = (Button) findViewById(R.id.buttonSalvar);
		
		final UsuarioDAO dao = new UsuarioDAO();
		final Usuario dados = dao.buscarUsuario(sessao.getIdUsuario());
		
		email.setText(dados.getEmail());
		nome.setText(dados.getNome());
		
		FormatoDataNascimento fdn = new FormatoDataNascimento();
		final String dataNascAndroid = fdn.formatarDataAndroid(dados.getDataNascimento());
		dataNasc.setText(dataNascAndroid);
		
		alvoBPM.setText(String.valueOf(dados.getAlvoBPM()));
				
		dataNasc.setOnClickListener(new OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new FragmentoDatePicker((EditText) v).show(getFragmentManager(), "datePicker");
			}
			
		});
		
		buttonSalvar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v){
				
				if (
						!(email.getText().toString()).equals(dados.getEmail()) || 
						!(nome.getText().toString()).equals(dados.getNome()) || 
						!(dataNasc.getText().toString()).equals(dataNascAndroid) ||
						!(alvoBPM.getText().toString()).equals(String.valueOf(dados.getAlvoBPM()))
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
						
						FormatoDataNascimento fdn = new FormatoDataNascimento();
						final String dataNascSQL = fdn.formatarDataSQL(dataNasc.getText().toString());

						dao.atualizarUsuario(new Usuario(
								dados.getId(), 
								email.getText().toString(),
								criptSenha,
								nome.getText().toString(), 
								dataNascSQL,
								Integer.parseInt(alvoBPM.getText().toString())
						));
						
						Intent voltarTelaPrincipal = new Intent(getApplicationContext(), TelaPrincipal.class);
	                    startActivity(voltarTelaPrincipal);
	                    finish();
					}
				}
				else {
					Intent voltarTelaPrincipal = new Intent(getApplicationContext(), TelaPrincipal.class);
					Toast.makeText(getApplicationContext(), "Nao houve alteracao", Toast.LENGTH_LONG).show();
                    startActivity(voltarTelaPrincipal);
                    finish();
				}
			}
		});
		
	}
}
