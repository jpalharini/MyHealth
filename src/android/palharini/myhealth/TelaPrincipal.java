package android.palharini.myhealth;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.palharini.myhealth.dao.IndicadorDAO;
import android.palharini.myhealth.dao.UsuarioDAO;
import android.palharini.myhealth.entidades.Indicador;
import android.palharini.myhealth.entidades.Usuario;
import android.palharini.myhealth.sessao.GerenciamentoSessao;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TelaPrincipal extends Activity {

	private GerenciamentoSessao sessao;
	
	private UsuarioDAO usrDAO;
	private Usuario usuario;
	private IndicadorDAO indDAO;
	private Indicador indAltura, indPeso, indicador;
	
	private TextView txOla, txIMC, txStatusIMC, txAlvoBPM;
	private Button btCadIndicador, btAcompanhamento, btDados, btConfiguracoes;
	
	private ArrayList<Indicador> arrIndicadores;
	private List<String> lsFaixasIMC;
	private String stNome, stPrimeiroNome, vtFaixasIMC[];
	private Double dbAltura, dbPeso, dbIMC;
	private Integer intIdade, bpmDescanso, bpmDescansoFinal, bpmDescansoMedia, bpmMaximo,
	bpmReserva, bpmAlvoLimMin, bpmAlvoLimMax, bpmAlvo;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_principal);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		txOla = (TextView) findViewById(R.id.tvOla);
		txIMC = (TextView) findViewById(R.id.IMC);
		txStatusIMC = (TextView) findViewById(R.id.tvStatusIMC);
		txAlvoBPM = (TextView) findViewById(R.id.alvoBPM);
		
		btCadIndicador = (Button) findViewById(R.id.btCadIndicador);
		btAcompanhamento = (Button) findViewById(R.id.btAcompanhamento);
		btDados = (Button) findViewById(R.id.btDados);
		btConfiguracoes = (Button) findViewById(R.id.btConfiguracoes);
		
		sessao = new GerenciamentoSessao(getApplicationContext());
		
		usrDAO = new UsuarioDAO();

		indDAO = new IndicadorDAO();
		
		setNomeUsuario();
		calcularIMC();
		calcularBPMIdeal();
	
		btCadIndicador.setOnClickListener(new Button.OnClickListener () {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent irTelaCadIndicador = new Intent(getApplicationContext(), TelaCadastroIndicador.class);
				startActivity(irTelaCadIndicador);
			}
		});
		
		btAcompanhamento.setOnClickListener(new Button.OnClickListener () {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent irTelaAcompanhamento = new Intent(getApplicationContext(), TelaTipos.class);
				startActivity(irTelaAcompanhamento);
			}
		});
		
		btDados.setOnClickListener(new Button.OnClickListener () {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent irTelaEdicaoUsuario = new Intent(getApplicationContext(), TelaEdicaoUsuario.class);
				startActivity(irTelaEdicaoUsuario);
			}
		});
		
		btConfiguracoes.setOnClickListener(new Button.OnClickListener () {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent irTelaConfiguracoes = new Intent(getApplicationContext(), TelaEdicaoPreferencias.class);
				startActivity(irTelaConfiguracoes);
			}
		});
		
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		setNomeUsuario();
		calcularIMC();
		calcularBPMIdeal();
	}
	
	public void setNomeUsuario () {
		
		usuario = usrDAO.buscarUsuario(sessao.getIdUsuario());
		
		stNome = usuario.getNome();
		
		if (stNome.contains(" ")) {
			stPrimeiroNome = stNome.substring(0, stNome.indexOf(" "));
		}
		else {
			stPrimeiroNome = stNome;
		}
		
		txOla.setText(getString(R.string.tvOla) + " " + stPrimeiroNome);
	}
	
	public void calcularIMC () {
		
		indAltura = indDAO.buscarIndicadorTipo(sessao.getIdUsuario(), 0, 1);
		indPeso = indDAO.buscarIndicadorTipo(sessao.getIdUsuario(), 1, 1);
		
		dbPeso = indPeso.getMedida1();
		dbAltura = indAltura.getMedida1();
		
		dbAltura = dbAltura/100;
		
		dbIMC = (dbPeso / (dbAltura * dbAltura));
		DecimalFormat decimal = new DecimalFormat("0.0");
		txIMC.setText(decimal.format(dbIMC));
		
		vtFaixasIMC = getResources().getStringArray(R.array.faixasIMC);
		lsFaixasIMC = Arrays.asList(vtFaixasIMC);
		
		if (dbIMC > 0 && dbIMC <= 18.5)
			txStatusIMC.setText(lsFaixasIMC.get(0));
		if (dbIMC >= 18.6 && dbIMC <= 24.9)
			txStatusIMC.setText(lsFaixasIMC.get(1));
		if (dbIMC >= 25 && dbIMC <= 29.9)
			txStatusIMC.setText(lsFaixasIMC.get(2));
		if (dbIMC >= 30 && dbIMC <= 34.9)
			txStatusIMC.setText(lsFaixasIMC.get(3));
		if (dbIMC >= 35 && dbIMC <= 39.9)
			txStatusIMC.setText(lsFaixasIMC.get(4));
		if (dbIMC >= 40)
			txStatusIMC.setText(lsFaixasIMC.get(5));
	}
	
	public void calcularBPMIdeal () {
		
		intIdade = usrDAO.buscarIdadeUsuario(sessao.getIdUsuario());
		
		arrIndicadores = indDAO.buscarIndicadoresTipo(sessao.getIdUsuario(), 2);
		
		bpmDescansoFinal=0;
		
		if (arrIndicadores.size() >= 3) {
			int x;
							
			for (x=1; x<=3; x++) {
				indicador = arrIndicadores.get(x);
				bpmDescanso = indicador.getMedida1().intValue();
				bpmDescansoFinal = bpmDescansoFinal + bpmDescanso;					
			}
			
			bpmDescansoMedia = bpmDescansoFinal / 3;
			bpmMaximo = 220 - intIdade;
			bpmReserva = bpmMaximo - bpmDescansoMedia;
			bpmAlvoLimMin = (bpmReserva * (60 / 100)) + bpmDescansoMedia;
			bpmAlvoLimMax = (bpmReserva * (80 / 100)) + bpmDescansoMedia;
			bpmAlvo = (bpmAlvoLimMin + bpmAlvoLimMax) / 2;
			
			txAlvoBPM.setText(bpmAlvo + " BPM");
		}
	}
}
