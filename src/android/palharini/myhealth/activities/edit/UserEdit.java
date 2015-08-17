package android.palharini.myhealth.activities.edit;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.os.Bundle;
import android.palharini.myhealth.R;
import android.palharini.myhealth.date_time.DateFormat;
import android.palharini.myhealth.date_time.pickers.DatePickerBox;
import android.palharini.myhealth.db.dao.UserDAO;
import android.palharini.myhealth.db.entities.User;
import android.palharini.myhealth.session.SessionManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserEdit extends Activity {

	private SessionManager sessao;
	private DateFormat fd;
	
	private EditText etEmail, etSenha, etConfSenha, etNome, etDataNasc;
	private Button btSalvar;	
	
	private String stEmail, stNome, stSenha, stConfSenha, stCriptSenha, stDataNasc, stDataNascSQL;
	private Boolean blUsr;
	
	private UserDAO usrDAO;
	private User usrUsuario;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_edit);
		
		sessao = new SessionManager(getApplicationContext());
		
		usrDAO = new UserDAO();
		usrUsuario = usrDAO.searchUser(sessao.getUserID());
		
		fd = new DateFormat();
		
		etEmail = (EditText) findViewById(R.id.etEmail);
		etSenha = (EditText) findViewById(R.id.etSenha);
		etConfSenha = (EditText) findViewById(R.id.etConfSenha);
		etNome = (EditText) findViewById(R.id.etNome);
		etDataNasc = (EditText) findViewById(R.id.etNasc);
		btSalvar = (Button) findViewById(R.id.btSalvar);
		
		etEmail.setText(usrUsuario.getEmail());
		etNome.setText(usrUsuario.getNome());

		stDataNasc = fd.getDataAndroid(usrUsuario.getDataNascimento());
		etDataNasc.setText(stDataNasc);
						
		etDataNasc.setOnClickListener(new OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerBox((EditText) v).show(getFragmentManager(), "datePicker");
			}
			
		});
		
		btSalvar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v){
			
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
					
					stDataNascSQL = fd.getDataSQL(etDataNasc.getText().toString());

					stEmail = etEmail.getText().toString();
					stNome = etNome.getText().toString();
					
					blUsr = usrDAO.atualizarUsuario(new User(
							usrUsuario.getId(), 
							stEmail,
							stCriptSenha,
							stNome, 
							stDataNascSQL,
							0
					));
					if (blUsr) {
						Toast.makeText(getApplicationContext(), getString(R.string.toastUsrUpdOK), Toast.LENGTH_LONG).show();
	                    finish();
					}
					else {
						Toast.makeText(getApplicationContext(), getString(R.string.toastUsrUpdFail), Toast.LENGTH_LONG).show();
					}
				}
			}
		});
		
	}
}
