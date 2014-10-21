package android.palharini.myhealth.daos;

public class ConectaWS {

	private String URL = "http://191.4.36.241:8080/MyHealthWS/services/";
	private String NAMESPACE = "http://dao.ws.myhealth.palharini.android";
	
	public String getUrl(String nomeDAO) {
		String urlDAO = URL + nomeDAO + "?wsdl";
		return urlDAO;
	}
	public String getNamespace() {
		return NAMESPACE;
	}
	
}
