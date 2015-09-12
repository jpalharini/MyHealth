package android.palharini.myhealth.activities.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.palharini.myhealth.R;
import android.palharini.myhealth.activities.CryptPassword;
import android.palharini.myhealth.date_time.DateFormat;
import android.palharini.myhealth.date_time.pickers.DatePickerBox;
import android.palharini.myhealth.db.ws.dao.IndicatorDAO;
import android.palharini.myhealth.db.ws.dao.UserDAO;
import android.palharini.myhealth.db.entities.Indicator;
import android.palharini.myhealth.db.entities.User;
import android.palharini.myhealth.session.SessionManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class UserRegister extends Activity {

	private User user;
	private UserDAO userDAO;
	private IndicatorDAO indicatorDAO;

	private SessionManager sessionManager;
	private DateFormat dateFormat;
    private CryptPassword cryptPassword;
	
	private EditText etEmail, etPassword, etConfPassword, etName, etBirthDate, etHeight, etWeight;
	private Button btContinue;
	
	private String strEmail, strName, strPassword, strConfPassword, strCryptPassword, strBirthDate, strBirthDate_SQL;
	private String[] strArrUnits;
	private List<String> lsUnits;
	private Double dbHeight, dbWeight;
	private Boolean blUser, blHeightInd, blWeightInd;

	private Intent irTelaCadastroPreferencias;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		setContentView(R.layout.activity_user_register);

        userDAO = new UserDAO();
        indicatorDAO = new IndicatorDAO();

        sessionManager = new SessionManager(getApplicationContext());
        dateFormat = new DateFormat();
        cryptPassword = new CryptPassword();

		etEmail = (EditText) findViewById(R.id.etEmail);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etConfPassword = (EditText) findViewById(R.id.etConfPassword);
		etName = (EditText) findViewById(R.id.etName);
		etBirthDate = (EditText) findViewById(R.id.etBirthDate);
		etHeight = (EditText) findViewById(R.id.etHeight);
		etWeight = (EditText) findViewById(R.id.etWeight);
		
		btContinue = (Button) findViewById(R.id.btContinue);
		
		etBirthDate.setOnClickListener(new EditText.OnClickListener () {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerBox((EditText) v).show(getFragmentManager(), "datePicker");
			}
			
		});
		
		btContinue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v){
				
				strEmail = etEmail.getText().toString();
				strName = etName.getText().toString();
				dbHeight = Double.parseDouble(etHeight.getText().toString());
				dbWeight = Double.parseDouble(etWeight.getText().toString());

				strPassword = etPassword.getText().toString();
				strConfPassword = etConfPassword.getText().toString();
				strCryptPassword = null;
				
				if (strPassword.equals(strConfPassword)){
					strCryptPassword = cryptPassword.encryptPassword(strPassword);
					
					strBirthDate = etBirthDate.getText().toString();
					strBirthDate_SQL = dateFormat.getSqlDate(strBirthDate);
					
					blUser = userDAO.cadastrarUsuario(new User(
							0,
							strEmail,
							strCryptPassword,
							strName,
							strBirthDate_SQL
							));
					
					user = userDAO.searchUserByEmail(strEmail);
					
					strArrUnits = getResources().getStringArray(R.array.lsUnidades);
					lsUnits = Arrays.asList(strArrUnits);
					
					blHeightInd = indicatorDAO.register(new Indicator(
							0,
							0,
							user.getId(),
							dbHeight,
							0.0,
							lsUnits.get(0),
							dateFormat.getCurrentSqlDate(),
							dateFormat.getCurrentSqlTime()
							));
					
					blWeightInd = indicatorDAO.register(new Indicator(
							0,
							1,
							user.getId(),
							dbWeight,
							0.0,
							lsUnits.get(1),
							dateFormat.getCurrentSqlDate(),
							dateFormat.getCurrentSqlTime()
							));
					
					if (blUser && blHeightInd && blWeightInd) {
						sessionManager.criarSessao(user.getId(), user.getNome(), user.getEmail());
						Toast.makeText(getApplicationContext(), getString(R.string.toastUsrOK), Toast.LENGTH_LONG).show();
						irTelaCadastroPreferencias = new Intent(getApplicationContext(), PreferencesRegister.class);
						startActivity(irTelaCadastroPreferencias);
						finish();
					}
					else {
						Toast.makeText(getApplicationContext(), getString(R.string.toastUsrFail), Toast.LENGTH_LONG).show();
					}
					
				}
				else {
					Toast.makeText(getApplicationContext(), getString(R.string.toastNoMatchPass), Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}