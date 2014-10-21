package android.palharini.myhealth.daos;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.palharini.myhealth.entidades.Indicador;

public class IndicadorDAO {
	
	ConectaWS conexao = new ConectaWS();
	
	private final String URL = conexao.getUrl("IndicadorDAO");
	private final String NAMESPACE = conexao.getNamespace();
	
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

	public Indicador buscarIndicadorPeriodo (int idUsuario, String periodo, int data){
		Indicador indicador = null;
		
		SoapObject buscarIndicadorPeriodo = new SoapObject (NAMESPACE, BUSCAR_PERIODO);
		buscarIndicadorPeriodo.addProperty("idUsuario", idUsuario);
		buscarIndicadorPeriodo.addProperty("periodo", periodo);
		buscarIndicadorPeriodo.addProperty("data", data);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarIndicadorPeriodo);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			http.call("urn:" + BUSCAR_PERIODO, envelope);
			
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
	
	public Indicador buscarIndicadorTipo (int idUsuario, int idTipo, int limite) {
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
	
	public Indicador buscarIndicadorPeriodoTipo (int idUsuario, int idTipo, String periodo, int data, int limite) {
		Indicador indicador = null;
		
		SoapObject buscarIndicadorPeriodo = new SoapObject (NAMESPACE, BUSCAR_PERIODO_TIPO);
		buscarIndicadorPeriodo.addProperty("idUsuario", idUsuario);
		buscarIndicadorPeriodo.addProperty("idTipo", idTipo);
		buscarIndicadorPeriodo.addProperty("periodo", periodo);
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

