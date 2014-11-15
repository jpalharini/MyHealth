package android.palharini.myhealth.dao;

public class ConectaWS {

	private static final String URL = "http://192.168.1.108:8080/MyHealthWS/services/";
	private static final String NAMESPACE = "http://dao.ws.myhealth.palharini.android";
	private static final int TIMEOUT = 20000;
	
	public String getURL() {
		return URL;
	}

	public String getNamespace() {
		return NAMESPACE;
	}

	public int getTimeout() {
		return TIMEOUT;
	}
	
}
