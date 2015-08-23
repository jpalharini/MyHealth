package android.palharini.myhealth.activities.edit;

import android.app.Activity;
import android.os.Bundle;
import android.palharini.myhealth.R;
import android.palharini.myhealth.activities.CryptPassword;
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

    private UserDAO userDAO;
    private User user;

    private SessionManager sessionManager;
	private DateFormat dateFormat;
    private CryptPassword cryptPassword;
	
	private EditText etEmail, etPassword, etConfPassword, etName, etBirthDate;
	private Button btSave;
	
	private String strEmail, strName, strPassword, strConfPassword, strCryptPassword, strBirthDate, strBirthDate_SQL;
	private Boolean blUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_edit);

		userDAO = new UserDAO();
		user = userDAO.searchUser(sessionManager.getUserID());

		etEmail = (EditText) findViewById(R.id.etEmail);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etConfPassword = (EditText) findViewById(R.id.etConfPassword);
		etName = (EditText) findViewById(R.id.etName);
		etBirthDate = (EditText) findViewById(R.id.etBirthDate);
		btSave = (Button) findViewById(R.id.btSave);

        dateFormat = new DateFormat();

        // Create session
        sessionManager = new SessionManager(getApplicationContext());

        // Call password-encrypting class
        cryptPassword = new CryptPassword();

        etEmail.setText(user.getEmail());
        etName.setText(user.getNome());

        strBirthDate = dateFormat.getAndroidDate(user.getBirthDate());
        etBirthDate.setText(strBirthDate);
						
		etBirthDate.setOnClickListener(new OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerBox((EditText) v).show(getFragmentManager(), "datePicker");
			}
			
		});
		
		btSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v){
			
				strPassword = etPassword.getText().toString();
				strConfPassword = etConfPassword.getText().toString();

				if (strPassword.equals(strConfPassword)){
					strCryptPassword = cryptPassword.encryptPassword(strPassword);

					strBirthDate_SQL = dateFormat.getSqlDate(etBirthDate.getText().toString());

					strEmail = etEmail.getText().toString();
					strName = etName.getText().toString();
					
					blUser = userDAO.atualizarUsuario(new User(
							user.getId(),
                            strEmail,
                            strCryptPassword,
                            strName,
                            strBirthDate_SQL,
							0
					));
					if (blUser) {
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
