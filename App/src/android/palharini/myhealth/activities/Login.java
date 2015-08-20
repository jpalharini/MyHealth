package android.palharini.myhealth.activities;
 
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

    private User user;
    private UserDAO userDAO;
 
	private SessionManager sessionManager;
	private DialogBox dialogBox;
	
	private EditText etEmail, etPassword;
	private Button btLogin, btRegister;
	
	private String strEmail, strPassword, strCryptPassword;
    private CryptPassword cryptPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // TODO: Optimize UI thread
        if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

        userDAO = new UserDAO();

        // Map screen elements
        etEmail = (EditText) findViewById(R.id.etEmail);
    	etPassword = (EditText) findViewById(R.id.etPassword);
    	btLogin = (Button) findViewById(R.id.btLogin);
    	btRegister = (Button) findViewById(R.id.btRegister);

        // Create dialog box
        dialogBox = new DialogBox();

        // Create session
        sessionManager = new SessionManager(getApplicationContext());

        // Call password-encrypting class
        cryptPassword = new CryptPassword();

        btLogin.setOnClickListener(new Button.OnClickListener() {      
        @Override
        public void onClick(View arg0) {
            strEmail = etEmail.getText().toString();
            strPassword = etPassword.getText().toString();

            // If password not null, then encrypt password
            if (strPassword != null) {
                strCryptPassword = cryptPassword.encryptPassword(strPassword);
            } else {
                // TODO: Create null password error string
                dialogBox.showAlertDialog(
                        Login.this,
                        getString(R.string.tvLogonFalha),
                        getString(R.string.tvLogonErrado),
                        false);
            }

            if(strEmail.trim().length() > 0 && strCryptPassword.trim().length() > 0){
                user = userDAO.searchUserByEmail(strEmail);
                
                if (user != null) {
	                if(strEmail.equals(user.getEmail()) && strCryptPassword.equals(user.getPassword())){
	                	sessionManager.criarSessao(user.getId(), user.getNome(), user.getEmail());
	
	                    Intent irTelaPrincipal = new Intent(getApplicationContext(), Main.class);
	                    startActivity(irTelaPrincipal);
	                    finish();
	                     
	                }else{
	                	dialogBox.showAlertDialog(
	                			Login.this, 
	                			getString(R.string.tvLogonFalha), 
	                			getString(R.string.tvLogonErrado), 
	                			false);
	                }
                }
                else {
                	dialogBox.showAlertDialog(
                			Login.this, 
                			getString(R.string.tvFalhaConexao), 
                			getString(R.string.tvServidorNaoResponde), 
                			false);
                }
            }
            else{
            	dialogBox.showAlertDialog(
            			Login.this, 
            			getString(R.string.tvLogonFalha), 
            			getString(R.string.tvLogonVazio), 
            			false);
                }
                 
            }
        });
        
        btRegister.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	Intent goUsrRegisterScreen = new Intent(getApplicationContext(), UserRegister.class);
                startActivity(goUsrRegisterScreen);
                finish();
            }
        });
    }        
}