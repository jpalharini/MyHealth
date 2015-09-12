package android.palharini.myhealth.activities.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.palharini.myhealth.R;
import android.palharini.myhealth.date_time.DateFormat;
import android.palharini.myhealth.db.ws.dao.IndicatorDAO;
import android.palharini.myhealth.db.entities.Indicator;
import android.palharini.myhealth.session.SessionManager;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.Arrays;
import java.util.List;

public class IndicatorRegister extends Activity {

	private DateFormat dateFormat;
	private SessionManager sessionManager;
	
	private IndicatorDAO indicatorDAO;
	
	private int userId;
	private String stCurrentDate, stCurrentTime, stMeasUnit;
	private Double dbMeasure1, dbMeasure2;
	
	private Spinner spType;
	private EditText etMeasure1, etMeasure2;
	private TextView tvMeasUnit1, tvMeasUnit2, tvMeasure2;
	private Button btSave;
	
	private ArrayAdapter<String> arrAdTypes;
	private List<String> stLsMeasUnits;
	private String[] stArrMeasUnits;
	private int selectedTypeId;
	
	private boolean register;
	
	private Intent type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_indicator_register);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		type = getIntent();
		
		dateFormat = new DateFormat();
		sessionManager = new SessionManager(getApplicationContext());
		
		indicatorDAO = new IndicatorDAO();
		
		userId = sessionManager.getUserID();
		stCurrentDate = dateFormat.getCurrentSqlDate();
		stCurrentTime = dateFormat.getCurrentSqlTime();
		
		spType = (Spinner) findViewById(R.id.spTipo);
		arrAdTypes = new ArrayAdapter<>(
				this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.lsTypes));
		arrAdTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spType.setAdapter(arrAdTypes);
		
		etMeasure1 = (EditText) findViewById(R.id.etMedida1);
		tvMeasUnit1 = (TextView) findViewById(R.id.tvUnidade1);
		
		tvMeasure2 = (TextView) findViewById(R.id.tvMedida2);
		etMeasure2 = (EditText) findViewById(R.id.etMedida2);
		tvMeasUnit2 = (TextView) findViewById(R.id.tvUnidade2);
		
		btSave = (Button) findViewById(R.id.btSave);
		
		spType.setSelection(type.getIntExtra("tipoSelecionado", 0));
				
		spType.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int posSpinner, long id) {
				// TODO Auto-generated method stub
				stArrMeasUnits = getResources().getStringArray(R.array.lsUnidades);
				stLsMeasUnits = Arrays.asList(stArrMeasUnits);
				switch (posSpinner) {
				case 0:
					tvMeasUnit1.setText(stLsMeasUnits.get(posSpinner));
					tvMeasure2.setVisibility(View.GONE);
					etMeasure2.setVisibility(View.GONE);
					tvMeasUnit2.setVisibility(View.GONE);
					break;
				case 1:
					tvMeasUnit1.setText(stLsMeasUnits.get(posSpinner));
					tvMeasure2.setVisibility(View.GONE);
					etMeasure2.setVisibility(View.GONE);
					tvMeasUnit2.setVisibility(View.GONE);
					break;
				case 2:
					tvMeasUnit1.setText(stLsMeasUnits.get(posSpinner));
					tvMeasure2.setVisibility(View.GONE);
					etMeasure2.setVisibility(View.GONE);
					tvMeasUnit2.setVisibility(View.GONE);
					break;
				case 3:
					tvMeasUnit1.setText(stLsMeasUnits.get(posSpinner));
					tvMeasUnit2.setText("");
					tvMeasure2.setVisibility(View.VISIBLE);
					etMeasure2.setVisibility(View.VISIBLE);
					tvMeasUnit2.setVisibility(View.VISIBLE);
					break;
				case 4:
					tvMeasUnit1.setText(stLsMeasUnits.get(posSpinner));
					tvMeasure2.setVisibility(View.GONE);
					etMeasure2.setVisibility(View.GONE);
					tvMeasUnit2.setVisibility(View.GONE);
					break;
				case 5:
					tvMeasUnit1.setText(stLsMeasUnits.get(posSpinner));
					tvMeasure2.setVisibility(View.GONE);
					etMeasure2.setVisibility(View.GONE);
					tvMeasUnit2.setVisibility(View.GONE);
					break;
				case 6:
					tvMeasUnit1.setText(stLsMeasUnits.get(posSpinner));
					tvMeasure2.setVisibility(View.GONE);
					etMeasure2.setVisibility(View.GONE);
					tvMeasUnit2.setVisibility(View.GONE);
					break;
				case 7:
					tvMeasUnit1.setText(stLsMeasUnits.get(posSpinner));
					tvMeasure2.setVisibility(View.GONE);
					etMeasure2.setVisibility(View.GONE);
					tvMeasUnit2.setVisibility(View.GONE);
					break;
				case 8:
					tvMeasUnit1.setText(stLsMeasUnits.get(posSpinner));
					tvMeasure2.setVisibility(View.GONE);
					etMeasure2.setVisibility(View.GONE);
					tvMeasUnit2.setVisibility(View.GONE);
					break;
				case 9:
					tvMeasUnit1.setText(stLsMeasUnits.get(posSpinner));
					tvMeasure2.setVisibility(View.GONE);
					etMeasure2.setVisibility(View.GONE);
					tvMeasUnit2.setVisibility(View.GONE);
					break;
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btSave.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick (View v){
				stMeasUnit = tvMeasUnit1.getText().toString();
				dbMeasure1 = Double.parseDouble(etMeasure1.getText().toString());
				selectedTypeId = spType.getSelectedItemPosition();
				
				if (selectedTypeId != 3) {
					register = indicatorDAO.register(new Indicator(
							0,
							selectedTypeId,
							userId,
							dbMeasure1,
							0.0,
							stMeasUnit,
							stCurrentDate,
							stCurrentTime
							));
				}
				else {
					dbMeasure2 = Double.parseDouble(etMeasure2.getText().toString());
					register = indicatorDAO.register(new Indicator(
							0,
							selectedTypeId,
							userId,
							dbMeasure1,
							dbMeasure2,
							stMeasUnit,
							stCurrentDate,
							stCurrentTime
							));
				}
				
				if (register) {
					Toast.makeText(getApplicationContext(), R.string.toastIndOk, Toast.LENGTH_LONG).show();
					finish();
				}
				else {
					Toast.makeText(getApplicationContext(), R.string.toastIndFalha, Toast.LENGTH_LONG).show();
				}
					
			}
		});
	}
}
