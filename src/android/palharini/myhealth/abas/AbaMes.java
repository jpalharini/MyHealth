package android.palharini.myhealth.abas;

import java.util.ArrayList;
import java.util.Arrays;

import com.androidplot.ui.SizeLayoutType;
import com.androidplot.ui.SizeMetrics;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.palharini.myhealth.R;
import android.palharini.myhealth.TelaEdicaoIndicador;
import android.palharini.myhealth.dao.IndicadorDAO;
import android.palharini.myhealth.datas.Timestamp;
import android.palharini.myhealth.entidades.Indicador;
import android.palharini.myhealth.sessao.GerenciamentoSessao;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class AbaMes extends Fragment {
	
	private Integer	difData = 4;
	private String periodo = "WEEK";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_aba_mes, container, false);
		
		GerenciamentoSessao sessao = new GerenciamentoSessao(getActivity());
		Timestamp ts = new Timestamp();
		IndicadorDAO dao = new IndicadorDAO();
		
		final ListView listaInd = (ListView) view.findViewById(R.id.listViewInd);
				
		XYPlot grafico = (XYPlot) view.findViewById(R.id.xyPlot);
		grafico.getBackgroundPaint().setColor(Color.WHITE);
		grafico.getGraphWidget().getBackgroundPaint().setColor(Color.WHITE);
		grafico.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
        grafico.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
        grafico.setPlotMargins(0, 0, 0, 0);
        grafico.setPlotPadding(25, 10, 20, 0);
        grafico.setGridPadding(30, 30, 30, 10);
        grafico.getGraphWidget().setSize(new SizeMetrics(0, SizeLayoutType.FILL, 0, SizeLayoutType.FILL));
        grafico.getLayoutManager().remove(grafico.getLegendWidget());
        grafico.getLayoutManager().remove(grafico.getDomainLabelWidget());
        grafico.getLayoutManager().remove(grafico.getRangeLabelWidget());
        grafico.getLayoutManager().remove(grafico.getTitleWidget());  
        grafico.getLayoutManager().getPaddingPaint().setColor(Color.TRANSPARENT);
		grafico.setTicksPerRangeLabel(5);
		grafico.setTicksPerDomainLabel(1);
		grafico.getGraphWidget().setMarginBottom(15);
		
		Intent intent = getActivity().getIntent();
		final Integer tipoSelecionado = intent.getIntExtra("tipoSelecionado", 0);
		
		Integer idUsuario = sessao.getIdUsuario();
		String dataBusca = ts.getDataAtualBusca();
		
		Double[] medias = new Double[difData+1];
		Integer[] datas = new Integer[difData+1];
		int y = 0;
		for (int x = difData; x>=0; x--) {
			Double media = dao.buscarMedia1Periodo(
					tipoSelecionado, idUsuario, periodo, dataBusca, x);
			if (media > 0) {
				medias[x] = media;
				datas[x] = x;
				y = x;
			}
			else {
				medias[x] = medias[y];
				datas[x] = x;
				
			}
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