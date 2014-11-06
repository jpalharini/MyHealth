package android.palharini.myhealth;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.palharini.myhealth.daos.IndicadorDAO;
import android.palharini.myhealth.entidades.Indicador;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TelaEdicaoIndicador extends Activity implements OnItemSelectedListener{

	private Spinner spTipo;
	private EditText etMedida;
	private TextView tvUnidade;
	private Button btSalvar, btExcluir;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_cadastro_indicador);
		
		Intent intent = getIntent();
		int indicSelecionado = intent.getIntExtra("idIndicador", 0);
				
		spTipo = (Spinner) findViewById(R.id.spTipo);
		etMedida = (EditText) findViewById(R.id.editMedicao);
		tvUnidade = (TextView) findViewById(R.id.textUnidade);
		btSalvar = (Button) findViewById(R.id.btSalvar);
		btExcluir = (Button) findViewById(R.id.btSalvar);
		
		
		ArrayAdapter<String> tipos = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.lsTipos));
		tipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spTipo.setAdapter(tipos);
		
		spTipo.setOnItemSelectedListener(this);
		
		String[] listUnidades = getResources().getStringArray(R.array.lsUnidades);
		final List<String> unidades = Arrays.asList(listUnidades);
		
		final IndicadorDAO dao = new IndicadorDAO();
		final Indicador indicador = dao.buscarIndicadorId(indicSelecionado);
		
		spTipo.setSelection(indicador.getIdTipo());
		etMedida.setText(indicador.getMedida().toString());
		
		final int tipoSelecionadoId = (int) (long) spTipo.getSelectedItemId();
		tvUnidade.setText(unidades.get(tipoSelecionadoId));
		
		
		btSalvar.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick (View v){
				dao.atualizarIndicador(new Indicador(
						indicador.getId(), 
						tipoSelecionadoId,
						Double.parseDouble(etMedida.getText().toString()), 
						unidades.get(tipoSelecionadoId)
				));
			}
		});
		
		
	}

	public void onItemSelected(AdapterView<?> parent, View view, int posicao, long id) {
		// TODO Auto-generated method stub
		final TextView unidade;
		String[] listUnidades = getResources().getStringArray(R.array.lsUnidades);
		final List<String> unidades = Arrays.asList(listUnidades);
		switch (posicao) {
		case 0:
			unidade = (TextView) findViewById(R.id.textUnidade);
			unidade.setText(unidades.get(posicao));
			break;
		case 1:
			unidade = (TextView) findViewById(R.id.textUnidade);
			unidade.setText(unidades.get(posicao));
			break;
		case 2:
			unidade = (TextView) findViewById(R.id.textUnidade);
			unidade.setText(unidades.get(posicao));
			break;
		case 3:
			unidade = (TextView) findViewById(R.id.textUnidade);
			unidade.setText(unidades.get(posicao));
			break;
		case 4:
			unidade = (TextView) findViewById(R.id.textUnidade);
			unidade.setText(unidades.get(posicao));
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}