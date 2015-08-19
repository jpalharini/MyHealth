package android.palharini.myhealth.db.dao;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.palharini.myhealth.db.ConnectWS;
import android.palharini.myhealth.db.entities.Preferences;

public class PreferencesDAO {
	
	private static final String classeWS = "PreferenciasDAO?wsdl";
	private static final ConnectWS conexao = new ConnectWS();
	
	private static final String URL = conexao.getURL() + classeWS;
	private static final String NAMESPACE = conexao.getNamespace();
	private static final int TIMEOUT = conexao.getTimeout();
	
	private static final String CADASTRAR = "cadastrarPreferencias";
	private static final String ATUALIZAR = "atualizarPreferencias";
	private static final String BUSCAR = "buscarPreferencias";

	public boolean cadastrarPreferencias (Preferences preferences) {
		
		SoapObject cadastrarPreferencias = new SoapObject(NAMESPACE, CADASTRAR);
		
		SoapObject prefs = new SoapObject(NAMESPACE, "preferencias");
		
		prefs.addProperty("idUsuario", preferences.getIdUsuario());
		prefs.addProperty("lembretePeso", preferences.isLembretePeso());
		prefs.addProperty("horaLembretePeso", preferences.getHoraLembretePeso());
		prefs.addProperty("lembreteBPM", preferences.isLembreteBPM());
		prefs.addProperty("horaLembreteBPM", preferences.getHoraLembreteBPM());
		
		cadastrarPreferencias.addSoapObject(prefs);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(cadastrarPreferencias);
		envelope.implicitTypes = true;
		
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
		
	public boolean atualizarPreferencias(Preferences preferences){
		
		SoapObject atualizarPreferencias = new SoapObject(NAMESPACE, ATUALIZAR);
		
		SoapObject prefs = new SoapObject(NAMESPACE, "preferencias");
		
		prefs.addProperty("idUsuario", preferences.getIdUsuario());
		prefs.addProperty("lembretePeso", preferences.isLembretePeso());
		prefs.addProperty("horaLembretePeso", preferences.getHoraLembretePeso());
		prefs.addProperty("lembreteBPM", preferences.isLembreteBPM());
		prefs.addProperty("horaLembreteBPM", preferences.getHoraLembreteBPM());
		
		atualizarPreferencias.addSoapObject(prefs);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(atualizarPreferencias);
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

	public Preferences buscarPreferencias (int idUsuario){
		Preferences prefs = null;
		
		SoapObject buscarPreferencias = new SoapObject (NAMESPACE, BUSCAR);
		buscarPreferencias.addProperty("idUsuario", idUsuario);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarPreferencias);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL, TIMEOUT);
		
		try {
			http.call("urn:" + BUSCAR, envelope);
			
			SoapObject resposta = (SoapObject) envelope.getResponse();
			
			prefs = new Preferences();
			
			prefs.setLembretePeso(Boolean.parseBoolean(resposta.getProperty("lembretePeso").toString()));
			prefs.setHoraLembretePeso(resposta.getProperty("horaLembretePeso").toString());
			prefs.setLembreteBPM(Boolean.parseBoolean(resposta.getProperty("lembreteBPM").toString()));
			prefs.setHoraLembreteBPM(resposta.getProperty("horaLembreteBPM").toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return prefs;
	}
}