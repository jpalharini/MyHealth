package android.palharini.myhealth;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TelaCadastroIndicador extends Activity implements OnItemSelectedListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_cadastro_indicador);
		
		final Spinner tipo = (Spinner) findViewById(R.id.spinnerTipo);
		final EditText medicao = (EditText) findViewById(R.id.editMedicao);
		
		ArrayAdapter<String> tipos = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.listaTipos));
		tipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		tipo.setAdapter(tipos);
		
		tipo.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int posicao, long id) {
		// TODO Auto-generated method stub
		String tipoSelecionado = parent.getItemAtPosition(posicao).toString();
		if (posicao == 0) {
			final TextView unidade = (TextView) findViewById(R.id.textUnidade);
			unidade.setText("kg");
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
