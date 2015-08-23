package android.palharini.myhealth.activities.edit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.palharini.myhealth.R;
import android.palharini.myhealth.db.dao.IndicatorDAO;
import android.palharini.myhealth.db.entities.Indicator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.Arrays;
import java.util.List;

public class IndicatorEdit extends Activity {

	private Indicator indicator;
	private IndicatorDAO indicatorDAO;
	
	private Spinner spType;
	private EditText etMeasure1, etMeasure2;
	private TextView tvMeasUnit1, tvMeasUnit2, tvMeasure2;
	private Button btSave;
	private Button btDelete;
	
	private Integer intSelectedIndicator, intSelectedIndicatorID;
	private ArrayAdapter<String> adTypes;
	private String[] arrMeasUnits;
	private List<String> lsMeasUnits;
	
	private Boolean blUpdate, blDelete;
	private String stMeasUnit;
	private Double dbMeasure1, dbMeasure2;
	
	private Intent itData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_indicator_edit);

        // Gets data from call on monitoring tabs
		itData = getIntent();
		intSelectedIndicator = itData.getIntExtra("idIndicador", 0);

        // Instantiating screen elements
		spType = (Spinner) findViewById(R.id.spTipo);
		etMeasure1 = (EditText) findViewById(R.id.etMedida1);
		tvMeasUnit1 = (TextView) findViewById(R.id.tvUnidade1);
		tvMeasure2 = (TextView) findViewById(R.id.tvMedida2);
		etMeasure2 = (EditText) findViewById(R.id.etMedida2);
		tvMeasUnit2 = (TextView) findViewById(R.id.tvUnidade2);
		btSave = (Button) findViewById(R.id.btSave);
		btDelete = (Button) findViewById(R.id.btExcluir);

        // Setting up spinner for type selection
		adTypes = new ArrayAdapter<>(
				this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.lsTypes));
		adTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spType.setAdapter(adTypes);
		
		spType.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int posSpinner, long id) {
				// TODO Auto-generated method stub
				arrMeasUnits = getResources().getStringArray(R.array.lsUnidades);
				lsMeasUnits = Arrays.asList(arrMeasUnits);
				switch (posSpinner) {
				case 0:
					tvMeasUnit1.setText(lsMeasUnits.get(posSpinner));
					tvMeasure2.setVisibility(View.GONE);
					etMeasure2.setVisibility(View.GONE);
					tvMeasUnit2.setVisibility(View.GONE);
					break;
				case 1:
					tvMeasUnit1.setText(lsMeasUnits.get(posSpinner));
					tvMeasure2.setVisibility(View.GONE);
					etMeasure2.setVisibility(View.GONE);
					tvMeasUnit2.setVisibility(View.GONE);
					break;
				case 2:
					tvMeasUnit1.setText(lsMeasUnits.get(posSpinner));
					tvMeasure2.setVisibility(View.GONE);
					etMeasure2.setVisibility(View.GONE);
					tvMeasUnit2.setVisibility(View.GONE);
					break;
				case 3:
					tvMeasUnit1.setText(lsMeasUnits.get(posSpinner));
					tvMeasUnit2.setText("");
					tvMeasure2.setVisibility(View.VISIBLE);
					etMeasure2.setVisibility(View.VISIBLE);
					tvMeasUnit2.setVisibility(View.VISIBLE);
					break;
				case 4:
					tvMeasUnit1.setText(lsMeasUnits.get(posSpinner));
					tvMeasure2.setVisibility(View.GONE);
					etMeasure2.setVisibility(View.GONE);
					tvMeasUnit2.setVisibility(View.GONE);
					break;
				case 5:
					tvMeasUnit1.setText(lsMeasUnits.get(posSpinner));
					tvMeasure2.setVisibility(View.GONE);
					etMeasure2.setVisibility(View.GONE);
					tvMeasUnit2.setVisibility(View.GONE);
					break;
				case 6:
					tvMeasUnit1.setText(lsMeasUnits.get(posSpinner));
					tvMeasure2.setVisibility(View.GONE);
					etMeasure2.setVisibility(View.GONE);
					tvMeasUnit2.setVisibility(View.GONE);
					break;
				case 7:
					tvMeasUnit1.setText(lsMeasUnits.get(posSpinner));
					tvMeasure2.setVisibility(View.GONE);
					etMeasure2.setVisibility(View.GONE);
					tvMeasUnit2.setVisibility(View.GONE);
					break;
				case 8:
					tvMeasUnit1.setText(lsMeasUnits.get(posSpinner));
					tvMeasure2.setVisibility(View.GONE);
					etMeasure2.setVisibility(View.GONE);
					tvMeasUnit2.setVisibility(View.GONE);
					break;
				case 9:
					tvMeasUnit1.setText(lsMeasUnits.get(posSpinner));
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
				
		arrMeasUnits = getResources().getStringArray(R.array.lsUnidades);
		lsMeasUnits = Arrays.asList(arrMeasUnits);
		
		indicatorDAO = new IndicatorDAO();
		indicator = indicatorDAO.buscarIndicadorId(intSelectedIndicator);
		
		spType.setSelection(indicator.getTypeID());
		etMeasure1.setText(indicator.getMeasure1().toString());
		
		btSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v){
				dbMeasure1 = Double.parseDouble(etMeasure1.getText().toString());
				stMeasUnit = tvMeasUnit1.getText().toString();
				intSelectedIndicatorID = spType.getSelectedItemPosition();
				
				if (intSelectedIndicatorID !=3) {
					blUpdate = indicatorDAO.atualizarIndicador(new Indicator(
							indicator.getId(),
                            intSelectedIndicatorID,
                            dbMeasure1,
							0.0,
                            stMeasUnit
					));
				}
				else {
					dbMeasure2 = Double.parseDouble(etMeasure2.getText().toString());
					blUpdate = indicatorDAO.atualizarIndicador(new Indicator(
							indicator.getId(),
                            intSelectedIndicatorID,
                            dbMeasure1,
                            dbMeasure2,
                            stMeasUnit
					));
				}
				
				if (blUpdate) {
					Toast.makeText(getApplicationContext(), getString(R.string.toastIndAtOK), Toast.LENGTH_LONG).show();
					finish();
				}
				else {
					Toast.makeText(getApplicationContext(), getString(R.string.toastIndAtFalha), Toast.LENGTH_LONG).show();
				}
			}
		});
		
		btDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v){
				blDelete = indicatorDAO.excluirIndicador(indicator.getId());
				
				if (blDelete) {
					Toast.makeText(getApplicationContext(), getString(R.string.toastIndExcOK), Toast.LENGTH_LONG).show();
					finish();
				}
				else {
					Toast.makeText(getApplicationContext(), getString(R.string.toastIndExcFalha), Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}
}