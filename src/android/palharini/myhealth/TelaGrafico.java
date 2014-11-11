package android.palharini.myhealth;

import java.util.Arrays;

import com.androidplot.ui.SizeLayoutType;
import com.androidplot.ui.SizeMetrics;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.palharini.myhealth.dao.IndicadorDAO;
import android.palharini.myhealth.datas.Timestamp;
import android.palharini.myhealth.sessao.GerenciamentoSessao;

public class TelaGrafico extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_grafico);
		
		Intent intent = getIntent();
		Integer difData = intent.getIntExtra("difData", 0);
		String periodo = intent.getStringExtra("periodo");
		Integer tipoSelecionado = intent.getIntExtra("tipoSelecionado", 0);
		
		Integer x;
		Double media;
		Double[] medias = new Double[difData+1];
		Integer[] datas = new Integer[difData+1];
		
		XYPlot grafico = (XYPlot) findViewById(R.id.xyPlot);
		grafico.getBackgroundPaint().setColor(Color.WHITE);
		grafico.getGraphWidget().getBackgroundPaint().setColor(Color.WHITE);
		grafico.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
        grafico.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
        grafico.setPlotMargins(0, 0, 0, 0);
        grafico.setPlotPadding(25, 10, 20, 0);
        grafico.setGridPadding(20, 30, 20, 10);
        grafico.getGraphWidget().setSize(new SizeMetrics(0, SizeLayoutType.FILL, 0, SizeLayoutType.FILL));
        grafico.getLayoutManager().remove(grafico.getLegendWidget());
        grafico.getLayoutManager().remove(grafico.getDomainLabelWidget());
        grafico.getLayoutManager().remove(grafico.getRangeLabelWidget());
        grafico.getLayoutManager().remove(grafico.getTitleWidget());  
        grafico.getLayoutManager().getPaddingPaint().setColor(Color.TRANSPARENT);
		grafico.setTicksPerRangeLabel(1);
		grafico.setTicksPerDomainLabel(1);
		grafico.getGraphWidget().setMarginBottom(15);
		
		GerenciamentoSessao sessao = new GerenciamentoSessao(getApplicationContext());
		Timestamp ts = new Timestamp();
		IndicadorDAO dao = new IndicadorDAO();
		Integer idUsuario = sessao.getIdUsuario();
		String dataAtual = ts.getDataAtualBusca();
		int y=0;
		
		for (x=difData; x>=0; x--) {
			media = dao.buscarMediaPeriodo(
					tipoSelecionado, idUsuario, periodo, dataAtual, x);
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
		formatoMedias.configure(getApplicationContext(), R.xml.formato_serie_medias);
		
		grafico.addSeries(serieMedias, formatoMedias);
	}
}
