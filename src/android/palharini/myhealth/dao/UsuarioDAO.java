package android.palharini.myhealth.dao;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.palharini.myhealth.entity.Usuario;

public class UsuarioDAO {
	
	private static final String URL = "http://192.168.1.100:8080/MyHealthWS/services/UsuarioDAO?wsdl";
	private static final String NAMESPACE = "http://ws.myhealth.palharini.android";
	
	private static final String CADASTRAR = "cadastrarUsuario";
	private static final String ATUALIZAR = "atualizarUsuario";
	private static final String BUSCAR = "buscarUsuario";
	private static final String BUSCAR_LOGIN = "buscarUsuarioLogin";

	public boolean cadastrarUsuario (Usuario usuario) {
		
		SoapObject cadastrarUsuario = new SoapObject(NAMESPACE, CADASTRAR);
		
		SoapObject usr = new SoapObject(NAMESPACE, "usuario");
		
		usr.addProperty("id", usuario.getId());
		usr.addProperty("email", usuario.getEmail());
		usr.addProperty("senha", usuario.getSenha());
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
		
	public boolean atualizarUsuario(Usuario usuario){
		
		SoapObject atualizarUsuario = new SoapObject(NAMESPACE, ATUALIZAR);
		
		SoapObject usr = new SoapObject(NAMESPACE, "usuario");
		
		usr.addProperty("id", usuario.getId());
		usr.addProperty("email", usuario.getEmail());
		usr.addProperty("senha", usuario.getSenha());
		usr.addProperty("nome", usuario.getNome());
		usr.addProperty("dataNascimento", usuario.getDataNascimento());
		usr.addProperty("altura", usuario.getAltura());
		usr.addProperty("peso", usuario.getPeso());
		usr.addProperty("maxBPM", usuario.getMaxBPM());
		usr.addProperty("minBPM", usuario.getMinBPM());
		
		atualizarUsuario.addSoapObject(usr);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(atualizarUsuario);
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

	public Usuario buscarUsuario(int id){
		Usuario dados = null;
		
		SoapObject buscarUsuario = new SoapObject (NAMESPACE, BUSCAR);
		buscarUsuario.addProperty("id", id);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarUsuario);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			http.call("urn:" + BUSCAR, envelope);
			
			SoapObject resposta = (SoapObject) envelope.getResponse();
			
			dados = new Usuario();
			
			dados.setId(Integer.parseInt(resposta.getProperty("id").toString()));
			dados.setEmail(resposta.getProperty("email").toString());
			dados.setNome(resposta.getProperty("nome").toString());
			dados.setAltura(Double.parseDouble(resposta.getProperty("altura").toString()));
			dados.setPeso(Double.parseDouble(resposta.getProperty("peso").toString()));
			dados.setMaxBPM(Integer.parseInt(resposta.getProperty("maxBPM").toString()));
			dados.setMinBPM(Integer.parseInt(resposta.getProperty("minBPM").toString()));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dados;
	}
	
	public Usuario buscarUsuarioLogin(String email){
		Usuario dados = null;
		
		SoapObject buscarUsuarioLogin = new SoapObject (NAMESPACE, BUSCAR_LOGIN);
		buscarUsuarioLogin.addProperty("email", email);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarUsuarioLogin);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL);
		
		try {
			http.call("urn:" + BUSCAR_LOGIN, envelope);
			
			SoapObject resposta = (SoapObject) envelope.getResponse();
			
			dados = new Usuario();
			
			dados.setId(Integer.parseInt(resposta.getProperty("id").toString()));
			dados.setEmail(resposta.getProperty("email").toString());
			dados.setSenha(resposta.getProperty("senha").toString());
			dados.setNome(resposta.getProperty("nome").toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dados;
	}
}
