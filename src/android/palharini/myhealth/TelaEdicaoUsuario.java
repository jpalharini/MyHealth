package android.palharini.myhealth;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.palharini.myhealth.dao.UsuarioDAO;
import android.palharini.myhealth.datas.FormatoDataNascimento;
import android.palharini.myhealth.datas.FragmentoDatePicker;
import android.palharini.myhealth.entidades.Usuario;
import android.palharini.myhealth.sessao.GerenciamentoSessao;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TelaEdicaoUsuario extends Activity {

	private GerenciamentoSessao sessao;
	private FormatoDataNascimento fdn;
	
	private EditText etEmail, etSenha, etConfSenha, etNome, etDataNasc, etAlvoBPM;
	private Button btSalvar;	
	
	private String stEmail, stNome, stSenha, stConfSenha, stCriptSenha, stDataNasc, stDataNascSQL;
	private Boolean blUsr;
	
	private UsuarioDAO usrDAO;
	private Usuario usrUsuario;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_edicao_usuario);
		
		sessao = new GerenciamentoSessao(getApplicationContext());
		
		usrDAO = new UsuarioDAO();
		usrUsuario = usrDAO.buscarUsuario(sessao.getIdUsuario());
		
		fdn = new FormatoDataNascimento();
		
		etEmail = (EditText) findViewById(R.id.etEmail);
		etSenha = (EditText) findViewById(R.id.etSenha);
		etConfSenha = (EditText) findViewById(R.id.etConfSenha);
		etNome = (EditText) findViewById(R.id.etNome);
		etDataNasc = (EditText) findViewById(R.id.etNasc);
		etAlvoBPM = (EditText) findViewById(R.id.etAlvoBPM);
		btSalvar = (Button) findViewById(R.id.btSalvar);
		
		etEmail.setText(usrUsuario.getEmail());
		etNome.setText(usrUsuario.getNome());

		stDataNasc = fdn.formatarDataAndroid(usrUsuario.getDataNascimento());
		etDataNasc.setText(stDataNasc);
		
		etAlvoBPM.setText(String.valueOf(usrUsuario.getAlvoBPM()));
				
		etDataNasc.setOnClickListener(new OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new FragmentoDatePicker((EditText) v).show(getFragmentManager(), "datePicker");
			}
			
		});
		
		btSalvar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v){
				
				if (
						!(etEmail.getText().toString()).equals(usrUsuario.getEmail()) || 
						!(etNome.getText().toString()).equals(usrUsuario.getNome()) || 
						!(etDataNasc.getText().toString()).equals(stDataNasc) ||
						!(etAlvoBPM.getText().toString()).equals(String.valueOf(usrUsuario.getAlvoBPM()))
						) {
			
					stSenha = etSenha.getText().toString();
					stConfSenha = etConfSenha.getText().toString();
					stCriptSenha = null;
					
					if (stSenha.equals(stConfSenha)){
						try {
							MessageDigest md = MessageDigest.getInstance("MD5");
							md.update(stSenha.getBytes("UTF-8"));
							BigInteger hash = new BigInteger(1, md.digest());
							stCriptSenha = hash.toString(16);
						} catch (NoSuchAlgorithmException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						fdn = new FormatoDataNascimento();
						stDataNascSQL = fdn.formatarDataSQL(etDataNasc.getText().toString());

						stEmail = etEmail.getText().toString();
						stNome = etNome.getText().toString();
						
						blUsr = usrDAO.atualizarUsuario(new Usuario(
								usrUsuario.getId(), 
								stEmail,
								stCriptSenha,
								stNome, 
								stDataNascSQL,
								Integer.parseInt(etAlvoBPM.getText().toString())
						));
						if (blUsr) {
							Intent voltarTelaPrincipal = new Intent(getApplicationContext(), TelaPrincipal.class);
							Toast.makeText(getApplicationContext(), getString(R.string.toastUsrAtOK), Toast.LENGTH_LONG).show();
		                    startActivity(voltarTelaPrincipal);
		                    finish();
						}
						else {
							Toast.makeText(getApplicationContext(), getString(R.string.toastUsrAtFalha), Toast.LENGTH_LONG).show();
						}
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
