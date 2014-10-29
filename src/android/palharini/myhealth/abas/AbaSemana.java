package android.palharini.myhealth.abas;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.palharini.myhealth.R;
import android.palharini.myhealth.daos.IndicadorDAO;
import android.palharini.myhealth.datas.Timestamp;
import android.palharini.myhealth.entidades.Indicador;
import android.palharini.myhealth.sessao.GerenciamentoSessao;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;


public class AbaSemana extends Fragment {

	int x;
	int difData = 30;
	String periodo = "DAY";
	
	Double media;
	ArrayList<Double> medias = new ArrayList<Double>();
	ArrayList<Integer> datas = new ArrayList<Integer>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_aba, container, false);
		
		XYPlot grafico = (XYPlot) view.findViewById(R.id.xyPlot);
		ListView listaInd = (ListView) view.findViewById(R.id.listViewInd);
		
		Intent intent = getActivity().getIntent();
		int tipoSelecionado = intent.getIntExtra("tipoSelecionado", 0);
		
		GerenciamentoSessao sessao = new GerenciamentoSessao(getActivity());
		Timestamp ts = new Timestamp();
		IndicadorDAO dao = new IndicadorDAO();
		
		for (x=difData; x==1; x--) {
			media = dao.buscarMediaPeriodo(
					sessao.getIdUsuario(), tipoSelecionado, periodo, ts.getDataAtual(), x);
			medias.add(media);
			datas.add(x);
		}
		
		XYSeries serieMedias = new SimpleXYSeries(medias, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Médias");
		XYSeries serieDatas = new SimpleXYSeries(datas, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Datas");
		
		LineAndPointFormatter formatoMedias = new LineAndPointFormatter();
		formatoMedias.setPointLabelFormatter(new PointLabelFormatter());
		formatoMedias.configure(getActivity(), R.xml.formato_serie_medias);
		grafico.addSeries(serieMedias, formatoMedias);

		LineAndPointFormatter formatoDatas = new LineAndPointFormatter();
		formatoDatas.setPointLabelFormatter(new PointLabelFormatter());
		formatoDatas.configure(getActivity(), R.xml.formato_serie_datas);
		grafico.addSeries(serieDatas, formatoDatas);
		
		grafico.setTicksPerRangeLabel(1);
		grafico.getGraphWidget().setDomainLabelOrientation(-45);
		
		ArrayList<Indicador> indicadores = dao.buscarIndicadoresPeriodoTipo(
				sessao.getIdUsuario(), tipoSelecionado, periodo, ts.getDataAtualBusca(), difData);
		
		ArrayAdapter<Indicador> adapterInd = new ArrayAdapter<Indicador>(
				getActivity(), android.R.layout.simple_list_item_1, indicadores);
		
		listaInd.setAdapter(adapterInd);
		
		return view;
	}

}