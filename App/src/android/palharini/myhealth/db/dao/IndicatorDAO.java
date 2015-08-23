package android.palharini.myhealth.db.dao;

import android.palharini.myhealth.db.ConnectWS;
import android.palharini.myhealth.db.MarshalDouble;
import android.palharini.myhealth.db.entities.Indicator;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Vector;

public class IndicatorDAO {
	
	private static final String classeWS = "IndicadorDAO?wsdl";
	private static final ConnectWS conexao = new ConnectWS();
	
	private static final String URL = conexao.getURL() + classeWS;
	private static final String NAMESPACE = conexao.getNamespace();
	private static final Integer TIMEOUT = conexao.getTimeout();
	
	public static final String REGISTER = "cadastrarIndicador";
	public static final String UPDATE = "atualizarIndicador";
	public static final String DELETE = "excluirIndicador";
	public static final String SEARCH_ID = "buscarIndicadorId";
	public static final String SEARCH_TYPE = "buscarIndicadorTipo";
	public static final String SEARCH_ALL_TYPES = "selectIndicatorTypes";
	public static final String SEARCH_BY_PERIOD_TYPE = "buscarIndicadoresPeriodoTipo";
	public static final String SEARCH_AVG_PERIOD_1 = "buscarMedia1Periodo";
	public static final String SEARCH_AVG_PERIOD_2 = "buscarMedia2Periodo";
	
	
	public boolean cadastrarIndicador (Indicator indicator) {
		
		SoapObject cadastrarIndicador = new SoapObject(NAMESPACE, REGISTER);
		
		SoapObject ind = new SoapObject(NAMESPACE, "indicador");
		
		ind.addProperty("id", indicator.getId());
		ind.addProperty("idTipo", indicator.getTypeID());
		ind.addProperty("idUsuario", indicator.getUserID());
		ind.addProperty("medida1", indicator.getMeasure1());
		ind.addProperty("medida2", indicator.getMeasure2());
		ind.addProperty("unidade", indicator.getMeasUnit());
		ind.addProperty("data", indicator.getStrDate());
		ind.addProperty("hora", indicator.getStrTime());

		cadastrarIndicador.addSoapObject(ind);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(cadastrarIndicador);
		envelope.implicitTypes = true;
		MarshalDouble md = new MarshalDouble();
		md.register(envelope);
		
		HttpTransportSE http = new HttpTransportSE(URL, TIMEOUT);
		
		try {
			http.call("urn:" + REGISTER, envelope);
			
			SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
			
			return Boolean.parseBoolean(resposta.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean atualizarIndicador(Indicator indicator){
		
		SoapObject atualizarIndicador = new SoapObject(NAMESPACE, UPDATE);
		
		SoapObject ind = new SoapObject(NAMESPACE, "indicador");
		
		ind.addProperty("id", indicator.getId());
		ind.addProperty("idTipo", indicator.getTypeID());
		ind.addProperty("medida1", indicator.getMeasure1());
		ind.addProperty("medida2", indicator.getMeasure2());
		ind.addProperty("unidade", indicator.getMeasUnit());
		
		atualizarIndicador.addSoapObject(ind);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(atualizarIndicador);
		envelope.implicitTypes = true;
		MarshalDouble md = new MarshalDouble();
		md.register(envelope);
		
		HttpTransportSE http = new HttpTransportSE(URL, TIMEOUT);
		
		try {
			http.call("urn:" + UPDATE, envelope);
			
			SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
			
			return Boolean.parseBoolean(resposta.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean excluirIndicador (Integer id) {
		
		SoapObject excluirIndicador = new SoapObject(NAMESPACE, DELETE);
		
		excluirIndicador.addProperty("id", id);
				
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(excluirIndicador);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL, TIMEOUT);
		
		try {
			http.call("urn:" + DELETE, envelope);
			
			SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
			
			return Boolean.parseBoolean(resposta.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	public Indicator buscarIndicadorId (Integer id) {
		Indicator indicator = null;
		
		SoapObject buscarIndicadorId = new SoapObject (NAMESPACE, SEARCH_ID);
		buscarIndicadorId.addProperty("id", id);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarIndicadorId);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL, TIMEOUT);
		
		try {
			http.call("urn:" + SEARCH_ID, envelope);
			
			SoapObject resposta = (SoapObject) envelope.getResponse();
			
			indicator = new Indicator();
			
			indicator.setId(Integer.parseInt(resposta.getProperty("id").toString()));
			indicator.setTypeId(Integer.parseInt(resposta.getProperty("idTipo").toString()));
			indicator.setUserId(Integer.parseInt(resposta.getProperty("idUsuario").toString()));
			indicator.setMeasure1(Double.parseDouble(resposta.getProperty("medida1").toString()));
			indicator.setMeasure2(Double.parseDouble(resposta.getProperty("medida2").toString()));
			indicator.setMeasUnit(resposta.getProperty("unidade").toString());
			indicator.setStrDate(resposta.getProperty("data").toString());
			indicator.setStrTime(resposta.getProperty("hora").toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return indicator;
	}
	
	public ArrayList<Indicator> selectIndicatorTypes(Integer idUsuario, Integer idTipo) {
		Indicator indicator;

		ArrayList<Indicator> indicadores = new ArrayList<>();
		
		SoapObject buscarIndicadoresTipo = new SoapObject (NAMESPACE, SEARCH_ALL_TYPES);
		buscarIndicadoresTipo.addProperty("idUsuario", idUsuario);
		buscarIndicadoresTipo.addProperty("idTipo", idTipo);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarIndicadoresTipo);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL, TIMEOUT);
		
		try {
			http.call("urn:" + SEARCH_ALL_TYPES, envelope);
			
			Object resposta = envelope.getResponse();
			
			if (resposta instanceof Vector) {
				Vector<SoapObject> vectorResposta = (Vector<SoapObject>) resposta;

				for (SoapObject soapObject1 : vectorResposta) {
					
					indicator = new Indicator();
					
					indicator.setId(Integer.parseInt(soapObject1.getProperty("id").toString()));
					indicator.setTypeId(Integer.parseInt(soapObject1.getProperty("idTipo").toString()));
					indicator.setUserId(Integer.parseInt(soapObject1.getProperty("idUsuario").toString()));
					indicator.setMeasure1(Double.parseDouble(soapObject1.getProperty("medida1").toString()));
					indicator.setMeasure2(Double.parseDouble(soapObject1.getProperty("medida2").toString()));
					indicator.setStrDate(soapObject1.getProperty("data").toString());
					indicator.setStrTime(soapObject1.getProperty("hora").toString());
					
					indicadores.add(indicator);
				}
			}
			else {
                if (resposta instanceof SoapObject) {
                    SoapObject soapObject;
                    Vector<SoapObject> vectorResposta;

                    vectorResposta = new Vector<>();

                    soapObject = (SoapObject) resposta;
                    vectorResposta.add(soapObject);

                    for (SoapObject soapObject2 : vectorResposta) {

                        indicator = new Indicator();

                        indicator.setId(Integer.parseInt(soapObject2.getProperty("id").toString()));
                        indicator.setTypeId(Integer.parseInt(soapObject2.getProperty("idTipo").toString()));
                        indicator.setUserId(Integer.parseInt(soapObject2.getProperty("idUsuario").toString()));
                        indicator.setMeasure1(Double.parseDouble(soapObject2.getProperty("medida1").toString()));
                        indicator.setMeasure2(Double.parseDouble(soapObject2.getProperty("medida2").toString()));
                        indicator.setStrDate(soapObject2.getProperty("data").toString());
                        indicator.setStrTime(soapObject2.getProperty("hora").toString());

                        indicadores.add(indicator);
                    }
                }
            }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return indicadores;
	}
	
	public Indicator buscarIndicadorTipo (Integer idUsuario, Integer idTipo, Integer limite) {
		Indicator indicator = null;
		
		SoapObject buscarIndicadorTipo = new SoapObject (NAMESPACE, SEARCH_TYPE);
		buscarIndicadorTipo.addProperty("idUsuario", idUsuario);
		buscarIndicadorTipo.addProperty("idTipo", idTipo);
		buscarIndicadorTipo.addProperty("limite", limite);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarIndicadorTipo);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL, TIMEOUT);
		
		try {
			http.call("urn:" + SEARCH_TYPE, envelope);
			
			SoapObject resposta = (SoapObject) envelope.getResponse();
			
			indicator = new Indicator();
			
			indicator.setId(Integer.parseInt(resposta.getProperty("id").toString()));
			indicator.setTypeId(Integer.parseInt(resposta.getProperty("idTipo").toString()));
			indicator.setUserId(Integer.parseInt(resposta.getProperty("idUsuario").toString()));
			indicator.setMeasure1(Double.parseDouble(resposta.getProperty("medida1").toString()));
			indicator.setMeasure2(Double.parseDouble(resposta.getProperty("medida2").toString()));
			indicator.setMeasUnit(resposta.getProperty("unidade").toString());
			indicator.setStrDate(resposta.getProperty("data").toString());
			indicator.setStrTime(resposta.getProperty("hora").toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return indicator;
	}
	
	public ArrayList<Indicator> buscarIndicadoresPeriodoTipo (Integer idUsuario, Integer idTipo, String periodo, String dataAtual, Integer difData) {
		Indicator indicator = null;
		
		ArrayList<Indicator> indicadores = new ArrayList<>();
		
		SoapObject buscarIndicadoresPeriodoTipo = new SoapObject (NAMESPACE, SEARCH_BY_PERIOD_TYPE);
		buscarIndicadoresPeriodoTipo.addProperty("idUsuario", idUsuario);
		buscarIndicadoresPeriodoTipo.addProperty("idTipo", idTipo);
		buscarIndicadoresPeriodoTipo.addProperty("periodo", periodo);
		buscarIndicadoresPeriodoTipo.addProperty("dataAtual", dataAtual);
		buscarIndicadoresPeriodoTipo.addProperty("difData", difData);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarIndicadoresPeriodoTipo);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			http.call("urn:" + SEARCH_BY_PERIOD_TYPE, envelope);
			
			Object resposta = envelope.getResponse();
			
			if (resposta instanceof Vector) {
				@SuppressWarnings("unchecked")
				Vector<SoapObject> vectorResposta = (Vector<SoapObject>) resposta;
				
				for (SoapObject soapObject1 : vectorResposta) {
					
					indicator = new Indicator();
					
					indicator.setId(Integer.parseInt(soapObject1.getProperty("id").toString()));
					indicator.setTypeId(Integer.parseInt(soapObject1.getProperty("idTipo").toString()));
					indicator.setUserId(Integer.parseInt(soapObject1.getProperty("idUsuario").toString()));
					indicator.setMeasure1(Double.parseDouble(soapObject1.getProperty("medida1").toString()));
					indicator.setMeasure2(Double.parseDouble(soapObject1.getProperty("medida2").toString()));
					indicator.setMeasUnit(soapObject1.getProperty("unidade").toString());
					indicator.setStrDate(soapObject1.getProperty("data").toString());
					indicator.setStrTime(soapObject1.getProperty("hora").toString());
					
					indicadores.add(indicator);
				}
			}
			else if (resposta instanceof SoapObject) {
				SoapObject soapObject = (SoapObject) resposta;
				Vector<SoapObject> vectorResposta = new Vector<>();
				vectorResposta.add(soapObject);
				
				for (SoapObject soapObject2 : vectorResposta) {
					
					indicator = new Indicator();
					
					indicator.setId(Integer.parseInt(soapObject2.getProperty("id").toString()));
					indicator.setTypeId(Integer.parseInt(soapObject2.getProperty("idTipo").toString()));
					indicator.setUserId(Integer.parseInt(soapObject2.getProperty("idUsuario").toString()));
					indicator.setMeasure1(Double.parseDouble(soapObject2.getProperty("medida1").toString()));
					indicator.setMeasure2(Double.parseDouble(soapObject2.getProperty("medida2").toString()));
					indicator.setMeasUnit(soapObject2.getProperty("unidade").toString());
					indicator.setStrDate(soapObject2.getProperty("data").toString());
					indicator.setStrTime(soapObject2.getProperty("hora").toString());
					
					indicadores.add(indicator);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return indicadores;
	}
	
	public Double buscarMedia1Periodo (Integer idTipo, Integer idUsuario, String periodo, String dataAtual, Integer difData) {
		
		Double media = null;
		
		SoapObject buscarMediaPeriodo = new SoapObject (NAMESPACE, SEARCH_AVG_PERIOD_1);
		buscarMediaPeriodo.addProperty("idTipo", idTipo);
		buscarMediaPeriodo.addProperty("idUsuario", idUsuario);
		buscarMediaPeriodo.addProperty("periodo", periodo);
		buscarMediaPeriodo.addProperty("dataAtual", dataAtual);
		buscarMediaPeriodo.addProperty("difData", difData);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarMediaPeriodo);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL, TIMEOUT);
		
		try {
			http.call("urn:" + SEARCH_AVG_PERIOD_1, envelope);
			
			SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
			
			media = Double.parseDouble(resposta.toString());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return media;
	}
	
	public Double buscarMedia2Periodo (Integer idTipo, Integer idUsuario, String periodo, String dataAtual, Integer difData) {
		
		Double media = null;
		
		SoapObject buscarMedia2Periodo = new SoapObject (NAMESPACE, SEARCH_AVG_PERIOD_2);
		buscarMedia2Periodo.addProperty("idTipo", idTipo);
		buscarMedia2Periodo.addProperty("idUsuario", idUsuario);
		buscarMedia2Periodo.addProperty("periodo", periodo);
		buscarMedia2Periodo.addProperty("dataAtual", dataAtual);
		buscarMedia2Periodo.addProperty("difData", difData);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarMedia2Periodo);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL, TIMEOUT);
		
		try {
			http.call("urn:" + SEARCH_AVG_PERIOD_2, envelope);
			
			SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
			
			media = Double.parseDouble(resposta.toString());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return media;
	}
	
}

