package android.palharini.myhealth.daos;

public class ConectaWS {

	private static final String URL = "http://191.4.36.241:8080/MyHealthWS/services/";
	private static final String NAMESPACE = "http://dao.ws.myhealth.palharini.android";
	
	
	public String getURL() {
		return URL;
	}

	public String getNamespace() {
		return NAMESPACE;
	}
	
}
