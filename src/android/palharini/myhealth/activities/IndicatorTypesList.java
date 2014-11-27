package android.palharini.myhealth.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.palharini.myhealth.R;
import android.palharini.myhealth.fragments.tabs.adapters.TabsAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class IndicatorTypesList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_indicator_typeslist);
		
		final ListView lViewTipos = (ListView) findViewById(R.id.listViewInd);
		
		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_list_item_1, 
				getResources().getStringArray(R.array.lsTipos));
		
		lViewTipos.setAdapter(listAdapter);
		
		lViewTipos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int posicao, long arg3) {
				// TODO Auto-generated method stub
				Intent irTelaGrafico = new Intent(IndicatorTypesList.this, TabsAdapter.class);
				irTelaGrafico.putExtra("tipoSelecionado", posicao);
				startActivity(irTelaGrafico);
			}
		});
		
	}
}
