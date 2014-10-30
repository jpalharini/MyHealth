package android.palharini.myhealth.entidades;

public class Indicador {

	private int id;
	private int idTipo;
	private int idUsuario;
	private double medida;
	private String unidade;
	private String data;
	private String hora;
	
	public Indicador(){}

	public Indicador(int id, int idTipo, int idUsuario, double medida,
			String unidade, String data, String hora) {
		this.id = id;
		this.idTipo = idTipo;
		this.idUsuario = idUsuario;
		this.medida = medida;
		this.unidade = unidade;
		this.data = data;
		this.hora = hora;
	}
	
	public Indicador(int id, int idTipo, double medida,
			String unidade) {
		this.id = id;
		this.idTipo = idTipo;
		this.medida = medida;
		this.unidade = unidade;
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	@Override
	public String toString() {
		return this.data + " " + this.hora;
	}
	
}