package android.palharini.myhealth.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.palharini.myhealth.R;
import android.palharini.myhealth.activities.edit.PreferencesEdit;
import android.palharini.myhealth.activities.edit.UserEdit;
import android.palharini.myhealth.activities.register.IndicatorRegister;
import android.palharini.myhealth.db.dao.IndicatorDAO;
import android.palharini.myhealth.db.dao.UserDAO;
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

	private SessionManager session;
	
	private UserDAO usrDAO;
	private User user;
	private IndicatorDAO indDAO;
	private Indicator indHeight, indWeight, indicator;
	
	private TextView tvHello, tvBMI, tvStatusBMI, tvTargetBPM;
	private Button btInsIndicator, btMonitoring, btUserInfo, btSettings;
	
	private ArrayList<Indicator> arrayIndicators;
	private List<String> listRangesBMI;
	private String stName, stFirstName, vtFaixasIMC[];
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
		
		tvHello = (TextView) findViewById(R.id.tvOla);
		tvBMI = (TextView) findViewById(R.id.IMC);
		tvStatusBMI = (TextView) findViewById(R.id.tvStatusIMC);
		tvTargetBPM = (TextView) findViewById(R.id.alvoBPM);
		
		btInsIndicator = (Button) findViewById(R.id.btCadIndicador);
		btMonitoring = (Button) findViewById(R.id.btAcompanhamento);
		btUserInfo = (Button) findViewById(R.id.btDados);
		btSettings = (Button) findViewById(R.id.btConfiguracoes);
		
		session = new SessionManager(getApplicationContext());
		
		usrDAO = new UserDAO();

		indDAO = new IndicatorDAO();
		
		setNomeUsuario();
		calcularIMC();
		calcularBPMIdeal();
	
		btInsIndicator.setOnClickListener(new Button.OnClickListener () {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent irTelaCadIndicador = new Intent(getApplicationContext(), IndicatorRegister.class);
				startActivity(irTelaCadIndicador);
			}
		});
		
		btMonitoring.setOnClickListener(new Button.OnClickListener () {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent irTelaAcompanhamento = new Intent(getApplicationContext(), IndicatorTypesList.class);
				startActivity(irTelaAcompanhamento);
			}
		});
		
		btUserInfo.setOnClickListener(new Button.OnClickListener () {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent irTelaEdicaoUsuario = new Intent(getApplicationContext(), UserEdit.class);
				startActivity(irTelaEdicaoUsuario);
			}
		});
		
		btSettings.setOnClickListener(new Button.OnClickListener () {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent irTelaConfiguracoes = new Intent(getApplicationContext(), PreferencesEdit.class);
				startActivity(irTelaConfiguracoes);
			}
		});
		
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		setNomeUsuario();
		calcularIMC();
		calcularBPMIdeal();
	}
	
	public void setNomeUsuario () {
		
		user = usrDAO.buscarUsuario(session.getIdUsuario());
		
		stName = user.getNome();
		
		if (stName.contains(" ")) {
			stFirstName = stName.substring(0, stName.indexOf(" "));
		}
		else {
			stFirstName = stName;
		}
		
		tvHello.setText(getString(R.string.tvOla) + " " + stFirstName);
	}
	
	public void calcularIMC () {
		
		indHeight = indDAO.buscarIndicadorTipo(session.getIdUsuario(), 0, 1);
		indWeight = indDAO.buscarIndicadorTipo(session.getIdUsuario(), 1, 1);
		
		dbWeight = indWeight.getMedida1();
		dbHeight = indHeight.getMedida1();
		
		dbHeight = dbHeight /100;
		
		dbBMI = (dbWeight / (dbHeight * dbHeight));
		DecimalFormat decimal = new DecimalFormat("0.0");
		tvBMI.setText(decimal.format(dbBMI));
		
		vtFaixasIMC = getResources().getStringArray(R.array.faixasIMC);
		listRangesBMI = Arrays.asList(vtFaixasIMC);
		
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
	
	public void calcularBPMIdeal () {

		intAge = usrDAO.buscarIdadeUsuario(session.getIdUsuario());
		
		arrayIndicators = indDAO.buscarIndicadoresTipo(session.getIdUsuario(), 2);
		
		intFinalRestingBPM =0;
		
		if (arrayIndicators.size() >= 3) {
			int x;
							
			for (x=1; x<=3; x++) {
				indicator = arrayIndicators.get(x);
				intRestingBPM = indicator.getMedida1().intValue();
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
