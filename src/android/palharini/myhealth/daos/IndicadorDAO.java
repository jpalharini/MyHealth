package android.palharini.myhealth.daos;

import java.util.ArrayList;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.palharini.myhealth.entidades.Indicador;

public class IndicadorDAO {
	
	private static final String classeWS = "IndicadorDAO?wsdl";
	private static final ConectaWS conexao = new ConectaWS();
	
	private static final String URL = conexao.getURL() + classeWS;
	private static final String NAMESPACE = conexao.getNamespace();
	private static final int TIMEOUT = conexao.getTimeout();
	
	public static final String CADASTRAR = "cadastrarIndicador";
	public static final String ATUALIZAR = "atualizarIndicador";
	public static final String EXCLUIR = "excluirIndicador";
	public static final String BUSCAR_PERIODO_TODOS = "buscarIndicadoresPeriodo";
	public static final String BUSCAR_TIPO = "buscarIndicadorTipo";
	public static final String BUSCAR_TIPO_TODOS = "buscarIndicadoresTipo";
	public static final String BUSCAR_PERIODO_TIPO = "buscarIndicadoresPeriodoTipo";
	public static final String BUSCAR_MEDIA = "buscarMediaPeriodo";
	
	
	public boolean cadastrarIndicador (Indicador indicador) {
		
		SoapObject cadastrarIndicador = new SoapObject(NAMESPACE, CADASTRAR);
		
		SoapObject ind = new SoapObject(NAMESPACE, "indicador");
		
		ind.addProperty("id", indicador.getId());
		ind.addProperty("idTipo", indicador.getIdTipo());
		ind.addProperty("idUsuario", indicador.getIdUsuario());
		ind.addProperty("medida", indicador.getMedida());
		ind.addProperty("unidade", indicador.getUnidade());
		ind.addProperty("data", indicador.getData());
		ind.addProperty("hora", indicador.getHora());

		cadastrarIndicador.addSoapObject(ind);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(cadastrarIndicador);
		envelope.implicitTypes = true;
		MarshalDouble md = new MarshalDouble();
		md.register(envelope);
		
		HttpTransportSE http = new HttpTransportSE(URL, TIMEOUT);
		
		try {
			http.call("urn:" + CADASTRAR, envelope);
			
			SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
			
			return Boolean.parseBoolean(resposta.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean atualizarIndicador(Indicador indicador){
		
		SoapObject atualizarIndicador = new SoapObject(NAMESPACE, ATUALIZAR);
		
		SoapObject ind = new SoapObject(NAMESPACE, "indicador");
		
		ind.addProperty("id", indicador.getId());
		ind.addProperty("idTipo", indicador.getIdTipo());
		ind.addProperty("medida", indicador.getMedida());
		ind.addProperty("unidade", indicador.getUnidade());
		
		atualizarIndicador.addSoapObject(ind);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(atualizarIndicador);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL, TIMEOUT);
		
		try {
			http.call("urn:" + ATUALIZAR, envelope);
			
			SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
			
			return Boolean.parseBoolean(resposta.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}	

	public ArrayList<Indicador> buscarIndicadoresPeriodo (int idUsuario, String periodo, int difData, int data){
		
		Indicador indicador = null;
		
		ArrayList<Indicador> indicadores = new ArrayList<Indicador>();
		
		SoapObject buscarIndicadoresPeriodo = new SoapObject (NAMESPACE, BUSCAR_PERIODO_TODOS);
		buscarIndicadoresPeriodo.addProperty("idUsuario", idUsuario);
		buscarIndicadoresPeriodo.addProperty("periodo", periodo);
		buscarIndicadoresPeriodo.addProperty("difData", difData);
		buscarIndicadoresPeriodo.addProperty("data", data);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarIndicadoresPeriodo);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL, TIMEOUT);
		
		try {
			http.call("urn:" + BUSCAR_PERIODO_TODOS, envelope);
			
			Object resposta = envelope.getResponse();
			
			if (resposta instanceof Vector) {
				@SuppressWarnings("unchecked")
				Vector<SoapObject> vectorResposta = (Vector<SoapObject>) resposta;
				
				for (SoapObject soapObject1 : vectorResposta) {
					
					indicador = new Indicador();
					
					indicador.setId(Integer.parseInt(soapObject1.getProperty("id").toString()));
					indicador.setIdTipo(Integer.parseInt(soapObject1.getProperty("idTipo").toString()));
					indicador.setIdUsuario(Integer.parseInt(soapObject1.getProperty("idUsuario").toString()));
					indicador.setMedida(Double.parseDouble(soapObject1.getProperty("medida").toString()));
					indicador.setUnidade(soapObject1.getProperty("unidade").toString());
					indicador.setData(soapObject1.getProperty("data").toString());
					indicador.setHora(soapObject1.getProperty("hora").toString());
					
					indicadores.add(indicador);
				}
			}
			else if (resposta instanceof SoapObject) {
				SoapObject soapObject = (SoapObject) resposta;
				Vector<SoapObject> vectorResposta = new Vector<SoapObject>();
				vectorResposta.add(soapObject);
				
				for (SoapObject soapObject2 : vectorResposta) {
					
					indicador = new Indicador();
					
					indicador.setId(Integer.parseInt(soapObject2.getProperty("id").toString()));
					indicador.setIdTipo(Integer.parseInt(soapObject2.getProperty("idTipo").toString()));
					indicador.setIdUsuario(Integer.parseInt(soapObject2.getProperty("idUsuario").toString()));
					indicador.setMedida(Double.parseDouble(soapObject2.getProperty("medida").toString()));
					indicador.setUnidade(soapObject2.getProperty("unidade").toString());
					indicador.setData(soapObject2.getProperty("data").toString());
					indicador.setHora(soapObject2.getProperty("hora").toString());
					
					indicadores.add(indicador);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return indicadores;
	}
	
	public ArrayList<Indicador> buscarIndicadoresTipo (int idUsuario, int idTipo) {
		Indicador indicador = null;
		
		ArrayList<Indicador> indicadores = new ArrayList<Indicador>();
		
		SoapObject buscarIndicadoresTipo = new SoapObject (NAMESPACE, BUSCAR_TIPO_TODOS);
		buscarIndicadoresTipo.addProperty("idUsuario", idUsuario);
		buscarIndicadoresTipo.addProperty("idTipo", idTipo);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarIndicadoresTipo);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL, TIMEOUT);
		
		try {
			http.call("urn:" + BUSCAR_TIPO_TODOS, envelope);
			
			Object resposta = envelope.getResponse();
			
			if (resposta instanceof Vector) {
				@SuppressWarnings("unchecked")
				Vector<SoapObject> vectorResposta = (Vector<SoapObject>) resposta;
				
				for (SoapObject soapObject1 : vectorResposta) {
					
					indicador = new Indicador();
					
					indicador.setId(Integer.parseInt(soapObject1.getProperty("id").toString()));
					indicador.setIdTipo(Integer.parseInt(soapObject1.getProperty("idTipo").toString()));
					indicador.setIdUsuario(Integer.parseInt(soapObject1.getProperty("idUsuario").toString()));
					indicador.setMedida(Double.parseDouble(soapObject1.getProperty("medida").toString()));
					indicador.setUnidade(soapObject1.getProperty("unidade").toString());
					indicador.setData(soapObject1.getProperty("data").toString());
					indicador.setHora(soapObject1.getProperty("hora").toString());
					
					indicadores.add(indicador);
				}
			}
			else if (resposta instanceof SoapObject) {
				SoapObject soapObject = (SoapObject) resposta;
				Vector<SoapObject> vectorResposta = new Vector<SoapObject>();
				vectorResposta.add(soapObject);
				
				for (SoapObject soapObject2 : vectorResposta) {
					
					indicador = new Indicador();
					
					indicador.setId(Integer.parseInt(soapObject2.getProperty("id").toString()));
					indicador.setIdTipo(Integer.parseInt(soapObject2.getProperty("idTipo").toString()));
					indicador.setIdUsuario(Integer.parseInt(soapObject2.getProperty("idUsuario").toString()));
					indicador.setMedida(Double.parseDouble(soapObject2.getProperty("medida").toString()));
					indicador.setUnidade(soapObject2.getProperty("unidade").toString());
					indicador.setData(soapObject2.getProperty("data").toString());
					indicador.setHora(soapObject2.getProperty("hora").toString());
					
					indicadores.add(indicador);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return indicadores;
	}
	
	public Indicador buscarIndicadorTipo (int idUsuario, int idTipo, int limite) {
		Indicador indicador = null;
		
		SoapObject buscarIndicadorTipo = new SoapObject (NAMESPACE, BUSCAR_TIPO);
		buscarIndicadorTipo.addProperty("idUsuario", idUsuario);
		buscarIndicadorTipo.addProperty("idTipo", idTipo);
		buscarIndicadorTipo.addProperty("limite", limite);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarIndicadorTipo);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL, TIMEOUT);
		
		try {
			http.call("urn:" + BUSCAR_TIPO, envelope);
			
			SoapObject resposta = (SoapObject) envelope.getResponse();
			
			indicador = new Indicador();
			
			indicador.setId(Integer.parseInt(resposta.getProperty("id").toString()));
			indicador.setIdTipo(Integer.parseInt(resposta.getProperty("idTipo").toString()));
			indicador.setIdUsuario(Integer.parseInt(resposta.getProperty("idUsuario").toString()));
			indicador.setMedida(Double.parseDouble(resposta.getProperty("medida").toString()));
			indicador.setUnidade(resposta.getProperty("unidade").toString());
			indicador.setData(resposta.getProperty("data").toString());
			indicador.setHora(resposta.getProperty("hora").toString());		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return indicador;
	}
	
	public ArrayList<Indicador> buscarIndicadoresPeriodoTipo (int idUsuario, int idTipo, String periodo, String dataAtual, int difData) {
		Indicador indicador = null;
		
		ArrayList<Indicador> indicadores = new ArrayList<Indicador>();
		
		SoapObject buscarIndicadoresPeriodoTipo = new SoapObject (NAMESPACE, BUSCAR_PERIODO_TIPO);
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
			http.call("urn:" + BUSCAR_PERIODO_TIPO, envelope);
			
			Object resposta = envelope.getResponse();
			
			if (resposta instanceof Vector) {
				@SuppressWarnings("unchecked")
				Vector<SoapObject> vectorResposta = (Vector<SoapObject>) resposta;
				
				for (SoapObject soapObject1 : vectorResposta) {
					
					indicador = new Indicador();
					
					indicador.setId(Integer.parseInt(soapObject1.getProperty("id").toString()));
					indicador.setIdTipo(Integer.parseInt(soapObject1.getProperty("idTipo").toString()));
					indicador.setIdUsuario(Integer.parseInt(soapObject1.getProperty("idUsuario").toString()));
					indicador.setMedida(Double.parseDouble(soapObject1.getProperty("medida").toString()));
					indicador.setUnidade(soapObject1.getProperty("unidade").toString());
					indicador.setData(soapObject1.getProperty("data").toString());
					indicador.setHora(soapObject1.getProperty("hora").toString());
					
					indicadores.add(indicador);
				}
			}
			else if (resposta instanceof SoapObject) {
				SoapObject soapObject = (SoapObject) resposta;
				Vector<SoapObject> vectorResposta = new Vector<SoapObject>();
				vectorResposta.add(soapObject);
				
				for (SoapObject soapObject2 : vectorResposta) {
					
					indicador = new Indicador();
					
					indicador.setId(Integer.parseInt(soapObject2.getProperty("id").toString()));
					indicador.setIdTipo(Integer.parseInt(soapObject2.getProperty("idTipo").toString()));
					indicador.setIdUsuario(Integer.parseInt(soapObject2.getProperty("idUsuario").toString()));
					indicador.setMedida(Double.parseDouble(soapObject2.getProperty("medida").toString()));
					indicador.setUnidade(soapObject2.getProperty("unidade").toString());
					indicador.setData(soapObject2.getProperty("data").toString());
					indicador.setHora(soapObject2.getProperty("hora").toString());
					
					indicadores.add(indicador);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return indicadores;
	}
	
	public Double buscarMediaPeriodo (int idUsuario, int idTipo, String periodo, String dataAtual, int difData) {
		
		Double media = null;
		
		SoapObject buscarMediaPeriodo = new SoapObject (NAMESPACE, BUSCAR_MEDIA);
		buscarMediaPeriodo.addProperty("idUsuario", idUsuario);
		buscarMediaPeriodo.addProperty("idTipo", idTipo);
		buscarMediaPeriodo.addProperty("periodo", periodo);
		buscarMediaPeriodo.addProperty("dataAtual", dataAtual);
		buscarMediaPeriodo.addProperty("difData", difData);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarMediaPeriodo);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL, TIMEOUT);
		
		try {
			http.call("urn:" + BUSCAR_MEDIA, envelope);
			
			SoapObject resposta = (SoapObject) envelope.getResponse();
			
			media = Double.parseDouble(resposta.getProperty("media").toString());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return media;
	}
	
}

