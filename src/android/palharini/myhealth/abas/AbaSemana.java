package android.palharini.myhealth.abas;

import android.content.Intent;
import android.os.Bundle;
import android.palharini.myhealth.R;
import android.palharini.myhealth.daos.IndicadorDAO;
import android.palharini.myhealth.sessao.GerenciamentoSessao;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AbaSemana extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_aba, container, false);
		
		int x;
		int quantData = 7;
		String periodo = "DAY";
		
		Intent intent = getActivity().getIntent();
		int tipoSelecionado = intent.getIntExtra("tipoSelecionado", 0);
		
		GerenciamentoSessao sessao = new GerenciamentoSessao(getActivity());
		
		IndicadorDAO dao = new IndicadorDAO();
		
		Double media;
		
		Double[] medias = new Double[quantData];
		
		int datas[] = new int[quantData];
		
		for (x=1; x==quantData; x++) {
			media = dao.buscarMediaPeriodo(
					sessao.getIdUsuario(), tipoSelecionado, periodo, x);
			medias[x] = media;
			datas[x] = x;
		}
		
		return view;
	}

}