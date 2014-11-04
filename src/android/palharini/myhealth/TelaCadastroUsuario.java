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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TelaCadastroUsuario extends Activity {

	private GerenciamentoSessao sessao;
	private FormatoDataNascimento fdn;
	private Timestamp ts;
	
	private EditText email, senha, confSenha, nome, dataNasc, altura, peso;
	private Button buttonContinuar;	
	
	private String emailString, nomeString, senhaString, confSenhaString, criptSenha, dataNascString, dataNascSQL;
	private String[] listUnidades;
	private List<String> unidades;
	private Double alturaDouble, pesoDouble;
	private Boolean usr, indAltura, indPeso;
	
	private Usuario usuarioOK, usuarioCheck;
	private UsuarioDAO usrDAO;
	private IndicadorDAO indDAO;
	
	private Intent irTelaCadastroPreferencias;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		setContentView(R.layout.activity_tela_cadastro_usuario);
		
		ts = new Timestamp();
		indDAO = new IndicadorDAO();
		fdn = new FormatoDataNascimento();
		sessao = new GerenciamentoSessao(getApplicationContext());
		
		email = (EditText) findViewById(R.id.editEmail);
		senha = (EditText) findViewById(R.id.editSenha);
		confSenha = (EditText) findViewById(R.id.editConfSenha);
		nome = (EditText) findViewById(R.id.editNome);
		dataNasc = (EditText) findViewById(R.id.editNasc);
		altura = (EditText) findViewById(R.id.editAltura);
		peso = (EditText) findViewById(R.id.editPeso);
		
		buttonContinuar = (Button) findViewById(R.id.buttonContinuar);
		
		dataNasc.setOnClickListener(new EditText.OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new FragmentoDatePicker((EditText) v).show(getFragmentManager(), "datePicker");
			}
			
		});
		
		buttonContinuar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v){
				
				emailString = email.getText().toString();
				nomeString = nome.getText().toString();
				alturaDouble = Double.parseDouble(altura.getText().toString());
				pesoDouble = Double.parseDouble(peso.getText().toString());

				senhaString = senha.getText().toString();
				confSenhaString = confSenha.getText().toString();
				criptSenha = null;
				
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
					
					dataNascString = dataNasc.getText().toString();
					dataNascSQL = fdn.formatarDataSQL(dataNascString);
					
					usuarioCheck = usrDAO.buscarUsuarioEmail(emailString);
					
					if (usuarioCheck == null) {
						usrDAO = new UsuarioDAO();
						usr = usrDAO.cadastrarUsuario(new Usuario(
								0, 
								emailString,
								criptSenha,
								nomeString, 
								dataNascSQL
								));
						
						usuarioOK = usrDAO.buscarUsuarioEmail(emailString);
						
						listUnidades = getResources().getStringArray(R.array.listaUnidades);
						unidades = Arrays.asList(listUnidades);
						
						indAltura = indDAO.cadastrarIndicador(new Indicador(
								0,
								0,
								usuarioOK.getId(),
								alturaDouble,
								unidades.get(0),
								ts.getDataSQL(),
								ts.getHorarioSQL()
								));
						
						indPeso = indDAO.cadastrarIndicador(new Indicador(
								0,
								1,
								usuarioOK.getId(),
								pesoDouble,
								unidades.get(1),
								ts.getDataSQL(),
								ts.getHorarioSQL()
								));
						
						if (usr && indAltura && indPeso) {
							sessao.criarSessao(usuarioOK.getId(), usuarioOK.getNome(), usuarioOK.getEmail());
							Toast.makeText(getApplicationContext(), getString(R.string.toastUsrOK), Toast.LENGTH_LONG).show();
							irTelaCadastroPreferencias = new Intent(getApplicationContext(), TelaCadastroPreferencias.class);
							startActivity(irTelaCadastroPreferencias);
							finish();
						}
						else {
							Toast.makeText(getApplicationContext(), getString(R.string.toastUsrFalha), Toast.LENGTH_LONG).show();
						}
					}
					else {
						Toast.makeText(getApplicationContext(), getString(R.string.toastUsrExiste), Toast.LENGTH_LONG).show();
					}
					
						
				}
				else {
					Toast.makeText(getApplicationContext(), getString(R.string.toastSenhaInv), Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}