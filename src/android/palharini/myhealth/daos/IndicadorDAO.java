package android.palharini.myhealth.daos;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.palharini.myhealth.entidades.Indicador;

public class IndicadorDAO {

	private static final String URL = "http://192.168.129.222:8080/MyHealthWS/services/IndicadorDAO?wsdl";
	private static final String NAMESPACE = "http://dao.ws.myhealth.palharini.android";
	
	public static final String CADASTRAR = "cadastrarIndicador";
	public static final String ATUALIZAR = "atualizarIndicador";
	public static final String EXCLUIR = "excluirIndicador";
	public static final String BUSCAR_ANO = "buscarIndicadorAno";
	public static final String BUSCAR_MES = "buscarIndicadorMes";
	public static final String BUSCAR_DIA = "buscarIndicadorDia";
	
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

	public Indicador buscarIndicadorAno(int ano){
		Indicador indicador = null;
		
		SoapObject buscarIndicadorAno = new SoapObject (NAMESPACE, BUSCAR_ANO);
		buscarIndicadorAno.addProperty("ano", ano);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarIndicadorAno);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			http.call("urn:" + BUSCAR_ANO, envelope);
			
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
	
	public Indicador buscarIndicadorMes(int mes){
		Indicador indicador = null;
		
		SoapObject buscarIndicadorAno = new SoapObject (NAMESPACE, BUSCAR_MES);
		buscarIndicadorAno.addProperty("mes", mes);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarIndicadorAno);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			http.call("urn:" + BUSCAR_MES, envelope);
			
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
	
	public Indicador buscarIndicadorDia(int dia){
		Indicador indicador = null;
		
		SoapObject buscarIndicadorAno = new SoapObject (NAMESPACE, BUSCAR_DIA);
		buscarIndicadorAno.addProperty("dia", dia);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarIndicadorAno);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			http.call("urn:" + BUSCAR_DIA, envelope);
			
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

