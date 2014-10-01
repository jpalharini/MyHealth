package android.palharini.myhealth.entity;

public class Indicador {

	private int id;
	private String tipo;
	private int medida;
	private String unidade;
	private String timestamp;
	
	public Indicador(){}
	
	public Indicador(int id, String tipo, int medida, String unidade,
			String timestamp) {
		super();
		this.id = id;
		this.tipo = tipo;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getMedida() {
		return medida;
	}

	public void setMedida(int medida) {
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
