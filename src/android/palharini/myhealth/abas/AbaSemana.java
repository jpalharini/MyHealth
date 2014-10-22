package android.palharini.myhealth.abas;

import android.app.Fragment;
import android.os.Bundle;
import android.palharini.myhealth.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class AbaSemana extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_aba, container, false);
		
		final ListView listInd = (ListView) getView().findViewById(R.id.listViewInd);
		
		return rootView;
		
		
	}


}
