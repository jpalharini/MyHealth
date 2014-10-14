package android.palharini.myhealth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.palharini.myhealth.daos.IndicadorDAO;
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

public class TelaCadastroIndicador extends Activity implements OnItemSelectedListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_cadastro_indicador);
		
		final Spinner tipo = (Spinner) findViewById(R.id.spinnerTipo);
		final EditText medida = (EditText) findViewById(R.id.editMedicao);
		final Button okButton = (Button) findViewById(R.id.cadastraButton);
		
		final Calendar c = Calendar.getInstance();
		final int dia = c.get(Calendar.DAY_OF_MONTH);
	    final int mes = c.get(Calendar.MONTH);
	    final int ano = c.get(Calendar.YEAR);
	    final int hora = c.get(Calendar.HOUR_OF_DAY);
	    final int minuto = c.get(Calendar.MINUTE);
	    final int segundo = c.get(Calendar.SECOND);
	    
	    final String timestamp = String.valueOf(ano) + "-" + String.valueOf(mes + 1) + "-" + String.valueOf(dia) + 
	    		" " + String.valueOf(hora) + ":" + String.valueOf(minuto) + ":" + String.valueOf(segundo);
	    
		final String formatoDataSQL = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdfSQL = new SimpleDateFormat(formatoDataSQL);
		Date timestampDate = null;
		try {
			timestampDate = sdfSQL.parse(timestamp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final String timestampSQL = sdfSQL.format(timestampDate);
		
		final GerenciamentoSessao sessao = new GerenciamentoSessao(getApplicationContext());
		
		ArrayAdapter<String> tipos = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.listaTipos));
		tipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		tipo.setAdapter(tipos);
		
		tipo.setOnItemSelectedListener(this);
		
		String[] listUnidades = getResources().getStringArray(R.array.listaUnidades);
		final List<String> unidades = Arrays.asList(listUnidades);
		
		final int tipoSelecionadoId = (int) (long) tipo.getSelectedItemId();
		
		okButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick (View v){
				IndicadorDAO dao = new IndicadorDAO();
				dao.cadastrarIndicador(new Indicador(
						0, 
						tipoSelecionadoId,
						sessao.getIdUsuario(),
						Double.parseDouble(medida.getText().toString()), 
						unidades.get(tipoSelecionadoId),
						timestampSQL
				));
			}
		});
	}

	public void onItemSelected(AdapterView<?> parent, View view, int posicao, long id) {
		// TODO Auto-generated method stub
		final TextView unidade;
		String[] listUnidades = getResources().getStringArray(R.array.listaUnidades);
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
