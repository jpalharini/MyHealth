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

public class TelaCadastroIndicador extends Activity implements OnItemSelectedListener{

	TextView unidade;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_cadastro_indicador);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		unidade = (TextView) findViewById(R.id.textUnidade);
		final Spinner tipo = (Spinner) findViewById(R.id.spinnerTipo);
		final EditText medida = (EditText) findViewById(R.id.editMedicao);
		
		final Button okButton = (Button) findViewById(R.id.cadastraButton);
	    
		final Timestamp ts = new Timestamp();
		
		final GerenciamentoSessao sessao = new GerenciamentoSessao(getApplicationContext());
		
		ArrayAdapter<String> tipos = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.listaTipos));
		tipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		tipo.setAdapter(tipos);
		
		tipo.setOnItemSelectedListener(this);
				
		final int tipoSelecionadoId = (int) (long) tipo.getSelectedItemId();
		
		okButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick (View v){

				IndicadorDAO dao = new IndicadorDAO();
				boolean cadastro = dao.cadastrarIndicador(new Indicador(
						0, 
						tipoSelecionadoId,
						sessao.getIdUsuario(),
						Double.parseDouble(medida.getText().toString()), 
						unidade.getText().toString(),
						ts.getDataSQL(),
						ts.getHorarioSQL()
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

	public void onItemSelected(AdapterView<?> parent, View view, int posicao, long id) {
		// TODO Auto-generated method stub
		String[] listaUnidades = getResources().getStringArray(R.array.listaUnidades);
		final List<String> unidades = Arrays.asList(listaUnidades);
		switch (posicao) {
		case 0:
			unidade.setText(unidades.get(posicao));
			break;
		case 1:
			unidade.setText(unidades.get(posicao));
			break;
		case 2:
			unidade.setText(unidades.get(posicao));
			break;
		case 3:
			unidade.setText(unidades.get(posicao));
			break;
		case 4:
			unidade.setText(unidades.get(posicao));
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
