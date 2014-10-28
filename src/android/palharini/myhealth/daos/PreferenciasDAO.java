package android.palharini.myhealth.daos;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.palharini.myhealth.entidades.Preferencias;

public class PreferenciasDAO {
	
	private static final String classeWS = "PreferenciasDAO";
	static ConectaWS conexao = new ConectaWS();
	
	private static final String URL = conexao.getURL() + classeWS + "?wsdl";
	private static final String NAMESPACE = conexao.getNamespace();
	private static final int TIMEOUT = conexao.getTimeout();
	
	private static final String CADASTRAR = "cadastrarPreferencias";
	private static final String ATUALIZAR = "atualizarPreferencias";
	private static final String BUSCAR = "buscarPreferenciasPorId";

	public boolean cadastrarPreferencias (Preferencias preferencias) {
		
		SoapObject cadastrarPreferencias = new SoapObject(NAMESPACE, CADASTRAR);
		
		SoapObject prefs = new SoapObject(NAMESPACE, "preferencias");
		
		prefs.addProperty("idUsuario", preferencias.getIdUsuario());
		prefs.addProperty("lembretePeso", preferencias.isLembretePeso());
		prefs.addProperty("horaLembretePeso", preferencias.getHoraLembretePeso());
		prefs.addProperty("lembreteBPM", preferencias.isLembreteBPM());
		prefs.addProperty("horaLembreteBPM", preferencias.getHoraLembreteBPM());
		
		cadastrarPreferencias.addSoapObject(prefs);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(cadastrarPreferencias);
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
		
	public boolean atualizarPreferencias(Preferencias preferencias){
		
		SoapObject atualizarPreferencias = new SoapObject(NAMESPACE, ATUALIZAR);
		
		SoapObject prefs = new SoapObject(NAMESPACE, "preferencias");
		
		prefs.addProperty("idUsuario", preferencias.getIdUsuario());
		prefs.addProperty("lembretePeso", preferencias.isLembretePeso());
		prefs.addProperty("horaLembretePeso", preferencias.getHoraLembretePeso());
		prefs.addProperty("lembreteBPM", preferencias.isLembreteBPM());
		prefs.addProperty("horaLembreteBPM", preferencias.getHoraLembreteBPM());
		
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

	public Preferencias buscarPreferenciasPorId(int id){
		Preferencias prefs = null;
		
		SoapObject buscarPreferencias = new SoapObject (NAMESPACE, BUSCAR);
		buscarPreferencias.addProperty("id", id);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarPreferencias);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL, TIMEOUT);
		
		try {
			http.call("urn:" + BUSCAR, envelope);
			
			SoapObject resposta = (SoapObject) envelope.getResponse();
			
			prefs = new Preferencias();
			
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