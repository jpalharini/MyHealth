package android.palharini.myhealth.entidades;

public class Indicador {

	private int id;
	private int idTipo;
	private int idUsuario;
	private double medida;
	private String unidade;
	private String timestamp;
	
	public Indicador(){}

	public Indicador(int id, int idTipo, int idUsuario, double medida,
			String unidade, String timestamp) {
		this.id = id;
		this.idTipo = idTipo;
		this.idUsuario = idUsuario;
		this.medida = medida;
		this.unidade = unidade;
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(int idTipo) {
		this.idTipo = idTipo;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public double getMedida() {
		return medida;
	}

	public void setMedida(double medida) {
		this.medida = medida;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}