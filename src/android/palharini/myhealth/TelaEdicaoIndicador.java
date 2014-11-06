package android.palharini.myhealth;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.palharini.myhealth.daos.IndicadorDAO;
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
	private EditText etMedida;
	private TextView tvUnidade;
	private Button btSalvar, btExcluir;
	
	private Integer indicSelecionado, posSpinner;
	private ArrayAdapter<String> adTipos;
	private String[] arrUnidades;
	private List<String> lsUnidades;
	
	private Boolean blAtualizar, blExcluir;
	
	private Intent dados, voltarAbas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_cadastro_indicador);
		
		dados = getIntent();
		indicSelecionado = dados.getIntExtra("idIndicador", 0);
				
		spTipo = (Spinner) findViewById(R.id.spTipo);
		etMedida = (EditText) findViewById(R.id.editMedicao);
		tvUnidade = (TextView) findViewById(R.id.textUnidade);
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
					tvUnidade.setText(lsUnidades.get(posSpinner));
					break;
				case 1:
					tvUnidade.setText(lsUnidades.get(posSpinner));
					break;
				case 2:
					tvUnidade.setText(lsUnidades.get(posSpinner));
					break;
				case 3:
					tvUnidade.setText(lsUnidades.get(posSpinner));
					break;
				case 4:
					tvUnidade.setText(lsUnidades.get(posSpinner));
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
		etMedida.setText(indicador.getMedida().toString());
		
		tvUnidade.setText(lsUnidades.get(posSpinner));
		
		btSalvar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick (View v){
				blAtualizar = indDAO.atualizarIndicador(new Indicador(
						indicador.getId(), 
						posSpinner,
						Double.parseDouble(etMedida.getText().toString()), 
						lsUnidades.get(posSpinner)
				));
				
				if (blAtualizar) {
					Toast.makeText(getApplicationContext(), getString(R.string.toastIndAtOK), Toast.LENGTH_LONG).show();
					voltarAbas = new Intent(getApplicationContext(), TelaIndicadores.class);
					startActivity(voltarAbas);
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
					voltarAbas = new Intent(getApplicationContext(), TelaIndicadores.class);
					startActivity(voltarAbas);
					finish();
				}
				else {
					Toast.makeText(getApplicationContext(), getString(R.string.toastIndExcFalha), Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}
}