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
	
	private static final String classeWS = "IndicadorDAO" + "?wsdl";
	static ConectaWS conexao = new ConectaWS();
	
	private static final String URL = conexao.getURL() + classeWS;
	private static final String NAMESPACE = conexao.getNamespace();
	
	public static final String CADASTRAR = "cadastrarIndicador";
	public static final String ATUALIZAR = "atualizarIndicador";
	public static final String EXCLUIR = "excluirIndicador";
	public static final String BUSCAR_PERIODO = "buscarIndicadorPeriodo";
	public static final String BUSCAR_TIPO = "buscarIndicadorTipo";
	public static final String BUSCAR_PERIODO_TIPO = "buscarIndicadorPeriodoTipo";
	
	
	public boolean cadastrarIndicador (Indicador indicador) {
		
		SoapObject cadastrarIndicador = new SoapObject(NAMESPACE, CADASTRAR);
		
		SoapObject ind = new SoapObject(NAMESPACE, "indicador");
		
		ind.addProperty("id", indicador.getId());
		ind.addProperty("idTipo", indicador.getIdTipo());
		ind.addProperty("idUsuario", indicador.getIdUsuario());
		ind.addProperty("medida", indicador.getMedida());
		ind.addProperty("unidade", indicador.getUnidade());
		ind.addProperty("timestamp", indicador.getTimestamp());
		
		cadastrarIndicador.addSoapObject(ind);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(cadastrarIndicador);
		envelope.implicitTypes = true;
		MarshalDouble md = new MarshalDouble();
		md.register(envelope);
		
		HttpTransportSE http = new HttpTransportSE(URL, 10000);
		
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
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
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
		
		SoapObject buscarIndicadorPeriodo = new SoapObject (NAMESPACE, BUSCAR_PERIODO);
		buscarIndicadorPeriodo.addProperty("idUsuario", idUsuario);
		buscarIndicadorPeriodo.addProperty("periodo", periodo);
		buscarIndicadorPeriodo.addProperty("difData", difData);
		buscarIndicadorPeriodo.addProperty("data", data);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarIndicadorPeriodo);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			http.call("urn:" + BUSCAR_PERIODO, envelope);
			
			Vector<SoapObject> resposta = (Vector<SoapObject>) envelope.getResponse();
			
			for (SoapObject soapObject : resposta) {
				
				indicador = new Indicador();
				
				indicador.setId(Integer.parseInt(soapObject.getProperty("id").toString()));
				indicador.setIdTipo(Integer.parseInt(soapObject.getProperty("idTipo").toString()));
				indicador.setIdUsuario(Integer.parseInt(soapObject.getProperty("idUsuario").toString()));
				indicador.setMedida(Double.parseDouble(soapObject.getProperty("medida").toString()));
				indicador.setUnidade(soapObject.getProperty("unidade").toString());
				indicador.setTimestamp(soapObject.getProperty("timestamp").toString());
				
				indicadores.add(indicador);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return indicadores;
	}
	
	public Indicador buscarIndicadoresTipo (int idUsuario, int idTipo) {
		Indicador indicador = null;
		
		SoapObject buscarIndicadorPeriodo = new SoapObject (NAMESPACE, BUSCAR_TIPO);
		buscarIndicadorPeriodo.addProperty("idUsuario", idUsuario);
		buscarIndicadorPeriodo.addProperty("idTipo", idTipo);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarIndicadorPeriodo);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			http.call("urn:" + BUSCAR_TIPO, envelope);
			
			SoapObject resposta = (SoapObject) envelope.getResponse();
			
			indicador = new Indicador();
			
			indicador.setId(Integer.parseInt(resposta.getProperty("id").toString()));
			indicador.setIdTipo(Integer.parseInt(resposta.getProperty("idTipo").toString()));
			indicador.setIdUsuario(Integer.parseInt(resposta.getProperty("idUsuario").toString()));
			indicador.setMedida(Double.parseDouble(resposta.getProperty("medida").toString()));
			indicador.setUnidade(resposta.getProperty("unidade").toString());
			indicador.setTimestamp(resposta.getProperty("timestamp").toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return indicador;
	}
	
	public Indicador buscarIndicadoresTipo (int idUsuario, int idTipo, int limite) {
		Indicador indicador = null;
		
		SoapObject buscarIndicadorPeriodo = new SoapObject (NAMESPACE, BUSCAR_TIPO);
		buscarIndicadorPeriodo.addProperty("idUsuario", idUsuario);
		buscarIndicadorPeriodo.addProperty("idTipo", idTipo);
		buscarIndicadorPeriodo.addProperty("limite", limite);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarIndicadorPeriodo);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			http.call("urn:" + BUSCAR_TIPO, envelope);
			
			SoapObject resposta = (SoapObject) envelope.getResponse();
			
			indicador = new Indicador();
			
			indicador.setId(Integer.parseInt(resposta.getProperty("id").toString()));
			indicador.setIdTipo(Integer.parseInt(resposta.getProperty("idTipo").toString()));
			indicador.setIdUsuario(Integer.parseInt(resposta.getProperty("idUsuario").toString()));
			indicador.setMedida(Double.parseDouble(resposta.getProperty("medida").toString()));
			indicador.setUnidade(resposta.getProperty("unidade").toString());
			indicador.setTimestamp(resposta.getProperty("timestamp").toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return indicador;
	}
	
	public Indicador buscarIndicadoresPeriodoTipo (int idUsuario, int idTipo, String periodo, int difData, int data) {
		Indicador indicador = null;
		
		SoapObject buscarIndicadorPeriodo = new SoapObject (NAMESPACE, BUSCAR_PERIODO_TIPO);
		buscarIndicadorPeriodo.addProperty("idUsuario", idUsuario);
		buscarIndicadorPeriodo.addProperty("idTipo", idTipo);
		buscarIndicadorPeriodo.addProperty("periodo", periodo);
		buscarIndicadorPeriodo.addProperty("difData", difData);
		buscarIndicadorPeriodo.addProperty("data", data);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarIndicadorPeriodo);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			http.call("urn:" + BUSCAR_PERIODO_TIPO, envelope);
			
			SoapObject resposta = (SoapObject) envelope.getResponse();
			
			indicador = new Indicador();
			
			indicador.setId(Integer.parseInt(resposta.getProperty("id").toString()));
			indicador.setIdTipo(Integer.parseInt(resposta.getProperty("idTipo").toString()));
			indicador.setIdUsuario(Integer.parseInt(resposta.getProperty("idUsuario").toString()));
			indicador.setMedida(Double.parseDouble(resposta.getProperty("medida").toString()));
			indicador.setUnidade(resposta.getProperty("unidade").toString());
			indicador.setTimestamp(resposta.getProperty("timestamp").toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return indicador;
	}
	
	public Indicador buscarIndicadoresPeriodoTipo (int idUsuario, int idTipo, String periodo, int data, int difData, int limite) {
		Indicador indicador = null;
		
		SoapObject buscarIndicadorPeriodo = new SoapObject (NAMESPACE, BUSCAR_PERIODO_TIPO);
		buscarIndicadorPeriodo.addProperty("idUsuario", idUsuario);
		buscarIndicadorPeriodo.addProperty("idTipo", idTipo);
		buscarIndicadorPeriodo.addProperty("periodo", periodo);
		buscarIndicadorPeriodo.addProperty("difData", difData);
		buscarIndicadorPeriodo.addProperty("data", data);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarIndicadorPeriodo);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			http.call("urn:" + BUSCAR_PERIODO_TIPO, envelope);
			
			SoapObject resposta = (SoapObject) envelope.getResponse();
			
			indicador = new Indicador();
			
			indicador.setId(Integer.parseInt(resposta.getProperty("id").toString()));
			indicador.setIdTipo(Integer.parseInt(resposta.getProperty("idTipo").toString()));
			indicador.setIdUsuario(Integer.parseInt(resposta.getProperty("idUsuario").toString()));
			indicador.setMedida(Double.parseDouble(resposta.getProperty("medida").toString()));
			indicador.setUnidade(resposta.getProperty("unidade").toString());
			indicador.setTimestamp(resposta.getProperty("timestamp").toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return indicador;
	}
	
}

