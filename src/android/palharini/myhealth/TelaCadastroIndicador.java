package android.palharini.myhealth;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.palharini.myhealth.daos.IndicadorDAO;
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
	private Double medidaString;
	
	private Spinner spTipo;
	private EditText etMedida;
	private TextView tvUnidade;
	private Button btSalvar;
	
	private ArrayAdapter<String> adTipos;
	private List<String> lsUnidades;
	private String[] arrUnidades;
	private int posSpinner;
	
	private boolean cadastro;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_cadastro_indicador);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
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
		
		etMedida = (EditText) findViewById(R.id.editMedicao);
		tvUnidade = (TextView) findViewById(R.id.textUnidade);
		btSalvar = (Button) findViewById(R.id.btSalvar);
				
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
		
		unidadeString = tvUnidade.getText().toString();
		medidaString = Double.parseDouble(etMedida.getText().toString());
		
		btSalvar.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick (View v){
				cadastro = indDAO.cadastrarIndicador(new Indicador(
						0, 
						posSpinner,
						idUsuario,
						medidaString, 
						unidadeString,
						dataAtual,
						horarioAtual
						));
				
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
