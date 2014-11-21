package android.palharini.myhealth;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.palharini.myhealth.dao.IndicadorDAO;
import android.palharini.myhealth.entidades.Indicador;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TelaEdicaoIndicador extends Activity {

	private Indicador indicador;
	private IndicadorDAO indDAO;
	
	private Spinner spTipo;
	private EditText etMedida1, etMedida2;
	private TextView tvUnidade1, tvUnidade2, tvMedida2;
	private Button btSalvar;
	private Button btExcluir;
	
	private Integer indicSelecionado, idTipoSelecionado;
	private ArrayAdapter<String> adTipos;
	private String[] arrUnidades;
	private List<String> lsUnidades;
	
	private Boolean blAtualizar, blExcluir;
	private String stUnidade;
	private Double dbMedida1, dbMedida2;
	
	private Intent dados;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_edicao_indicador);
		
		dados = getIntent();
		indicSelecionado = dados.getIntExtra("idIndicador", 0);
				
		spTipo = (Spinner) findViewById(R.id.spTipo);
		
		etMedida1 = (EditText) findViewById(R.id.etMedida1);
		tvUnidade1 = (TextView) findViewById(R.id.tvUnidade1);
		
		tvMedida2 = (TextView) findViewById(R.id.tvMedida2);
		etMedida2 = (EditText) findViewById(R.id.etMedida2);
		tvUnidade2 = (TextView) findViewById(R.id.tvUnidade2);
		
		btSalvar = (Button) findViewById(R.id.btSalvar);
		btExcluir = (Button) findViewById(R.id.btExcluir);
		
		adTipos = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.lsTipos));
		adTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spTipo.setAdapter(adTipos);
		
		spTipo.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int posSpinner, long id) {
				// TODO Auto-generated method stub
				arrUnidades = getResources().getStringArray(R.array.lsUnidades);
				lsUnidades = Arrays.asList(arrUnidades);
				switch (posSpinner) {
				case 0:
					tvUnidade1.setText(lsUnidades.get(posSpinner));
					tvMedida2.setVisibility(View.GONE);
					etMedida2.setVisibility(View.GONE);
					tvUnidade2.setVisibility(View.GONE);
					break;
				case 1:
					tvUnidade1.setText(lsUnidades.get(posSpinner));
					tvMedida2.setVisibility(View.GONE);
					etMedida2.setVisibility(View.GONE);
					tvUnidade2.setVisibility(View.GONE);
					break;
				case 2:
					tvUnidade1.setText(lsUnidades.get(posSpinner));
					tvMedida2.setVisibility(View.GONE);
					etMedida2.setVisibility(View.GONE);
					tvUnidade2.setVisibility(View.GONE);
					break;
				case 3:
					tvUnidade1.setText(lsUnidades.get(posSpinner));
					tvUnidade2.setText("");
					tvMedida2.setVisibility(View.VISIBLE);
					etMedida2.setVisibility(View.VISIBLE);
					tvUnidade2.setVisibility(View.VISIBLE);
					break;
				case 4:
					tvUnidade1.setText(lsUnidades.get(posSpinner));
					tvMedida2.setVisibility(View.GONE);
					etMedida2.setVisibility(View.GONE);
					tvUnidade2.setVisibility(View.GONE);
					break;
				case 5:
					tvUnidade1.setText(lsUnidades.get(posSpinner));
					tvMedida2.setVisibility(View.GONE);
					etMedida2.setVisibility(View.GONE);
					tvUnidade2.setVisibility(View.GONE);
					break;
				case 6:
					tvUnidade1.setText(lsUnidades.get(posSpinner));
					tvMedida2.setVisibility(View.GONE);
					etMedida2.setVisibility(View.GONE);
					tvUnidade2.setVisibility(View.GONE);
					break;
				case 7:
					tvUnidade1.setText(lsUnidades.get(posSpinner));
					tvMedida2.setVisibility(View.GONE);
					etMedida2.setVisibility(View.GONE);
					tvUnidade2.setVisibility(View.GONE);
					break;
				case 8:
					tvUnidade1.setText(lsUnidades.get(posSpinner));
					tvMedida2.setVisibility(View.GONE);
					etMedida2.setVisibility(View.GONE);
					tvUnidade2.setVisibility(View.GONE);
					break;
				case 9:
					tvUnidade1.setText(lsUnidades.get(posSpinner));
					tvMedida2.setVisibility(View.GONE);
					etMedida2.setVisibility(View.GONE);
					tvUnidade2.setVisibility(View.GONE);
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
				
		arrUnidades = getResources().getStringArray(R.array.lsUnidades);
		lsUnidades = Arrays.asList(arrUnidades);
		
		indDAO = new IndicadorDAO();
		indicador = indDAO.buscarIndicadorId(indicSelecionado);
		
		spTipo.setSelection(indicador.getIdTipo());
		etMedida1.setText(indicador.getMedida1().toString());
		
		btSalvar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v){
				dbMedida1 = Double.parseDouble(etMedida1.getText().toString());				
				stUnidade = tvUnidade1.getText().toString();
				idTipoSelecionado = spTipo.getSelectedItemPosition();
				
				if (idTipoSelecionado !=3) {
					blAtualizar = indDAO.atualizarIndicador(new Indicador(
							indicador.getId(), 
							idTipoSelecionado,
							dbMedida1,
							0.0,
							stUnidade
					));
				}
				else {
					dbMedida2 = Double.parseDouble(etMedida2.getText().toString());
					blAtualizar = indDAO.atualizarIndicador(new Indicador(
							indicador.getId(), 
							idTipoSelecionado,
							dbMedida1,
							dbMedida2,
							stUnidade
					));
				}
				
				if (blAtualizar) {
					Toast.makeText(getApplicationContext(), getString(R.string.toastIndAtOK), Toast.LENGTH_LONG).show();
					finish();
				}
				else {
					Toast.makeText(getApplicationContext(), getString(R.string.toastIndAtFalha), Toast.LENGTH_LONG).show();
				}
			}
		});
		
		btExcluir.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v){
				blExcluir = indDAO.excluirIndicador(indicador.getId());
				
				if (blExcluir) {
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