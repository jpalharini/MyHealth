package android.palharini.myhealth;

import android.app.Activity;
import android.os.Bundle;
import android.palharini.myhealth.daos.UsuarioDAO;
import android.palharini.myhealth.entidades.Usuario;
import android.palharini.myhealth.sessao.GerenciamentoSessao;
import android.widget.Button;
import android.widget.TextView;

public class TelaPrincipal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_principal);
		
		final TextView ola = (TextView) findViewById(R.id.textOla);
		final TextView imc = (TextView) findViewById(R.id.IMC);
		final TextView imcStatus = (TextView) findViewById(R.id.textStatusIMC);
		
		final Button buttonAcompanhamento = (Button) findViewById(R.id.buttonAcompanhamento);
		final Button buttonDados = (Button) findViewById(R.id.buttonDados);
		final Button buttonConfiguracoes = (Button) findViewById(R.id.buttonConfiguracoes);
		
		GerenciamentoSessao sessao = new GerenciamentoSessao(getApplicationContext());
		
		UsuarioDAO dao = new UsuarioDAO();
		Usuario usuario = dao.buscarUsuario(sessao.getIdUsuario());
		
		ola.setText(usuario.getNome());
		
	}
}
