package android.palharini.myhealth;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.palharini.myhealth.dao.IndicadorDAO;
import android.palharini.myhealth.datas.Timestamp;
import android.palharini.myhealth.entidades.Indicador;
import android.palharini.myhealth.sessao.GerenciamentoSessao;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TelaCadastroIndicador extends Activity {

	private Timestamp ts;
	private GerenciamentoSessao sessao;
	
	private IndicadorDAO indDAO;
	
	private int idUsuario;
	private String dataAtual, horarioAtual, unidadeString;
	private Double dbMedida1, dbMedida2;
	
	private Spinner spTipo;
	private EditText etMedida1, etMedida2;
	private TextView tvUnidade1, tvUnidade2, tvMedida2;
	private Button btSalvar;
	
	private ArrayAdapter<String> adTipos;
	private List<String> lsUnidades;
	private String[] arrUnidades;
	private int idTipoSelecionado;
	
	private boolean cadastro;
	
	private Intent tipo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_cadastro_indicador);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		tipo = getIntent();
		
		ts = new Timestamp();
		sessao = new GerenciamentoSessao(getApplicationContext());
		
		indDAO = new IndicadorDAO();
		
		idUsuario = sessao.getIdUsuario();
		dataAtual = ts.getDataSQL();
		horarioAtual = ts.getHorarioSQL();
		
		spTipo = (Spinner) findViewById(R.id.spTipo);
		adTipos = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.lsTipos));
		adTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spTipo.setAdapter(adTipos);
		
		etMedida1 = (EditText) findViewById(R.id.etMedida1);
		tvUnidade1 = (TextView) findViewById(R.id.tvUnidade1);
		
		tvMedida2 = (TextView) findViewById(R.id.tvMedida2);
		etMedida2 = (EditText) findViewById(R.id.etMedida2);
		tvUnidade2 = (TextView) findViewById(R.id.tvUnidade2);
		
		btSalvar = (Button) findViewById(R.id.btSalvar);
		
		spTipo.setSelection(tipo.getIntExtra("tipoSelecionado", 0));
				
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
		
		btSalvar.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick (View v){
				unidadeString = tvUnidade1.getText().toString();
				dbMedida1 = Double.parseDouble(etMedida1.getText().toString());
				idTipoSelecionado = spTipo.getSelectedItemPosition();
				
				if (idTipoSelecionado != 3) {	
					cadastro = indDAO.cadastrarIndicador(new Indicador(
							0, 
							idTipoSelecionado,
							idUsuario,
							dbMedida1,
							0.0,
							unidadeString,
							dataAtual,
							horarioAtual
							));
				}
				else {
					dbMedida2 = Double.parseDouble(etMedida2.getText().toString());
					cadastro = indDAO.cadastrarIndicador(new Indicador(
							0, 
							idTipoSelecionado,
							idUsuario,
							dbMedida1,
							dbMedida2, 
							unidadeString,
							dataAtual,
							horarioAtual
							));
				}
				
				if (cadastro) {
					Toast.makeText(getApplicationContext(), R.string.toastIndOk, Toast.LENGTH_LONG).show();
					Intent irTelaPrincipal = new Intent(getApplicationContext(), TelaPrincipal.class);
					startActivity(irTelaPrincipal);
				}
				else {
					Toast.makeText(getApplicationContext(), R.string.toastIndFalha, Toast.LENGTH_LONG).show();
				}
					
			}
		});
	}
}
