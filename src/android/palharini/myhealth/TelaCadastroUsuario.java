package android.palharini.myhealth;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.palharini.myhealth.daos.IndicadorDAO;
import android.palharini.myhealth.daos.UsuarioDAO;
import android.palharini.myhealth.datas.FormatoDataNascimento;
import android.palharini.myhealth.datas.Timestamp;
import android.palharini.myhealth.entidades.Indicador;
import android.palharini.myhealth.entidades.Usuario;
import android.palharini.myhealth.fragmentos.FragmentoDatePicker;
import android.palharini.myhealth.sessao.GerenciamentoSessao;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TelaCadastroUsuario extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		setContentView(R.layout.activity_tela_cadastro_usuario);
		
		final EditText email = (EditText) findViewById(R.id.editEmail);
		final EditText senha = (EditText) findViewById(R.id.editSenha);
		final EditText confSenha = (EditText) findViewById(R.id.editConfSenha);
		final EditText nome = (EditText) findViewById(R.id.editNome);
		final EditText dataNasc = (EditText) findViewById(R.id.editNasc);
		final EditText altura = (EditText) findViewById(R.id.editAltura);
		final EditText peso = (EditText) findViewById(R.id.editPeso);
		
		Button okButton = (Button) findViewById(R.id.okButton);
		
		final GerenciamentoSessao sessao = new GerenciamentoSessao(getApplicationContext());
		
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
				
				String emailString = email.getText().toString();
				String nomeString = nome.getText().toString();
				Double alturaDouble = Double.parseDouble(altura.getText().toString());
				Double pesoDouble = Double.parseDouble(peso.getText().toString());

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
					FormatoDataNascimento fdn = new FormatoDataNascimento();
					String dataNascSQL = fdn.formatarDataSQL(dataNascString);
					
					UsuarioDAO usrdao = new UsuarioDAO();
					boolean usr = usrdao.cadastrarUsuario(new Usuario(
							0, 
							emailString,
							criptSenha,
							nomeString, 
							dataNascSQL
							));
	                
					Usuario usuario = usrdao.buscarUsuarioEmail(emailString);
					
					String[] listUnidades = getResources().getStringArray(R.array.listaUnidades);
					final List<String> unidades = Arrays.asList(listUnidades);
					
					final Timestamp ts = new Timestamp();
					
					IndicadorDAO inddao = new IndicadorDAO();
					
					boolean indAltura = inddao.cadastrarIndicador(new Indicador(
							0,
							0,
							usuario.getId(),
							alturaDouble,
							unidades.get(0),
							ts.getTimestamp()
							));
					
					boolean indPeso = inddao.cadastrarIndicador(new Indicador(
							0,
							1,
							usuario.getId(),
							pesoDouble,
							unidades.get(1),
							ts.getTimestamp()
							));
					
					if (usr && indAltura && indPeso) {
						sessao.criarSessao(usuario.getId(), usuario.getNome(), usuario.getEmail());
						Toast.makeText(getApplicationContext(), getString(R.string.toastUsrOK), Toast.LENGTH_LONG).show();
						Intent irTelaCadastroPreferencias = new Intent(getApplicationContext(), TelaCadastroPreferencias.class);
						startActivity(irTelaCadastroPreferencias);
						finish();
					}
					else {
						Toast.makeText(getApplicationContext(), getString(R.string.toastUsrFalha), Toast.LENGTH_LONG).show();
					}
						
				}
				
				Intent irTelaPrincipal = new Intent(getApplicationContext(), TelaPrincipal.class);
				startActivity(irTelaPrincipal);
				finish();
			}
		});
	}
}