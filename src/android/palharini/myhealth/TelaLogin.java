package android.palharini.myhealth;
 
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.palharini.myhealth.R;
import android.palharini.myhealth.daos.UsuarioDAO;
import android.palharini.myhealth.entidades.Usuario;
import android.palharini.myhealth.sessao.CaixaDialogo;
import android.palharini.myhealth.sessao.GerenciamentoSessao;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
 
public class TelaLogin extends Activity {
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);
        
        if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

    	final EditText email = (EditText) findViewById(R.id.editEmail);
    	final EditText senha = (EditText) findViewById(R.id.editSenha);
    	final Button loginButton = (Button) findViewById(R.id.loginButton);
    	final Button cadastreButton = (Button) findViewById(R.id.cadastreButton);
        final CaixaDialogo caixa = new CaixaDialogo();
        final GerenciamentoSessao sessao = new GerenciamentoSessao(getApplicationContext());

        loginButton.setOnClickListener(new Button.OnClickListener() {      
        @Override
        public void onClick(View arg0) {
            String emailString = email.getText().toString();
            String senhaString = senha.getText().toString();
            String criptSenha = null;
            
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
              
            if(emailString.trim().length() > 0 && criptSenha.trim().length() > 0){
                UsuarioDAO dao = new UsuarioDAO();
                Usuario dados = dao.buscarUsuarioEmail(emailString);
                
                if (dados != null) {                
	                if(emailString.equals(dados.getEmail()) && criptSenha.equals(dados.getSenha())){
	                	sessao.criarSessao(dados.getId(), dados.getNome(), dados.getEmail());
	
	                    Intent irTelaPrincipal = new Intent(getApplicationContext(), TelaPrincipal.class);
	                    startActivity(irTelaPrincipal);
	                    finish();
	                     
	                }else{
	                	caixa.showAlertDialog(
	                			TelaLogin.this, 
	                			getString(R.string.textLogonFalha), 
	                			getString(R.string.textLogonErrado), 
	                			false);
	                }
                }
                else {
                	caixa.showAlertDialog(
                			TelaLogin.this, 
                			getString(R.string.textFalhaConexao), 
                			getString(R.string.textServidorNaoResponde), 
                			false);
                }
            }
            else{
            	caixa.showAlertDialog(
            			TelaLogin.this, 
            			getString(R.string.textLogonFalha), 
            			getString(R.string.textLogonVazio), 
            			false);
                }
                 
            }
        });
        
        cadastreButton.setOnClickListener(new Button.OnClickListener() {      
            @Override
            public void onClick(View arg0) {
            	Intent irTelaCadastro = new Intent(getApplicationContext(), TelaCadastroUsuario.class);
                startActivity(irTelaCadastro);
            }
        });
    }        
}