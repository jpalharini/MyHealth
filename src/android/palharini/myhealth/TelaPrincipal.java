package android.palharini.myhealth;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.palharini.myhealth.daos.IndicadorDAO;
import android.palharini.myhealth.daos.UsuarioDAO;
import android.palharini.myhealth.entidades.Indicador;
import android.palharini.myhealth.entidades.Usuario;
import android.palharini.myhealth.sessao.GerenciamentoSessao;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TelaPrincipal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_principal);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		final TextView ola = (TextView) findViewById(R.id.textOla);
		final TextView imc = (TextView) findViewById(R.id.IMC);
		final TextView imcStatus = (TextView) findViewById(R.id.textStatusIMC);
		
		final Button buttonAcompanhamento = (Button) findViewById(R.id.buttonAcompanhamento);
		final Button buttonDados = (Button) findViewById(R.id.buttonDados);
		final Button buttonConfiguracoes = (Button) findViewById(R.id.buttonConfiguracoes);
		
		GerenciamentoSessao sessao = new GerenciamentoSessao(getApplicationContext());
		
		UsuarioDAO usrdao = new UsuarioDAO();
		Usuario usuario = usrdao.buscarUsuario(sessao.getIdUsuario());
		
		IndicadorDAO inddao = new IndicadorDAO();
		Indicador altura = inddao.buscarIndicadorTipo(sessao.getIdUsuario(), 0, 1);
		Indicador peso = inddao.buscarIndicadorTipo(sessao.getIdUsuario(), 1, 1);
		
		if (usuario != null && altura != null && peso != null) {
			String nomeUsuario = usuario.getNome();
			Double pesoUsuario = peso.getMedida();
			Double alturaUsuario = altura.getMedida();
			
			String primeiroNomeUsuario;
			if (nomeUsuario.contains(" ")) {
				primeiroNomeUsuario = nomeUsuario.substring(0, nomeUsuario.indexOf(" "));
			}
			else {
				primeiroNomeUsuario = nomeUsuario;
			}
			ola.setText(getString(R.string.textOla) + " " + primeiroNomeUsuario);
			
			Double imcDouble = (pesoUsuario / (alturaUsuario * alturaUsuario));
			imc.setText(imcDouble.toString());
			
			String[] listaFaixasIMC = getResources().getStringArray(R.array.faixasIMC);
			final List<String> faixas = Arrays.asList(listaFaixasIMC);
			
			if (imcDouble > 0 && imcDouble <= 18.5)
				imcStatus.setText(faixas.get(0));
			if (imcDouble >= 18.6 && imcDouble <= 24.9)
				imcStatus.setText(faixas.get(1));
			if (imcDouble >= 25 && imcDouble <= 29.9)
				imcStatus.setText(faixas.get(2));
			if (imcDouble >= 30 && imcDouble <= 34.9)
				imcStatus.setText(faixas.get(3));
			if (imcDouble >= 35 && imcDouble <= 39.9)
				imcStatus.setText(faixas.get(4));
			if (imcDouble >= 40)
				imcStatus.setText(faixas.get(5));
		}
		
		buttonAcompanhamento.setOnClickListener(new Button.OnClickListener () {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent irTelaAcompanhamento = new Intent(getApplicationContext(), TelaAcompanhamento.class);
				startActivity(irTelaAcompanhamento);
			}
		});
		
		buttonDados.setOnClickListener(new Button.OnClickListener () {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent irTelaEdicaoUsuario = new Intent(getApplicationContext(), TelaEdicaoUsuario.class);
				startActivity(irTelaEdicaoUsuario);
			}
		});
		
		buttonConfiguracoes.setOnClickListener(new Button.OnClickListener () {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent irTelaConfiguracoes = new Intent(getApplicationContext(), TelaConfiguracoes.class);
				startActivity(irTelaConfiguracoes);
			}
		});
		
	}
}
