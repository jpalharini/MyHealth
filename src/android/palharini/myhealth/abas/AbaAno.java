package android.palharini.myhealth.abas;

import android.os.Bundle;
import android.palharini.myhealth.R;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class AbaAno extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_aba, container, false);

	    final ListView lViewTipos = (ListView) view.findViewById(R.id.listViewInd);
		
		ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(
				getActivity(), 
				android.R.layout.simple_list_item_1, 
				getResources().getStringArray(R.array.listaTipos));
		
		lViewTipos.setAdapter(listAdapter);
		
		return view;
	}

}