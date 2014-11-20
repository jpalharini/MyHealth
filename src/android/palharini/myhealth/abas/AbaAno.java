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

public class AbaAno extends Fragment {
	
	private static Integer difData = 12;
	private static String periodo = "MONTH";
	
	private GerenciamentoSessao sessao;
	private Timestamp ts;
	
	private IndicadorDAO indDAO;
	
	private ListView lvIndicadores;
	private Integer intTipoIndicador;
	private int x, y=0;
	
	private String stDataBusca;
	private Double dbMedia, vtMedias1[], vtMedias2[];
	private Integer intIdUsuario, vtDatas[];
	private ArrayList<Indicador> arrIndicadores;
	private ArrayAdapter<Indicador> adIndicadores;
	
	private XYPlot grafico;
	private XYSeries grafSerie1, grafSerie2;
	private LineAndPointFormatter grafFormat;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_aba_semana, container, false);
		
		sessao = new GerenciamentoSessao(getActivity());
		ts = new Timestamp();
		indDAO = new IndicadorDAO();
		
		lvIndicadores = (ListView) view.findViewById(R.id.listViewInd);
				
		grafico = (XYPlot) view.findViewById(R.id.xyPlot);
		
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
		intTipoIndicador = intent.getIntExtra("tipoSelecionado", 0);
		
		intIdUsuario = sessao.getIdUsuario();
		stDataBusca = ts.getDataAtualBusca();
		
		vtMedias1 = new Double[difData+1];
		vtMedias2 = new Double[difData+1];
		
		vtDatas = new Integer[difData+1];
		
		for (x = difData; x>=0; x--) {
			dbMedia = indDAO.buscarMedia1Periodo(
					intTipoIndicador, intIdUsuario, periodo, stDataBusca, x);
			if (dbMedia > 0) {
				vtMedias1[x] = dbMedia;
				vtDatas[x] = x;
				y = x;
			}
			else {
				vtMedias1[x] = vtMedias1[y];
				vtDatas[x] = x;
				
			}
		}
		
		grafSerie1 = new SimpleXYSeries (Arrays.asList(vtDatas), Arrays.asList(vtMedias1), "Médias");
		
		grafFormat = new LineAndPointFormatter(
				Color.RED, 
				Color.RED, 
				Color.TRANSPARENT, 
				null);
		grafFormat.setPointLabelFormatter(new PointLabelFormatter());
		grafFormat.configure(getActivity(), R.xml.formato_serie_medias);
		
		grafico.addSeries(grafSerie1, grafFormat);
		
		if (intTipoIndicador == 3) {
			for (int x = difData; x>=0; x--) {
				dbMedia = indDAO.buscarMedia2Periodo(
						intTipoIndicador, intIdUsuario, periodo, stDataBusca, x);
				if (dbMedia > 0) {
					vtMedias2[x] = dbMedia;
					vtDatas[x] = x;
					y = x;
				}
				else {
					vtMedias2[x] = vtMedias2[y];
					vtDatas[x] = x;
					
				}
			}
			grafSerie2 = new SimpleXYSeries (Arrays.asList(vtDatas), Arrays.asList(vtMedias2), "Médias");
			grafico.addSeries(grafSerie2, grafFormat);			
		}
		
		arrIndicadores = (ArrayList<Indicador>) indDAO.buscarIndicadoresPeriodoTipo(
				intIdUsuario, intTipoIndicador, periodo, stDataBusca, difData);
		
		adIndicadores = new ArrayAdapter<Indicador>(
				getActivity(), android.R.layout.simple_list_item_1, arrIndicadores);
		
		lvIndicadores.setAdapter(adIndicadores);
		
		lvIndicadores.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
				// TODO Auto-generated method stub
				Indicador indicSelecionado = new Indicador();
				indicSelecionado = (Indicador) lvIndicadores.getItemAtPosition(position);
				Intent irTelaEdicaoIndicador = new Intent(getActivity(), TelaEdicaoIndicador.class);
				irTelaEdicaoIndicador.putExtra("idIndicador", indicSelecionado.getId());
				startActivity(irTelaEdicaoIndicador);
				getActivity().finish();
			}
		});
		return view;
	}

}