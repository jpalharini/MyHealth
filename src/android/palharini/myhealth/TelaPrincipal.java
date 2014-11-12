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
	
	private TextView txOla, txIMC, txStatusIMC, txAlvoBPM;
	private Button btCadIndicador, btAcompanhamento, btDados, btConfiguracoes;
	
	private Integer bpm, bpmDescanso, bpmDescansoFinal, bpmDescansoMedia;
	private Integer bpmMaximo, bpmReserva, bpmAlvoLimMin, bpmAlvoLimMax, bpmAlvo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
		
		UsuarioDAO usrdao = new UsuarioDAO();
		Usuario usuario = usrdao.buscarUsuario(sessao.getIdUsuario());
		
		IndicadorDAO inddao = new IndicadorDAO();
		Indicador altura = inddao.buscarIndicadorTipo(sessao.getIdUsuario(), 0, 1);
		Indicador peso = inddao.buscarIndicadorTipo(sessao.getIdUsuario(), 1, 1);
		
		if (usuario != null && altura != null && peso != null) {
			String nomeUsuario = usuario.getNome();
			Double pesoUsuario = peso.getMedida();
			Double alturaUsuario = altura.getMedida();
			Integer idadeUsuario = usrdao.buscarIdadeUsuario(sessao.getIdUsuario());
			
			alturaUsuario = alturaUsuario/100;
			
			String primeiroNomeUsuario;
			if (nomeUsuario.contains(" ")) {
				primeiroNomeUsuario = nomeUsuario.substring(0, nomeUsuario.indexOf(" "));
			}
			else {
				primeiroNomeUsuario = nomeUsuario;
			}
			txOla.setText(getString(R.string.tvOla) + " " + primeiroNomeUsuario);
			
			Double imcDouble = (pesoUsuario / (alturaUsuario * alturaUsuario));
			DecimalFormat decimal = new DecimalFormat("0.0");
			txIMC.setText(decimal.format(imcDouble));
			
			String[] listaFaixasIMC = getResources().getStringArray(R.array.faixasIMC);
			final List<String> faixas = Arrays.asList(listaFaixasIMC);
			
			if (imcDouble > 0 && imcDouble <= 18.5)
				txStatusIMC.setText(faixas.get(0));
			if (imcDouble >= 18.6 && imcDouble <= 24.9)
				txStatusIMC.setText(faixas.get(1));
			if (imcDouble >= 25 && imcDouble <= 29.9)
				txStatusIMC.setText(faixas.get(2));
			if (imcDouble >= 30 && imcDouble <= 34.9)
				txStatusIMC.setText(faixas.get(3));
			if (imcDouble >= 35 && imcDouble <= 39.9)
				txStatusIMC.setText(faixas.get(4));
			if (imcDouble >= 40)
				txStatusIMC.setText(faixas.get(5));
			
			IndicadorDAO dao = new IndicadorDAO();
			Indicador indicador = new Indicador();
			ArrayList<Indicador> indicadores = dao.buscarIndicadoresTipo(sessao.getIdUsuario(), 2);
			
			if (indicadores.size() >= 3) {
				int x;
								
				for (x=1; x==3; x++) {
					indicador = indicadores.get(x);
					bpmDescanso = indicador.getMedida().intValue();
					bpmDescansoFinal = bpmDescanso+bpm;					
				}
				
				bpmDescansoMedia = bpmDescansoFinal / 3;
				bpmMaximo = 220 - idadeUsuario;
				bpmReserva = bpmMaximo - bpmDescansoMedia;
				bpmAlvoLimMin = (bpmReserva * (60 / 100)) + bpmDescansoMedia;
				bpmAlvoLimMax = (bpmReserva * (80 / 100)) + bpmDescansoMedia;
				bpmAlvo = (bpmAlvoLimMin + bpmAlvoLimMax) / 2;
				txAlvoBPM.setText(bpmAlvo.toString());
			}
		}
		
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
}
