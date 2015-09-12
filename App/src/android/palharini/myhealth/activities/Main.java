package android.palharini.myhealth.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.palharini.myhealth.R;
import android.palharini.myhealth.activities.edit.PreferencesEdit;
import android.palharini.myhealth.activities.edit.UserEdit;
import android.palharini.myhealth.activities.register.IndicatorRegister;
import android.palharini.myhealth.db.ws.dao.IndicatorDAO;
import android.palharini.myhealth.db.ws.dao.UserDAO;
import android.palharini.myhealth.db.entities.Indicator;
import android.palharini.myhealth.db.entities.User;
import android.palharini.myhealth.session.SessionManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Activity {

	private SessionManager sessionManager;
	
	private UserDAO usrDAO;
	private User user;
	private IndicatorDAO indDAO;
	private Indicator indHeight, indWeight, indicator;
	
	private TextView tvHello, tvBMI, tvStatusBMI, tvTargetBPM;
	private Button btInsIndicator, btMonitoring, btUserInfo, btSettings;
	
	private ArrayList<Indicator> arrayIndicators;
	private List<String> listRangesBMI;
	private String stName;
    private String stFirstName;
    private String[] strArrBmiRanges;
	private Double dbHeight, dbWeight, dbBMI;
	private Integer intAge;
	// Heart Rate measures
	private Integer intRestingBPM, intFinalRestingBPM, intAvgRestingBPM, intMaxBPM,
            intReserveBPM, intMinTargetBPM, intMaxTargetBPM, intTargetBPM;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// TODO Isolate data processing threads from UI thread
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		tvHello = (TextView) findViewById(R.id.tvHello);
		tvBMI = (TextView) findViewById(R.id.tvBMI);
		tvStatusBMI = (TextView) findViewById(R.id.tvStatusBMI);
		tvTargetBPM = (TextView) findViewById(R.id.tvTargetBPM);
		
		btInsIndicator = (Button) findViewById(R.id.btCadIndicador);
		btMonitoring = (Button) findViewById(R.id.btAcompanhamento);
		btUserInfo = (Button) findViewById(R.id.btDados);
		btSettings = (Button) findViewById(R.id.btConfiguracoes);
		
		sessionManager = new SessionManager(getApplicationContext());
		
		usrDAO = new UserDAO();

		indDAO = new IndicatorDAO();
		
		setUserName();
		calcBMI();
		calcIdealBMI();
	
		btInsIndicator.setOnClickListener(new Button.OnClickListener () {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent goInsIndicatorScreen = new Intent(getApplicationContext(), IndicatorRegister.class);
				startActivity(goInsIndicatorScreen);
			}
		});
		
		btMonitoring.setOnClickListener(new Button.OnClickListener () {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent goMonitoringScreen = new Intent(getApplicationContext(), IndicatorTypesList.class);
				startActivity(goMonitoringScreen);
			}
		});
		
		btUserInfo.setOnClickListener(new Button.OnClickListener () {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent goEditUserScreen = new Intent(getApplicationContext(), UserEdit.class);
				startActivity(goEditUserScreen);
			}
		});
		
		btSettings.setOnClickListener(new Button.OnClickListener () {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent goSettingsScreen = new Intent(getApplicationContext(), PreferencesEdit.class);
				startActivity(goSettingsScreen);
			}
		});
		
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		setUserName();
		calcBMI();
		calcIdealBMI();
	}
	
	public void setUserName() {
		
		user = usrDAO.searchUser(sessionManager.getUserID());
		
		stName = user.getNome();
		
		if (stName.contains(" ")) {
			stFirstName = stName.substring(0, stName.indexOf(" "));
		}
		else {
			stFirstName = stName;
		}
		
		tvHello.setText(getString(R.string.tvOla) + " " + stFirstName);
	}
	
	public void calcBMI() {
		
		indHeight = indDAO.buscarIndicadorTipo(sessionManager.getUserID(), 0, 1);
		indWeight = indDAO.buscarIndicadorTipo(sessionManager.getUserID(), 1, 1);
		
		dbWeight = indWeight.getMeasure1();
		dbHeight = indHeight.getMeasure1();
		
		dbHeight = dbHeight /100;
		
		dbBMI = (dbWeight / (dbHeight * dbHeight));
		DecimalFormat decimal = new DecimalFormat("0.0");
		tvBMI.setText(decimal.format(dbBMI));
		
		strArrBmiRanges = getResources().getStringArray(R.array.faixasIMC);
		listRangesBMI = Arrays.asList(strArrBmiRanges);

        // BMI Categories - according to http://www.nhlbi.nih.gov/health/educational/lose_wt/BMI/bmicalc.htm
		if (dbBMI > 0 && dbBMI <= 18.5)
			tvStatusBMI.setText(listRangesBMI.get(0));
		if (dbBMI >= 18.6 && dbBMI <= 24.9)
			tvStatusBMI.setText(listRangesBMI.get(1));
		if (dbBMI >= 25 && dbBMI <= 29.9)
			tvStatusBMI.setText(listRangesBMI.get(2));
		if (dbBMI >= 30 && dbBMI <= 34.9)
			tvStatusBMI.setText(listRangesBMI.get(3));
		if (dbBMI >= 35 && dbBMI <= 39.9)
			tvStatusBMI.setText(listRangesBMI.get(4));
		if (dbBMI >= 40)
			tvStatusBMI.setText(listRangesBMI.get(5));
	}
	
	public void calcIdealBMI() {

		intAge = usrDAO.selectUserAge(sessionManager.getUserID());
		
		arrayIndicators = indDAO.selectIndicatorTypes(sessionManager.getUserID(), 2);
		
		intFinalRestingBPM =0;
		
		if (arrayIndicators.size() >= 3) {
			int x;
							
			for (x=1; x<=3; x++) {
				indicator = arrayIndicators.get(x);
				intRestingBPM = indicator.getMeasure1().intValue();
				intFinalRestingBPM = intFinalRestingBPM + intRestingBPM;
			}
			
			intAvgRestingBPM = intFinalRestingBPM / 3;
			intMaxBPM = 220 - intAge;
			intReserveBPM = intMaxBPM - intAvgRestingBPM;

			intMinTargetBPM = (intReserveBPM * (60 / 100)) + intAvgRestingBPM;
			intMaxTargetBPM = (intReserveBPM * (80 / 100)) + intAvgRestingBPM;
			intTargetBPM = (intMinTargetBPM + intMaxTargetBPM) / 2;
			
			tvTargetBPM.setText(intTargetBPM + " BPM");
		}
	}
}
