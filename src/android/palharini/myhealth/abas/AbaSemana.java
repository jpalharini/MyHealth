package android.palharini.myhealth.abas;

import android.app.ListFragment;
import android.os.Bundle;
import android.palharini.myhealth.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;


public class AbaSemana extends ListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ListAdapter listAdapter = new ArrayAdapter<String>(
				getActivity(), 
				android.R.layout.simple_list_item_1, 
				getResources().getStringArray(R.array.listaTipos));
		
		setListAdapter(listAdapter);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_aba, container, false);
	}

}
