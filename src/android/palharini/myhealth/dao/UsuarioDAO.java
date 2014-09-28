package android.palharini.myhealth.dao;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.palharini.myhealth.entity.Usuario;

public class UsuarioDAO {
	
	private static final String URL = "http://192.168.1.100:8080/MyHealthWS/services/UsuarioDAO?wsdl";
	private static final String NAMESPACE = "http://ws.myhealth.palharini.android";
	
	private static final String CADASTRAR = "cadastrarUsuario";
	private static final String ATUALIZAR = "atualizarUsuario";
	private static final String BUSCAR = "buscarUsuario";

	public boolean cadastrarUsuario (Usuario usuario) {
		
		SoapObject cadastrarUsuario = new SoapObject(NAMESPACE, CADASTRAR);
		
		SoapObject usr = new SoapObject(NAMESPACE, "usuario");
		
		usr.addProperty("id", usuario.getId());
		usr.addProperty("nome", usuario.getNome());
		usr.addProperty("dataNascimento", usuario.getDataNascimento());
		usr.addProperty("altura", usuario.getAltura());
		usr.addProperty("peso", usuario.getPeso());
		usr.addProperty("maxBPM", usuario.getMaxBPM());
		usr.addProperty("minBPM", usuario.getMinBPM());
		
		cadastrarUsuario.addSoapObject(usr);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(cadastrarUsuario);
		envelope.implicitTypes = true;
		
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
		
	public boolean atualizarUsuario(Usuario usuario){
		
		return true;
	}	

	public Usuario buscarUsuario(Usuario usuario){
		Usuario dados = null;
		
		return dados;
	}
	
}
