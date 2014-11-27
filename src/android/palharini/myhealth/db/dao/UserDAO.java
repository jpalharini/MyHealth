package android.palharini.myhealth.db.dao;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.palharini.myhealth.db.ConnectWS;
import android.palharini.myhealth.db.MarshalDouble;
import android.palharini.myhealth.db.entities.User;

public class UserDAO {
	
	private static final String classeWS = "UsuarioDAO?wsdl";
	private static final ConnectWS conexao = new ConnectWS();
	
	private static final String URL = conexao.getURL() + classeWS;
	private static final String NAMESPACE = conexao.getNamespace();
	private static final int TIMEOUT = conexao.getTimeout();
	
	private static final String CADASTRAR = "cadastrarUsuario";
	private static final String ATUALIZAR = "atualizarUsuario";
	private static final String ATUALIZAR_ALVO_BPM = "atualizarAlvoBPM";
	private static final String BUSCAR = "buscarUsuario";
	private static final String BUSCAR_EMAIL = "buscarUsuarioEmail";
	private static final String BUSCAR_IDADE = "buscarIdadeUsuario";

	public boolean cadastrarUsuario (User user) {
		
		SoapObject cadastrarUsuario = new SoapObject(NAMESPACE, CADASTRAR);
		
		SoapObject usr = new SoapObject(NAMESPACE, "usuario");
		
		usr.addProperty("id", user.getId());
		usr.addProperty("email", user.getEmail());
		usr.addProperty("senha", user.getSenha());
		usr.addProperty("nome", user.getNome());
		usr.addProperty("dataNascimento", user.getDataNascimento());
		
		cadastrarUsuario.addSoapObject(usr);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(cadastrarUsuario);
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
		
	public boolean atualizarUsuario(User user){
		
		SoapObject atualizarUsuario = new SoapObject(NAMESPACE, ATUALIZAR);
		
		SoapObject usr = new SoapObject(NAMESPACE, "usuario");
		
		usr.addProperty("id", user.getId());
		usr.addProperty("email", user.getEmail());
		usr.addProperty("senha", user.getSenha());
		usr.addProperty("nome", user.getNome());
		usr.addProperty("dataNascimento", user.getDataNascimento());
		usr.addProperty("alvoBPM", user.getAlvoBPM());
		
		atualizarUsuario.addSoapObject(usr);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(atualizarUsuario);
		envelope.implicitTypes = true;
		MarshalDouble md = new MarshalDouble();
		md.register(envelope);
		
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
	
	public boolean atualizarAlvoBPM (int id, int alvoBPM) {
		
		SoapObject atualizarUsuario = new SoapObject(NAMESPACE, ATUALIZAR_ALVO_BPM);
		
		SoapObject usr = new SoapObject(NAMESPACE, "usuario");
		
		usr.addProperty("id", id);
		usr.addProperty("alvoBPM", alvoBPM);
		
		atualizarUsuario.addSoapObject(usr);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(atualizarUsuario);
		envelope.implicitTypes = true;
		MarshalDouble md = new MarshalDouble();
		md.register(envelope);
		
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

	public User buscarUsuario(int id){
		User dados = null;
		
		SoapObject buscarUsuario = new SoapObject (NAMESPACE, BUSCAR);
		buscarUsuario.addProperty("id", id);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarUsuario);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL, TIMEOUT);
		
		try {
			http.call("urn:" + BUSCAR, envelope);
			
			SoapObject resposta = (SoapObject) envelope.getResponse();
			
			dados = new User();
			
			dados.setId(Integer.parseInt(resposta.getProperty("id").toString()));
			dados.setEmail(resposta.getProperty("email").toString());
			dados.setNome(resposta.getProperty("nome").toString());
			dados.setDataNascimento(resposta.getProperty("dataNascimento").toString());
			dados.setAlvoBPM(Integer.parseInt(resposta.getProperty("alvoBPM").toString()));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dados;
	}
	
	public User buscarUsuarioEmail(String email){
		User usr = null;
		
		SoapObject buscarUsuarioEmail = new SoapObject (NAMESPACE, BUSCAR_EMAIL);
		buscarUsuarioEmail.addProperty("email", email);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarUsuarioEmail);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL, TIMEOUT);
		
		try {
			http.call("urn:" + BUSCAR_EMAIL, envelope);
			
			SoapObject resposta = (SoapObject) envelope.getResponse();
			
			usr = new User();
			
			usr.setId(Integer.parseInt(resposta.getProperty("id").toString()));
			usr.setEmail(resposta.getProperty("email").toString());
			usr.setSenha(resposta.getProperty("senha").toString());
			usr.setNome(resposta.getProperty("nome").toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return usr;
	}
	
	public Integer buscarIdadeUsuario (Integer id){
		Integer idade = null;
		
		SoapObject buscarIdadeUsuario = new SoapObject (NAMESPACE, BUSCAR_IDADE);
		buscarIdadeUsuario.addProperty("id", id);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.setOutputSoapObject(buscarIdadeUsuario);
		envelope.implicitTypes = true;
		
		HttpTransportSE http = new HttpTransportSE(URL, TIMEOUT);
		
		try {
			http.call("urn:" + BUSCAR_IDADE, envelope);
			
			SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
			
			idade = Integer.parseInt(resposta.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return idade;
	}
	
}
