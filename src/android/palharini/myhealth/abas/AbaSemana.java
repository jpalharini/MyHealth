package android.palharini.myhealth.abas;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Intent;
import android.graphics.Color;
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

import com.androidplot.ui.SizeLayoutType;
import com.androidplot.ui.SizeMetrics;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;


public class AbaSemana extends Fragment {

	int x;
	Integer	 difData = 7;
	String periodo = "DAY";
	
	Double media;
	Double[] medias = new Double[difData+1];
	Integer[] datas = new Integer[difData+1];
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_aba, container, false);
		
		XYPlot grafico = (XYPlot) view.findViewById(R.id.xyPlot);
		grafico.getBackgroundPaint().setColor(Color.WHITE);
		grafico.getGraphWidget().getBackgroundPaint().setColor(Color.WHITE);
		grafico.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
        grafico.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
        grafico.setPlotMargins(0, 0, 0, 0);
        grafico.setPlotPadding(20, 20, 20, 20);
        grafico.setGridPadding(10, 20, 10, 00);
        grafico.getGraphWidget().setSize(new SizeMetrics(0, SizeLayoutType.FILL, 0, SizeLayoutType.FILL));
        grafico.getLayoutManager().remove(grafico.getLegendWidget());
        grafico.getLayoutManager().remove(grafico.getDomainLabelWidget());
        grafico.getLayoutManager().remove(grafico.getRangeLabelWidget());
        grafico.getLayoutManager().remove(grafico.getTitleWidget());    
		grafico.setTicksPerRangeLabel(1);
		grafico.setTicksPerDomainLabel(1);
		
		ListView listaInd = (ListView) view.findViewById(R.id.listViewInd);
		
		Intent intent = getActivity().getIntent();
		int tipoSelecionado = intent.getIntExtra("tipoSelecionado", 0);
		
		GerenciamentoSessao sessao = new GerenciamentoSessao(getActivity());
		Timestamp ts = new Timestamp();
		IndicadorDAO dao = new IndicadorDAO();
		
		for (x=difData; x>=1; x--) {
			media = dao.buscarMediaPeriodo(
					sessao.getIdUsuario(), tipoSelecionado, periodo, ts.getDataAtualBusca(), x);
			medias[x] = media;
			datas[x] = x;
		}
		
		XYSeries serieMedias = new SimpleXYSeries (Arrays.asList(datas), Arrays.asList(medias), "Médias");
		
		
		LineAndPointFormatter formatoMedias = new LineAndPointFormatter(
				Color.RED, 
				Color.RED, 
				Color.TRANSPARENT, 
				null);
		formatoMedias.setPointLabelFormatter(new PointLabelFormatter());
		formatoMedias.configure(getActivity(), R.xml.formato_serie_medias);
		
		grafico.addSeries(serieMedias, formatoMedias);		
		
		ArrayList<Indicador> indicadores = (ArrayList<Indicador>) dao.buscarIndicadoresPeriodoTipo(
				sessao.getIdUsuario(), tipoSelecionado, periodo, ts.getDataAtualBusca(), difData);
		
		ArrayAdapter<Indicador> adapterInd = new ArrayAdapter<Indicador>(
				getActivity(), android.R.layout.simple_list_item_1, indicadores);
		
		listaInd.setAdapter(adapterInd);
		
		return view;
	}

}