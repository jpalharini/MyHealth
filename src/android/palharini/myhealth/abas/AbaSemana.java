package android.palharini.myhealth.abas;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;

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
	int difData = 7;
	String periodo = "DAY";
	
	Double media;
	Double[] medias = new Double[difData];
	Integer[] datas = new Integer[difData];
	
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
			medias[x] = media;
			datas[x] = x;
		}
		
		XYSeries serieMedias = new SimpleXYSeries(Arrays.asList(medias), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Médias");
		
		LineAndPointFormatter formatoMedias = new LineAndPointFormatter();
		formatoMedias.setPointLabelFormatter(new PointLabelFormatter());
		formatoMedias.configure(getActivity(), R.xml.formato_serie_medias);
		grafico.addSeries(serieMedias, formatoMedias);

		grafico.getGraphWidget().setDomainLabelOrientation(-45);
		
		grafico.setDomainValueFormat(new Format() {

			@Override
			public StringBuffer format(Object object, StringBuffer buffer, FieldPosition field) {
				// TODO Auto-generated method stub
				return new StringBuffer(datas [((Number)object).intValue()]);
			}

			@Override
			public Object parseObject(String string, ParsePosition position) {
				// TODO Auto-generated method stub
				return null;
			}
        });
		
		ArrayList<Indicador> indicadores = (ArrayList<Indicador>) dao.buscarIndicadoresPeriodoTipo(
				sessao.getIdUsuario(), tipoSelecionado, periodo, ts.getDataAtualBusca(), difData);
		
		ArrayAdapter<Indicador> adapterInd = new ArrayAdapter<Indicador>(
				getActivity(), android.R.layout.simple_list_item_1, indicadores);
		
		listaInd.setAdapter(adapterInd);
		
		return view;
	}

}