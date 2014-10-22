package android.palharini.myhealth.abas;

import android.os.Bundle;
import android.palharini.myhealth.R;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AbaMes extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_aba, container, false);
		
		return rootView;
	}

}
