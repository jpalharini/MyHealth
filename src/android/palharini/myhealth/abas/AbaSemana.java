package android.palharini.myhealth.abas;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.palharini.myhealth.R;
import android.palharini.myhealth.TelaEdicaoIndicador;
import android.palharini.myhealth.TelaGrafico;
import android.palharini.myhealth.daos.IndicadorDAO;
import android.palharini.myhealth.datas.Timestamp;
import android.palharini.myhealth.entidades.Indicador;
import android.palharini.myhealth.sessao.GerenciamentoSessao;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class AbaSemana extends Fragment {
	
	Integer x;
	Integer	difData = 7;
	String periodo = "DAY";
	
	Double media;
	Double[] medias = new Double[difData+1];
	Integer[] datas = new Integer[difData+1];
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_aba_semana, container, false);
		
		GerenciamentoSessao sessao = new GerenciamentoSessao(getActivity());
		Timestamp ts = new Timestamp();
		IndicadorDAO dao = new IndicadorDAO();
		
		final ListView listaInd = (ListView) view.findViewById(R.id.listViewInd);
		
		final Button buttonGrafico = (Button) view.findViewById(R.id.btGrafico);
		
		Intent intent = getActivity().getIntent();
		final Integer tipoSelecionado = intent.getIntExtra("tipoSelecionado", 0);
		
		buttonGrafico.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent irTelaGrafico = new Intent(getActivity(), TelaGrafico.class);
				irTelaGrafico.putExtra("difData", difData);
				irTelaGrafico.putExtra("periodo", periodo);
				irTelaGrafico.putExtra("tipoSelecionado", tipoSelecionado);
				startActivity(irTelaGrafico);
			}
		});
		
		Integer idUsuario = sessao.getIdUsuario();
		String dataBusca = ts.getDataAtualBusca();
		
		final ArrayList<Indicador> indicadores = (ArrayList<Indicador>) dao.buscarIndicadoresPeriodoTipo(
				idUsuario, tipoSelecionado, periodo, dataBusca, difData);
		
		ArrayAdapter<Indicador> adapterInd = new ArrayAdapter<Indicador>(
				getActivity(), android.R.layout.simple_list_item_1, indicadores);
		
		listaInd.setAdapter(adapterInd);
		
		listaInd.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
				// TODO Auto-generated method stub
				Indicador indicSelecionado = new Indicador();
				indicSelecionado = (Indicador) listaInd.getItemAtPosition(position);
				Intent irTelaEdicaoIndicador = new Intent(getActivity(), TelaEdicaoIndicador.class);
				irTelaEdicaoIndicador.putExtra("idIndicador", indicSelecionado.getId());
				startActivity(irTelaEdicaoIndicador);
			}
		});
		return view;
	}

}