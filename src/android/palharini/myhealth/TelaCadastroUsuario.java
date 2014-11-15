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
import android.palharini.myhealth.dao.IndicadorDAO;
import android.palharini.myhealth.dao.UsuarioDAO;
import android.palharini.myhealth.datas.FormatoDataNascimento;
import android.palharini.myhealth.datas.FragmentoDatePicker;
import android.palharini.myhealth.datas.Timestamp;
import android.palharini.myhealth.entidades.Indicador;
import android.palharini.myhealth.entidades.Usuario;
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
	
	private EditText etEmail, etSenha, etConfSenha, etNome, etDataNasc, etAltura, etPeso;
	private Button btContinuar;	
	
	private String stEmail, stNome, stSenha, stConfSenha, stCriptSenha, stDataNasc, stDataNascSQL;
	private String[] arrUnidades;
	private List<String> lsUnidades;
	private Double dbAltura, dbPeso;
	private Boolean blUsr, blIndAltura, blIndPeso;
	
	private Usuario usrUsuario;
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
		usrDAO = new UsuarioDAO();
		indDAO = new IndicadorDAO();
		fdn = new FormatoDataNascimento();
		sessao = new GerenciamentoSessao(getApplicationContext());
		
		etEmail = (EditText) findViewById(R.id.etEmail);
		etSenha = (EditText) findViewById(R.id.etSenha);
		etConfSenha = (EditText) findViewById(R.id.etConfSenha);
		etNome = (EditText) findViewById(R.id.etNome);
		etDataNasc = (EditText) findViewById(R.id.etNasc);
		etAltura = (EditText) findViewById(R.id.etAltura);
		etPeso = (EditText) findViewById(R.id.etPeso);
		
		btContinuar = (Button) findViewById(R.id.btContinuar);
		
		etDataNasc.setOnClickListener(new EditText.OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new FragmentoDatePicker((EditText) v).show(getFragmentManager(), "datePicker");
			}
			
		});
		
		btContinuar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v){
				
				stEmail = etEmail.getText().toString();
				stNome = etNome.getText().toString();
				dbAltura = Double.parseDouble(etAltura.getText().toString());
				dbPeso = Double.parseDouble(etPeso.getText().toString());

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
					
					stDataNasc = etDataNasc.getText().toString();
					stDataNascSQL = fdn.formatarDataSQL(stDataNasc);
					
					usrDAO = new UsuarioDAO();
					blUsr = usrDAO.cadastrarUsuario(new Usuario(
							0, 
							stEmail,
							stCriptSenha,
							stNome, 
							stDataNascSQL
							));
					
					usrUsuario = usrDAO.buscarUsuarioEmail(stEmail);
					
					arrUnidades = getResources().getStringArray(R.array.lsUnidades);
					lsUnidades = Arrays.asList(arrUnidades);
					
					blIndAltura = indDAO.cadastrarIndicador(new Indicador(
							0,
							0,
							usrUsuario.getId(),
							dbAltura,
							0.0,
							lsUnidades.get(0),
							ts.getDataSQL(),
							ts.getHorarioSQL()
							));
					
					blIndPeso = indDAO.cadastrarIndicador(new Indicador(
							0,
							1,
							usrUsuario.getId(),
							dbPeso,
							0.0,
							lsUnidades.get(1),
							ts.getDataSQL(),
							ts.getHorarioSQL()
							));
					
					if (blUsr && blIndAltura && blIndPeso) {
						sessao.criarSessao(usrUsuario.getId(), usrUsuario.getNome(), usrUsuario.getEmail());
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
					Toast.makeText(getApplicationContext(), getString(R.string.toastSenhaInv), Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}