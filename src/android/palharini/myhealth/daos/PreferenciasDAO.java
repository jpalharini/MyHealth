package android.palharini.myhealth.daos;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.palharini.myhealth.entidades.Preferencias;

public class PreferenciasDAO {
	
	private static final String URL = "http://191.4.36.241:8080/MyHealthWS/services/PreferenciasDAO?wsdl";
	private static final String NAMESPACE = "http://dao.ws.myhealth.palharini.android";
	
	private static final String CADASTRAR = "cadastrarPreferencias";
	private static final String ATUALIZAR = "atualizarPreferencias";
	private static final String BUSCAR = "buscarPreferencias";

	public boolean cadastrarPreferencias (Preferencias preferencias) {
		
		SoapObject cadastrarPreferencias = new SoapObject(NAMESPACE, CADASTRAR);
		
		SoapObject prefs = new SoapObject(NAMESPACE, "preferencias");
		
		prefs.addProperty("idUsuario", preferencias.getIdUsuario());
		prefs.addProperty("lembretePeso", preferencias.isLembretePeso());
		prefs.addProperty("horaLembrete", preferencias.getHoraLembrete());
		prefs.addProperty("alvoBPM", preferencias.isAlvoBPM());
		prefs.addProperty("horaAcorda", preferencias.getHoraAcorda());
		
		cadastrarPreferencias.addSoapObject(prefs);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(cadastrarPreferencias);
		envelope.implicitTypes = true;
		MarshalDouble md = new MarshalDouble();
		md.register(envelope);
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
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
		
	public boolean atualizarPreferencias(Preferencias preferencias){
		
		SoapObject atualizarPreferencias = new SoapObject(NAMESPACE, ATUALIZAR);
		
		SoapObject prefs = new SoapObject(NAMESPACE, "preferencias");
		
		prefs.addProperty("idUsuario", preferencias.getIdUsuario());
		prefs.addProperty("lembretePeso", preferencias.isLembretePeso());
		prefs.addProperty("horaLembrete", preferencias.getHoraLembrete());
		prefs.addProperty("alvoBPM", preferencias.isAlvoBPM());
		prefs.addProperty("horaAcorda", preferencias.getHoraAcorda());
		
		atualizarPreferencias.addSoapObject(prefs);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(atualizarPreferencias);
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

	public Preferencias buscarPreferencias(int id){
		Preferencias prefs = null;
		
		SoapObject buscarPreferencias = new SoapObject (NAMESPACE, BUSCAR);
		buscarPreferencias.addProperty("id", id);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarPreferencias);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			http.call("urn:" + BUSCAR, envelope);
			
			SoapObject resposta = (SoapObject) envelope.getResponse();
			
			prefs = new Preferencias();
			
			prefs.setIdUsuario(Integer.parseInt(resposta.getProperty("idUsuario").toString()));
			prefs.setLembretePeso(Boolean.parseBoolean(resposta.getProperty("lembretePeso").toString()));
			prefs.setHoraLembrete(resposta.getProperty("horaLembrete").toString());
			prefs.setAlvoBPM(Boolean.parseBoolean(resposta.getProperty("alvoBPM").toString()));
			prefs.setHoraAcorda(resposta.getProperty("horaAcorda").toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return prefs;
	}
}