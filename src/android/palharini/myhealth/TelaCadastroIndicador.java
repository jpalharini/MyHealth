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
	
	private Spinner tipo;
	private EditText medida;
	private TextView unidade;
	private Button salvarButton;
	
	private ArrayAdapter<String> tipos;
	private List<String> unidades;
	private String[] listaUnidades;
	private int posicaoSpinner;
	
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
		
		tipo = (Spinner) findViewById(R.id.spinnerTipo);
		tipos = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.listaTipos));
		tipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		tipo.setAdapter(tipos);
		
		medida = (EditText) findViewById(R.id.editMedicao);
		unidade = (TextView) findViewById(R.id.textUnidade);
		salvarButton = (Button) findViewById(R.id.cadastraButton);
				
		tipo.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int posicaoSpinner, long id) {
				// TODO Auto-generated method stub
				listaUnidades = getResources().getStringArray(R.array.listaUnidades);
				unidades = Arrays.asList(listaUnidades);
				switch (posicaoSpinner) {
				case 0:
					unidade.setText(unidades.get(posicaoSpinner));
					break;
				case 1:
					unidade.setText(unidades.get(posicaoSpinner));
					break;
				case 2:
					unidade.setText(unidades.get(posicaoSpinner));
					break;
				case 3:
					unidade.setText(unidades.get(posicaoSpinner));
					break;
				case 4:
					unidade.setText(unidades.get(posicaoSpinner));
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		unidadeString = unidade.getText().toString();
		medidaString = Double.parseDouble(medida.getText().toString());
		
		salvarButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick (View v){
				cadastro = indDAO.cadastrarIndicador(new Indicador(
						0, 
						posicaoSpinner,
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
