package android.palharini.myhealth.activities;
 
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.palharini.myhealth.R;
import android.palharini.myhealth.activities.register.UserRegister;
import android.palharini.myhealth.db.dao.UserDAO;
import android.palharini.myhealth.db.entities.User;
import android.palharini.myhealth.session.DialogBox;
import android.palharini.myhealth.session.SessionManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
 
public class Login extends Activity {
 
	private SessionManager sessao;
	private DialogBox caixa;
	
	private EditText etEmail, etSenha;
	private Button btLogin, btCadastre;
	
	private String stEmail, stSenha, stCriptSenha;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

        caixa = new DialogBox();
        sessao = new SessionManager(getApplicationContext());

    	etEmail = (EditText) findViewById(R.id.etEmail);
    	etSenha = (EditText) findViewById(R.id.etSenha);
    	btLogin = (Button) findViewById(R.id.btLogin);
    	btCadastre = (Button) findViewById(R.id.btCadastre);
        caixa = new DialogBox();
        sessao = new SessionManager(getApplicationContext());

        btLogin.setOnClickListener(new Button.OnClickListener() {      
        @Override
        public void onClick(View arg0) {
            stEmail = etEmail.getText().toString();
            stSenha = etSenha.getText().toString();
            stCriptSenha = null;
            
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
              
            if(stEmail.trim().length() > 0 && stCriptSenha.trim().length() > 0){
                UserDAO dao = new UserDAO();
                User dados = dao.buscarUsuarioEmail(stEmail);
                
                if (dados != null) {                
	                if(stEmail.equals(dados.getEmail()) && stCriptSenha.equals(dados.getPassword())){
	                	sessao.criarSessao(dados.getId(), dados.getNome(), dados.getEmail());
	
	                    Intent irTelaPrincipal = new Intent(getApplicationContext(), Main.class);
	                    startActivity(irTelaPrincipal);
	                    finish();
	                     
	                }else{
	                	caixa.showAlertDialog(
	                			Login.this, 
	                			getString(R.string.tvLogonFalha), 
	                			getString(R.string.tvLogonErrado), 
	                			false);
	                }
                }
                else {
                	caixa.showAlertDialog(
                			Login.this, 
                			getString(R.string.tvFalhaConexao), 
                			getString(R.string.tvServidorNaoResponde), 
                			false);
                }
            }
            else{
            	caixa.showAlertDialog(
            			Login.this, 
            			getString(R.string.tvLogonFalha), 
            			getString(R.string.tvLogonVazio), 
            			false);
                }
                 
            }
        });
        
        btCadastre.setOnClickListener(new Button.OnClickListener() {      
            @Override
            public void onClick(View arg0) {
            	Intent irTelaCadastro = new Intent(getApplicationContext(), UserRegister.class);
                startActivity(irTelaCadastro);
                finish();
            }
        });
    }        
}