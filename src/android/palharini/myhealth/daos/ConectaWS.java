package android.palharini.myhealth.daos;

public class ConectaWS {

	private static final String URL = "http://172.20.10.4:8080/MyHealthWS/services/";
	private static final String NAMESPACE = "http://dao.ws.myhealth.palharini.android";
	private static final int TIMEOUT = 3000;
	
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
