package android.palharini.myhealth;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.palharini.myhealth.R;
import android.palharini.myhealth.daos.UsuarioDAO;
import android.palharini.myhealth.entidades.Usuario;
import android.palharini.myhealth.sessao.CaixaDialogo;
import android.palharini.myhealth.sessao.GerenciamentoSessao;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class TelaLogin extends Activity {

	final EditText email = (EditText) findViewById(R.id.editEmail);
	final EditText senha = (EditText) findViewById(R.id.editSenha);

	final Button loginButton = (Button) findViewById(R.id.loginButton);
     
    // Alert Dialog Manager
    CaixaDialogo caixa = new CaixaDialogo();
     
    // Session Manager Class
    GerenciamentoSessao sessao;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login); 

        sessao = new GerenciamentoSessao(getApplicationContext());
         
        Toast.makeText(getApplicationContext(), "User Login Status: " + sessao.isLoggedIn(), Toast.LENGTH_LONG).show();

        loginButton.setOnClickListener(new Button.OnClickListener() {
             
        @Override
        public void onClick(View arg0) {
            // Get username, password from EditText
            String emailString = email.getText().toString();
            String senhaString = senha.getText().toString();
              
            if(emailString.trim().length() > 0 && senhaString.trim().length() > 0){
                UsuarioDAO dao = new UsuarioDAO();
                Usuario usuario = dao.buscarUsuarioLogin(emailString);
                
                if(emailString.equals(usuario.getEmail()) && senhaString.equals(usuario.getSenha())){
                	sessao.criarSessao(usuario.getNome(), usuario.getEmail());

                    Intent i = new Intent(getApplicationContext(), TelaPrincipal.class);
                    startActivity(i);
                    finish();
                     
                }else{
                    // E-mail ou senha incorretos
                	caixa.showAlertDialog(TelaLogin.this, "Falha no login", "E-mail ou senha incorretos", false);
                }               
            }else{
                // Usuário deixou campos em branco
            	caixa.showAlertDialog(TelaLogin.this, "Falha no login", "Por favor, digite seu e-mail e senha", false);
                }
                 
            }
        });
    }        
}